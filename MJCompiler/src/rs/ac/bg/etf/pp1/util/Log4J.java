package rs.ac.bg.etf.pp1.util;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;

public class Log4J {
	private static Log4J log = new Log4J();
	
	public static Log4J getInstance() {
		return log;
	}
	
	public URL findLoggerConfigFile() {
		return Thread.currentThread().getContextClassLoader().getResource("log4j.xml");
	}
	
	public void prepareLogFile(Logger rootLogger) {
		Appender appender = rootLogger.getAppender("file");
		if (!(appender instanceof FileAppender)) {
			return;
		}
		
		FileAppender fileAppender = (FileAppender)appender;
		
		String logFileName = fileAppender.getFile();
		logFileName = logFileName.substring(0, logFileName.lastIndexOf('.')) + "-test.log";
		
		File logFile = new File(logFileName);
		logFile.delete();
		
		fileAppender.setFile(logFile.getAbsolutePath());
		fileAppender.activateOptions();
	}
}