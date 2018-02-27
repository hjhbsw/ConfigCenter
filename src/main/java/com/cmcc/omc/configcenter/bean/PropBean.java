package com.cmcc.omc.configcenter.bean;

import lombok.Data;

@Data
public class PropBean {
	
	private String key;
	
	private String value;

	private boolean dynamic;
	
	public PropBean(String key, String value,boolean dynamic) {
		super();
		this.key = key;
		this.value = value;
	}

	public PropBean() {
		super();
	}
	
	
}
