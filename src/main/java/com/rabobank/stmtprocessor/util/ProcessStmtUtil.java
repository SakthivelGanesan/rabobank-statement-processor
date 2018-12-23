package com.rabobank.stmtprocessor.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rabobank.stmtprocessor.parser.obj.StatementRecord;
import com.rabobank.stmtprocessor.respoonse.Response;
import com.rabobank.stmtprocessor.respoonse.ResponseRecord;
import com.rabobank.stmtprocessor.respoonse.ResponseStatus;
import com.rabobank.stmtprocessor.service.impl.ProcessStmtService.ResponseDetails;

@Component
public class ProcessStmtUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProcessStmtUtil.class);

	public List<ResponseRecord> getDuplicateRecords(List<StatementRecord> statementRecordList) {

		LOG.info("Total Record Count---> {}", statementRecordList.size());
		
		List<StatementRecord> invalidList = statementRecordList.stream().
				filter(invalidEndBal()).collect(Collectors.toList()); // Filter Invalid Balance Records
		LOG.info("Invalid Balance Records---> {}", invalidList);
		
		List<StatementRecord> duplicateRef =statementRecordList.stream().filter(i -> Collections.frequency(statementRecordList, i) >1)
        .collect(Collectors.toList()); // Filter Duplicate ReF Records
		LOG.info("Duplicate Refernce  Records---> {}", duplicateRef);	
		
		invalidList.addAll(duplicateRef);
		
		// Prepare Response List
		List<ResponseRecord> responseList = new ArrayList<ResponseRecord>();
		invalidList.forEach(respRec -> {
			ResponseRecord responseRecord = new ResponseRecord();
			responseRecord.setDescription(respRec.getDescription());
			responseRecord.setReference(respRec.getReference());
			responseList.add(responseRecord);
		});
		
	return responseList;
	
	}
	
	public Predicate<StatementRecord> invalidEndBal() {
        return rec ->(!(Math.round(rec.getEndBalance() - rec.getStartBalance()) == Math.round(rec.getMutation())));
    }
	
	public Response getErrorResponse() {

		ResponseStatus responseStatus = new ResponseStatus();
		Response response = new Response();
		responseStatus.setStatusCode(ResponseDetails.ERROR_RESPONSE.respCode);
		responseStatus.setStatusDescription(ResponseDetails.ERROR_RESPONSE.respDesc);
		response.setStatus(responseStatus);
		
		return response;

	}

}
