/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
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
