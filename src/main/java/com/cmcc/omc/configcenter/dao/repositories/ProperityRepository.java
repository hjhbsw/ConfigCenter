package com.cmcc.omc.configcenter.dao.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cmcc.omc.configcenter.dao.dto.Property;

public interface ProperityRepository extends CrudRepository<Property, Integer> {

	List<Property> findByConfigFileId(Integer id);
	
	List<Property> findByModuleId(Integer id);
	
	Property findByModuleIdAndPropKey(Integer moduleId,String propKey);
}
