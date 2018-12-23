package com.rabobank.stmtprocessor.parser;

import java.io.File;
import java.util.List;

import com.rabobank.stmtprocessor.parser.obj.StatementRecord;

public interface StatementParser {

	public List<StatementRecord> parseStatement(File file) throws Exception;

}
