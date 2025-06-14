package com.imshiivam.proxy.services.impl;

import com.imshiivam.proxy.services.ProxyServices;
import jakarta.servlet.http.HttpServletRequest;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class ProxyServicesImpl implements ProxyServices {

	final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<?> doProxy(HttpServletRequest request) {

		try {
			List<String> supportedMethods = Arrays.asList("POST", "GET", "PATCH", "PUT");

			if (!supportedMethods.contains(request.getMethod().toUpperCase())) {
				return ResponseEntity.status(405).body("Method Not Allowed");
			}

			String targetUrl = request.getHeader("X-Target-URL");
			if (targetUrl == null || targetUrl.isEmpty()) {
				return ResponseEntity.badRequest().body("Missing 'X-Target-URL' header");
			}

			Map<String, String> headers = new HashMap<>();
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				headers.put(headerName, request.getHeader(headerName));
			}
			headers.remove("host");
			headers.put("via", "1.1 shivam-proxy");

			// Query params
			Map<String, Object> queryParams = new HashMap<>();
			Enumeration<String> parameterNames = request.getParameterNames();
			while (parameterNames.hasMoreElements()) {
				String name = parameterNames.nextElement();
				queryParams.put(name, request.getParameter(name));
			}

			HttpResponse<byte[]> response = null;

			switch (request.getMethod().toUpperCase()) {
				case "GET":
					response = Unirest.get(targetUrl).headers(headers).queryString(queryParams).asBytes();
					break;
				case "POST":
					response = Unirest.post(targetUrl).headers(headers).queryString(queryParams)
							.body(getBody(request)).asBytes();
					break;
				case "PUT":
					response = Unirest.put(targetUrl).headers(headers).queryString(queryParams)
							.body(getBody(request)).asBytes();
					break;
				case "PATCH":
					response = Unirest.patch(targetUrl).headers(headers).queryString(queryParams)
							.body(getBody(request)).asBytes();
					break;
			}

			if (response == null) {
				return ResponseEntity.status(502).body("No response from target");
			}

			HttpHeaders responseHeaders = new HttpHeaders();
			response.getHeaders().all().forEach(h -> responseHeaders.add(h.getName(), h.getValue()));

			String contentType = response.getHeaders().getFirst("content-type");

			if (contentType != null && contentType.contains("image")) {
				return ResponseEntity.status(response.getStatus()).headers(responseHeaders).body(response.getBody());
			} else {
				String body = new String(response.getBody()).replaceAll("https://", "http://");
				return ResponseEntity.status(response.getStatus()).headers(responseHeaders).body(body);
			}

		} catch (Exception e) {
			log.error("Proxy error", e);
			return ResponseEntity.internalServerError().body("Proxy Error: " + e.getMessage());
		}
	}

	private String getBody(HttpServletRequest request) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuilder body = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) body.append(line);
		return body.toString();
	}
}
