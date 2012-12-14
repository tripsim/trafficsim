package edu.trafficsim.framework.actions;

import javax.servlet.http.HttpServletRequest;

import edu.trafficsim.framework.model.Model;

public class DemoAction extends Action {

	public DemoAction (Model model) {
		
	}
	
	@Override
	public String getName() {
		return "demo.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		return "demo.html";
	}

}
