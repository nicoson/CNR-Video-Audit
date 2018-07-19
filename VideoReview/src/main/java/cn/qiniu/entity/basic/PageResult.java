package cn.qiniu.entity.basic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageResult<T extends Serializable> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long number;
	private long pageSize;
	private long totalElements;
	
	private List<T> content = new ArrayList<T>();

	public PageResult(List<T> content, long number, long pageSize, long totalElements) {

		this.content = content;
		this.number = number;
		this.pageSize = pageSize;
		this.totalElements = totalElements;

	}
	
	public long getNumber() {
		return number==0?1:number;
	}

	public long getTotalPages() {
		if(pageSize>0){
			long totalPage = (int) Math.ceil((double) totalElements / (double) pageSize);
			return totalPage == 0? 1: totalPage;
		}else{
			return 1;
		}
	}

	public long getTotalElements() {
		return totalElements;
	}


	public List<T> getContent() {
		return content;
	}

	
}
