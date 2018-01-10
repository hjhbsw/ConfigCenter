package com.cmcc.omc.configcenter.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmcc.omc.configcenter.dao.dto.ConfigFile;
import com.cmcc.omc.configcenter.dao.dto.ConfigMap;
import com.cmcc.omc.configcenter.dao.repositories.ConfigFileReposity;
import com.cmcc.omc.configcenter.dao.repositories.ConfigMapRepository;

@Controller
@RequestMapping("/cm")
public class ConfigMapController {

	@Autowired
	ConfigMapRepository cmRsp;

	@Autowired
	ConfigFileReposity cfRsp;

	@RequestMapping("/index")
	public String openConfigMap(Integer id, Model model) {
		ConfigMap cm = cmRsp.findByModuleId(id);

		model.addAttribute("moduleId", id);
		if (cm != null) {
			model.addAttribute("configMap", cm);

			List<ConfigFile> cfs = cfRsp.findByConfigMapId(cm.getId());

			if (cfs != null && cfs.size() > 0) {
				model.addAttribute("cfs", cfs);
			}
		}
		return "cmIndex";
	}

	@RequestMapping("/add")
	public String addConfigMap(ConfigMap cm, RedirectAttributes red) {

		String result = "add Config Map Success";
		if (StringUtils.isEmpty(cm.getName())) {
			result = "name is empty";

		} else if (StringUtils.isEmpty(cm.getNameSpace())) {
			result = "nameSpace is empty";
		} else {
			cmRsp.save(cm);
		}

		red.addFlashAttribute("message", result);
		red.addAttribute("id", cm.getModuleId());
		return "redirect:/cm/index";
	}

	@RequestMapping("/update")
	public String updateConfigMap(ConfigMap cm, RedirectAttributes red) {

		String result = "update Config Map Success";
		if (StringUtils.isEmpty(cm.getName())) {
			result = "name is empty";

		} else if (StringUtils.isEmpty(cm.getNameSpace())) {
			result = "nameSpace is empty";
		} else {
			cmRsp.save(cm);
		}

		red.addFlashAttribute("message", result);
		red.addAttribute("id", cm.getModuleId());
		return "redirect:/cm/index";
	}

	@PostMapping("/addFile")
	public String addConfigFile(@RequestParam("file") MultipartFile file, Integer configMapId, Integer moduleId,
			RedirectAttributes red) {

		if (file.isEmpty()) {
			red.addFlashAttribute("message", "file is empty");
		} else if (file.getSize() > 10000) {
			red.addFlashAttribute("message", "file must less than 1k");
		} else {
			ConfigFile cf = new ConfigFile();
			cf.setConfigMapId(configMapId);
			cf.setFileName(file.getOriginalFilename());

			try {
				byte[] bs = file.getBytes();
				cf.setData(new String(bs));
				cfRsp.save(cf);
				red.addFlashAttribute("message", "save file success");
			} catch (IOException e) {
				e.printStackTrace();
				red.addFlashAttribute("message", "read file error");
			}

		}
		red.addAttribute("id", moduleId);
		return "redirect:/cm/index";
	}

	@PostMapping("/updateFile")
	public String uploadConfigFile(@RequestParam("file") MultipartFile file, Integer id, Integer configMapId,
			Integer moduleId, RedirectAttributes red) {

		if (file.isEmpty()) {
			red.addFlashAttribute("message", "file is empty");
		} else if (file.getSize() > 10000) {
			red.addFlashAttribute("message", "file must less than 1k");
		} else {
			ConfigFile cf = new ConfigFile();
			cf.setId(id);
			cf.setFileName(file.getOriginalFilename());
			cf.setConfigMapId(configMapId);

			try {
				byte[] bs = file.getBytes();
				cf.setData(new String(bs));
				cfRsp.save(cf);
			} catch (IOException e) {
				e.printStackTrace();
				red.addFlashAttribute("message", "read file error");
			}

		}
		red.addAttribute("id", moduleId);
		return "redirect:/cm/index";
	}

	@RequestMapping("/delCf")
	public String delFile(Integer id, Integer moduleId, RedirectAttributes red) {

		cfRsp.delete(id);
		red.addFlashAttribute("message", "delete file success");
		red.addAttribute("id", moduleId);
		return "redirect:/cm/index";
	}
}
