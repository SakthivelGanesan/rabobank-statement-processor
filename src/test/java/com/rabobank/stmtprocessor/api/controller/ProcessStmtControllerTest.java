package com.rabobank.stmtprocessor.api.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabobank.stmtprocessor.respoonse.Response;
import com.rabobank.stmtprocessor.respoonse.ResponseRecord;
import com.rabobank.stmtprocessor.respoonse.ResponseStatus;
import com.rabobank.stmtprocessor.service.impl.ProcessStmtService;
import com.rabobank.stmtprocessor.service.impl.ProcessStmtService.ResponseDetails;
import com.rabobank.stmtprocessor.util.ProcessStmtUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProcessStmtController.class, secure = false)
public class ProcessStmtControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ProcessStmtService processStmtService;
	
	@MockBean
	ProcessStmtUtil processStmtUtil;
	

	List<ResponseRecord> responseRecordList = new ArrayList<ResponseRecord>();

	
	@Before
	public void setUp() throws Exception {
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
	}
	
		
	@Test
	public void processCSVSuccessTest() throws Exception {

		Response response = new Response();
		response.setDuplicateRecords(responseRecordList);
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(ResponseDetails.SUCCESS_RESPONSE.getrespCode());
		responseStatus.setStatusDescription(ResponseDetails.SUCCESS_RESPONSE.getrespDesc());
		response.setStatus(responseStatus);

		when(this.processStmtService.processCSVFile(any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();

		Response expected = mapper.readValue(
				new ClassPathResource("com/rabobank/stmtprocessor/api/controller/SuccessResponse.json").getFile(),
				Response.class);
		MvcResult result = this.mockMvc.perform(get("/api/rest/process/statement/csvfile")).andExpect(status().isOk())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	}

	@Test
	public void processCSVErrorTest() throws Exception {

		Response response = new Response();
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(ResponseDetails.ERROR_RESPONSE.getrespCode());
		responseStatus.setStatusDescription(ResponseDetails.ERROR_RESPONSE.getrespDesc());
		response.setStatus(responseStatus);

		when(this.processStmtService.processCSVFile(any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();

		Response expected = mapper.readValue(
				new ClassPathResource("com/rabobank/stmtprocessor/api/controller/FailResponse.json").getFile(),
				Response.class);
		MvcResult result = this.mockMvc.perform(get("/api/rest/process/statement/csvfile")).andExpect(status().isOk())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	}

	@Test
	public void processCSVNoDuplicateTest() throws Exception {

		Response response = new Response();
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(ResponseDetails.NODUPLICATE_AVAILABLE.getrespCode());
		responseStatus.setStatusDescription(ResponseDetails.NODUPLICATE_AVAILABLE.getrespDesc());
		response.setStatus(responseStatus);
		
		when(this.processStmtService.processCSVFile(any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();

		Response expected = mapper.readValue(
				new ClassPathResource("com/rabobank/stmtprocessor/api/controller/NoDuplicateResponse.json").getFile(),
				Response.class);
		MvcResult result = this.mockMvc.perform(get("/api/rest/process/statement/csvfile")).andExpect(status().isOk())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	}

	@Test
	public void processXMLNoDuplicateTest() throws Exception {
		
		Response response = new Response();
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(ResponseDetails.NODUPLICATE_AVAILABLE.getrespCode());
		responseStatus.setStatusDescription(ResponseDetails.NODUPLICATE_AVAILABLE.getrespDesc());
		response.setStatus(responseStatus);
		
		when(this.processStmtService.processXMLFile(any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();

		Response expected = mapper.readValue(
				new ClassPathResource("com/rabobank/stmtprocessor/api/controller/NoDuplicateResponse.json").getFile(),
				Response.class);
		MvcResult result = this.mockMvc.perform(get("/api/rest/process/statement/xmlfile")).andExpect(status().isOk())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	
	}

	

	@Test
	public void processXMLErrorTest() throws Exception {
		
		Response response = new Response();
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(ResponseDetails.ERROR_RESPONSE.getrespCode());
		responseStatus.setStatusDescription(ResponseDetails.ERROR_RESPONSE.getrespDesc());
		response.setStatus(responseStatus);
		
		when(this.processStmtService.processXMLFile(any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();

		Response expected = mapper.readValue(
				new ClassPathResource("com/rabobank/stmtprocessor/api/controller/FailResponse.json").getFile(),
				Response.class);
		MvcResult result = this.mockMvc.perform(get("/api/rest/process/statement/xmlfile")).andExpect(status().isOk())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	
	}

	@Test
	public void processXMLSuccessTest() throws Exception {
		
		Response response = new Response();
		response.setDuplicateRecords(responseRecordList);
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setStatusCode(ResponseDetails.SUCCESS_RESPONSE.getrespCode());
		responseStatus.setStatusDescription(ResponseDetails.SUCCESS_RESPONSE.getrespDesc());
		response.setStatus(responseStatus);
		
		when(this.processStmtService.processXMLFile(any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();

		Response expected = mapper.readValue(
				new ClassPathResource("com/rabobank/stmtprocessor/api/controller/SuccessResponse.json").getFile(),
				Response.class);
		MvcResult result = this.mockMvc.perform(get("/api/rest/process/statement/xmlfile")).andExpect(status().isOk())
				.andReturn();

		assertEquals(mapper.writeValueAsString(expected), result.getResponse().getContentAsString());
	
	}

	
}
