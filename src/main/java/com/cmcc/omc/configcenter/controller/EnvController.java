package com.cmcc.omc.configcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmcc.omc.configcenter.dao.dto.Environment;
import com.cmcc.omc.configcenter.dao.dto.K8sAdapter;
import com.cmcc.omc.configcenter.dao.repositories.EnvRepository;
import com.cmcc.omc.configcenter.enums.K8sApiEnum;
import com.cmcc.omc.configcenter.enums.NetElementTypeEnum;
import com.cmcc.omc.configcenter.service.ConfigService;

@Controller
@RequestMapping(path = "/env")
public class EnvController {

	@Autowired
	EnvRepository rep;

	@Autowired
	ConfigService service;

	@RequestMapping(path = "/add")
	public String addNewEnv(Environment env,
			RedirectAttributes red) {
		
		
		red.addFlashAttribute("message", service.addEnvironment(env));
		
		return "redirect:/env/index";
	}

	@RequestMapping("/del")
	public String delete(Integer id,RedirectAttributes red){
		
		service.delEnv(id);
		
		red.addFlashAttribute("message", "delete success");
		
		return "redirect:/env/index";
	}
	
	@RequestMapping("/modify")
	public String openModifyPage(Integer id,Model model){
		Environment env = service.fetchEnv(id);
		
		model.addAttribute("id", env.getId());
		model.addAttribute("name", env.getName());
		model.addAttribute("descript", env.getDescript());
		model.addAttribute("type", env.getType());
		model.addAttribute("adapterId", env.getAdapter().getId());
		model.addAttribute("adapterType", env.getAdapter().getApiType().ordinal());
		model.addAttribute("adapterUrl", env.getAdapter().getUrl());
		return "modiPage";
	}
	@RequestMapping(path = "/update")
	public String update(Environment env,RedirectAttributes red) {
		
		red.addFlashAttribute("message", service.update(env));
		return "redirect:/env/index";
	}

	@GetMapping(path = "/index")
	public String getAllEnv(Model model, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer size) {

		Page<Environment> pageEnv = service.pageEnvironment(page, size);
		model.addAttribute("totalPage", pageEnv.getTotalPages());
		model.addAttribute("envs", pageEnv.getContent());
		model.addAttribute("number", pageEnv.getSize());
		model.addAttribute("total", pageEnv.getTotalElements());
		model.addAttribute("page", page);
		model.addAttribute("size", size);
		return "envIndex";
	}
}
