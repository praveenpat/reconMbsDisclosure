package com.mvmlabs.springboot.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.compare.doc.ComparedResults;

public class CusipRequest {

     String cusipId;
     
     String errorMessage;
     
     private CommonsMultipartFile fileData;
     
     
     public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<ComparedResults> getResults() {
		return results;
	}

	public void setResults(List<ComparedResults> results) {
		this.results = results;
	}

	List<ComparedResults> results  = new ArrayList<>();
     
     

	public String getCusipId() {
		return cusipId;
	}

	public void setCusipId(String cusipId) {
		this.cusipId = cusipId;
	}


}
