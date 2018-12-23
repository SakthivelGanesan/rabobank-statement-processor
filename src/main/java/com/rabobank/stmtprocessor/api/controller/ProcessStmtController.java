package com.rabobank.stmtprocessor.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.stmtprocessor.api.ProcessStmtAPI;
import com.rabobank.stmtprocessor.respoonse.Response;
import com.rabobank.stmtprocessor.service.ProcessStmt;
import com.rabobank.stmtprocessor.util.ProcessStmtUtil;

@RestController
public class ProcessStmtController implements ProcessStmtAPI {

	public static final Logger LOG = LoggerFactory.getLogger(ProcessStmtController.class);

	@Autowired
	ProcessStmt processStmtService;
	
	@Autowired
	ProcessStmtUtil processStmtUtil;

	@Override
	public ResponseEntity<Response> processXML() {

		Response response;
		try {
			Resource xmlResource = new ClassPathResource("records.xml");
			LOG.info("Processing XML File Starts");
			response = processStmtService.processXMLFile(xmlResource.getFile());
			LOG.info("Processing XML File Completed");

		} catch (Exception exception) {
			LOG.error("---Exception in Processing XML File---", exception);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(processStmtUtil.getErrorResponse());
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<Response> processCSV() {

		Response response;
		try {
			Resource csvResource = new ClassPathResource("records.csv");
			LOG.info("Processing CSV File Starts");
			response = processStmtService.processCSVFile(csvResource.getFile());
			LOG.info("Processing CSV File Completed");
		} catch (Exception exception) {
			LOG.error("---Exception in Processing CSV File---", exception);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(processStmtUtil.getErrorResponse());
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
