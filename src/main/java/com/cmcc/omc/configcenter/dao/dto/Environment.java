package com.cmcc.omc.configcenter.dao.dto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.cmcc.omc.configcenter.enums.NetElementTypeEnum;

import lombok.Data;

@Entity
@Data
public class Environment {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String name;
		
	private String descript;

	private NetElementTypeEnum type; 
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "adapterId")
	private K8sAdapter adapter;
	
}
