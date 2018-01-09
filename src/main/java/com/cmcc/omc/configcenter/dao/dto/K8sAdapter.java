package com.cmcc.omc.configcenter.dao.dto;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import com.cmcc.omc.configcenter.enums.K8sApiEnum;

import lombok.Data;

@Entity
@Data
public class K8sAdapter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String url;

	private K8sApiEnum apiType;

}
