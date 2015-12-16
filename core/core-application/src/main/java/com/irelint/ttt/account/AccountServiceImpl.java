package com.irelint.ttt.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.aop.OptimisticLockRetry;
import com.irelint.ttt.event.OrderPayedEvent;
import com.irelint.ttt.event.OrderReceivedEvent;
import com.irelint.ttt.event.ToPayOrderEvent;
import com.irelint.ttt.event.ToRefundOrderEvent;
import com.irelint.ttt.event.UserCreatedEvent;

@Service
@Transactional
public class AccountServiceImpl implements AccountService, ApplicationEventPublisherAware {
	private final static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired private AccountDao dao;
	
	private ApplicationEventPublisher publisher;
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.account.AccountService#get(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public Account get(Long userId) {
		return dao.findOne(userId);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.account.AccountService#createAccount(com.irelint.ttt.event.UserCreatedEvent)
	 */
	@Override
	@EventListener
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
		return AccountResult.success(account);
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
			return AccountResult.success(account);
		}
		return AccountResult.fail(account);
	}

	@Override
	@EventListener
	@OptimisticLockRetry
	public void pay(ToPayOrderEvent event) {
		Account account = dao.findOne(event.getOrderBuyerId());
		if (!account.freeze(event.getOrderMoney())) {
			return;
		}
		
		publisher.publishEvent(new OrderPayedEvent(this, event.getOrderId()));
	}

	@Override
	@EventListener
	@OptimisticLockRetry
	public void refund(ToRefundOrderEvent event) {
		Account account = dao.findOne(event.getOrderBuyerId());
		account.refund(event.getOrderMoney());
	}

	@Override
	@EventListener
	@OptimisticLockRetry
	public void confirm(OrderReceivedEvent event) {
		Account buyer = dao.findOne(event.getOrderBuyerId());
		buyer.confirmPay(event.getOrderMoney());
		Account seller = dao.findOne(event.getOrderSellerId());
		seller.receivePay(event.getOrderMoney());
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
}
