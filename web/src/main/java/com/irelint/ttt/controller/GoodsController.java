package com.irelint.ttt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.irelint.ttt.goods.GoodsService;
import com.irelint.ttt.goods.model.CategoryMap;
import com.irelint.ttt.goods.model.Goods;
import com.irelint.ttt.inventory.InventoryService;
import com.irelint.ttt.order.Rating;
import com.irelint.ttt.user.UserService;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	//private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);
	@Autowired GoodsService goodsService;
	@Autowired UserService userService;
	@Autowired InventoryService inventoryService;
	@Autowired CategoryMap categoryMap;

	@RequestMapping("{id}")
	public String findGoods(@PathVariable Long id, Model model) {
		Goods goods;
		try {
			goods = goodsService.get(id);
		} catch (DataAccessException e) {
			return "goods/not_found";
		}
		
		model.addAttribute("goods", goods);
		model.addAttribute("inventory", inventoryService.findByGoodsId(id));
		model.addAttribute("category", categoryMap.getMap().get(goods.getCategoryId()));
		model.addAttribute("seller", userService.get(goods.getOwnerId()));
		return "goods/goods";
	}

	private final int RATING_PAGE_SIZE = 10;
	
	@RequestMapping("/{id}/rating/page")
	public @ResponseBody Page<Rating> findRatings(@PathVariable Long id, 
			@PageableDefault(size=RATING_PAGE_SIZE) Pageable pageable, Model model) {
		return goodsService.findRatings(id, pageable);
	}
}
