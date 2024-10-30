package com.koti.request;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class SearchRequest {

	private String planName;
	private String planStatus;
	private String gender;
	private String planStartDate;
	private String planEndDate;
	
}
