package com.rabobank.stmtprocessor.parser.impl;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.rabobank.stmtprocessor.parser.StatementParser;
import com.rabobank.stmtprocessor.parser.obj.StatementRecord;

@Component
public class CSVFileParser implements StatementParser {

	private static final Logger LOG = LoggerFactory.getLogger(CSVFileParser.class);

	@Override
	public List<StatementRecord> parseStatement(File file) throws Exception {

		List<StatementRecord> statementRecordList = new ArrayList<StatementRecord>();
		try (FileReader fileReader = new FileReader(file);
				CSVReader csvReader = new CSVReader(fileReader, ',')) {
			
			String[] record = null;
			// Skipp Header For Pojo Conversion
			csvReader.readNext();

			while ((record = csvReader.readNext()) != null) {
				StatementRecord statementRecord = new StatementRecord();
				statementRecord.setReference(new Long(record[0]));
				statementRecord.setAccountNumber(record[1]);
				statementRecord.setDescription(record[2]);
				statementRecord.setStartBalance(Double.valueOf(record[3]));
				statementRecord.setMutation(Double.valueOf(record[4]));
				statementRecord.setEndBalance(Double.valueOf(record[5]));
				statementRecordList.add(statementRecord);
				
			}
			LOG.debug("Converted CSV Record Count---> {} " , statementRecordList.size());
			LOG.info("CSV to Object Conversion Complteted");

		} catch (Exception exception) {

			LOG.error("---Eception in CSV to Object Conversion---", exception);
		}

		return statementRecordList;
	}

}
