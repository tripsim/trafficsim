package edu.trafficsim.engine;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

public class Engine {

	public static ObjectMapper mapper = new ObjectMapper();
	public static JsonFactory jsonFactory = new MappingJsonFactory(mapper);
	public static WKTWriter writer = new WKTWriter();
	public static WKTReader reader = new WKTReader();
}
