package com.crypto.crypto.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter(filterName = "XssBodyFilter", urlPatterns = "/api/*")
public class XssBodyFilter implements Filter{

    FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request2 = (HttpServletRequest) request;
        if (request2.getRequestURI().contains("/notifications") || !request2.getMethod().equalsIgnoreCase("PUT")) {
            XssHttpServletRequestBodyWrapper xssRequest = new XssHttpServletRequestBodyWrapper((HttpServletRequest) request);
            chain.doFilter(xssRequest, response);
        } else {
            chain.doFilter(new FormDataXssRequest(request2),response);
        }
    }

}
