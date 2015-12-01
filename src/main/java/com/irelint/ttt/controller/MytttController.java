package com.irelint.ttt.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.irelint.ttt.aop.LoginRequired;
import com.irelint.ttt.order.OrderService;
import com.irelint.ttt.user.Address;
import com.irelint.ttt.user.User;
import com.irelint.ttt.user.UserService;

@Controller
@RequestMapping("/myttt")
public class MytttController {
	private final static Logger logger = LoggerFactory.getLogger(MytttController.class);

	@Autowired UserService userService;
	@Autowired OrderService orderService;
	
	private static final int ORDER_PAGE_SIZE = 10;
	
	@RequestMapping(value="", method=RequestMethod.GET) 
	@LoginRequired
	public String myttt(Model model, HttpSession session) {
		return "myttt/myttt";
	}
	
	@RequestMapping(value="/address", method=RequestMethod.GET)
	@LoginRequired
	public String myAddress(Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		model.addAttribute("address", new Address());
		model.addAttribute("addressList", userService.findAddresses(user.getId()));
		return "myttt/address";
	}
	
	@RequestMapping(value="/address", method=RequestMethod.POST)
	@LoginRequired
	public String createAddress(@Valid Address address, BindingResult result, Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		if (result.hasErrors()) {
			model.addAttribute("addressList", userService.findAddresses(user.getId()));
			return "myttt/address";
		}
		
		userService.saveAddress(address);
		if (logger.isDebugEnabled()) {
			logger.debug("user " + user.getId() + " add a new address");
		}
		
		return "redirect:/myttt/address";
	}
	
	@RequestMapping(value="/deleteAddress/{addressId}")
	@LoginRequired
	public String deleteAddress(@PathVariable Long addressId, Model model, HttpSession session) {
		userService.deleteAddress(addressId);
		if (logger.isDebugEnabled()) {
			User user = (User)session.getAttribute("user");
			logger.debug("user " + user.getId() + " delete address " + addressId);
		}
		
		return "redirect:/myttt/address";
	}
	
	@RequestMapping(value="/orders/{pageNo}", method=RequestMethod.GET) 
	@LoginRequired
	public String myOrders(@PathVariable int pageNo, Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		model.addAttribute("page", orderService.findBuyerOrders(user.getId(), pageNo, ORDER_PAGE_SIZE));
		return "myttt/orders";
	}
}
