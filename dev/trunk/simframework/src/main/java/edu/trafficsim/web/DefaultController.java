package edu.trafficsim.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DefaultController {

	@RequestMapping(value = "/")
	public String home() {
		System.out.println("HomeController: Passing through...");
		return "index";
	}
	
//	@RequestMapping(value="/simulation")
//	public @ResponseBody String demoSimulation() {
//		
//		return "TT";
//	}
}
