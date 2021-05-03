package com.littlecorgi.courseji;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志打印
 *
 * @author littlecorgi
 * @date 2021/5/3
 */
@Slf4j
public class LoggableDispatcherServlet extends DispatcherServlet {

    private static final Logger logger = LoggerFactory.getLogger("HttpLogger");

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        //创建一个 json 对象，用来存放 http 日志信息
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("uri", requestWrapper.getRequestURI());
        rootNode.put("clientIp", requestWrapper.getRemoteAddr());
        rootNode.set("requestHeaders", mapper.valueToTree(getRequestHeaders(requestWrapper)));
        String method = requestWrapper.getMethod();
        rootNode.put("method", method);
        try {
            super.doDispatch(requestWrapper, responseWrapper);
        } finally {
            if (method.equals("GET")) {
                rootNode.set("request", mapper.valueToTree(requestWrapper.getParameterMap()));
            } else {
                JsonNode newNode = mapper.readTree(requestWrapper.getContentAsByteArray());
                rootNode.set("request", newNode);
            }

            rootNode.put("status", responseWrapper.getStatus());
            JsonNode newNode = mapper.readTree(responseWrapper.getContentAsByteArray());
            rootNode.set("response", newNode);

            responseWrapper.copyBodyToResponse();

            rootNode.set("responseHeaders", mapper.valueToTree(getResponseHeaders(responseWrapper)));
            logger.info(rootNode.toString());
        }
    }

    private Map<String, Object> getRequestHeaders(HttpServletRequest request) {
        Map<String, Object> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;

    }

    private Map<String, Object> getResponseHeaders(ContentCachingResponseWrapper response) {
        Map<String, Object> headers = new HashMap<>();
        Collection<String> headerNames = response.getHeaderNames();
        for (String headerName : headerNames) {
            headers.put(headerName, response.getHeader(headerName));
        }
        return headers;
    }
}