package io.renren.util;

import java.util.List;

/**
 * This is a pagination DTO
 * 
 * @author sinba
 * @modifier chb[yanglingxiao2009@163.com]
 */

public class PageDTO {

	private PageDTO() {
	}

	public static final <T> PageDTO getPagination(int total, List<T> rows) {
		PageDTO pagination = new PageDTO();
		pagination.setRows(rows);
		pagination.setTotal(total);
		return pagination;
	};

	@SuppressWarnings("rawtypes")
	public List getRows() {
		return rows;
	}

	@SuppressWarnings("rawtypes")
	public void setRows(List rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@SuppressWarnings("rawtypes")
	private List rows;//需要显示的数据

	private int total;//总的页数

}