package com.koti.service.impl;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.koti.entity.CitizenPlan;
import com.koti.repo.CitizenPlansRepository;
import com.koti.request.SearchRequest;
import com.koti.service.ReportService;
import com.koti.utils.EmailUtils;
import com.koti.utils.ExcelGenerator;
import com.koti.utils.PdfGenerator;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlansRepository repo;
	@Autowired
	private ExcelGenerator excelGenerator;
	@Autowired
	private PdfGenerator pdfGenerator;
	@Autowired
	private EmailUtils email;

	/**
	 * Retrieves plan names from the database.
	 * 
	 * @return List of plan names
	 */
	@Override
	public List<String> getPlanNames() {

		return repo.getPlanNames();// Query plan names from repository
	}

	/**
	 * Retrieves plan statuses from the database.
	 * 
	 * @return List of plan statuses
	 */
	@Override
	public List<String> getPlanStauses() {
		return repo.getPlanStatus(); // Query plan statuses from repository
	}

	/**
	 * Searches citizen plans based on the given search criteria.
	 * 
	 * @param request Search criteria
	 * @return List of matching CitizenPlan objects request from search method in
	 *         controller class
	 */

	@Override
	public List<CitizenPlan> search(SearchRequest request) {
		CitizenPlan entity = new CitizenPlan();

		if (request.getPlanName() != null && !request.getPlanName().isEmpty()) {
			entity.setPlanName(request.getPlanName());// validating plan name is not empty and not null
		}
		if (request.getPlanStatus() != null && !request.getPlanStatus().isEmpty()) {
			entity.setPlanStatus(request.getPlanStatus());
		}
		if (request.getGender() != null && !request.getGender().isEmpty()) {
			entity.setGender(request.getGender());
		}
		if (request.getPlanStartDate() != null && !request.getPlanStartDate().isEmpty()) {
			LocalDate startDate = LocalDate.parse(request.getPlanStartDate());
			entity.setPlanStartDate(startDate);
		}
		if (request.getPlanEndDate() != null && !request.getPlanEndDate().isEmpty()) {
			LocalDate endDate = LocalDate.parse(request.getPlanEndDate());
			entity.setPlanEndDate(endDate);
		}

		/*
		 * ex: select * from reports where planName=:planName like this it will fetch
		 * data asper form selection
		 */
		// Execute dynamic query based on example entity
		return repo.findAll(Example.of(entity));
	}

	/**
	 * Exports filtered data to Excel and sends via email.
	 * 
	 * @param response HttpServletResponse to write Excel data
	 * @param search   Search criteria
	 * @return true if successful
	 */
	@Override
	public boolean exportExcel(HttpServletResponse response, SearchRequest search) throws Exception {
		File file = new File("plans.xls");
		List<CitizenPlan> records = search(search); // Fetch data
		excelGenerator.generate(response, search, records, file); // Generate Excel

		/*
		 * String subject = "Reports Plans Information"; String
		 * body="<h1>This Body Belongs To Reports Plans Information</h1> "; String to
		 * ="nambulagopi999@gmail.com";
		 */

		email.sendEmail("Reports Plans Information", "<h1>This Body Belongs To Reports Plans Information</h1>",
				"nambulagopi999@gmail.com", file); // Send email
		file.delete(); // Clean up file
		return true;
	}

	/**
	 * Exports filtered data to PDF and sends via email.
	 * 
	 * @param response HttpServletResponse to write PDF data
	 * @param search   Search criteria
	 * @return true if successful
	 */
	@Override
	public boolean exportPdf(HttpServletResponse response, SearchRequest search) throws Exception {
		File file = new File("plans.pdf");
		List<CitizenPlan> records = search(search); // Fetch data
		pdfGenerator.generate(response, search, records, file); // Generate PDF
		email.sendEmail("Reports Plans Information", "<h1>This Body Belongs To Reports Plans Information</h1>",
				"nambulagopi999@gmail.com", file); // Send email
		file.delete(); // Clean up file
		return true;
	}
}
