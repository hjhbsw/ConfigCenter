package com.cmcc.omc.configcenter.dao.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cmcc.omc.configcenter.dao.dto.ConfigFile;

public interface ConfigFileReposity extends CrudRepository<ConfigFile, Integer> {
	List<ConfigFile> findByConfigMapId(Integer configMapId);
}
