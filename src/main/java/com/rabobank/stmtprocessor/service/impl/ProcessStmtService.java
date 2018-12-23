package com.rabobank.stmtprocessor.service.impl;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabobank.stmtprocessor.parser.impl.CSVFileParser;
import com.rabobank.stmtprocessor.parser.impl.ParserConfig;
import com.rabobank.stmtprocessor.parser.impl.XMLFileParser;
import com.rabobank.stmtprocessor.parser.obj.StatementRecord;
import com.rabobank.stmtprocessor.respoonse.Response;
import com.rabobank.stmtprocessor.respoonse.ResponseRecord;
import com.rabobank.stmtprocessor.respoonse.ResponseStatus;
import com.rabobank.stmtprocessor.service.ProcessStmt;
import com.rabobank.stmtprocessor.util.ProcessStmtUtil;

@Service
public class ProcessStmtService implements ProcessStmt {

	private static final Logger LOG = LoggerFactory.getLogger(ProcessStmtService.class);

	@Autowired
	ParserConfig parserConfig;

	@Autowired
	CSVFileParser csvFileParser;

	@Autowired
	XMLFileParser xmlFileParser;
	
	@Autowired
	ProcessStmtUtil processStmtUtil;

	@Override
	public Response processCSVFile(File file) {

		Response response = null;;
		try {
			parserConfig.setParser(csvFileParser);
			List<StatementRecord> statementRecordList = parserConfig.parseFile(file);
			List<ResponseRecord> responseRecordList = processStmtUtil.getDuplicateRecords(statementRecordList);
			LOG.debug("Duplicate CSV Record Count---> {} " , responseRecordList.size());
			response = getValidResponse(responseRecordList);

		} catch (Exception exception) {
			LOG.error("---Eception in Processing CSV File---", exception);
			response = processStmtUtil.getErrorResponse();
		}
		return response;
	}

	@Override
	public Response processXMLFile(File file) {

		Response response = null;

		try {
			parserConfig.setParser(xmlFileParser);
			List<StatementRecord> statementRecordList = parserConfig.parseFile(file);
			List<ResponseRecord> responseRecordList = processStmtUtil.getDuplicateRecords(statementRecordList);
			LOG.debug("Duplicate XML Record Count---> {} " , responseRecordList.size());
			response = getValidResponse(responseRecordList);

		} catch (Exception exception) {
			LOG.error("---Eception in Processing CSV File---", exception);
			response = processStmtUtil.getErrorResponse();
		}
		return response;

	}

	public static Response getValidResponse(List<ResponseRecord> responseRecordList) {

		ResponseStatus responseStatus = new ResponseStatus();
		Response response = new Response();

		if (responseRecordList.isEmpty()) {
			responseStatus.setStatusCode(ResponseDetails.NODUPLICATE_AVAILABLE.respCode);
			responseStatus.setStatusDescription(ResponseDetails.NODUPLICATE_AVAILABLE.respDesc);
			response.setStatus(responseStatus);
		} else {
			responseStatus.setStatusCode(ResponseDetails.SUCCESS_RESPONSE.respCode);
			responseStatus.setStatusDescription(ResponseDetails.SUCCESS_RESPONSE.respDesc);
			response.setDuplicateRecords(responseRecordList);
			response.setStatus(responseStatus);
		}
		return response;

	}

	public enum ResponseDetails {

		SUCCESS_RESPONSE("1001", "Duplicate Records Retrived Successfully"),
		NODUPLICATE_AVAILABLE("1002", "No Duplicate Records Available"),
		ERROR_RESPONSE("400", "Error in Retriving Duplicate Records"),;

		public String respCode;

		public String respDesc;

		ResponseDetails(String respCode, String respDesc) {
			this.respCode = respCode;
			this.respDesc = respDesc;
		}

		public String getrespCode() {
			return respCode;
		}

		public String getrespDesc() {
			return respDesc;
		}

	}

}
