package com.cmcc.omc.configcenter.dao.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Data
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"topic","moduleId"})})
public class DynamicProp {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private Integer moduleId;
	
	private String topic;
	
	private String json;
}
