package com.cmcc.omc.configcenter.dao.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cmcc.omc.configcenter.enums.NetElementTypeEnum;

import lombok.Data;

@Entity
@Data
public class Environment {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String name;
		
	private NetElementTypeEnum type; 
	
	private String url;
}
