package com.cmcc.omc.configcenter.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.cmcc.omc.configcenter.dao.dto.Module;

public interface ModuleRepository extends CrudRepository<Module, Integer>{

	public Module findByName(String name);
}
