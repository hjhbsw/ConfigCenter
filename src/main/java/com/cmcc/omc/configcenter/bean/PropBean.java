package com.cmcc.omc.configcenter.bean;

import lombok.Data;

@Data
public class PropBean {
	
	private String key;
	
	private String value;

	public PropBean(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public PropBean() {
		super();
	}
	
	
}
