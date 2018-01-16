package com.cmcc.omc.configcenter.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.cmcc.omc.configcenter.dao.dto.DynamicProp;

public interface DynamicPropReposity extends JpaRepository<DynamicProp, Integer>,CrudRepository<DynamicProp, Integer>{

	public List<DynamicProp> findByModuleId(Integer id);
}
