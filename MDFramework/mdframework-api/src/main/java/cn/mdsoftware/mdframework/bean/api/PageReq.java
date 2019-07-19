package cn.mdsoftware.mdframework.bean.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页参数
 * @author Jax
 *
 */
public class PageReq implements Serializable {

	private static final long serialVersionUID = -5518644640849302693L;
	
	/**
	 * 第pageNum页，从1开始
	 */
	private Integer pageNum;
	/**
	 * 每页大小
	 */
	private Integer pageSize;
	
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "PageReq [pageNum=" + pageNum + ", pageSize=" + pageSize + "]";
	}
	public Map<String, Integer> getLimits() {
		Map<String, Integer> map = new HashMap<>();
		this.pageNum = this.pageNum <= 0 ? 1:this.pageNum;
		map.put("offset", (this.pageNum - 1) * this.pageSize);
		map.put("limit", this.pageSize);
		System.out.println(map);
		return map;
	}
}
