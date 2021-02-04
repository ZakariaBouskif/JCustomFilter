package com.jcustom.filter;

import java.util.ArrayList;
import java.util.List;

public class ServiceFilter {
	
	private List<ServiceFilterGroup> filtersGroupes = new ArrayList<>();


	public List<ServiceFilterGroup> getFiltersGroupes() {
		return filtersGroupes;
	}

	public void setFiltersGroupes(List<ServiceFilterGroup> filtersGroupes) {
		this.filtersGroupes = filtersGroupes;
	}


	public ServiceFilter() {
		super();
	}

}