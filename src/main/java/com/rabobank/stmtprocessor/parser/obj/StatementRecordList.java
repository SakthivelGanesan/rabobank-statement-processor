package com.rabobank.stmtprocessor.parser.obj;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "records")
public class StatementRecordList {

	List<StatementRecord> statementRecordList;

	@XmlElement(name = "record")
	public List<StatementRecord> getStatements() {
		return statementRecordList;
	}

	public void setStatements(List<StatementRecord> statementRecordList) {
		this.statementRecordList = statementRecordList;
	}
}
