package com.cmcc.omc.configcenter.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cmcc.omc.configcenter.Config;
import com.cmcc.omc.configcenter.bean.PropBean;
import com.cmcc.omc.configcenter.dao.dto.ConfigFile;
import com.cmcc.omc.configcenter.dao.dto.ConfigMap;
import com.cmcc.omc.configcenter.dao.dto.Module;
import com.cmcc.omc.configcenter.dao.dto.Property;
import com.cmcc.omc.configcenter.dao.repositories.ConfigFileReposity;
import com.cmcc.omc.configcenter.dao.repositories.ConfigMapRepository;
import com.cmcc.omc.configcenter.dao.repositories.ModuleRepository;
import com.cmcc.omc.configcenter.dao.repositories.ProperityRepository;
import com.cmcc.omc.configcenter.publisher.k8sApi;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	ModuleRepository mr;
	
	@Autowired
	ConfigMapRepository cmr;
	
	@Autowired
	ConfigFileReposity cfr;
	
	@Autowired
	ProperityRepository pr;
	
	@Autowired
	k8sApi api;
	
	@Autowired
	Config config;
	
	@Override
	public String publish(Integer id) {
		
		Module m = mr.findOne(id);
		
		String name = m.getName();
		boolean flag = this.publish(m);
		
		return "Publish "+ name +(flag? " Success " : " Failed " );
	}

	@Override
	public boolean publishByModuleName(String name) {

		Module m = mr.findByName(name);
				
		return this.publish(m);
	}

	
	private synchronized boolean publish(Module m){
		if(m == null){
			return false;
		}
		
		ConfigMap cm = cmr.findByModuleId(m.getId());
		
		String modulePath = config.getTempPath() + "/" + m.getName();
		
		Path mp = Paths.get(modulePath);
		
		if(!Files.exists(mp)){
			try {
				Files.createDirectories(mp);
			} catch (IOException e) {
				log.warn("Create folder {} error",modulePath,e);
				return false;
			}
		}
	
		List<ConfigFile> cfList = cfr.findByConfigMapId(cm.getId());
		
		for(ConfigFile cf:cfList){
			
			String data = cf.getData();
			List<Property> props = pr.findByConfigFileId(cf.getId());
			
			if(props != null){
				for(Property prop:props){
					data = data.replace(prop.getPlaceHolder(), prop.getPropValue());
				}
			}
			
			
			Path path = Paths.get(modulePath,cf.getFileName());
			
			if(!Files.exists(path)){
				try {
					Files.createFile(path);
				} catch (IOException e) {
					log.warn("Save file {} error ",path,e);
					return false;
				}
			}
			
			try (BufferedWriter bw =  Files.newBufferedWriter(path, Charset.forName("utf-8"))){
				bw.write(data);
				
			} catch (IOException e) {
				log.warn("Write file {} error ",path,e);
				return false;
			}
		}
		
		if(api.updateConfigMap(cm.getName(), modulePath, cm.getNameSpace()))
		{
			if(api.restartResources(m.getWorkloadName(), "default")){
				return true;
			}
		}
		return false;
	}
	@Override
	public List<PropBean> getModuleProperty(String moduleName) {
		
		Module module = mr.findByName(moduleName);
	
		List<PropBean> list = new ArrayList<PropBean>();
		if(module != null){
			String mn = module.getName();
			List<Property> props = pr.findByModuleId(module.getId());
			
			if(props != null){
				for(Property one :props){
					String key = one.getPropKey();
					String value = one.getPropValue();
					
					list.add(new PropBean(key,value));
				}
			}
		}
		return list;
	}

	@Override
	public boolean upateProp(String moduleName, List<PropBean> props) {

		boolean result = false;
		Module module = mr.findByName(moduleName);
		
		if(module != null){
			if(props != null){
				for(PropBean one:props){
					Property p = pr.findByModuleIdAndPropKey(module.getId(),one.getKey());
					
					if(p != null){
						p.setPropValue(one.getValue());
						
						pr.save(p);
						
						result = true;
					}
				}
			}
		}
			
		return result;
	}

}
