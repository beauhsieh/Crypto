package com.crypto.crypto.configurations.interceptor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.crypto.crypto.filter.FormDataXssRequest;
import com.crypto.crypto.filter.XssHttpServletRequestBodyWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LoggerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // set log4j2 paramenters
        Map<String, String> parameters = getHeaders(request);
        setThreadContext(parameters);

        if (parameters.containsKey("XRequestID"))
            response.setHeader("X-Request-ID", parameters.get("XRequestID"));

        return super.preHandle(request, response, handler);

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (!request.getMethod().equalsIgnoreCase("GET") && !request.getRequestURI().contains("/auth/")) {
            log(request, response);
            clearThreadContext();
            super.afterCompletion(request, response, handler, ex);
        }
    }

    private void log(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ThreadContext.put("HttpStatus", String.valueOf(status.value()));

        if (status.is2xxSuccessful()) {
            if ((request instanceof ContentCachingRequestWrapper)
                    && (response instanceof ContentCachingResponseWrapper)) {
                logger.info(createLoggerMessage((ContentCachingRequestWrapper) request,
                        (ContentCachingResponseWrapper) response));
            } else if (request instanceof StandardMultipartHttpServletRequest
                    && (response instanceof ContentCachingResponseWrapper)) {
                logger.info(createLoggerMessage((ContentCachingResponseWrapper) response));
            } else if (request instanceof StandardMultipartHttpServletRequest) {
                logger.info(createLoggerMessage((StandardMultipartHttpServletRequest) request));
            } else if (request instanceof XssHttpServletRequestBodyWrapper
                    && (response instanceof ContentCachingResponseWrapper)) {
                logger.info(createLoggerMessage((XssHttpServletRequestBodyWrapper) request,
                        (ContentCachingResponseWrapper) response));
            } else if (request instanceof FormDataXssRequest
                    && (response instanceof ContentCachingResponseWrapper)) {
                logger.info(createLoggerMessage((ContentCachingResponseWrapper) response));
            }
        } else {
            if ((request instanceof ContentCachingRequestWrapper)
                    && (response instanceof ContentCachingResponseWrapper)) {
                logger.error(createLoggerMessage((ContentCachingRequestWrapper) request,
                        (ContentCachingResponseWrapper) response));
            } else if (request instanceof StandardMultipartHttpServletRequest
                    && (response instanceof ContentCachingResponseWrapper)) {
                logger.error(createLoggerMessage((ContentCachingResponseWrapper) response));
            } else if (request instanceof XssHttpServletRequestBodyWrapper
                    && (response instanceof ContentCachingResponseWrapper)) {
                logger.error(createLoggerMessage((ContentCachingResponseWrapper) response));
            } else if (request instanceof FormDataXssRequest
                    && (response instanceof ContentCachingResponseWrapper)) {
                logger.error(createLoggerMessage((ContentCachingResponseWrapper) response));
            }
        }

        // clean thread context
        clearThreadContext();
    }

    private String createLoggerMessage(XssHttpServletRequestBodyWrapper request, ContentCachingResponseWrapper response) throws IOException {
        ApiLogMessage message = new ApiLogMessage();
        String requestMessage = new String(request.getRequestPostStr(request)).replaceAll("[\\t\\n\\r ]", "");
        message.setRequest(requestMessage);

        byte[] responseBytes = response.getContentAsByteArray();
        if ((responseBytes != null) && (responseBytes.length > 0)) {
            String responseMessage = new String(responseBytes).replaceAll("[\\t\\n\\r ]", "");
            message.setResponse(responseMessage);
        }

        message.setHeaders(getHeaders(request));
        return message.toString();
    }

    private String createLoggerMessage(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
        ApiLogMessage message = new ApiLogMessage();
        byte[] requestBytes = request.getContentAsByteArray();
        if ((requestBytes != null) && (requestBytes.length > 0)) {
            String requestMessage = new String(requestBytes).replaceAll("[\\t\\n\\r ]", "");
            message.setRequest(requestMessage);
        }

        byte[] responseBytes = response.getContentAsByteArray();
        if ((responseBytes != null) && (responseBytes.length > 0)) {
            String responseMessage = new String(responseBytes).replaceAll("[\\t\\n\\r ]", "");
            message.setResponse(responseMessage);
        }

        message.setHeaders(getHeaders(request));
        return message.toString();
    }

    private String createLoggerMessage(ContentCachingResponseWrapper response) {
        ApiLogMessage message = new ApiLogMessage();
        byte[] responseBytes = response.getContentAsByteArray();
        if ((responseBytes != null) && (responseBytes.length > 0)) {
            String responseMessage = new String(responseBytes).replaceAll("[\\t\\n\\r ]", "");
            message.setResponse(responseMessage);
        }

        return message.toString();
    }

    private String createLoggerMessage(StandardMultipartHttpServletRequest request) {
        ApiLogMessage message = new ApiLogMessage();
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> paraKeySet = parameterMap.keySet();
        for (String key: paraKeySet) {
            if (parameterMap.get(key).length == 1) {
                stringBuilder.append(key + ":\"" + parameterMap.get(key)[0] + "\", ");
            }
        }
        message.setRequest(stringBuilder.toString());


        message.setHeaders(getHeaders(request));
        return message.toString();
    }

    private void setThreadContext(Map<String, String> parameters) {
        if (!parameters.isEmpty())
            ThreadContext.putAll(parameters);
    }

    private void clearThreadContext() {
        ThreadContext.clearAll();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    class ApiLogMessage {
        private Object request;
        private Object response;
        private Map<String, String> headers;

        public ApiLogMessage() {
        }

        public ApiLogMessage(Object request, Object response) {
            super();
            this.request = request;
            this.response = response;
        }

        public Object getRequest() {
            return request;
        }

        public void setRequest(Object request) {
            this.request = request;
        }

        public Object getResponse() {
            return response;
        }

        public void setResponse(Object response) {
            this.response = response;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }

        @Override
        public String toString() {
            return "ApiLogMessage{" +
                    "request=" + request +
                    ", response=" + response +
                    ", headers=" + headers +
                    '}';
        }
    }

    protected static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<String, String>();
        String requestToken = (request.getHeader("X-Api-Key") != null) ? request.getHeader("X-Api-Key") : request.getHeader("X-Access-Token");
        parameters.put("XRequestID", getXRequestID(request));
        parameters.put("UserIP", getIpAddress());
        parameters.put("Method", request.getMethod());
        parameters.put("UserAgent", request.getHeader("User-Agent"));
        parameters.put("UserToken", requestToken);

        if ((request.getHeader("Accept-Language") != null)
                && (!StringUtils.isBlank(request.getHeader("Accept-Language"))))
            parameters.put("Accept-Language", request.getHeader("Accept-Language"));
        else
            parameters.put("Accept-Language", "en-US");

        String url = request.getRequestURI();
        if (StringUtils.isNotBlank(url)) {
            url = url.replaceAll("\r", "%0D").replaceAll("\n","%0A");
            if (StringUtils.isBlank(request.getQueryString())) {
                parameters.put("Url", url);
            } else {
                String queryString = request.getQueryString();
                queryString = queryString.replaceAll("\r", "%0D").replaceAll("\n","%0A");
                parameters.put("Url", String.format("%s?%s", url, queryString));
            }
        }

        return parameters;
    }

    private static String getXRequestID(HttpServletRequest requestWrapper) {
        String xRequestID = requestWrapper.getHeader("X-Request-ID");

        if (StringUtils.isBlank(xRequestID)) {
            xRequestID = UUID.randomUUID().toString();
        } else {
        }

        return xRequestID;
    }

    private static String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

