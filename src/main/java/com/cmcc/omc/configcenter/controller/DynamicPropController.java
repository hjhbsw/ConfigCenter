package com.cmcc.omc.configcenter.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmcc.omc.configcenter.dao.dto.DynamicProp;
import com.cmcc.omc.configcenter.dao.repositories.DynamicPropReposity;
import com.cmcc.omc.configcenter.service.ModuleService;

@Controller
@RequestMapping("/dy")
public class DynamicPropController {

	@Autowired
	DynamicPropReposity rep;

	@Autowired
	ModuleService service;

	@RequestMapping("/index")
	public String index(Integer id, Model model) {
		List<DynamicProp> list = rep.findByModuleId(id);

		model.addAttribute("moduleId", id);

		if (list != null && list.size() > 0) {
			model.addAttribute("dys", list);
		}

		return "dyIndex";
	}

	@RequestMapping("/add")
	public String add(DynamicProp prop, RedirectAttributes red) {
		

		String result = "add DynamicProp Success";

		if (StringUtils.isEmpty(prop.getTopic())) {
			result = "Topic is empty";
		} else if (StringUtils.isEmpty(prop.getJson())) {
			result = "Json is empty";
		} else if (null == prop.getModuleId()) {
			result = "Session is dead";
		} else {
			

			try {
				rep.save(prop);
			} catch (DataIntegrityViolationException e) {
				result = "This module has confilct topic = " + prop.getTopic();
			}
		}

		red.addFlashAttribute("message", result);
		red.addAttribute("id", prop.getModuleId());

		return "redirect:/dy/index";
	}

	@RequestMapping("/del")
	public String del(Integer id,Integer moduleId, RedirectAttributes red) {

		rep.delete(id);

		red.addFlashAttribute("message", "Del Success");
		red.addAttribute("id", moduleId);
		return "redirect:/dy/index";
	}

	@RequestMapping("/open")
	public String open(Integer id, Model model) {
		DynamicProp prop = rep.findOne(id);
		model.addAttribute("dynamic", prop);

		return "dyModify";
	}

	@RequestMapping("/update")
	public String update(DynamicProp prop, RedirectAttributes red) {
		String result = "update DynamicProperty Success";

		if (StringUtils.isEmpty(prop.getTopic())) {
			result = "Topic is empty";
		} else if (StringUtils.isEmpty(prop.getJson())) {
			result = "Json value is empty";
		} else {

			try {

				rep.save(prop);
			} catch (DataIntegrityViolationException e) {
				result = "This module has conflict topic = " + prop.getTopic();
			}
		}

		red.addFlashAttribute("message", result);
		red.addAttribute("id", prop.getModuleId());
		return "redirect:/dy/index";
	}

	@RequestMapping("/publish")
	public String publish(Integer id,Integer moduleId, RedirectAttributes red) {
		service.pushDynamic(id);

		red.addFlashAttribute("message", "Publish Success");
		red.addAttribute("id",moduleId);
		return "redirect:/dy/index";
	}
}
