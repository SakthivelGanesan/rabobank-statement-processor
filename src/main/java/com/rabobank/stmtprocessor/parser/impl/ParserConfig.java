package com.rabobank.stmtprocessor.parser.impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rabobank.stmtprocessor.parser.StatementParser;
import com.rabobank.stmtprocessor.parser.obj.StatementRecord;

@Component
public class ParserConfig {
	
	private StatementParser statementParser;
	

	public void setParser(StatementParser statementParser) {
		this.statementParser = statementParser;
	}


	public List<StatementRecord> parseFile( File file) throws Exception {
		return statementParser.parseStatement(file);
	}

}