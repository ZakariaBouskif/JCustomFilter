package com.jcustom.filter;

import java.util.ArrayList;
import java.util.List;

public class ServiceFilterElement {
	
	private String field;
	private List<String> value = new ArrayList<>();
	private String condition;
	private String alias;
	
	
	public ServiceFilterElement(String field, List<String> value, String condition, String alias) {
		super();
		this.field = field;
		this.value = value;
		this.condition = condition;
		this.alias = alias;
	}

	public ServiceFilterElement() {
		super();
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	
	public List<String> getValue() {
		return value;
	}
	public void setValue(List<String> value) {
		this.value = value;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
}