package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4J;

public class MJParserTest {
	static {
		DOMConfigurator.configure(Log4J.getInstance().findLoggerConfigFile());
		Log4J.getInstance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws IOException {
		Logger logger = Logger.getLogger(MJLexerTest.class);
		Reader bufferReader = null;
		try {
			File sourceCode = new File("test/test-parser-program.mj");
			bufferReader = new BufferedReader(new FileReader(sourceCode));
			
			logger.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			Yylex lexer = new Yylex(bufferReader);
			
			MJParser parser = new MJParser(lexer);
			
			logger.info("Parsing started");
			
	        Symbol symbol = parser.parse();
	        Program program = (Program)(symbol.value);
	        
	        logger.info("Parsing successful!");
	        
	        // Syntax tree
	        logger.info(program.toString(""));

			/*RuleVisitor visitor = new RuleVisitor();
			program.traverseBottomUp(visitor);
			logger.info(" Print count calls = " + visitor.printCallCount);
			logger.info(" Deklarisanih promenljivih ima = " + visitor.varDeclCount);*/
		} catch (Exception exception) {
			exception.printStackTrace();
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