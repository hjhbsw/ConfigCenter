package com.cmcc.omc.configcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmcc.omc.configcenter.bean.RestBean;
import com.cmcc.omc.configcenter.dao.dto.Province;
import com.cmcc.omc.configcenter.service.DisasterService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/province")
@Slf4j
public class DisasterApi {

	@Autowired
	DisasterService service;

	@RequestMapping("/back")
	public RestBean back(@RequestParam("id") Integer id, @RequestParam("operate") boolean operate) {

		return service.backSwitch(id, operate);
	}

	@RequestMapping("/all")
	public List<Province> all() {

		return service.all();
	}

	@RequestMapping("/change/{operate}")
	public RestBean change(@PathVariable("operate") Operate operate, @RequestBody Province province) {

		switch (operate) {
		case add:

			return service.add(province);
			
		case del:
			
			return service.del(province.getId()); 
			
		default:
			return service.modify(province);
		}
	}

	static enum Operate {
		add, del, modify;
	}
}
