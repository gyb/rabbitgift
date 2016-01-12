package com.irelint.ttt.aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.irelint.ttt.dto.UserDto;

@Aspect
@Component
public class LoginRequiredAspect {

	@Around("@annotation(LoginRequired) && args(.., model, session)")
	public Object checkLogin(ProceedingJoinPoint pjp, Model model, HttpSession session) throws Throwable {
		UserDto user = (UserDto)session.getAttribute("user");
		if (user == null) {
			model.addAttribute(new UserDto());
			return "redirect:/user/login";
		}
		
		return pjp.proceed();
	}
}
