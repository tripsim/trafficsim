package edu.trafficsim.web;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SimulationProject extends AbstractProject {

	public SimulationProject() throws UserInterfaceException {

	}

	// TODO plugin type mapping
}
