package com.cmcc.omc.configcenter.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cmcc.omc.configcenter.dao.dto.SctpLink;
import com.cmcc.omc.configcenter.dao.repositories.SctpReposity;

@Service
public class SctpRedisAndDbImpl implements SctpService {

	@Autowired
	SctpReposity rep;
	
	@Autowired
	private StringRedisTemplate template;
	

	@Override
	public List<SctpLink> getAll() {
		
		return rep.findAll();
	}

	
	@Override
	public boolean add(SctpLink link) {
		
		rep.save(link);				
		
		this.updateRedia(link);
		return true;
	}

	@Override
	public boolean del(SctpLink link) {

		rep.delete(link.getId());
		this.updateRedia(link);
		return true;
	}

	@Override
	public boolean modify(SctpLink link) {
	
		rep.save(link);
		this.updateRedia(link);
		return true;
	}

	private void updateRedia(SctpLink link){
		template.convertAndSend(SCTP_OPEATE, JSON.toJSONString(link));
		
		List<SctpLink> list = rep.findAll();
		template.boundValueOps(SCTP_ALL).set(JSON.toJSONString(list));
	}
	private static final String SCTP_ALL = "SCTP_ALL";
	
	private static final String SCTP_OPEATE = "SCTP_CHANGE";
}
