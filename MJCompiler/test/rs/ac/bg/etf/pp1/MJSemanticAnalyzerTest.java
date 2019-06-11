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
import rs.etf.pp1.symboltable.Tab;

public class MJSemanticAnalyzerTest {
	static {
		DOMConfigurator.configure(Log4J.getInstance().findLoggerConfigFile());
		Log4J.getInstance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static final int NUM_OF_EXAMPLES = 2;
	
	private static Logger logger = Logger.getLogger(MJLexerTest.class);
	
	public static void compile(String fileName) throws Exception {
		Reader bufferReader = null;
		try {
			File sourceCode = new File(fileName);
			bufferReader = new BufferedReader(new FileReader(sourceCode));
			
			logger.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			MJLexer lexer = new MJLexer(bufferReader);
			MJParser parser = new MJParser(lexer);
			
			logger.info("Parsing started");
			
	        Symbol symbol = parser.parse();
	        Program program = (Program)(symbol.value);
	        
	        // Syntax tree
	        logger.info(program.toString(""));
	        
	        if (parser.isErrorDetected()) {
	        	logger.error("Parsing unsuccessful (recovered error(s) found)");
	        } else {
	        	logger.info("Parsing successful");
	        }

	        logger.info("Semantic analysis started");
	        
			MJSemanticAnalyzer analyzer = new MJSemanticAnalyzer();
			Tab.init();
			program.traverseBottomUp(analyzer);
			
			// Symbol table
			Tab.dump();
			
			if (analyzer.isErrorDetected()) {
	        	logger.error("Semantic analysis unsuccessful (error(s) found)");
	        } else {
	        	logger.info("Semantic analysis successful");
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
	
	public static void main(String[] args) throws Exception {
		logger.info("======= MJ SEMANTIC ANALYSER TEST =======");
		
		// Semantic errors
		compile("test/semantics/const.mj");
		compile("test/semantics/var.mj");
		
		/*// Error recovery
		compile("test/parser/recovery.mj");
		
		// Program examples
		for (int i = 1; i <= NUM_OF_EXAMPLES; ++i) {
			compile("test/example" + i + ".mj");
		}*/
		
		logger.info("===== MJ SEMANTIC ANALYSER TEST END =====");
	}	
}