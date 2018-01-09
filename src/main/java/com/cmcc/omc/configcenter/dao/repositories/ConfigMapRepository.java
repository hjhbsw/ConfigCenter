package com.cmcc.omc.configcenter.dao.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cmcc.omc.configcenter.dao.dto.ConfigMap;

public interface ConfigMapRepository extends CrudRepository<ConfigMap, Integer> {

}
