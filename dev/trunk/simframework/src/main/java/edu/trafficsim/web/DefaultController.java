package edu.trafficsim.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


public class DefaultController {

	@Controller
	public class HomeController {
	 
	    @RequestMapping(value = "/")
	    public String home() {
	        System.out.println("HomeController: Passing through...");
	        return "WEB-INF/views/home.jsp";
	    }
	}
}
