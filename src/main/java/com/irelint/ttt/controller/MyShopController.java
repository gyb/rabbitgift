package com.irelint.ttt.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.irelint.ttt.aop.LoginRequired;
import com.irelint.ttt.goods.CategoryMap;
import com.irelint.ttt.goods.Goods;
import com.irelint.ttt.goods.GoodsResult;
import com.irelint.ttt.goods.GoodsService;
import com.irelint.ttt.order.OrderService;
import com.irelint.ttt.user.User;
import com.irelint.ttt.util.ImagePathGen;

@Controller
@RequestMapping("/myshop")
public class MyShopController {
	private final static Logger logger = LoggerFactory.getLogger(MyShopController.class);
	@Autowired GoodsService goodsService;
	@Autowired OrderService orderService;
	@Autowired ImagePathGen imagePathGen;
	@Autowired CategoryMap categoryMap;
	private final static int PAGE_SIZE = 12;
	private final static int ORDER_PAGE_SIZE = 12;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	@LoginRequired
	public String myshop(Model model, HttpSession session) {
		return "myshop/myshop";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.GET)
	@LoginRequired
	public String showUpload(Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		Goods goods = new Goods();
		goods.setUserId(user.getId());
		model.addAttribute(goods);
		model.addAttribute("category", categoryMap.getMap());
		return "myshop/upload";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	@LoginRequired
	public String upload(@Valid Goods goods, BindingResult result,
			@RequestParam(value="image",required=false) MultipartFile image, 
			Model model, HttpSession session) {
		
		String fileName = imagePathGen.getNextId() + ".jpg";
		if(!image.getContentType().equals("image/jpeg")){
			result.reject("Only JPG images accepted");
		} else {
			try {
				File file = new File(imagePathGen.getUploadFilePath() + fileName);
				FileUtils.writeByteArrayToFile(file, image.getBytes());
			} catch (IOException e) {
				result.reject("Unable to save image");
			}
		}
		
		if (result.hasErrors()) {
			return "myshop/upload";
		}
		
		goods.setPicUrl(imagePathGen.getImageUrl() + fileName);
		goodsService.create(goods);
		
		User user = (User)session.getAttribute("user");
		logger.info("user " + user.getLogin() + " upload goods " + goods.getName() + " id " + goods.getId());
		return "myshop/upload_success";
	}
	
	@RequestMapping(value="/createdPage/{pageNo}")
	@LoginRequired
	public String createdGoodsPage(@PathVariable int pageNo, Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		model.addAttribute("page", goodsService.findCreatedPage(user.getId(), pageNo, PAGE_SIZE));
		return "myshop/created";
	}
	
	@RequestMapping(value="/goods/{goodsId}")
	@LoginRequired
	public String findGoods(@PathVariable Long goodsId, Model model, HttpSession session) {
		Goods goods;
		try {
			goods = goodsService.get(goodsId);
		} catch (DataAccessException e) {
			return "myshop/goods_notfound";
		}
		
		model.addAttribute("goods", goods);
		model.addAttribute("category", categoryMap.getMap().get(goods.getCategoryId()));
		
		if (goods.isOnline()) {
			return "myshop/online_goods";
		} else if (goods.isNew()) {
			return "myshop/created_goods";
		} else {
			return "myshop/offline_goods";
		}
	}
	
	@RequestMapping(value="/putOnline/{goodsId}")
	@LoginRequired
	public String putOnline(@PathVariable Long goodsId, Model model, HttpSession session) {
		try {
			GoodsResult result = goodsService.putOnline(goodsId);
			if (result.fail()) {
				return "myshop/online_fail";
			}
			
			model.addAttribute("goods", result.goods);
			return "myshop/online_success";
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			return "myshop/online_fail";
		}
	}
	
	@RequestMapping(value="/onlinePage/{pageNo}")
	@LoginRequired
	public String onlineGoodsPage(@PathVariable int pageNo, Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		model.addAttribute("page", goodsService.findOnlinePage(user.getId(), pageNo, PAGE_SIZE));
		return "myshop/online";
	}
	
	@RequestMapping(value="/putOffline/{goodsId}")
	@LoginRequired
	public String putOffline(@PathVariable Long goodsId, Model model, HttpSession session) {
		try {
			GoodsResult result = goodsService.putOffline(goodsId);
			if (result.fail()) {
				return "myshop/offline_fail";
			}
			
			model.addAttribute("goods", result.goods);
			return "myshop/offline_success";
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			return "myshop/offline_fail";
		}
	}
	
	@RequestMapping(value="/offlinePage/{pageNo}")
	@LoginRequired
	public String offlineGoodsPage(@PathVariable int pageNo, Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		model.addAttribute("page", goodsService.findOfflinePage(user.getId(), pageNo, PAGE_SIZE));
		return "myshop/offline";
	}
	
	@RequestMapping(value="/copy/{goodsId}")
	@LoginRequired
	public String copy(@PathVariable Long goodsId, Model model, HttpSession session) {
		try {
			GoodsResult result = goodsService.copy(goodsId);
			if (result.fail()) {
				return "myshop/copy_fail";
			}
			
			model.addAttribute("goods", result.goods);
			return "myshop/copy_success";
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			return "myshop/copy_fail";
		}
	}
	
	@RequestMapping(value="/orders/{pageNo}", method=RequestMethod.GET) 
	@LoginRequired
	public String myOrders(@PathVariable int pageNo, Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		model.addAttribute("page", orderService.findSellerOrders(user.getId(), pageNo, ORDER_PAGE_SIZE));
		return "myshop/orders";
	}
}
