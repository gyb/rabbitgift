package dreamsky.ttt.goods;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMap {
	private Map<Integer, String> map;
	
	@Autowired
	public CategoryMap(CategoryDao dao) {
		map = dao.getAll();
	}
	
	public Map<Integer, String> getMap() {
		return map; 
	}
}
