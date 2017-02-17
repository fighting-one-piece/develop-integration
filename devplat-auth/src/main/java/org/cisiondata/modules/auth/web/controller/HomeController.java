package org.cisiondata.modules.auth.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping(value = {"/{portal:portal;?.*}"})
    public String protalView(Model model) {
        return "/front/portal";
    }
	
	@RequestMapping(value = {"/admin/{portal:portal;?.*}"})
    public String adminView(Model model) {
        return "/admin/portal";
    }
	
}
