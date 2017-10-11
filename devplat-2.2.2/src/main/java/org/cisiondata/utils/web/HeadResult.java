package org.cisiondata.utils.web;

import java.util.List;

import org.cisiondata.modules.abstr.entity.QueryResult;

public class HeadResult<Entity> extends QueryResult<Entity> {
	private static final long serialVersionUID = 1L;
	private List<Object> head=null;
	public List<Object> getHead() {
		return head;
	}
	public void setHead(List<Object> head) {
		this.head = head;
	}
}
