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

import com.irelint.ttt.api.GoodsApi;
import com.irelint.ttt.api.InventoryApi;
import com.irelint.ttt.api.OrderApi;
import com.irelint.ttt.api.UserApi;
import com.irelint.ttt.dto.GoodsDto;
import com.irelint.ttt.dto.RatingDto;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	//private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);
	@Autowired GoodsApi goodsService;
	@Autowired UserApi userService;
	@Autowired InventoryApi inventoryService;
	@Autowired OrderApi orderService;

	@RequestMapping("{id}")
	public String findGoods(@PathVariable Long id, Model model) {
		GoodsDto goods;
		try {
			goods = goodsService.get(id);
		} catch (DataAccessException e) {
			return "goods/not_found";
		}
		
		model.addAttribute("goods", goods);
		model.addAttribute("inventory", inventoryService.findByGoodsId(id));
		model.addAttribute("category", goodsService.getCategory(goods.getCategoryId()));
		model.addAttribute("seller", userService.get(goods.getOwnerId()));
		return "goods/goods";
	}

	private final int RATING_PAGE_SIZE = 10;
	
	@RequestMapping("/{id}/rating/page")
	public @ResponseBody Page<RatingDto> findRatings(@PathVariable Long id, 
			@PageableDefault(size=RATING_PAGE_SIZE) Pageable pageable, Model model) {
		return orderService.findRatings(id, pageable.getPageNumber(), pageable.getPageSize());
	}
}
