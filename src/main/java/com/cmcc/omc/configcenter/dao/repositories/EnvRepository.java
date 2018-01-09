package com.cmcc.omc.configcenter.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.cmcc.omc.configcenter.dao.dto.Environment;

public interface EnvRepository extends JpaRepository<Environment, Integer>,CrudRepository<Environment, Integer>{

}
