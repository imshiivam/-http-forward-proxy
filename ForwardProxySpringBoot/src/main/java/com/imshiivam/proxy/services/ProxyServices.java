package com.imshiivam.proxy.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ProxyServices {
	ResponseEntity<?> doProxy(HttpServletRequest request);
}
