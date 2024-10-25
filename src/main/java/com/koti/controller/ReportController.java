package com.koti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.koti.service.ReportService;

@Controller
public class ReportController {

	@Autowired
	private ReportService service;
	
	@GetMapping("/")
	public String index()
	{
		return "index";
	}
}
