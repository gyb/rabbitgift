package com.irelint.ttt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

/**
 * @author yibing
 * Aspect of Optimistic Lock
 */
@Aspect
@Component
public class OptimisticLockingRetryAspect implements Ordered {
	private static final Logger logger = LoggerFactory.getLogger(OptimisticLockingRetryAspect.class);
	private static final int DEFAULT_MAX_RETRIES = 2;

	private int maxRetries = DEFAULT_MAX_RETRIES;
	private int order = 1;

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Around("@annotation(OptimisticLockRetry)")
	public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
		int numAttempts = 0;
		OptimisticLockingFailureException lockFailureException;
		do {
			numAttempts++;
			try {
				logger.debug("-------------------aop start -----------------------");
				return pjp.proceed();
			} catch (OptimisticLockingFailureException ex) {
				logger.debug("---------------------catch exception--------------");
				lockFailureException = ex;
			}
		} while (numAttempts <= this.maxRetries);
		throw lockFailureException;
	}

}
