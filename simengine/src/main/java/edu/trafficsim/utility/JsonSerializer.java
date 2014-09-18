/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * 
 * @author Xuan Shi
 */
public final class JsonSerializer {

	private final ObjectMapper mapper;
	private final JsonFactory jsonFactory;

	private static class SerializerHolder {
		private static JsonSerializer serializer = new JsonSerializer();
	}

	private JsonSerializer() {
		mapper = new ObjectMapper();
		jsonFactory = new MappingJsonFactory(mapper);
	}

	private static ObjectMapper getMapper() {
		return SerializerHolder.serializer.mapper;
	}

	public static <T> T fromJson(String jsonString, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		return getMapper().readValue(jsonString, clazz);
	}

	public static String toJson(Object object) throws JsonProcessingException {
		return getMapper().writeValueAsString(object);
	}

	private static JsonFactory getJsonFactory() {
		return SerializerHolder.serializer.jsonFactory;
	}

	public static JsonGenerator createJsonGenerator(OutputStream output)
			throws IOException {
		return getJsonFactory().createGenerator(output);
	}

	public static JsonParser createJsonParser(InputStream input)
			throws JsonParseException, IOException {
		return getJsonFactory().createParser(input);
	}

	public static JsonNode readTree(JsonParser jsonParser)
			throws JsonProcessingException, IOException {
		return getMapper().readTree(jsonParser);
	}

}
