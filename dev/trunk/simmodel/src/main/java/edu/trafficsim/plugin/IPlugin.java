package edu.trafficsim.plugin;

import java.io.Serializable;

public interface IPlugin extends Serializable {

	public String getName();
	
	public void init() throws Exception;
	
	public void upgrade() throws Exception;
	
	public void activate() throws Exception;
	
	public void deactivate() throws Exception;
}
