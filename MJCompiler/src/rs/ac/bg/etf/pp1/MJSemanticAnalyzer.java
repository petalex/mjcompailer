package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

import org.apache.log4j.Logger;

public class MJSemanticAnalyzer extends VisitorAdaptor {
	private boolean errorDetected;
	
	private static Logger log = Logger.getLogger(MJSemanticAnalyzer.class);

	public boolean isErrorDetected() {
		return errorDetected;
	}
	
	private String getMessage(String message, SyntaxNode info) {
		StringBuilder stringBuilder = new StringBuilder("[Semantic Analyser] "); 
		stringBuilder.append (message);
		if (info != null) {
			stringBuilder.append(" (line: ").append(info.getLine()).append(")");
		}
		return stringBuilder.toString();
	}
	
	private void reportInfo(String message, SyntaxNode info) {
		log.info(getMessage(message, info));
	}
	
	private void reportError(String message, SyntaxNode info) {
		errorDetected = true;
		log.error(getMessage(message, info));
	}
	
	public void visit(ProgramName programName) {
		programName.obj = Tab.insert(Obj.Prog, programName.getName(), Tab.noType);
		Tab.openScope();
	}
	
	public void visit(Program program) {
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}
	
	public void visit(NumConstIdentifier numConstIdentifier) {
		SyntaxNode parent = numConstIdentifier.getParent();
		// Find type of constant
		for (; !(parent instanceof ConstDeclDefinition); parent = parent.getParent());
		Struct type = ((ConstDeclDefinition)parent).getType().struct;
		Obj symbol = Tab.find(numConstIdentifier.getName());
		if (symbol != Tab.noObj) {
			reportError("Symbol \"" + numConstIdentifier.getName() + "\" is already defined", numConstIdentifier);
			numConstIdentifier.obj = Tab.noObj;
		}
		else if (type != Tab.intType) {
			reportError("Constant type is not int, but initialized with int value", numConstIdentifier);
			numConstIdentifier.obj = Tab.noObj;
		}
		else {
			// Correct integer constant
			numConstIdentifier.obj = Tab.insert(Obj.Con, numConstIdentifier.getName(), type);
			numConstIdentifier.obj.setAdr(numConstIdentifier.getValue());
		}
	}
	
	public void visit(CharConstIdentifier charConstIdentifier) {
		SyntaxNode parent = charConstIdentifier.getParent();
		// Find type of constant
		for (; !(parent instanceof ConstDeclDefinition); parent = parent.getParent());
		Struct type = ((ConstDeclDefinition)parent).getType().struct;
		Obj symbol = Tab.find(charConstIdentifier.getName());
		if (symbol != Tab.noObj) {
			reportError("Symbol \"" + charConstIdentifier.getName() + "\" is already defined", charConstIdentifier);
			charConstIdentifier.obj = Tab.noObj;
		}
		else if (type != Tab.charType) {
			reportError("Constant type is not char, but initialized with char value", charConstIdentifier);
			charConstIdentifier.obj = Tab.noObj;
		}
		else {
			// Correct char constant
			charConstIdentifier.obj = Tab.insert(Obj.Con, charConstIdentifier.getName(), type);
			charConstIdentifier.obj.setAdr(charConstIdentifier.getValue().charValue());
		}
	}
	
	public void visit(BoolConstIdentifier boolConstIdentifier) {
		SyntaxNode parent = boolConstIdentifier.getParent();
		// Find type of constant
		for (; !(parent instanceof ConstDeclDefinition); parent = parent.getParent());
		Struct type = ((ConstDeclDefinition)parent).getType().struct;
		Obj symbol = Tab.find(boolConstIdentifier.getName());
		if (symbol != Tab.noObj) {
			reportError("Symbol \"" + boolConstIdentifier.getName() + "\" is already defined", boolConstIdentifier);
			boolConstIdentifier.obj = Tab.noObj;
		}
		else if (type != Tab.charType) {
			reportError("Constant type is not boolean, but initialized with boolean value", boolConstIdentifier);
			boolConstIdentifier.obj = Tab.noObj;
		}
		else {
			// Correct bool constant
			boolConstIdentifier.obj = Tab.insert(Obj.Con, boolConstIdentifier.getName(), type);
			boolConstIdentifier.obj.setAdr(boolConstIdentifier.getValue().booleanValue() ? 1 : 0);
		}
	}
	
	public void visit(TypeName typeName) {
		Obj type = Tab.find(typeName.getName());
		if (type == Tab.noObj) {
			reportError("Symbol \"" + typeName.getName() + "\" is not defined", typeName);
			typeName.struct = Tab.noType;
		}
		else if (type.getKind() != Obj.Type) {
			reportError("Symbol \"" + typeName.getName() + "\" is not a type", typeName);
			typeName.struct = Tab.noType;
		}
		else {
			// Correct type
			typeName.struct = type.getType();
		}
	}
	
	public void visit(EnumerationName enumerationName) {
		Obj enumeration = Tab.find(enumerationName.getName());
		if (enumeration != Tab.noObj) {
			reportError("Symbol \"" + enumerationName.getName() + "\" is already defined", enumerationName);
			enumerationName.obj = Tab.noObj;
		} else {
			// Correct enum type
			Struct enumType = new Struct(Struct.Enum);
			enumerationName.obj = Tab.insert(Obj.Type, enumerationName.getName(), enumType);
			Tab.openScope();
		}
	}
	
	private boolean enumExists(EnumIdent enumIdent) {
		SyntaxNode parent = enumIdent.getParent();
		for (; !(parent instanceof EnumDeclDefinition); parent = parent.getParent());
		return ((EnumDeclDefinition)parent).getEnumName().obj != Tab.noObj;
	}
	
	private int getPrevEnumConstVal(EnumIdent enumIdent) {
		SyntaxNode parent = enumIdent.getParent();
		if (parent instanceof FirstEnumIdentifier) {
			return -1;
		} else {
			SyntaxNode previousEnumIdentList = ((EnumIdentifiers)parent).getEnumIdentList();
			SyntaxNode previousEnumConstant = null;
			if (previousEnumIdentList instanceof FirstEnumIdentifier) {
				previousEnumConstant = ((FirstEnumIdentifier)previousEnumIdentList).getEnumIdent();
			} else {
				previousEnumConstant = ((EnumIdentifiers)previousEnumIdentList).getEnumIdent();
			}
			return ((EnumIdent)previousEnumConstant).obj.getAdr();
		}
	}
	
	private boolean enumConstValExists(EnumIdent enumIdent, int value) {
		SyntaxNode parent = enumIdent.getParent();
		if (parent instanceof FirstEnumIdentifier) {
			return false;
		} else {
			parent = ((EnumIdentifiers)parent).getEnumIdentList();
			for (; parent instanceof EnumIdentifiers; parent = ((EnumIdentifiers)parent).getEnumIdentList()) {
				EnumIdent previousEnumConstant = ((EnumIdentifiers)parent).getEnumIdent();
				if (previousEnumConstant.obj.getAdr() == value) {
					return true;
				}
			}
			EnumIdent previousEnumConstant = ((FirstEnumIdentifier)parent).getEnumIdent();
			if (previousEnumConstant.obj.getAdr() == value) {
				return true;
			}
			
			return false;
		}
	}
	
	public void visit(NumEnumIdentifier numEnumIdentifier) {
		if (!enumExists(numEnumIdentifier)) {
			return;
		}
		Obj enumConstant = Tab.find(numEnumIdentifier.getName());
		if (enumConstant != Tab.noObj) {
			reportError("Symbol \"" + numEnumIdentifier.getName() + "\" is already defined", numEnumIdentifier);
			numEnumIdentifier.obj = Tab.noObj;
		} else {
			int value = numEnumIdentifier.getValue();
			if (enumConstValExists(numEnumIdentifier, value)) {
				reportError("Enum constant value already exists", numEnumIdentifier);
				numEnumIdentifier.obj = Tab.noObj;
			} else {
				// Correct enum constant
				numEnumIdentifier.obj = Tab.insert(Obj.Con, numEnumIdentifier.getName(), Tab.intType);
				numEnumIdentifier.obj.setAdr(value);
			}
		}
	}
	
	public void visit(EnumIdentifier enumIdentifier) {
		if (!enumExists(enumIdentifier)) {
			return;
		}
		Obj enumConstant = Tab.find(enumIdentifier.getName());
		if (enumConstant != Tab.noObj) {
			reportError("Symbol \"" + enumIdentifier.getName() + "\" is already defined", enumIdentifier);
			enumIdentifier.obj = Tab.noObj;
		} else {
			int value = getPrevEnumConstVal(enumIdentifier) + 1;
			if (enumConstValExists(enumIdentifier, value)) {
				reportError("Enum constant value already exists", enumIdentifier);
				enumIdentifier.obj = Tab.noObj;
			} else {
				// Correct enum constant
				enumIdentifier.obj = Tab.insert(Obj.Con, enumIdentifier.getName(), Tab.intType);
				enumIdentifier.obj.setAdr(value);
			}
		}
	}
	
	public void visit(EnumDeclDefinition enumDeclDefinition) {
		if (enumDeclDefinition.getEnumName().obj == Tab.noObj) {
			return;
		} else {
			Tab.chainLocalSymbols(enumDeclDefinition.getEnumName().obj.getType());
			Tab.closeScope();
		}
	}
	
	public void visit(ArrayVarIdentifier arrayVarIdentifier) {
		SyntaxNode parent = arrayVarIdentifier.getParent();
		// Find type of array element
		for (; !(parent instanceof VarDeclDefinition); parent = parent.getParent());
		Struct type = ((VarDeclDefinition)parent).getType().struct;
		Obj symbol = Tab.find(arrayVarIdentifier.getName());
		if (symbol != Tab.noObj) {
			reportError("Symbol \"" + arrayVarIdentifier.getName() + "\" is already defined", arrayVarIdentifier);
			arrayVarIdentifier.obj = Tab.noObj;
		} else {
			// Correct array variable
			Struct arrayType = new Struct(Struct.Array, type);
			arrayVarIdentifier.obj = Tab.insert(Obj.Var, arrayVarIdentifier.getName(), arrayType);
		}
	}
	
	public void visit(VarIdentifier varIdentifier) {
		SyntaxNode parent = varIdentifier.getParent();
		// Find type of variable
		for (; !(parent instanceof VarDeclDefinition); parent = parent.getParent());
		Struct type = ((VarDeclDefinition)parent).getType().struct;
		Obj symbol = Tab.find(varIdentifier.getName());
		if (symbol != Tab.noObj) {
			reportError("Symbol \"" + varIdentifier.getName() + "\" is already defined", varIdentifier);
			varIdentifier.obj = Tab.noObj;
		} else {
			// Correct variable
			varIdentifier.obj = Tab.insert(Obj.Var, varIdentifier.getName(), type);
		}
	}
	
	public void visit(LastArrayVarIdentifier lastArrayVarIdentifier) {
		SyntaxNode parent = lastArrayVarIdentifier.getParent();
		// Find type of array element
		for (; !(parent instanceof VarDeclDefinition); parent = parent.getParent());
		Struct type = ((VarDeclDefinition)parent).getType().struct;
		Obj symbol = Tab.find(lastArrayVarIdentifier.getName());
		if (symbol != Tab.noObj) {
			reportError("Symbol \"" + lastArrayVarIdentifier.getName() + "\" is already defined", lastArrayVarIdentifier);
			lastArrayVarIdentifier.obj = Tab.noObj;
		} else {
			// Correct array variable
			Struct arrayType = new Struct(Struct.Array, type);
			lastArrayVarIdentifier.obj = Tab.insert(Obj.Var, lastArrayVarIdentifier.getName(), arrayType);
		}
	}
	
	public void visit(LastSingleVarIdentifier lastSingleVarIdentifier) {
		SyntaxNode parent = lastSingleVarIdentifier.getParent();
		// Find type of variable
		for (; !(parent instanceof VarDeclDefinition); parent = parent.getParent());
		Struct type = ((VarDeclDefinition)parent).getType().struct;
		Obj symbol = Tab.find(lastSingleVarIdentifier.getName());
		if (symbol != Tab.noObj) {
			reportError("Symbol \"" + lastSingleVarIdentifier.getName() + "\" is already defined", lastSingleVarIdentifier);
			lastSingleVarIdentifier.obj = Tab.noObj;
		} else {
			// Correct variable
			lastSingleVarIdentifier.obj = Tab.insert(Obj.Var, lastSingleVarIdentifier.getName(), type);
		}
	}
}
