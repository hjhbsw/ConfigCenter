package com.cmcc.omc.configcenter.service;


import org.springframework.data.domain.Page;

import com.cmcc.omc.configcenter.dao.dto.Environment;

public interface ConfigService {

	public Page<Environment> pageEnvironment(Integer page,Integer size);
	
	public String addEnvironment(Environment env);
	
	public void delEnv(Integer id);
	
	public Environment fetchEnv(Integer id);
	
	public String update(Environment env);
}
