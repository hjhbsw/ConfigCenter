package com.cmcc.omc.configcenter.dao.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"name","nameSpace"})})
public class ConfigMap {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
		
	private String name;
	
	private String nameSpace = "default";
	
	private Integer moduleId;
}
