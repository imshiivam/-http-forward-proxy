package com.imshiivam.proxy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imshiivam.proxy.services.ProxyServices;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class ProxyController  {

	final Logger log1=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ProxyServices proxyService;

	
	@RequestMapping(value = "/**")
	public ResponseEntity handleProxyRequest(HttpServletRequest request) {

		log1.info("Receive {} for url {}",request.getMethod(),request.getRequestURL().toString());
		
		return proxyService.doProxy(request);

	}



}
