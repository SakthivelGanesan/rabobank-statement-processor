package com.rabobank.stmtprocessor.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.rabobank.stmtprocessor.parser.impl.CSVFileParser;
import com.rabobank.stmtprocessor.parser.impl.ParserConfig;
import com.rabobank.stmtprocessor.parser.impl.XMLFileParser;
import com.rabobank.stmtprocessor.parser.obj.StatementRecord;
import com.rabobank.stmtprocessor.respoonse.Response;
import com.rabobank.stmtprocessor.respoonse.ResponseRecord;
import com.rabobank.stmtprocessor.respoonse.ResponseStatus;
import com.rabobank.stmtprocessor.service.impl.ProcessStmtService.ResponseDetails;
import com.rabobank.stmtprocessor.util.ProcessStmtUtil;
@RunWith(MockitoJUnitRunner.class)
public class ProcessStmtServiceTest {

	@InjectMocks
	private ProcessStmtService processStmtService;

	@Mock
	private ParserConfig parserConfig;
	
	@Mock
	CSVFileParser csvFileParser;
	
	@Mock
	XMLFileParser xmlFileParser;

	@Mock
	ProcessStmtUtil processStmtUtil;
	
	List<StatementRecord> statementRecordList =new ArrayList<StatementRecord>();
	List<StatementRecord> uniqueRecordList =new ArrayList<StatementRecord>();
	List<ResponseRecord> responseRecordList =new ArrayList<ResponseRecord>();
	
	@Before
	public void setUp() throws Exception{
		
		ResponseRecord responseRecord = new ResponseRecord();
		responseRecord.setReference(Long.valueOf("112806"));
		responseRecord.setDescription("Clothes for Willem Dekker");
		ResponseRecord responseRecord1 = new ResponseRecord();
		responseRecord1.setReference(Long.valueOf("112806"));
		responseRecord1.setDescription("Clothes for Richard de Vries");
		ResponseRecord responseRecord2 = new ResponseRecord();
		responseRecord2.setReference(Long.valueOf("112806"));
		responseRecord2.setDescription("Tickets from Richard Bakker");
		responseRecordList.add(responseRecord);
		responseRecordList.add(responseRecord1);
		responseRecordList.add(responseRecord2);
		
	
		StatementRecord statementRecord=new StatementRecord();
		statementRecord.setReference(Long.valueOf("112806"));
		statementRecord.setAccountNumber("NL91RABO0315273637");
		statementRecord.setDescription("Clothes for Richard de Vries");
		statementRecord.setEndBalance(-20.23);
		statementRecord.setStartBalance(21.6);
		statementRecord.setMutation(-41.83);
		
		StatementRecord statementRecord1=new StatementRecord();
		statementRecord1.setReference(Long.valueOf("179430"));
		statementRecord1.setAccountNumber("NL91RABO0315273637");
		statementRecord1.setDescription("description");
		statementRecord1.setEndBalance(-20.23);
		statementRecord1.setStartBalance(21.6);
		statementRecord1.setMutation(-41.83);
		
		StatementRecord statementRecord2=new StatementRecord();
		statementRecord2.setReference(Long.valueOf("112806"));
		statementRecord2.setAccountNumber("NL91RABO0315273637");
		statementRecord2.setDescription("Clothes for Richard de Vries");
		statementRecord2.setEndBalance(-20.23);
		statementRecord2.setStartBalance(21.6);
		statementRecord2.setMutation(-41.83);

		StatementRecord statementRecord3=new StatementRecord();
		statementRecord3.setReference(Long.valueOf("194261"));
		statementRecord3.setAccountNumber("NL91RABO0315273637");
		statementRecord3.setDescription("Clothes from Jan Bakker");
		statementRecord3.setEndBalance(-20.23);
		statementRecord3.setStartBalance(21.6);
		statementRecord3.setMutation(-41.83);
		
		StatementRecord statementRecord4=new StatementRecord();
		statementRecord4.setReference(Long.valueOf("112806"));
		statementRecord4.setAccountNumber("NL91RABO0315273637");
		statementRecord4.setDescription("Tickets from Richard Bakker");
		statementRecord4.setEndBalance(-20.23);
		statementRecord4.setStartBalance(21.6);
		statementRecord4.setMutation(-41.83);
		
		statementRecordList.add(statementRecord);
		statementRecordList.add(statementRecord1);
		statementRecordList.add(statementRecord2);
		statementRecordList.add(statementRecord3);
		statementRecordList.add(statementRecord4);
		
		uniqueRecordList.add(statementRecord);
		uniqueRecordList.add(statementRecord1);
		
	}

	@Test
	public void processCSVSuccessTest() throws Exception {
		
		Response expResponse = new Response();
		expResponse.setDuplicateRecords(responseRecordList);
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(ResponseDetails.SUCCESS_RESPONSE.getrespCode());
		responseStatus.setStatusDescription(ResponseDetails.SUCCESS_RESPONSE.getrespDesc());
		expResponse.setStatus(responseStatus);
		
		when(this.parserConfig.parseFile(any())).thenReturn(statementRecordList);
		
		when(this.processStmtUtil.getDuplicateRecords(statementRecordList)).thenReturn(responseRecordList);
		
		Response orgResponse = processStmtService.processCSVFile(any());
		assertThat(expResponse).isEqualToComparingFieldByFieldRecursively(orgResponse);
	}
	@Test
	public void testCSVNoDuplicate() throws Exception {
		
		Response expResponse = new Response();
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(ResponseDetails.NODUPLICATE_AVAILABLE.getrespCode());
		responseStatus.setStatusDescription(ResponseDetails.NODUPLICATE_AVAILABLE.getrespDesc());
		expResponse.setStatus(responseStatus);
		
		when(this.parserConfig.parseFile(any())).thenReturn(uniqueRecordList);
		
		when(this.processStmtUtil.getDuplicateRecords(uniqueRecordList)).thenReturn(new ArrayList<ResponseRecord>());
		
		Response orgResponse = processStmtService.processCSVFile(any());
		assertThat(expResponse).isEqualToComparingFieldByFieldRecursively(orgResponse);
	}
	
	@Test
	public void testCSVError() throws Exception {
		
		Response expResponse = new Response();
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(ResponseDetails.NODUPLICATE_AVAILABLE.getrespCode());
		responseStatus.setStatusDescription(ResponseDetails.NODUPLICATE_AVAILABLE.getrespDesc());
		expResponse.setStatus(responseStatus);
		
		when(this.parserConfig.parseFile(any())).thenReturn(uniqueRecordList);
		
		when(this.processStmtUtil.getDuplicateRecords(uniqueRecordList)).thenReturn(new ArrayList<ResponseRecord>());
		
		Response orgResponse = processStmtService.processCSVFile(any());
		assertThat(expResponse).isEqualToComparingFieldByFieldRecursively(orgResponse);
	}
	
	@Test
	public void processXMLSuccessTest() throws Exception {
		
		Response expResponse = new Response();
		expResponse.setDuplicateRecords(responseRecordList);
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(ResponseDetails.SUCCESS_RESPONSE.getrespCode());
		responseStatus.setStatusDescription(ResponseDetails.SUCCESS_RESPONSE.getrespDesc());
		expResponse.setStatus(responseStatus);
		
		when(this.parserConfig.parseFile(any())).thenReturn(statementRecordList);
		
		when(this.processStmtUtil.getDuplicateRecords(statementRecordList)).thenReturn(responseRecordList);
		
		Response orgResponse = processStmtService.processXMLFile(any());
		assertThat(expResponse).isEqualToComparingFieldByFieldRecursively(orgResponse);
	}
	
	@Test
	public void testXMLNoDuplicate() throws Exception {
		
		Response expResponse = new Response();
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(ResponseDetails.NODUPLICATE_AVAILABLE.getrespCode());
		responseStatus.setStatusDescription(ResponseDetails.NODUPLICATE_AVAILABLE.getrespDesc());
		expResponse.setStatus(responseStatus);
		
		when(this.parserConfig.parseFile(any())).thenReturn(uniqueRecordList);
		
		when(this.processStmtUtil.getDuplicateRecords(uniqueRecordList)).thenReturn(new ArrayList<ResponseRecord>());
		
		Response orgResponse = processStmtService.processXMLFile(any());
		assertThat(expResponse).isEqualToComparingFieldByFieldRecursively(orgResponse);
	}
	
	@Test
	public void testXMLError() throws Exception {
		
		Response expResponse = new Response();
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(ResponseDetails.ERROR_RESPONSE.getrespCode());
		responseStatus.setStatusDescription(ResponseDetails.ERROR_RESPONSE.getrespDesc());
		expResponse.setStatus(responseStatus);

		when(this.parserConfig.parseFile(any())).thenReturn(statementRecordList);
		
		when(this.processStmtUtil.getDuplicateRecords(statementRecordList)).thenThrow(new Exception());
		
		Response orgResponse = processStmtService.processXMLFile(any());
		assertThat(expResponse).isEqualToComparingFieldByFieldRecursively(orgResponse);
	}

}
