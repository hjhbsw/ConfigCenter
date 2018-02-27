package com.cmcc.omc.configcenter.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.cmcc.omc.configcenter.dao.dto.SctpLink;

public interface SctpReposity extends JpaRepository<SctpLink, Integer>,CrudRepository<SctpLink, Integer>{

}
