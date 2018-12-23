package com.rabobank.stmtprocessor.parser.impl;

import static org.junit.Assert.assertEquals;
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
import org.springframework.core.io.ClassPathResource;

import com.rabobank.stmtprocessor.parser.StatementParser;
import com.rabobank.stmtprocessor.parser.obj.StatementRecord;

@RunWith(MockitoJUnitRunner.class)
public class ParseConfigTest {

	@InjectMocks
	private ParserConfig parserConfig;
	
	@Mock
	private StatementParser statementParser;

List<StatementRecord> statementRecordList =new ArrayList<StatementRecord>();
	
	@Before
	public void setUp() throws Exception{
		
		StatementRecord statementRecord=new StatementRecord();
		statementRecord.setReference(Long.valueOf("194261"));
		statementRecord.setAccountNumber("NL91RABO0315273637");
		statementRecord.setDescription("Clothes from Jan Bakker");
		statementRecord.setEndBalance(-20.23);
		statementRecord.setStartBalance(21.6);
		statementRecord.setMutation(-41.83);
		
		StatementRecord statementRecord1=new StatementRecord();
		statementRecord1.setReference(Long.valueOf("112806"));
		statementRecord1.setAccountNumber("NL27SNSB0917829871");
		statementRecord1.setDescription("Clothes for Willem Dekker");
		statementRecord1.setEndBalance(106.8);
		statementRecord1.setStartBalance(91.23);
		statementRecord1.setMutation(15.57);
		
		StatementRecord statementRecord2=new StatementRecord();
		statementRecord2.setReference(Long.valueOf("112806"));
		statementRecord2.setAccountNumber("NL69ABNA0433647324");
		statementRecord2.setDescription("Clothes for Richard de Vries");
		statementRecord2.setEndBalance(79.92);
		statementRecord2.setStartBalance(90.83);
		statementRecord2.setMutation(-10.91);
		
		statementRecordList.add(statementRecord);
		statementRecordList.add(statementRecord1);
		statementRecordList.add(statementRecord2);
		
	}
	
	@Test
	public void successTest() throws Exception {
		
		when(statementParser.parseStatement(any())).thenReturn(statementRecordList);
		List<StatementRecord> actual=parserConfig.parseFile(new ClassPathResource("records.csv").getFile());
		assertEquals(statementRecordList,actual);
	}
	
	@Test(expected=Exception.class)
	public void failTest() throws Exception {
		
		when(statementParser.parseStatement(any())).thenThrow(Exception.class);
		
		List<StatementRecord> actual=parserConfig.parseFile(new ClassPathResource("records.csv").getFile());
		assertEquals(statementRecordList,actual);
	}

}
