package com.backend.hygeia.resources;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerController {

	    private static final Logger logger = LogManager.getLogger(LoggerController.class);
	    public static void main(String[] args) {
	        String msg = "${env:MAVEN_HOME}";
	        logger.error(msg);
	    }
	}

