package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4J;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class MJCompilerTest {
	static {
		DOMConfigurator.configure(Log4J.getInstance().findLoggerConfigFile());
		Log4J.getInstance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static final int NUM_OF_TESTS = 3;

	public static final int NUM_OF_EXAMPLES = 2;

	private static Logger logger = Logger.getLogger(MJCompilerTest.class);

	public static void tsdump() {
		MJSymbolTableVisitor dumpVisitor = new MJSymbolTableVisitor();
		Tab.dump(dumpVisitor);
	}

	public static void compile(String sourceFileName, String objectFileName) throws Exception {
		Reader bufferReader = null;
		try {
			File sourceCode = new File(sourceFileName);
			bufferReader = new BufferedReader(new FileReader(sourceCode));

			logger.info("Compiling source file: " + sourceCode.getAbsolutePath());

			MJLexer lexer = new MJLexer(bufferReader);
			MJParser parser = new MJParser(lexer);

			logger.info("Parsing started");

			Symbol symbol = parser.parse();
			Program program = (Program) (symbol.value);

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
			tsdump();

			if (analyzer.isErrorDetected()) {
				logger.error("Semantic analysis unsuccessful (error(s) found)");
			} else {
				logger.info("Semantic analysis successful");
			}

			if (!parser.isErrorDetected() && !analyzer.isErrorDetected()) {
				logger.info("Code generation started");

				MJCodeGenerator generator = new MJCodeGenerator();
				generator.setDataSize(analyzer.getNumberOfVariables());
				program.traverseBottomUp(generator);

				Code.dataSize = generator.getDataSize();
				Code.mainPc = generator.getMainPc();
				
				File objectFile = new File(objectFileName);
				if (objectFile.exists())
					objectFile.delete();
				FileOutputStream objectStream = new FileOutputStream(objectFile);
				
				Code.write(objectStream);

				logger.info("Code generation successful");
			}
		} finally {
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
		logger.info("======= MJ COMPILER TEST =======");
		
		// Public tests 
		for (int i = 1; i <= NUM_OF_TESTS; ++i) {
	 		 compile("test/public_tests/test30" + i + ".mj", "test/obj/test30" + i + ".obj");
		}
		
		// Test
		compile("test/public_tests/program-30p.mj", "test/obj/program-30p.obj");

		// Program examples
		/*for (int i = 1; i <= NUM_OF_EXAMPLES; ++i) {
	 		 compile("test/examples/example" + i + ".mj", "test/obj/example" + i + ".obj");
		}*/

		logger.info("===== MJ COMPILER TEST END =====");
	}
}