package com.rabobank.stmtprocessor.service;

import java.io.File;

import com.rabobank.stmtprocessor.respoonse.Response;

public interface ProcessStmt {

	public Response processCSVFile(File file);

	public Response processXMLFile(File file);

}
