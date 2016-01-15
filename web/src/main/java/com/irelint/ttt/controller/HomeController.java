package com.irelint.ttt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.irelint.ttt.api.GoodsApi;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired GoodsApi goodsService;
	
	private final int PAGE_SIZE = 12;

	@RequestMapping(value="", method=RequestMethod.GET)
	public String home(@PageableDefault(size=PAGE_SIZE) Pageable pageable, Model model) {
		model.addAttribute("category", goodsService.getCategory());
		model.addAttribute("page", goodsService.findHomePage(pageable.getPageNumber(), pageable.getPageSize()));
		return "index";
	}

}
