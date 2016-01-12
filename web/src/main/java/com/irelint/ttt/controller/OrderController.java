package com.irelint.ttt.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.irelint.ttt.aop.LoginRequired;
import com.irelint.ttt.api.AccountApi;
import com.irelint.ttt.api.GoodsApi;
import com.irelint.ttt.api.OrderApi;
import com.irelint.ttt.api.UserApi;
import com.irelint.ttt.dto.OrderDto;
import com.irelint.ttt.dto.OrderState;
import com.irelint.ttt.dto.RatingDto;
import com.irelint.ttt.dto.UserDto;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired UserApi userService;
	@Autowired AccountApi accountService;
	@Autowired GoodsApi goodsService;
	@Autowired OrderApi orderService;
	
	//FIXME validate orders belong to the user 

	@RequestMapping(value="/buy/{goodsId}", method=RequestMethod.GET)
	@LoginRequired
	public String buy(@PathVariable Long goodsId, Model model, HttpSession session) {
		UserDto user = (UserDto)session.getAttribute("user");
		model.addAttribute("goods", goodsService.get(goodsId));
		model.addAttribute("addressList", userService.findAddresses(user.getId()));
		
		OrderDto order = new OrderDto();
		order.setNum(1);
		order.setBuyerId(user.getId());
		order.setGoodsId(goodsId);
		model.addAttribute("order", order);
		return "order/create_order";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@LoginRequired
	public String createOrder(@Valid OrderDto order, BindingResult result, Model model, HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("order", order);
			return "order/create_order";
		}
		
		model.addAttribute("order", orderService.create(order));
		model.addAttribute("goods", goodsService.get(order.getGoodsId()));
		return "order/create_success";
	}
	
	@RequestMapping(value="/pay/{orderId}", method=RequestMethod.GET)
	@LoginRequired
	public String showPay(@PathVariable Long orderId, Model model, HttpSession session) {
		OrderDto order = orderService.get(orderId);
		if (order.getState() != OrderState.CONFIRMED) {
			return "order/pay_fail";
		}
		
		UserDto user = (UserDto)session.getAttribute("user");
		model.addAttribute("account", accountService.get(user.getId()));
		model.addAttribute("order", order);
		model.addAttribute("goods", goodsService.get(order.getGoodsId()));
		return "order/pay";
	}
	
	@RequestMapping(value="/pay/{orderId}", method=RequestMethod.POST)
	@LoginRequired
	public String pay(@PathVariable Long orderId, Model model, HttpSession session) {
		orderService.pay(orderId);
		return "order/pay_finished";
	}
	
	@RequestMapping(value="/cancel/{orderId}", method=RequestMethod.GET)
	@LoginRequired
	public String showCancel(@PathVariable Long orderId, Model model, HttpSession session) {
		OrderDto order = orderService.get(orderId);
		if (order.getState() != OrderState.CREATED && order.getState() != OrderState.CONFIRMED) {
			return "order/cancel_fail";
		}
		
		model.addAttribute("order", order);
		model.addAttribute("goods", goodsService.get(order.getGoodsId()));
		return "order/cancel";
	}
	
	@RequestMapping(value="/cancel/{orderId}", method=RequestMethod.POST)
	@LoginRequired
	public String cancel(@PathVariable Long orderId, Model model, HttpSession session) {
		OrderDto order = orderService.cancel(orderId);
		if (order == null) {
			return "order/cancel_fail";
		}
		
		model.addAttribute("order", order);
		model.addAttribute("goods", goodsService.get(order.getGoodsId()));
		return "order/cancel_success";
	}
	
	@RequestMapping(value="/deliver/{orderId}", method=RequestMethod.GET)
	@LoginRequired
	public String showDeliver(@PathVariable Long orderId, Model model, HttpSession session) {
		OrderDto order = orderService.get(orderId);
		if (order.getState() != OrderState.PAYED) {
			return "order/deliver_fail";
		}
		
		model.addAttribute("order", order);
		model.addAttribute("goods", goodsService.get(order.getGoodsId()));
		return "order/deliver";
	}
	
	@RequestMapping(value="/deliver/{orderId}", method=RequestMethod.POST)
	@LoginRequired
	public String deliver(@PathVariable Long orderId, Model model, HttpSession session) {
		OrderDto order = orderService.deliver(orderId);
		if (order == null) {
			return "order/deliver_fail";
		}
		
		model.addAttribute("order", order);
		model.addAttribute("goods", goodsService.get(order.getGoodsId()));
		return "order/deliver_success";
	}
	
	@RequestMapping(value="/refund/{orderId}", method=RequestMethod.GET)
	@LoginRequired
	public String showRefund(@PathVariable Long orderId, Model model, HttpSession session) {
		OrderDto order = orderService.get(orderId);
		if (order.getState() != OrderState.PAYED && order.getState() != OrderState.DELIVERED) {
			return "order/refund_fail";
		}
		
		model.addAttribute("order", order);
		model.addAttribute("goods", goodsService.get(order.getGoodsId()));
		return "order/refund";
	}
	
	@RequestMapping(value="/refund/{orderId}", method=RequestMethod.POST)
	@LoginRequired
	public String refund(@PathVariable Long orderId, Model model, HttpSession session) {
		OrderDto order = orderService.refund(orderId);
		if (order == null) {
			return "order/refund_fail";
		}
		
		model.addAttribute("order", order);
		model.addAttribute("goods", goodsService.get(order.getGoodsId()));
		return "order/refund_success";
	}
	
	@RequestMapping(value="/receive/{orderId}", method=RequestMethod.GET)
	@LoginRequired
	public String showReceive(@PathVariable Long orderId, Model model, HttpSession session) {
		OrderDto order = orderService.get(orderId);
		if (order.getState() != OrderState.DELIVERED) {
			return "order/receive_fail";
		}
		
		model.addAttribute("order", order);
		model.addAttribute("goods", goodsService.get(order.getGoodsId()));
		return "order/receive";
	}
	
	@RequestMapping(value="/receive/{orderId}", method=RequestMethod.POST)
	@LoginRequired
	public String receive(@PathVariable Long orderId, Model model, HttpSession session) {
		OrderDto order = orderService.receive(orderId);
		if (order == null) {
			return "order/receive_fail";
		}
		
		model.addAttribute("order", order);
		model.addAttribute("goods", goodsService.get(order.getGoodsId()));
		return "order/receive_success";
	}
	
	@RequestMapping(value="/rate/{orderId}", method=RequestMethod.GET)
	@LoginRequired
	public String showRate(@PathVariable Long orderId, Model model, HttpSession session) {
		OrderDto order = orderService.get(orderId);
		if (order.getState() != OrderState.RECEIVED) {
			return "order/rate_fail";
		}
		
		RatingDto rating = new RatingDto();
		rating.setGoodsId(order.getGoodsId());
		rating.setOrderId(orderId);
		rating.setUserId(((UserDto)session.getAttribute("user")).getId());
		rating.setUsername(((UserDto)session.getAttribute("user")).getLogin());
		
		model.addAttribute("rating", rating);
		model.addAttribute("order", order);
		model.addAttribute("goods", goodsService.get(order.getGoodsId()));
		return "order/rate";
	}
	
	@RequestMapping(value="/rate", method=RequestMethod.POST)
	@LoginRequired
	public String rate(@ModelAttribute("rating") @Valid RatingDto rating, BindingResult result, Model model, HttpSession session) {
		
		if (result.hasErrors()) {
			model.addAttribute("order", orderService.get(rating.getOrderId()));
			model.addAttribute("goods", goodsService.get(rating.getGoodsId()));
			return "order/rate";
		}

		OrderDto order = orderService.rate(rating);
		if (order == null) {
			return "order/rate_fail";
		}
		
		model.addAttribute("order", order);
		model.addAttribute("goods", goodsService.get(order.getGoodsId()));
		return "order/rate_success";
	}
	
	@RequestMapping(value="/history/{orderId}")
	@LoginRequired
	public String history(@PathVariable Long orderId, Model model, HttpSession session) {
		model.addAttribute("order", orderService.findDetail(orderId));
		model.addAttribute("history", orderService.history(orderId));
		return "order/history";
	}
}
