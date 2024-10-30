package com.koti.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.koti.entity.CitizenPlan;
import com.koti.request.SearchRequest;
import com.koti.service.ReportService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ReportController {

	@Autowired
	private ReportService service;

	/**
	 * Exports filtered data to PDF format.
	 * 
	 * @param search   Search criteria from form
	 * @param response HttpServletResponse to write PDF content
	 */
	@GetMapping("/pdf")
	public void exportPdf(@ModelAttribute("pdf") SearchRequest search, HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf"); // Set response type to PDF
		response.setHeader("Content-Disposition", "attachment;filename=plans.pdf"); // Set download filename
		service.exportPdf(response, search); // Call service method to generate PDF with filtered data
	}

	/**
	 * Exports filtered data to Excel format.
	 * 
	 * @param search   Search criteria from form
	 * @param response HttpServletResponse to write Excel content
	 */
	@GetMapping("/excel")
	public void exportExcel(@ModelAttribute("excel") SearchRequest search, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/octet-stream"); // Set response type to binary stream
		response.setHeader("Content-Disposition", "attachment;filename=plans.xls"); // Set download filename
		service.exportExcel(response, search); // Call service method to generate Excel with filtered data
	}

	/**
	 * Handles search requests from the UI form.
	 * 
	 * @param search Search criteria from form
	 * @param model  Model to pass data to the view
	 * @return index page
	 */
	@PostMapping("/search")
	public String handleSearch(@ModelAttribute("search") SearchRequest search, Model model) {
		List<CitizenPlan> plans = service.search(search); // Fetch filtered data
		model.addAttribute("plans", plans); // Add data to the model for UI
		init(model); // Populate dropdowns with plan names and statuses
		return "index"; // Return the index view
	}

	/**
	 * Loads the index page when the user accesses the root URL.
	 * 
	 * @param model Model to pass data to the view
	 * @return index page
	 */
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("search", new SearchRequest()); // Initialize search request object
		init(model); // Populate dropdowns with plan names and statuses
		return "index"; // Return the index view
	}

	/**
	 * Initializes plan names and statuses for form dropdowns.
	 * 
	 * @param model Model to pass data to the view
	 */
	private void init(Model model) {
		model.addAttribute("names", service.getPlanNames()); // Add plan names to model
		model.addAttribute("status", service.getPlanStauses()); // Add plan statuses to model
	}
}
