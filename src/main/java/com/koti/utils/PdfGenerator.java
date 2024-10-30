package com.koti.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.koti.entity.CitizenPlan;
import com.koti.request.SearchRequest;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class PdfGenerator {

	public void generate(HttpServletResponse response, SearchRequest search,List<CitizenPlan> records,File file ) throws Exception
	{
		// Initialize the document with A4 size and margins
				Document document = new Document(PageSize.A4, 20, 20, 30, 30);
				PdfWriter.getInstance(document, response.getOutputStream());
				PdfWriter.getInstance(document,new FileOutputStream(file));

				// Open the document to write content
				document.open();

				// Set fonts for title and table content
				Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
				Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
				Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);

				// Add title with centered alignment and spacing
				Paragraph title = new Paragraph("Citizen Plans Information", titleFont);
				title.setAlignment(Element.ALIGN_CENTER);
				title.setSpacingAfter(20);
				document.add(title);

				// Create a table with 8 columns and set widths
				PdfPTable table = new PdfPTable(8);
				table.setWidthPercentage(100); // Table width as 100% of the page
				table.setSpacingBefore(10f);
				table.setSpacingAfter(10f);
				table.setWidths(new float[] { 1.5f, 3.5f, 2f, 3f, 2.5f, 2.5f, 2.5f, 2.5f }); // Column widths

				// Define header cell style
				PdfPCell headerCell = new PdfPCell();
				headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
				headerCell.setPadding(5);
				headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

				// Add table headers with style
				String[] headers = { "Id", "Holder Name", "Gender", "Plan Name", "Plan Status", "Start Date", "End Date",
						"Benefit Amount" };

				for (String header : headers) {
					headerCell.setPhrase(new Phrase(header, headerFont));
					table.addCell(headerCell);
				}

				// Add data rows to the table
				for (CitizenPlan plan : records) {
					table.addCell(new PdfPCell(new Phrase(String.valueOf(plan.getCitizenId()), contentFont)));
					table.addCell(new PdfPCell(new Phrase(plan.getCitizenName(), contentFont)));
					table.addCell(new PdfPCell(new Phrase(plan.getGender(), contentFont)));
					table.addCell(new PdfPCell(new Phrase(plan.getPlanName(), contentFont)));
					table.addCell(new PdfPCell(new Phrase(plan.getPlanStatus(), contentFont)));

					if (null != plan.getPlanStartDate()) {
						table.addCell(new PdfPCell(new Phrase(String.valueOf(plan.getPlanStartDate()), contentFont)));
					} else {
						table.addCell(new PdfPCell(new Phrase("N/A", contentFont)));
					}

					if (null != plan.getPlanStartDate()) {
						table.addCell(new PdfPCell(new Phrase(String.valueOf(plan.getPlanEndDate()), contentFont)));
					} else {
						table.addCell(new PdfPCell(new Phrase("N/A", contentFont)));
					}

					if (null != plan.getPlanStartDate()) {
						table.addCell(new PdfPCell(new Phrase(String.valueOf(plan.getBenefitAmount()), contentFont)));
					} else {
						table.addCell(new PdfPCell(new Phrase("N/A", contentFont)));
					}
				}

				// Add the table to the document
				document.add(table);

				// Close the document
				document.close();
		
	}
}
