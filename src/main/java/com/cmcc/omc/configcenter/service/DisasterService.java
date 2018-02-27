package com.cmcc.omc.configcenter.service;

import java.util.List;

import com.cmcc.omc.configcenter.bean.RestBean;
import com.cmcc.omc.configcenter.dao.dto.Province;

public interface DisasterService {
	
	public RestBean backSwitch(Integer id,boolean operate);
	
	public RestBean add(Province province);
	
	public RestBean del(Integer id);
	
	public RestBean modify(Province province);
	
	public List<Province> all();
}
