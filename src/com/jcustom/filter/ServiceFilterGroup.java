package com.jcustom.filter;

import java.util.ArrayList;
import java.util.List;

public class ServiceFilterGroup {
	
	private List<ServiceFilterElement> filters = new ArrayList<>();
	private String operator;

	public List<ServiceFilterElement> getFilters() {
		return filters;
	}

	public void setFilters(List<ServiceFilterElement> filters) {
		this.filters = filters;
	}

	public ServiceFilterGroup(List<ServiceFilterElement> filters) {
		super();
		this.filters = filters;
	}

	public ServiceFilterGroup() {
		super();
		this.operator = "AND";
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	
	
	
	
	
}