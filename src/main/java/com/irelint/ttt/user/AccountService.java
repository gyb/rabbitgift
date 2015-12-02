package com.irelint.ttt.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.aop.OptimisticLockRetry;

@Service
public class AccountService {
	@Autowired AccountDao dao;
	private final static Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@Transactional(readOnly=true)
	public Account get(Long userId) {
		return dao.findOne(userId);
	}

	@Transactional
	@OptimisticLockRetry
	public AccountResult deposit(Long id, long money) {
		Account account = dao.findOne(id);
		
		if (logger.isDebugEnabled()) {
			logger.debug("account " + account.getUserId() + " deposit " + money);
		}
		
		account.deposit(money);
		dao.save(account);
		return AccountResult.success(account);
	}

	@Transactional
	@OptimisticLockRetry
	public AccountResult withdraw(Long id, long money) {
		Account account = dao.findOne(id);
		
		if (logger.isDebugEnabled()) {
			logger.debug("account " + account.getUserId() + " withdraw " + money);
		}
		
		if (account.withdraw(money)) {;
			dao.save(account);
			return AccountResult.success(account);
		}
		return AccountResult.fail(account);
	}
}
