package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.util.Log4J;

public class MJLexerTest {
	static {
		DOMConfigurator.configure(Log4J.getInstance().findLoggerConfigFile());
		Log4J.getInstance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws IOException {
		Logger logger = Logger.getLogger(MJLexerTest.class);
		Reader bufferReader = null;
		try {
			File sourceCode = new File("test/test-lexer.mj");
			bufferReader = new BufferedReader(new FileReader(sourceCode));
			
			logger.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			Yylex lexer = new Yylex(bufferReader);
			Symbol currentToken = null;
			while ((currentToken = lexer.next_token()).sym != sym.EOF) {
				if (currentToken != null && currentToken.value != null)
					logger.info("Token " + currentToken.toString() + ": " + currentToken.value.toString());
			}
		}
		finally {
			if (bufferReader != null) {
				try { 
					bufferReader.close(); 
				} catch (IOException exception) { 
					logger.error(exception.getMessage(), exception);
				} 
			}
		}
	}
}