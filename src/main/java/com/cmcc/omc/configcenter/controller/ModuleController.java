package com.cmcc.omc.configcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmcc.omc.configcenter.dao.dto.Module;
import com.cmcc.omc.configcenter.dao.repositories.ConfigFileReposity;
import com.cmcc.omc.configcenter.dao.repositories.ConfigMapRepository;
import com.cmcc.omc.configcenter.dao.repositories.ModuleRepository;
import com.cmcc.omc.configcenter.dao.repositories.ProperityRepository;

@Controller
@RequestMapping("/module")
public class ModuleController {
	
	@Autowired
	ModuleRepository mRep;
	
	@Autowired
	ConfigMapRepository cmRsp;
	
	@Autowired
	ConfigFileReposity cfRsp;
	
	@Autowired
	ProperityRepository pRsp;
	
	@RequestMapping("/index")
	public String list(Model model){
		
		Iterable<Module> listModule =  mRep.findAll();
		model.addAttribute("modules", listModule);
		
		return "moduleIndex";
		
	}
	
	@RequestMapping("/add")
	public String add(Module module,RedirectAttributes red) {
		
		String result = "add Success";
		
		if(StringUtils.isEmpty(module.getName())){
			result = "Name is Empty";
		}else if(StringUtils.isEmpty(module.getUpdateName())){
			result = "UpdateName is Empty";
		}else{
			mRep.save(module);
		}
		red.addFlashAttribute("message", result);
		
		return "redirect:/module/index";
	}
	
	@RequestMapping("/del")
	public String del(Integer id,RedirectAttributes red){
		mRep.delete(id);
		
		red.addFlashAttribute("message", "Delete id="+id+"success");
		
		return "redirect:/module/index";
	}
	
	@RequestMapping("/modify")
	public String openModifyPage(Integer id,Model model){
		Module m = mRep.findOne(id);
		model.addAttribute("id", m.getId());
		model.addAttribute("name", m.getName());
		model.addAttribute("updateName", m.getUpdateName());
		return "moduleModify";
	}
	
	@RequestMapping("/update")
	public String update(Module m,RedirectAttributes red){
		
		return "redirect:/module/index";
	}
}
