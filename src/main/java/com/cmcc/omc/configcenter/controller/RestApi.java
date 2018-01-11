package com.cmcc.omc.configcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmcc.omc.configcenter.bean.PropBean;
import com.cmcc.omc.configcenter.service.ModuleService;

@RestController
@RequestMapping("/rest")
public class RestApi {

	@Autowired
	ModuleService service;
	
	@RequestMapping("/all")
	public List<PropBean> fetchAll(String moduleName){
		return service.getModuleProperty(moduleName);
	}
	
	@RequestMapping("/update")
	public boolean updateProperty(String modulenName,List<PropBean> props){
		return service.upateProp(modulenName, props);
	}
	
	@RequestMapping("/publish")
	public boolean publish(String name){
		return service.publishByModuleName(name);
	}
}
