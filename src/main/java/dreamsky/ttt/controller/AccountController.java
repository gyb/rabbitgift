package dreamsky.ttt.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dreamsky.ttt.aop.LoginRequired;
import dreamsky.ttt.user.AccountResult;
import dreamsky.ttt.user.AccountService;
import dreamsky.ttt.user.User;

@Controller
@RequestMapping("/account")
public class AccountController {
	private final static Logger logger = LoggerFactory.getLogger(AccountController.class);
	@Autowired AccountService accountService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	@LoginRequired
	public String get(Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		model.addAttribute("account", accountService.get(user.getId()));
		return "account/account";
	}
	
	@RequestMapping(value="/deposit/{money}")
	@LoginRequired
	public String deposit(@PathVariable long money, Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		
		try {
			AccountResult result = accountService.deposit(user.getId(), money);
			model.addAttribute("account", result.account);
			if (result.success()) {
				logger.info("account " + user.getId() + " deposit " + money + " ok");
				return "account/account";
			} else {
				return "account/failure";
			}
		} catch (Exception e) {
			return "account/failure";
		}
	}
	
	@RequestMapping(value="/withdraw/{money}")
	@LoginRequired
	public String withdraw(@PathVariable long money, Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		
		try {
			AccountResult result = accountService.withdraw(user.getId(), money);
			model.addAttribute("account", result.account);
			if (result.success()) { 
				logger.info("account " + user.getId() + " withdraw " + money + " ok");
				return "account/account";
			} else {
				return "account/failure";
			}
		} catch (Exception e) {
			return "account/failure";
		}
	}
}
