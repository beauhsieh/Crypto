package com.crypto.crypto.filter;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "contentCachingFilter", urlPatterns = "/api/*")
public class ContentCachingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(
				(HttpServletResponse) response);

		try {
			chain.doFilter(requestWrapper, responseWrapper);
		} finally {

			// Do not forget this line after reading response content or actual
			// response will be empty!
			responseWrapper.copyBodyToResponse();
		}

	}

	@Override
	public void destroy() {
		System.out.println("This is content caching filter destroy");
	}

}
