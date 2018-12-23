package com.rabobank.stmtprocessor.respoonse;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel("Statement Processor Response")
public class Response {

	@JsonProperty(value = "response_status")
	private ResponseStatus responseStatus = new ResponseStatus();

	@JsonProperty(value = "duplicate_refnumber_records")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<ResponseRecord> duplicateRecords = new ArrayList<>();

	public List<ResponseRecord> getDuplicateRecords() {
		return duplicateRecords;
	}

	public void setDuplicateRecords(List<ResponseRecord> duplicateRecords) {
		this.duplicateRecords = duplicateRecords;
	}

	public void setStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;

	}

}
