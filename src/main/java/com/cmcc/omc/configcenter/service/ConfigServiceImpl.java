package com.cmcc.omc.configcenter.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cmcc.omc.configcenter.dao.dto.Environment;
import com.cmcc.omc.configcenter.dao.repositories.EnvRepository;
import com.cmcc.omc.configcenter.enums.K8sApiEnum;
import com.cmcc.omc.configcenter.enums.NetElementTypeEnum;

@Service
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	EnvRepository envRepository;
	
	@Override
	public Page<Environment> pageEnvironment(Integer page, Integer size) {
		
		return envRepository.findAll(new PageRequest(page-1, size, new Sort(Direction.DESC, "id")));

	}

	@Override
	public String addEnvironment(Environment env) {
		
		if(StringUtils.isEmpty(env.getName())){
			return "name must not empty";
		}
		
		if(StringUtils.isEmpty(env.getType())){
			return "type must not empty";
		}
		
		
		if(env.getType() == NetElementTypeEnum.K8S){
			
			if(null == env.getAdapter()){
				return "when type is K8S adapter must not empty";
			}
			
			if(null == env.getAdapter().getApiType()){
				return "apiType must not empty";
			}
			
			if(env.getAdapter().getApiType() == K8sApiEnum.URL_API){
				if(StringUtils.isEmpty(env.getAdapter().getUrl())){
					return "when apiType is URL_API url must not empty";
				}
			}
		}
		
		
		envRepository.save(env);
		
		return "You have success add Env "+env.getName();
	}

	@Override
	public void delEnv(Integer id) {
		
		envRepository.delete(id);;
	}

	@Override
	public Environment fetchEnv(Integer id) {
		// TODO Auto-generated method stub
		return envRepository.findOne(id);
	}

	@Override
	public String update(Environment env) {
		
		if(StringUtils.isEmpty(env.getName())){
			return "name must not empty";
		}
		
		if(StringUtils.isEmpty(env.getType())){
			return "type must not empty";
		}
		
		
		if(env.getType() == NetElementTypeEnum.K8S){
			
			if(null == env.getAdapter()){
				return "when type is K8S adapter must not empty";
			}
			
			if(null == env.getAdapter().getApiType()){
				return "apiType must not empty";
			}
			
			if(env.getAdapter().getApiType() == K8sApiEnum.URL_API){
				if(StringUtils.isEmpty(env.getAdapter().getUrl())){
					return "when apiType is URL_API url must not empty";
				}
			}
		}
		
		envRepository.save(env);
		return "update "+ env.getName()+" Success";
	}
	

}
