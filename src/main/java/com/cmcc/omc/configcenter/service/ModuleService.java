package com.cmcc.omc.configcenter.service;

import java.util.List;

import com.cmcc.omc.configcenter.bean.PropBean;

public interface ModuleService {

	public String publish(Integer id);
	
	public boolean publishByModuleName(String name);
	
	public List<PropBean> getModuleProperty(String moduleName);
	
	public boolean upateProp(String module,List<PropBean> props);
	
}
