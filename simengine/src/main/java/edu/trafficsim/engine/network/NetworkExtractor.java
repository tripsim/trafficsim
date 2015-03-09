package edu.trafficsim.engine.network;

import java.io.InputStream;

public interface NetworkExtractor {

	NetworkExtractResult extract(String urlStr, String name);

	NetworkExtractResult extractByXml(InputStream input, String name);

	NetworkExtractResult extractByJson(InputStream input, String name);

}
