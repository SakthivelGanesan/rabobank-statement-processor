package com.rabobank.stmtprocessor.parser.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;

import com.rabobank.stmtprocessor.parser.obj.StatementRecord;

@RunWith(MockitoJUnitRunner.class)
public class XMLFileParserTest {

	@InjectMocks
	private XMLFileParser xmlFileParser;
	
	List<StatementRecord> statementRecordList =new ArrayList<StatementRecord>();
	
	@Before
	public void setUp() throws Exception{
		
		StatementRecord statementRecord=new StatementRecord();
		statementRecord.setReference(Long.valueOf("130498"));
		statementRecord.setAccountNumber("130498");
		statementRecord.setDescription("Tickets for Peter Theuß");
		statementRecord.setEndBalance(18.78);
		statementRecord.setStartBalance(26.9);
		statementRecord.setMutation(-18.78);
		statementRecordList.add(statementRecord);
		
	}
	
	
	@Test
	public void testParseWithReocrds() throws Exception {
		List<StatementRecord> actual=xmlFileParser.parseStatement(new ClassPathResource("records.xml").getFile());
		assertEquals(statementRecordList,actual);
	
	}

	@Test(expected=Exception.class)
	public void testParseForException() throws Exception {
		xmlFileParser.parseStatement(new ClassPathResource("invalidRecords.xml").getFile());
	
	}
	


}
