package com.cmcc.omc.configcenter.service;

import java.util.List;

import com.cmcc.omc.configcenter.dao.dto.Module;
import com.cmcc.omc.configcenter.dao.dto.Property;

public interface ModuleService {

	public List<Module> listAll();
	
	public List<Property> listModuleProperty(Integer moduleId);
	
	
	
}
