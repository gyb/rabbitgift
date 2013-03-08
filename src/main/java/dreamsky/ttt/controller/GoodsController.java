package dreamsky.ttt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dreamsky.ttt.goods.CategoryMap;
import dreamsky.ttt.goods.Goods;
import dreamsky.ttt.goods.GoodsService;
import dreamsky.ttt.order.Rating;
import dreamsky.ttt.user.UserService;
import dreamsky.ttt.util.PageList;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	//private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);
	@Autowired GoodsService goodsService;
	@Autowired UserService userService;
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
		model.addAttribute("category", categoryMap.getMap().get(goods.getCategoryId()));
		model.addAttribute("seller", userService.get(goods.getUserId()));
		return "goods/goods";
	}

	private static int RATING_PAGE_SIZE = 10;
	
	@RequestMapping("/{id}/rating/page/{pageNo}")
	public @ResponseBody PageList<Rating> findRatings(@PathVariable Long id, 
			@PathVariable int pageNo, Model model) {
		return goodsService.findRatings(id, pageNo, RATING_PAGE_SIZE);
	}
}
