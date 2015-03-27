package org.tripsim.web.filter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UrlFilterTest {

	UrlFilter filter;

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	FilterChain chain;

	@Before
	public void setup() {
		filter = new UrlFilter();
		when(request.getScheme()).thenReturn("http");
		when(request.getServerName()).thenReturn("host");
		when(request.getServerPort()).thenReturn(80);
		when(request.getQueryString()).thenReturn("para1=abc&para2=123");
	}

	@Test
	public void testNotRedirect() throws IOException, ServletException {
		when(request.getRequestURI()).thenReturn("/a/ab/abc/");
		filter.doFilter(request, response, chain);
		verify(chain).doFilter(request, response);
		verifyNoMoreInteractions(response);
	}

	@Test
	public void testReplace() throws IOException, ServletException {
		when(request.getRequestURI()).thenReturn("//abc///abcdefg//");
		filter.doFilter(request, response, chain);
		verifyNoMoreInteractions(chain);

		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(response).sendRedirect(captor.capture());
		System.out.println(captor.getValue());
	}

}
