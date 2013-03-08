package dreamsky.ttt.util;

import java.util.List;

public class PageList<T> {
	private int totalPage;
	private int currentPage;
	private String pageUrl;
	private List<T> page;
	
	public PageList(PageDataProvider<T> provider, int pageNo, int pageSize, String pageUrl) {
		if (pageSize < 1) throw new IllegalArgumentException("page size must be greater than 0");
		
		currentPage = pageNo;
		int total = provider.total();
		totalPage = (total - 1) / pageSize + 1;
		int start = (currentPage - 1) * pageSize;
		page = provider.find(start, pageSize);

		this.pageUrl = pageUrl;
	}
	
	public List<T> getPage() {
		return page;
	}

	public int getNextNo() {
		return currentPage + 1;
	}

	public int getPageNo() {
		return currentPage;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public int getPrevNo() {
		return currentPage - 1;
	}

	public int getTotal() {
		return totalPage;
	}

	public boolean isHasNext() {
		return currentPage < totalPage;
	}

	public boolean isHasPrev() {
		return currentPage > 1;
	}

}
