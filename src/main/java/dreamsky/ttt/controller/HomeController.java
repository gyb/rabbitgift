package dreamsky.ttt.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dreamsky.ttt.goods.CategoryMap;
import dreamsky.ttt.goods.GoodsService;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired CategoryMap categoryMap;
	@Autowired GoodsService goodsService;
	
	private static int PAGE_SIZE = 12;

	@RequestMapping(value="", method=RequestMethod.GET)
	public String home(Model model, HttpSession session) {
		model.addAttribute("category", categoryMap.getMap());
		model.addAttribute("page", goodsService.findHomePage(1, PAGE_SIZE));
		return "index";
	}

	@RequestMapping(value="home/{pageNo}", method=RequestMethod.GET)
	public String home(@PathVariable int pageNo, Model model, HttpSession session) {
		model.addAttribute("category", categoryMap.getMap());
		model.addAttribute("page", goodsService.findHomePage(pageNo, PAGE_SIZE));
		return "index";
	}

	@RequestMapping(value="test", method=RequestMethod.GET)
	public String test(Model model, HttpSession session, HttpServletResponse response) {
		model.addAttribute("category", categoryMap.getMap());
		model.addAttribute("page", goodsService.findHomePage(1, PAGE_SIZE));
		
		response.setHeader("X-SENDFILE", "D:/1.jpg");
		response.setHeader("Content-Type", "application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"1.jpg\"");
		return "index";
	}
}
