package wheat.ttt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@GetMapping("/{id}")
	public String goods(@PathVariable Integer id) {
		return "goods/goods";
	}
}
