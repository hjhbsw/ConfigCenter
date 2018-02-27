package com.cmcc.omc.configcenter.dao.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Province {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JSONField(serialize=false)  
	private Integer id;

	@JSONField(serialize=false)  
	private Boolean back;

	private String name;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "province_id")
	private List<IpsmgwInfo> ipsmgw;

	@Data
	@Entity
	public static class IpsmgwInfo {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@JSONField(serialize=false)  
		private Integer id;
		private String address;
		@ElementCollection(targetClass=String.class)
		private List<String> scscf;
		
		@JSONField(serialize=false)  
		private Integer province_id;
	}
}
