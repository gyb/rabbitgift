package com.irelint.ttt.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.irelint.ttt.user.DuplicateEmailException;
import com.irelint.ttt.user.DuplicateLoginException;
import com.irelint.ttt.user.UserService;
import com.irelint.ttt.user.model.User;

@Controller
@RequestMapping("/user")
public class UserController {
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired UserService service;
	
	@RequestMapping(value="register", method=RequestMethod.GET)
	public String showRegister(Model model) {
		model.addAttribute(new User());
		return "user/register";
	}

	@RequestMapping(value="register", method=RequestMethod.POST)
	public String register(@Valid User user, BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			return "user/register";
		}
		
		try {
			service.register(user);
		} catch (DuplicateLoginException e) {
			result.addError(new FieldError("user", "login", "Duplicate Login!"));
			return "user/register";
		} catch (DuplicateEmailException e) {
			result.addError(new FieldError("user", "email", "Duplicate Email!"));
			return "user/register";
		} catch (Exception e) {
			logger.warn("register error", e);
			result.addError(new ObjectError("user", e.getMessage()));
			return "user/register";
		}
		
		session.setAttribute("user", user);
		return "user/register_success";
	}
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String showLogin(Model model) {
		model.addAttribute(new User());
		return "user/login";
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String login(User user, BindingResult result, HttpSession session) {
		user = service.login(user.getLogin(), user.getPassword());
		if (user == null) {
			result.addError(new ObjectError("user", "Incorrect login or password!"));
			return "user/login";
		}
		
		session.setAttribute("user", user);
		return "user/login_success";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}
}
