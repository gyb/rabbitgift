package dreamsky.ttt.util;

import java.util.List;

public interface PageDataProvider<T> {
	int total();
	List<T> find(int start, int size);
}
