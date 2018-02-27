package com.cmcc.omc.configcenter.service;

import java.util.List;

import com.cmcc.omc.configcenter.dao.dto.SctpLink;

public interface SctpService {

	public List<SctpLink> getAll();
	
	public boolean add(SctpLink link);
	
	public boolean del(SctpLink link);
	
	public boolean modify(SctpLink link);
}
