package edu.trafficsim.plugin;

import java.io.Serializable;

public interface IPlugin extends Serializable {

	String getName();

	void init() throws Exception;

	void upgrade() throws Exception;

	void activate() throws Exception;

	void deactivate() throws Exception;
}
