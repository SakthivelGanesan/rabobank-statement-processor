package com.rabobank.stmtprocessor.respoonse;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Statement Process Status")
public class ResponseStatus {

	@ApiModelProperty(allowableValues = "1001,1002,400")
	@JsonProperty(value = "status_code")
	private String statusCode;

	@ApiModelProperty(allowableValues = "Duplicate Records Retrived Successfully, No Duplicate Records Available,Error in Retriving Duplicate Records")
	@JsonProperty(value = "status_desc")
	private String statusDescription;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

}
