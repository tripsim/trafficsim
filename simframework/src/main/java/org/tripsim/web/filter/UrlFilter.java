package org.tripsim.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(UrlFilter.class);

	private static final String UNCLEAN_URI_REGX = "^.*//+.*$";
	private static final String SLASHES_REGX = "//+";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("UrlFilter: initialized url cleaning filter.");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String uri = httpRequest.getRequestURI();
		if (!uri.matches(UNCLEAN_URI_REGX)) {
			chain.doFilter(request, response);
			return;
		}

		uri = clean(uri);
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.sendRedirect(reconstructUrl(httpRequest, uri));
	}

	private String clean(String url) {
		return url.replaceAll(SLASHES_REGX, "/");
	}

	private String reconstructUrl(HttpServletRequest httpRequest, String newUri) {
		return httpRequest.getScheme()
				+ "://"
				+ httpRequest.getServerName()
				+ ("http".equals(httpRequest.getScheme())
						&& httpRequest.getServerPort() == 80
						|| "https".equals(httpRequest.getScheme())
						&& httpRequest.getServerPort() == 443 ? "" : ":"
						+ httpRequest.getServerPort())
				+ newUri
				+ (httpRequest.getQueryString() != null ? "?"
						+ httpRequest.getQueryString() : "");
	}

	@Override
	public void destroy() {
	}

}
