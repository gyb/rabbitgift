package com.irelint.ttt.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.irelint.ttt.aop.OptimisticLockRetry;
import com.irelint.ttt.dto.AccountDto;
import com.irelint.ttt.dto.AccountResult;
import com.irelint.ttt.event.OrderPayedEvent;
import com.irelint.ttt.event.OrderReceivedEvent;
import com.irelint.ttt.event.ToPayOrderEvent;
import com.irelint.ttt.event.ToRefundOrderEvent;
import com.irelint.ttt.event.UserCreatedEvent;
import com.irelint.ttt.service.AccountService;
import com.irelint.ttt.util.Constants;

@Service
@Transactional
@DubboService(interfaceClass = AccountService.class)
public class AccountServiceImpl implements AccountService {
	private final static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired 
	private AccountDao dao;
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.account.AccountService#get(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public AccountDto get(Long userId) {
		return dao.findOne(userId).toDto();
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.account.AccountService#createAccount(com.irelint.ttt.event.UserCreatedEvent)
	 */
	@Override
	@RabbitListener(queues=Constants.QUEUE_USERCREATED_ACCOUNT)
	public void createAccount(UserCreatedEvent event) {
		dao.save(new Account(event.getUserId()));
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.account.AccountService#deposit(java.lang.Long, long)
	 */
	@Override
	@OptimisticLockRetry
	public AccountResult deposit(Long id, long money) {
		Account account = dao.findOne(id);
		
		if (logger.isDebugEnabled()) {
			logger.debug("account " + account.getUserId() + " deposit " + money);
		}
		
		account.deposit(money);
		return AccountResult.success(account.toDto());
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.account.AccountService#withdraw(java.lang.Long, long)
	 */
	@Override
	@OptimisticLockRetry
	public AccountResult withdraw(Long id, long money) {
		Account account = dao.findOne(id);
		
		if (logger.isDebugEnabled()) {
			logger.debug("account " + account.getUserId() + " withdraw " + money);
		}
		
		if (account.withdraw(money)) {;
			return AccountResult.success(account.toDto());
		}
		return AccountResult.fail(account.toDto());
	}

	@Override
	@OptimisticLockRetry
	@RabbitListener(queues=Constants.QUEUE_TOPAYORDER_ACCOUNT)
	public void pay(ToPayOrderEvent event) {
		Account account = dao.findOne(event.getOrderBuyerId());
		if (!account.freeze(event.getOrderMoney())) {
			return;
		}
		
		amqpTemplate.convertAndSend("ttt-exchange", "event.orderPayed", new OrderPayedEvent(event.getOrderId()));
	}

	@Override
	@OptimisticLockRetry
	@RabbitListener(queues=Constants.QUEUE_TOREFUNDORDER_ACCOUNT)
	public void refund(ToRefundOrderEvent event) {
		Account account = dao.findOne(event.getOrderBuyerId());
		account.refund(event.getOrderMoney());
	}

	@Override
	@OptimisticLockRetry
	@RabbitListener(queues=Constants.QUEUE_ORDERRECEIVED_ACCOUNT)
	public void confirm(OrderReceivedEvent event) {
		Account buyer = dao.findOne(event.getOrderBuyerId());
		buyer.confirmPay(event.getOrderMoney());
		Account seller = dao.findOne(event.getOrderSellerId());
		seller.receivePay(event.getOrderMoney());
	}

}
