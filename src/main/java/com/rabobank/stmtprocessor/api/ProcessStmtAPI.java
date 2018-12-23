package com.rabobank.stmtprocessor.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rabobank.stmtprocessor.respoonse.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping(value = "/api/rest/process/statement/")
@Api(tags = { "Stament Processor APIs" })
public interface ProcessStmtAPI {



	@GetMapping(value = "xmlfile", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "Process XML File", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Executed Successfully"),
			@ApiResponse(code = 400, message = "Error in Execution")})
	public ResponseEntity<Response> processXML();

	@GetMapping(value = "csvfile", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "Process CSV File", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Executed Successfully"),
			@ApiResponse(code = 400, message = "Error in Execution")})
	public ResponseEntity<Response> processCSV();
}
