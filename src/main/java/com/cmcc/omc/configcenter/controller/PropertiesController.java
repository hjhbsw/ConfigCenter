package com.cmcc.omc.configcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmcc.omc.configcenter.dao.dto.ConfigFile;
import com.cmcc.omc.configcenter.dao.dto.Property;
import com.cmcc.omc.configcenter.dao.repositories.ConfigFileReposity;
import com.cmcc.omc.configcenter.dao.repositories.ProperityRepository;

@Controller
@RequestMapping("/prop")
public class PropertiesController {

	@Autowired
	ConfigFileReposity cfRsp;

	@Autowired
	ProperityRepository propRsp;

	@RequestMapping("/index")
	public String index(Integer id, Model model) {
		ConfigFile cf = cfRsp.findOne(id);

		model.addAttribute("configFile", cf);

		List<Property> props = propRsp.findByConfigFileId(id);

		if (props != null && props.size() > 0) {
			model.addAttribute("props", props);
		}
		return "PropIndex";
	}

	@RequestMapping("/add")
	public String addProperty(Property prop, @SessionAttribute("moduleId") Integer moduleId, RedirectAttributes red) {

		String result = "add Property Success";

		if (StringUtils.isEmpty(prop.getPropKey())) {
			result = "Prop key is empty";
		} else if (StringUtils.isEmpty(prop.getPropValue())) {
			result = "Prop value is empty";
		} else if (StringUtils.isEmpty(prop.getPlaceHolder())) {
			result = "Place Holder is empty";
		} else {
			prop.setModuleId(moduleId);

			try {

				propRsp.save(prop);
			} catch (DataIntegrityViolationException e) {
				result = "This module has confilct propKey = " + prop.getPropKey();
			}
		}

		red.addFlashAttribute("message", result);
		red.addAttribute("id", prop.getConfigFileId());
		return "redirect:/prop/index";
	}

	@RequestMapping("/del")
	public String deleteProperty(Integer id, RedirectAttributes red) {

		Property prop = propRsp.findOne(id);
		propRsp.delete(id);

		red.addFlashAttribute("message", "delete property Suceess");
		red.addAttribute("id", prop.getConfigFileId());
		return "redirect:/prop/index";
	}

	@RequestMapping("/open")
	public String openModifyPage(Integer id, Model model) {
		Property prop = propRsp.findOne(id);

		model.addAttribute("prop", prop);
		return "propModify";
	}

	@RequestMapping("/update")
	public String update(Property prop, RedirectAttributes red) {
		String result = "update Property Success";

		if (StringUtils.isEmpty(prop.getPropKey())) {
			result = "Prop key is empty";
		} else if (StringUtils.isEmpty(prop.getPropValue())) {
			result = "Prop value is empty";
		} else if (StringUtils.isEmpty(prop.getPlaceHolder())) {
			result = "Place Holder is empty";
		} else {

			try {

				propRsp.save(prop);
			} catch (DataIntegrityViolationException e) {
				result = "This module has conflict propKey = " + prop.getPropKey();
			}
		}

		red.addFlashAttribute("message", result);
		red.addAttribute("id", prop.getConfigFileId());
		return "redirect:/prop/index";
	}
}
