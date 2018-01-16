package com.cmcc.omc.configcenter.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmcc.omc.configcenter.dao.dto.DynamicProp;
import com.cmcc.omc.configcenter.dao.repositories.DynamicPropReposity;

@Controller
@RequestMapping("/dy")
public class DynamicPropController {

	@Autowired
	DynamicPropReposity rep;
	
	@RequestMapping("/index")
	public String index(Integer id,Model model,HttpSession session){
		List<DynamicProp> list = rep.findByModuleId(id);
		
		session.setAttribute("moduleId", id);
		
		if(list != null && list.size() > 0){
			model.addAttribute("dys", list);
		}
		
		return "dyIndex";
	}
}
