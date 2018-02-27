package com.cmcc.omc.configcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmcc.omc.configcenter.bean.RestBean;
import com.cmcc.omc.configcenter.dao.dto.SctpLink;
import com.cmcc.omc.configcenter.service.SctpService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sctp")
@Slf4j
public class SctpApi {

	@Autowired
	SctpService service;

	@RequestMapping("/all")
	public List<SctpLink> all() {
		return service.getAll();
	}

	@RequestMapping("/change")
	public RestBean change(@RequestBody SctpLink link) {
		RestBean result = new RestBean();

		if (link.getType() == null) {
			result.setResult(300);
			result.setReason("need type");
			
			
		} else if (link.getType() != SctpLink.OperateType.add && link.getId() == null) {
			result.setResult(301);
			result.setReason("need id");
		}
		else if(link.getType() == SctpLink.OperateType.add && link.getId() != null){
			result.setResult(302);
			result.setReason("id must be null");
		}
		else {
			
			boolean flag = false;
			try{
				
				switch (link.getType()) {
				case add:
					
					flag = service.add(link);
					break;
				case del:
					
					flag = service.del(link);
					break;
				case modify:
					
					flag = service.modify(link);
					break;
				}
			}catch (Exception e) {
				log.warn("change bean ={}",link,e);
			}
			
			result.setResult(flag? 200:500);
			if(!flag){				
				result.setReason("opeate error");
			}
		}
		
		return result;
	}
}
