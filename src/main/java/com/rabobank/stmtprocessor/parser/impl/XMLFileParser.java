package com.rabobank.stmtprocessor.parser.impl;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rabobank.stmtprocessor.parser.StatementParser;
import com.rabobank.stmtprocessor.parser.obj.StatementRecord;
import com.rabobank.stmtprocessor.parser.obj.StatementRecordList;

@Component
public class XMLFileParser implements StatementParser {

	private static final Logger LOG = LoggerFactory.getLogger(XMLFileParser.class);

	@Override
	public List<StatementRecord> parseStatement(File file) throws Exception {
		StatementRecordList statementRecordList = null;
		JAXBContext jaxbContext;

		try {

			jaxbContext = JAXBContext.newInstance(StatementRecordList.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			statementRecordList = (StatementRecordList) unmarshaller.unmarshal(file);
			LOG.debug("Converted XML Record Count---> {} ", statementRecordList.getStatements().size());

		} catch (Exception exception) {

			LOG.error("---Eception in XML to Object Conversion---", exception);
		}
		return statementRecordList.getStatements();
	}

}
