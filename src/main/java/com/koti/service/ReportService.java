package com.koti.service;

import java.net.http.HttpResponse;
import java.util.List;

import com.koti.entity.CitizenPlan;
import com.koti.request.SearchRequest;

import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {

	public List<String> getPlanNames();
	public List<String> getPlanStauses();
	public List<CitizenPlan> search(SearchRequest request);
	public boolean exportExcel(HttpServletResponse response,SearchRequest search) throws Exception;
	public boolean exportPdf(HttpServletResponse response,SearchRequest search)throws Exception;
} 
