package com.cmcc.omc.configcenter.dao.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Property {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String propKey;
	
	private String propValue;
	
	private String placeHolder;
	
	private Integer moduleId;
	
	private Integer configMapId;
}
