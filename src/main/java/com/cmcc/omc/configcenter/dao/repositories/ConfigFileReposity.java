package com.cmcc.omc.configcenter.dao.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cmcc.omc.configcenter.dao.dto.ConfigFile;

public interface ConfigFileReposity extends CrudRepository<ConfigFile, Integer> {

}
