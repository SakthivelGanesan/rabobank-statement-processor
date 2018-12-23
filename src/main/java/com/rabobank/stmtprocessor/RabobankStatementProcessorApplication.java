package com.rabobank.stmtprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabobankStatementProcessorApplication {

	public static final Logger LOG = LoggerFactory.getLogger(RabobankStatementProcessorApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(RabobankStatementProcessorApplication.class, args);
		LOG.info("*** Rabobank Statement Processor Application Started***");
	}

}

