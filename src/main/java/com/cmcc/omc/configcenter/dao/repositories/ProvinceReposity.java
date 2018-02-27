package com.cmcc.omc.configcenter.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.cmcc.omc.configcenter.dao.dto.Province;

public interface ProvinceReposity extends JpaRepository<Province, Integer>,CrudRepository<Province, Integer>{

}
