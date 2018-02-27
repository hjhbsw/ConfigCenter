package com.cmcc.omc.configcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.cmcc.omc.configcenter.bean.RestBean;
import com.cmcc.omc.configcenter.dao.dto.Province;
import com.cmcc.omc.configcenter.dao.repositories.ProvinceReposity;

import lombok.Data;

@Service
public class DefaultDisasterService implements DisasterService {

	@Autowired
	ProvinceReposity resposity;

	@Autowired
	private StringRedisTemplate template;

	@Override
	public RestBean backSwitch(Integer id, boolean operate) {

		RestBean result = new RestBean();

		Province province = resposity.getOne(id);

		if (province == null) {
			result.setResult(401);
			result.setReason("error id");
			return result;
		}

		province.setBack(operate);

		resposity.save(province);

		if (operate) {
			template.boundSetOps(BACK_LIST).add(province.getName());

		} else {
			template.boundSetOps(BACK_LIST).remove(province.getName());
		}

		Switch bean = new Switch();
		bean.province = province.getName();
		bean.operate = operate;

		template.convertAndSend(BACK_UPDATE, JSON.toJSONString(bean));

		result.setResult(200);
		return result;
	}

	@Override
	public RestBean add(Province province) {

		RestBean result = new RestBean();
		if (province == null || StringUtils.isEmpty(province.getName())
				|| CollectionUtils.isEmpty(province.getIpsmgw())) {
			result.setResult(402);
			result.setReason("need full param");
			return result;
		}

		province.setBack(false);
		try {
			resposity.save(province);
		} catch (Exception e) {
			result.setResult(403);
			result.setReason("has save province");
			return result;
		}

		template.<String, String>boundHashOps(PROVINCE_KEY).put(province.getName(), JSON.toJSONString(province));

		template.convertAndSend(PROVINCE_UPDATE, JSON.toJSONString(province));

		result.setResult(200);

		return result;
	}

	@Override
	public RestBean del(Integer id) {

		RestBean result = new RestBean();

		Province province = resposity.getOne(id);

		if (province == null) {
			result.setResult(401);
			result.setReason("error id");
			return result;
		}

		resposity.delete(id);

		template.boundSetOps(BACK_LIST).remove(province.getName());

		Switch bean = new Switch();
		bean.province = province.getName();
		bean.operate = false;

		template.convertAndSend(BACK_UPDATE, JSON.toJSONString(bean));

		template.<String, String>boundHashOps(PROVINCE_KEY).delete(province.getName());
		result.setResult(200);

		return result;
	}

	@Override
	public RestBean modify(Province province) {
		RestBean result = new RestBean();
		if (province == null || province.getId() == null || StringUtils.isEmpty(province.getName())
				|| CollectionUtils.isEmpty(province.getIpsmgw())) {
			result.setResult(402);
			result.setReason("need full param");
			return result;
		}

		Province old = resposity.getOne(province.getId());

		if (old == null) {
			result.setResult(401);
			result.setReason("error id");
			return result;
		}
		
		province.setBack(old.getBack());

		try {
			resposity.save(province);
		} catch (Exception e) {
			result.setResult(403);
			result.setReason("has conflict province");
			return result;
		}

		template.<String, String>boundHashOps(PROVINCE_KEY).put(province.getName(), JSON.toJSONString(province));

		template.convertAndSend(PROVINCE_UPDATE, JSON.toJSONString(province));

		result.setResult(200);

		return result;
	}

	@Override
	public List<Province> all() {
		// TODO Auto-generated method stub
		return resposity.findAll();
	}

	@Data
	static class Switch {

		private String province;

		private boolean operate;
	}

	private static final String PROVINCE_KEY = "PROVINCE_KEY";

	private static final String BACK_LIST = "BACK_LIST";

	private static final String BACK_UPDATE = "BACK_UPDATE";

	private static final String PROVINCE_UPDATE = "PROVINCE_UPDATE";
}
