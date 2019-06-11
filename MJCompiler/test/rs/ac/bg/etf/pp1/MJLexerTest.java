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
	
	public static final int NUM_OF_EXAMPLES = 2;
	
	private static Logger logger = Logger.getLogger(MJLexerTest.class);
	
	public static void compile(String fileName) throws IOException {
		Reader bufferReader = null;
		try {
			File sourceCode = new File(fileName);
			bufferReader = new BufferedReader(new FileReader(sourceCode));
			
			logger.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			MJLexer lexer = new MJLexer(bufferReader);
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
	
	public static void main(String[] args) throws IOException {
		logger.info("======= MJ LEXER TEST =======");
		
		// All lexical structures
		compile("test/lexer/lexical_structures.mj");
		
		// Program examples
		for (int i = 1; i <= NUM_OF_EXAMPLES; ++i) {
			compile("test/example" + i + ".mj");
		}
		
		logger.info("===== MJ LEXER TEST END =====");
	}
}