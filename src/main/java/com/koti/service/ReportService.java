package com.koti.service;

import java.util.List;

import com.koti.entity.CitizenPlan;
import com.koti.request.SearchRequest;

public interface ReportService {

	public List<String> getPlanNames();
	public List<String> getPlanStauses();
	public List<CitizenPlan> search(SearchRequest request);
	public boolean exportExcel();
	public boolean exportPdf();
} 
