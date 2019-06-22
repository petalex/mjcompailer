package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

import org.apache.log4j.Logger;

public class MJSemanticAnalyzer extends VisitorAdaptor {
	private boolean errorDetected;

	private static Logger log = Logger.getLogger(MJSemanticAnalyzer.class);

	public boolean isErrorDetected() {
		return errorDetected;
	}

	private String getMessage(String message, SyntaxNode info) {
		StringBuilder stringBuilder = new StringBuilder("[Semantic Analyser] ");
		stringBuilder.append(message);
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

	private int numberOfVariables;

	public int getNumberOfVariables() {
		return this.numberOfVariables;
	}

	private Obj program = null;

	private static final Struct boolType = new Struct(Struct.Bool);

	private List<Struct> arrayTypes = null;

	private Struct getArrayType(Struct arrayElementType) {
		if (this.arrayTypes == null)
			return null;
		Struct arrayType = null;
		for (int index = 0; index < this.arrayTypes.size(); ++index) {
			if (this.arrayTypes.get(index).getElemType() == arrayElementType) {
				// Existing array type
				arrayType = this.arrayTypes.get(index);
				break;
			}
		}
		if (arrayType == null) {
			// New array type
			arrayType = new Struct(Struct.Array, arrayElementType);
			this.arrayTypes.add(arrayType);
		}
		return arrayType;
	}

	public void visit(ProgramName programName) {
		// Add boolean type to universe scope
		Tab.insert(Obj.Type, "bool", MJSemanticAnalyzer.boolType);

		this.program = Tab.insert(Obj.Prog, programName.getName(), Tab.noType);
		Tab.openScope();

		this.arrayTypes = new ArrayList<Struct>();
	}

	public void visit(Program program) {
		Obj mainMethod = Tab.currentScope().findSymbol("main");
		if (mainMethod == null || mainMethod == Tab.noObj) {
			reportError("Symbol \"main\" is not defined", program);
		} else if (mainMethod.getKind() != Obj.Meth) {
			reportError("Symbol \"main\" is not a method", program);
		} else if (mainMethod.getType() != Tab.noType) {
			reportError("Symbol \"main\" is not a void method", program);
		} else if (mainMethod.getLevel() > 0) {
			reportError("Symbol \"main\" has formal parameters", program);
		}

		numberOfVariables = Tab.currentScope().getnVars();
		Tab.chainLocalSymbols(this.program);
		Tab.closeScope();
		this.program = null;
		this.arrayTypes = null;
	}

	public void visit(TypeName typeName) {
		Obj type = Tab.find(typeName.getName());
		if (type == Tab.noObj) {
			reportError("Symbol \"" + typeName.getName() + "\" is not defined", typeName);
			typeName.struct = Tab.noType;
		} else if (type.getKind() != Obj.Type) {
			reportError("Symbol \"" + typeName.getName() + "\" is not a type", typeName);
			typeName.struct = Tab.noType;
		} else {
			// Correct type
			typeName.struct = type.getType();
		}
	}

	private Struct constantType = null;

	public void visit(ConstantType constantType) {
		this.constantType = constantType.getType().struct;
	}

	public void visit(NumConstIdentifier numConstIdentifier) {
		Obj constant = Tab.currentScope().findSymbol(numConstIdentifier.getName());
		if (constant != null && constant != Tab.noObj) {
			reportError("Symbol \"" + numConstIdentifier.getName() + "\" is already defined", numConstIdentifier);
		} else if (this.constantType != Tab.intType) {
			reportError("Constant type is not int, but initialized with int value", numConstIdentifier);
		} else {
			// Correct integer constant
			constant = Tab.insert(Obj.Con, numConstIdentifier.getName(), this.constantType);
			constant.setAdr(numConstIdentifier.getValue());
		}
	}

	public void visit(CharConstIdentifier charConstIdentifier) {
		Obj constant = Tab.currentScope().findSymbol(charConstIdentifier.getName());
		if (constant != null && constant != Tab.noObj) {
			reportError("Symbol \"" + charConstIdentifier.getName() + "\" is already defined", charConstIdentifier);
		} else if (this.constantType != Tab.charType) {
			reportError("Constant type is not char, but initialized with char value", charConstIdentifier);
		} else {
			// Correct char constant
			constant = Tab.insert(Obj.Con, charConstIdentifier.getName(), this.constantType);
			constant.setAdr(charConstIdentifier.getValue().charValue());
		}
	}

	public void visit(BoolConstIdentifier boolConstIdentifier) {
		Obj constant = Tab.currentScope().findSymbol(boolConstIdentifier.getName());
		if (constant != null && constant != Tab.noObj) {
			reportError("Symbol \"" + boolConstIdentifier.getName() + "\" is already defined", boolConstIdentifier);
		} else if (this.constantType != MJSemanticAnalyzer.boolType) {
			reportError("Constant type is not boolean, but initialized with boolean value", boolConstIdentifier);
		} else {
			// Correct boolean constant
			constant = Tab.insert(Obj.Con, boolConstIdentifier.getName(), this.constantType);
			constant.setAdr(boolConstIdentifier.getValue().booleanValue() ? 1 : 0);
		}
	}

	public void visit(ConstDeclDefinition constDeclDefinition) {
		this.constantType = null;
	}

	private Struct enumerationType = null;

	private List<Obj> enumerationConstants = null;

	public void visit(EnumerationName enumerationName) {
		Obj enumeration = Tab.currentScope().findSymbol(enumerationName.getName());
		if (enumeration != null && enumeration != Tab.noObj) {
			reportError("Symbol \"" + enumerationName.getName() + "\" is already defined", enumerationName);
			this.enumerationType = Tab.noType;
		} else {
			// Correct enumeration type
			this.enumerationType = new Struct(Struct.Enum);
			Tab.insert(Obj.Type, enumerationName.getName(), this.enumerationType);
			this.enumerationConstants = new ArrayList<Obj>();
			Tab.openScope();
		}
	}

	public void visit(NumEnumIdentifier numEnumIdentifier) {
		if (this.enumerationType == Tab.noType)
			return;
		Obj enumumerationConstant = Tab.currentScope().findSymbol(numEnumIdentifier.getName());
		if (enumumerationConstant != null && enumumerationConstant != Tab.noObj) {
			reportError("Symbol \"" + numEnumIdentifier.getName() + "\" is already defined", numEnumIdentifier);
		} else {
			int enumumerationConstantValue = numEnumIdentifier.getValue();
			for (int index = 0; index < this.enumerationConstants.size(); ++index) {
				if (this.enumerationConstants.get(index).getAdr() == enumumerationConstantValue) {
					reportError("Enumeration constant value already exists", numEnumIdentifier);
					return;
				}
			}
			// Correct enumeration constant
			enumumerationConstant = Tab.insert(Obj.Con, numEnumIdentifier.getName(), this.enumerationType);
			enumumerationConstant.setAdr(enumumerationConstantValue);
			this.enumerationConstants.add(enumumerationConstant);
		}
	}

	public void visit(EnumIdentifier enumIdentifier) {
		if (this.enumerationType == Tab.noType)
			return;
		Obj enumumerationConstant = Tab.currentScope().findSymbol(enumIdentifier.getName());
		if (enumumerationConstant != null && enumumerationConstant != Tab.noObj) {
			reportError("Symbol \"" + enumIdentifier.getName() + "\" is already defined", enumIdentifier);
		} else {
			int enumumerationConstantValue;
			if (this.enumerationConstants.isEmpty()) {
				enumumerationConstantValue = 0;
			} else {
				enumumerationConstantValue = this.enumerationConstants.get(this.enumerationConstants.size() - 1)
						.getAdr() + 1;
			}
			for (int index = 0; index < this.enumerationConstants.size(); ++index) {
				if (this.enumerationConstants.get(index).getAdr() == enumumerationConstantValue) {
					reportError("Enumeration constant value already exists", enumIdentifier);
					return;
				}
			}
			// Correct enumeration constant
			enumumerationConstant = Tab.insert(Obj.Con, enumIdentifier.getName(), this.enumerationType);
			enumumerationConstant.setAdr(enumumerationConstantValue);
			this.enumerationConstants.add(enumumerationConstant);
		}
	}

	public void visit(EnumDeclDefinition enumDeclDefinition) {
		if (this.enumerationType != Tab.noType) {
			Tab.chainLocalSymbols(this.enumerationType);
			Tab.closeScope();
		}
		this.enumerationType = null;
		this.enumerationConstants = null;
	}

	private Struct variableType = null;

	public void visit(VariableType variableType) {
		this.variableType = variableType.getType().struct;
	}

	public void visit(ArrayVarIdentifier arrayVarIdentifier) {
		if (this.variableType == Tab.noType)
			return;
		Obj variable = Tab.currentScope().findSymbol(arrayVarIdentifier.getName());
		if (variable != null && variable != Tab.noObj) {
			reportError("Symbol \"" + arrayVarIdentifier.getName() + "\" is already defined", arrayVarIdentifier);
		} else {
			// Correct array variable
			Tab.insert(Obj.Var, arrayVarIdentifier.getName(), this.getArrayType(this.variableType));
		}
	}

	public void visit(VarIdentifier varIdentifier) {
		if (this.variableType == Tab.noType)
			return;
		Obj variable = Tab.currentScope().findSymbol(varIdentifier.getName());
		if (variable != null && variable != Tab.noObj) {
			reportError("Symbol \"" + varIdentifier.getName() + "\" is already defined", varIdentifier);
		} else {
			// Correct variable
			Tab.insert(Obj.Var, varIdentifier.getName(), this.variableType);
		}
	}

	public void visit(LastArrayVarIdentifier lastArrayVarIdentifier) {
		if (this.variableType == Tab.noType)
			return;
		Obj variable = Tab.currentScope().findSymbol(lastArrayVarIdentifier.getName());
		if (variable != null && variable != Tab.noObj) {
			reportError("Symbol \"" + lastArrayVarIdentifier.getName() + "\" is already defined",
					lastArrayVarIdentifier);
		} else {
			// Correct array variable
			Tab.insert(Obj.Var, lastArrayVarIdentifier.getName(), this.getArrayType(this.variableType));
		}
	}

	public void visit(LastSingleVarIdentifier lastSingleVarIdentifier) {
		if (this.variableType == Tab.noType)
			return;
		Obj variable = Tab.currentScope().findSymbol(lastSingleVarIdentifier.getName());
		if (variable != null && variable != Tab.noObj) {
			reportError("Symbol \"" + lastSingleVarIdentifier.getName() + "\" is already defined",
					lastSingleVarIdentifier);
		} else {
			// Correct variable
			Tab.insert(Obj.Var, lastSingleVarIdentifier.getName(), this.variableType);
		}
	}

	public void visit(VarDeclDefinition varDeclDefinition) {
		this.variableType = null;
	}

	private Obj classObj = null;
	private int classFieldAddress = 0;

	public void visit(ClassName className) {
		Obj symbol = Tab.currentScope().findSymbol(className.getName());
		if (symbol != null && symbol != Tab.noObj) {
			reportError("Symbol \"" + className.getName() + "\" is already defined", className);
			this.classObj = Tab.noObj;
		} else {
			// Correct class
			this.classObj = Tab.insert(Obj.Type, className.getName(), new Struct(Struct.Class));
			this.classFieldAddress = 1; // Skip virtual table pointer
			
			// For generating code
			className.obj = this.classObj;
						
			Tab.openScope();
		}
	}

	public void visit(ExtendsAndImplements extendsAndImplements) {
		if (this.classObj == Tab.noObj)
			return;
		Struct baseClass = extendsAndImplements.getType().struct;
		if (baseClass == Tab.noType)
			return;
		if (baseClass.getKind() != Struct.Class) {
			reportError("Base class type is not a class", extendsAndImplements);
		} else {
			// Correct base class
			this.classObj.getType().setElementType(baseClass);
		}
	}

	public void visit(ExtendsClass extendsClass) {
		if (this.classObj == Tab.noObj)
			return;
		Struct baseClass = extendsClass.getType().struct;
		if (baseClass == Tab.noType)
			return;
		if (baseClass.getKind() != Struct.Class) {
			reportError("Base class type is not a class", extendsClass);
		} else {
			// Correct base class
			this.classObj.getType().setElementType(baseClass);
		}
	}

	private boolean checkExistingInterfaces(Struct newInterface) {
		if (this.classObj == Tab.noObj)
			return false;
		Collection<Struct> implementedInterfaces = this.classObj.getType().getImplementedInterfaces();
		for (Struct implementedInterface : implementedInterfaces) {
			if (newInterface.equals(implementedInterface)) {
				return false;
			}
		}
		return true;
	}

	public void visit(Types types) {
		if (this.classObj == Tab.noObj)
			return;
		Struct type = types.getType().struct;
		if (type == Tab.noType)
			return;
		if (type.getKind() != Struct.Interface) {
			reportError("Implemented interface type is not an interface", types);
		} else if (!checkExistingInterfaces(type)) {
			reportError("Interface is implemented more than once", types);
		} else {
			this.classObj.getType().addImplementedInterface(type);
		}
	}

	public void visit(FirstType firstType) {
		if (this.classObj == Tab.noObj)
			return;
		Struct type = firstType.getType().struct;
		if (type == Tab.noType)
			return;
		if (type.getKind() != Struct.Interface) {
			reportError("Implemented interface type is not an interface", firstType);
		} else if (!checkExistingInterfaces(type)) {
			reportError("Interface is implemented more than once", firstType);
		} else {
			this.classObj.getType().addImplementedInterface(type);
		}
	}

	public void visit(ExtendsAndImplementsEnd extendsAndImplementsEnd) {
		if (this.classObj == Tab.noObj)
			return;
		Struct baseClass = this.classObj.getType().getElemType();
		if (baseClass == null)
			return;
		// Copy all fields from base class
		Collection<Obj> baseClassMembers = baseClass.getMembers();
		int baseClassFieldCount = baseClass.getNumberOfFields();
		for (Obj baseClassMember : baseClassMembers) {
			if (baseClassFieldCount-- == 0)
				// Skip all methods from base class
				break;
			Obj newMember = Tab.insert(baseClassMember.getKind(), baseClassMember.getName(), baseClassMember.getType());
			newMember.setAdr(this.classFieldAddress++);
			newMember.setLevel(baseClassMember.getLevel());
			if (baseClassMember.getFpPos() == MJSemanticAnalyzer.Modifer.PRIVATE.ordinal()) {
				// Change private fields from base class to inherited private fields
				newMember.setFpPos(MJSemanticAnalyzer.Modifer.INHERITED_PRIVATE.ordinal());
			} else {
				newMember.setFpPos(baseClassMember.getFpPos());
			}
		}
	}

	private static enum Modifer {
		INHERITED_PRIVATE, PRIVATE, PROTECTED, PUBLIC
	}

	private MJSemanticAnalyzer.Modifer fieldModifier = null;

	private Struct fieldType = null;

	public void visit(FieldModifer fldModifier) {
		if (this.classObj == null)
			return;
		Modifier modifer = fldModifier.getModifier();
		if (modifer instanceof PrivateModifier) {
			this.fieldModifier = MJSemanticAnalyzer.Modifer.PRIVATE;
		} else if (modifer instanceof ProtectedModifier) {
			this.fieldModifier = MJSemanticAnalyzer.Modifer.PROTECTED;
		} else if (modifer instanceof PublicModifier) {
			this.fieldModifier = MJSemanticAnalyzer.Modifer.PUBLIC;
		}
	}

	public void visit(FieldType fieldType) {
		if (this.classObj == null || this.fieldModifier == null)
			return;
		this.fieldType = fieldType.getType().struct;
	}

	public void visit(ArrayFieldIdentifier arrayFieldIdentifier) {
		if (this.classObj == null || this.fieldModifier == null || this.fieldType == Tab.noType)
			return;
		Obj field = Tab.currentScope().findSymbol(arrayFieldIdentifier.getName());
		if (field != null && field != Tab.noObj) {
			reportError("Symbol \"" + arrayFieldIdentifier.getName() + "\" is already defined in class "
					+ this.classObj.getName() + " or in its base class", arrayFieldIdentifier);
		} else {
			// Correct array field
			field = Tab.insert(Obj.Fld, arrayFieldIdentifier.getName(), this.getArrayType(this.fieldType));
			field.setAdr(this.classFieldAddress++);
			// Set field's access modifier
			field.setFpPos(this.fieldModifier.ordinal());
		}
	}

	public void visit(FieldIdentifier fieldIdentifier) {
		if (this.classObj == null || this.fieldModifier == null || this.fieldType == Tab.noType)
			return;
		Obj field = Tab.currentScope().findSymbol(fieldIdentifier.getName());
		if (field != null && field != Tab.noObj) {
			reportError("Symbol \"" + fieldIdentifier.getName() + "\" is already defined in class "
					+ this.classObj.getName() + " or in its base class", fieldIdentifier);
		} else {
			// Correct field
			field = Tab.insert(Obj.Fld, fieldIdentifier.getName(), this.fieldType);
			field.setAdr(this.classFieldAddress++);
			// Set field's access modifier
			field.setFpPos(this.fieldModifier.ordinal());
		}
	}

	public void visit(LastArrayFieldIdentifier lastArrayFieldIdentifier) {
		if (this.classObj == null || this.fieldModifier == null || this.fieldType == Tab.noType)
			return;
		Obj field = Tab.currentScope().findSymbol(lastArrayFieldIdentifier.getName());
		if (field != null && field != Tab.noObj) {
			reportError("Symbol \"" + lastArrayFieldIdentifier.getName() + "\" is already defined in class "
					+ this.classObj.getName() + " or in its base class", lastArrayFieldIdentifier);
		} else {
			// Correct array field
			field = Tab.insert(Obj.Fld, lastArrayFieldIdentifier.getName(), this.getArrayType(this.fieldType));
			field.setAdr(this.classFieldAddress++);
			// Set field's access modifier
			field.setFpPos(this.fieldModifier.ordinal());
		}
	}

	public void visit(LastSingleFieldIdentifier lastSingleFieldIdentifier) {
		if (this.classObj == null || this.fieldModifier == null || this.fieldType == Tab.noType)
			return;
		Obj field = Tab.currentScope().findSymbol(lastSingleFieldIdentifier.getName());
		if (field != null && field != Tab.noObj) {
			reportError("Symbol \"" + lastSingleFieldIdentifier.getName() + "\" is already defined in class "
					+ this.classObj.getName() + " or in its base class", lastSingleFieldIdentifier);
		} else {
			// Correct field
			field = Tab.insert(Obj.Fld, lastSingleFieldIdentifier.getName(), this.fieldType);
			field.setAdr(this.classFieldAddress++);
			// Set field's access modifier
			field.setFpPos(this.fieldModifier.ordinal());
		}
	}

	public void visit(FieldDeclDefinition fieldDeclDefinition) {
		this.fieldType = null;
	}

	public void visit(ClassFieldDeclarationEnd classFieldDeclarationEnd) {
		if (this.classObj == Tab.noObj)
			return;
		Tab.chainLocalSymbols(this.classObj.getType());
		Struct baseClass = this.classObj.getType().getElemType();
		if (baseClass == null)
			return;
		// Copy all methods from base class
		Collection<Obj> baseClassMembers = baseClass.getMembers();
		int baseClassFieldCount = baseClass.getNumberOfFields();
		for (Obj baseClassMember : baseClassMembers) {
			if (baseClassFieldCount-- > 0)
				// Skip all fields from base class
				continue;
			Obj newMember = Tab.insert(baseClassMember.getKind(), baseClassMember.getName(), baseClassMember.getType());
			newMember.setLevel(baseClassMember.getLevel());
			if (baseClassMember.getFpPos() == MJSemanticAnalyzer.Modifer.PRIVATE.ordinal()) {
				// Change private methods from base class to inherited private methods
				newMember.setFpPos(MJSemanticAnalyzer.Modifer.INHERITED_PRIVATE.ordinal());
			} else {
				newMember.setFpPos(baseClassMember.getFpPos());
			}
			Collection<Obj> baseClassMemberLocals = baseClassMember.getLocalSymbols();
			Tab.openScope();
			for (Obj baseClassMemberLocal : baseClassMemberLocals) {
				Struct newMemberLocalType = null;
				if (baseClassMemberLocal.getName().equals("this")) {
					// Change type of hidden this parameter to extended class type
					newMemberLocalType = this.classObj.getType();
				} else {
					newMemberLocalType = baseClassMemberLocal.getType();
				}
				Obj newMemberLocal = Tab.insert(baseClassMemberLocal.getKind(), baseClassMemberLocal.getName(),
						newMemberLocalType);
				newMemberLocal.setFpPos(baseClassMemberLocal.getFpPos());
			}
			Tab.chainLocalSymbols(newMember);
			Tab.closeScope();
		}
	}

	private MJSemanticAnalyzer.Modifer classMethodModifier = null;

	private Struct classMethodType = null;

	private Obj classMethod = null;

	private int classMethodLevel = 0;

	private Obj redefinedClassMethod = null;

	public void visit(ClassMethodModifier classMethModifier) {
		if (this.classObj == null)
			return;
		Modifier modifer = classMethModifier.getModifier();
		if (modifer instanceof PrivateModifier) {
			this.classMethodModifier = MJSemanticAnalyzer.Modifer.PRIVATE;
		} else if (modifer instanceof ProtectedModifier) {
			this.classMethodModifier = MJSemanticAnalyzer.Modifer.PROTECTED;
		} else if (modifer instanceof PublicModifier) {
			this.classMethodModifier = MJSemanticAnalyzer.Modifer.PUBLIC;
		}
	}

	public void visit(ClassMethodType clssMethodType) {
		if (this.classObj == null || this.classMethodModifier == null)
			return;
		Struct type = clssMethodType.getType().struct;
		this.classMethodType = (type != Tab.noType) ? type : null;
	}

	public void visit(NoClassMethodType noClassMethodType) {
		if (this.classObj == null || this.classMethodModifier == null)
			return;
		this.classMethodType = Tab.noType;
	}

	public void visit(ClassMethodName classMethodName) {
		if (this.classObj == null || this.classMethodModifier == null || this.classMethodType == null) {
			this.classMethod = Tab.noObj;
			return;
		}
		Obj symbol = Tab.currentScope().findSymbol(classMethodName.getName());
		if (symbol != null && symbol != Tab.noObj) {
			if (symbol.getKind() == Obj.Meth) {
				// Potential method redefinition
				this.redefinedClassMethod = symbol;

				// Method will be used for later redefinition only if redefinition really
				// occurred
				this.classMethod = new Obj(Obj.Meth, classMethodName.getName(), this.classMethodType);
				this.classMethodLevel = 0;
				this.returnTypes = new ArrayList<Struct>();

				// Set class method's access modifier
				this.classMethod.setFpPos(this.classMethodModifier.ordinal());

				Tab.openScope();

				// Set class method's hidden this parameter
				Obj thisObj = Tab.insert(Obj.Var, "this", this.classObj.getType());
				thisObj.setFpPos(this.classMethodLevel++);
			} else {
				reportError("Symbol \"" + classMethodName.getName() + "\" is already defined", classMethodName);
				this.classMethod = Tab.noObj;
			}
		} else {
			// Correct class method
			this.classMethod = Tab.insert(Obj.Meth, classMethodName.getName(), this.classMethodType);
			this.classMethodLevel = 0;
			this.returnTypes = new ArrayList<Struct>();

			// Set class method's access modifier
			this.classMethod.setFpPos(this.classMethodModifier.ordinal());

			Tab.openScope();

			// Set class method's hidden this parameter
			Obj thisObj = Tab.insert(Obj.Var, "this", this.classObj.getType());
			thisObj.setFpPos(this.classMethodLevel++);
		}
	}

	private boolean checkRedefinedMethodDeclarations() {
		int classMethodModifier = this.classMethod.getFpPos();
		int redefinedClassMethodModifier = this.redefinedClassMethod.getFpPos();
		if (classMethodModifier < redefinedClassMethodModifier)
			return false;
		if (!this.classMethod.getName().equals(this.redefinedClassMethod.getName()))
			return false;
		if (!(this.classMethod.getType() == this.redefinedClassMethod.getType()))
			return false;
		if (!(this.classMethod.getLevel() == this.redefinedClassMethod.getLevel()))
			return false;
		Collection<Obj> classMethodLocals = this.classMethod.getLocalSymbols();
		int classMethodParameterCount = 0;
		for (Obj classMethodLocal : classMethodLocals)
			// Check only parameters
			if (classMethodParameterCount++ < this.classMethod.getLevel()) {
				Collection<Obj> redefinedClassMethodLocals = this.redefinedClassMethod.getLocalSymbols();
				for (Obj redefinedClassMethodLocal : redefinedClassMethodLocals)
					if (classMethodLocal.getFpPos() == redefinedClassMethodLocal.getFpPos())
						if (!(classMethodLocal.getType() == redefinedClassMethodLocal.getType()))
							return false;
						else
							break;
			} else
				break;
		return true;
	}

	public void visit(ClassMethodDeclarationEnd classMethodDeclarationEnd) {
		if (this.classMethod == Tab.noObj)
			return;
		this.classMethod.setLevel(this.classMethodLevel);
		Tab.chainLocalSymbols(this.classMethod);

		if (this.redefinedClassMethod != null) {
			int classMethodModifier = this.classMethod.getFpPos();
			int redefinedClassMethodModifier = this.redefinedClassMethod.getFpPos();
			if (redefinedClassMethodModifier != MJSemanticAnalyzer.Modifer.INHERITED_PRIVATE.ordinal()
					&& classMethodModifier < redefinedClassMethodModifier) {
				reportError(
						"Symbol \"" + this.classMethod.getName() + "\" is already defined and could not be redefined",
						classMethodDeclarationEnd.getParent());
			} else if (!checkRedefinedMethodDeclarations()) {
				reportError(
						"Symbol \"" + this.classMethod.getName() + "\" is already defined and could not be redefined",
						classMethodDeclarationEnd.getParent());
			} else {
				// Redefine method
				this.redefinedClassMethod.setLevel(this.classMethod.getLevel());
				this.redefinedClassMethod.setFpPos(this.classMethod.getFpPos());
				Collection<Obj> classMethodLocals = this.classMethod.getLocalSymbols();
				Tab.openScope();
				for (Obj classMethodLocal : classMethodLocals) {
					Obj redefinedClassMethodLocal = Tab.insert(classMethodLocal.getKind(), classMethodLocal.getName(),
							classMethodLocal.getType());
					redefinedClassMethodLocal.setFpPos(classMethodLocal.getFpPos());
				}
				Tab.chainLocalSymbols(this.redefinedClassMethod);
				Tab.closeScope();
				this.classMethod = this.redefinedClassMethod;
			}
		}
		
		// For generating code
		((ClassMethodDeclaration) classMethodDeclarationEnd.getParent()).getClassMethName().obj = this.classMethod;
	}

	public void visit(ClassMethodDeclaration classMethodDeclaration) {
		if (this.classMethod != Tab.noObj) {
			if (!hasSameReturnTypes()) {
				reportError("Return expressions differ in type", classMethodDeclaration);
			} else {
				Struct returnType = this.returnTypes.isEmpty() ? Tab.noType : this.returnTypes.get(0);
				if (this.classMethodType != returnType) {
					reportError("Method and return expression(s) differ in type", classMethodDeclaration);
				}
			}
			Tab.closeScope();
		}
		this.classMethodModifier = null;
		this.classMethodType = null;
		this.classMethod = null;
		this.classMethodLevel = 0;
		this.returnTypes = null;
		this.redefinedClassMethod = null;
	}

	private boolean checkInterfaceAndClassMethodDeclarations(Obj interfaceMethod, Obj classMethod) {
		// Must exclude hidden this parameter from checking!
		if (!interfaceMethod.getName().equals(classMethod.getName()))
			return false;
		if (!(interfaceMethod.getType() == classMethod.getType()))
			return false;
		if (!(interfaceMethod.getLevel() == classMethod.getLevel() - 1))
			return false;
		Collection<Obj> interfaceMethodLocals = interfaceMethod.getLocalSymbols();
		for (Obj interfaceMethodLocal : interfaceMethodLocals) {
			Collection<Obj> classMethodLocals = classMethod.getLocalSymbols();
			for (Obj classMethodLocal : classMethodLocals)
				if (interfaceMethodLocal.getFpPos() == classMethodLocal.getFpPos() - 1)
					if (!(interfaceMethodLocal.getType() == classMethodLocal.getType()))
						return false;
					else
						break;
		}
		return true;
	}

	private boolean checkInterfaceMethodDeclarations(Obj interface1Method, Obj interface2Method) {
		if (!(interface1Method.getType() == interface2Method.getType()))
			return false;
		if (!(interface1Method.getLevel() == interface2Method.getLevel()))
			return false;
		Collection<Obj> interface1MethodLocals = interface1Method.getLocalSymbols();
		for (Obj interface1MethodLocal : interface1MethodLocals) {
			Collection<Obj> interface2MethodLocals = interface2Method.getLocalSymbols();
			for (Obj interface2MethodLocal : interface2MethodLocals)
				if (interface1MethodLocal.getFpPos() == interface2MethodLocal.getFpPos())
					if (!(interface1MethodLocal.getType() == interface2MethodLocal.getType()))
						return false;
					else
						break;
		}
		return true;
	}

	public void visit(ClassDeclDefinition classDeclDefinition) {
		if (this.classObj != Tab.noObj) {
			Tab.chainLocalSymbols(this.classObj.getType());
			Tab.closeScope();

			// Check if all interfaces methods are implemented
			Collection<Struct> classInterfaces = this.classObj.getType().getImplementedInterfaces();
			boolean hasUnimplementedInterfaceMethod;
			for (Struct classInterface : classInterfaces) {
				Collection<Obj> interfaceMethods = classInterface.getMembers();
				for (Obj interfaceMethod : interfaceMethods) {
					Collection<Obj> classMembers = this.classObj.getType().getMembers();
					hasUnimplementedInterfaceMethod = true;
					for (Obj classMember : classMembers) {
						if (classMember.getKind() == Obj.Meth) {
							if (checkInterfaceAndClassMethodDeclarations(interfaceMethod, classMember)) {
								hasUnimplementedInterfaceMethod = false;
								break;
							}
						}
					}
					if (hasUnimplementedInterfaceMethod) {
						reportError("Interface method \"" + interfaceMethod.getName()
								+ "\" is not implemented in class \"" + this.classObj.getName() + "\"",
								classDeclDefinition);
					}
				}
			}

			// Check if all interfaces have matched same named method declarations
			int interface1Index = 1;
			int interface2Index;
			for (Struct interface1 : classInterfaces) {
				interface2Index = 1;
				for (Struct interface2 : classInterfaces) {
					if (interface2Index > interface1Index) {
						Collection<Obj> interface1Methods = interface1.getMembers();
						Collection<Obj> interface2Methods = interface2.getMembers();
						boolean hasUnmatchedInterfaceMethods = false;
						String unmatchedInterfaceMethodName = null;
						for (Obj interface1Method : interface1Methods) {
							for (Obj interface2Method : interface2Methods) {
								if (interface1Method.getName().equals(interface2Method.getName())) {
									if (!checkInterfaceMethodDeclarations(interface1Method, interface2Method)) {
										hasUnmatchedInterfaceMethods = true;
										unmatchedInterfaceMethodName = interface1Method.getName();
										break;
									}
								}
							}
							if (hasUnmatchedInterfaceMethods) {
								reportError(
										"Method \"" + unmatchedInterfaceMethodName + "\" declaration from interfaces #"
												+ interface1Index + " and #" + interface2Index + " differ",
										classDeclDefinition);
								hasUnmatchedInterfaceMethods = false;
								unmatchedInterfaceMethodName = null;
								break;
							}
						}
					}
					++interface2Index;
				}
				++interface1Index;
			}
		}

		this.classObj = null;
		this.classFieldAddress = 1;
	}

	private Struct interfaceType = null;

	public void visit(InterfaceName interfaceName) {
		Obj symbol = Tab.currentScope().findSymbol(interfaceName.getName());
		if (symbol != null && symbol != Tab.noObj) {
			reportError("Symbol \"" + interfaceName.getName() + "\" is already defined", interfaceName);
			this.interfaceType = Tab.noType;
		} else {
			// Correct interface
			this.interfaceType = new Struct(Struct.Interface);
			Tab.insert(Obj.Type, interfaceName.getName(), this.interfaceType);
			Tab.openScope();
		}
	}

	public void visit(InterfaceDeclDefinition interfaceDeclDefinition) {
		if (this.interfaceType != Tab.noType) {
			Tab.chainLocalSymbols(this.interfaceType);
			Tab.closeScope();
		}
		this.interfaceType = null;
	}

	private Struct interfaceMethodType = null;

	private Obj interfaceMethod = null;

	private int interfaceMethodLevel = 0;

	public void visit(InterfaceMethodType interfaceMethodType) {
		Struct type = interfaceMethodType.getType().struct;
		this.interfaceMethodType = (type != Tab.noType) ? type : null;
	}

	public void visit(NoInterfaceMethodType noInterfaceMethodType) {
		this.interfaceMethodType = Tab.noType;
	}

	public void visit(InterfaceMethodName interfaceMethodName) {
		if (this.interfaceMethodType == null) {
			this.interfaceMethod = Tab.noObj;
			return;
		}
		Obj method = Tab.currentScope().findSymbol(interfaceMethodName.getName());
		if (method != null && method != Tab.noObj) {
			reportError("Symbol \"" + interfaceMethodName.getName() + "\" is already defined", interfaceMethodName);
			this.interfaceMethod = Tab.noObj;
		} else {
			// Correct method
			this.interfaceMethod = Tab.insert(Obj.Meth, interfaceMethodName.getName(), this.interfaceMethodType);
			this.interfaceMethodLevel = 0;
			Tab.openScope();
		}
	}

	public void visit(InterfaceMethodDeclaration interfaceMethodDeclaration) {
		if (this.interfaceMethod != Tab.noObj) {
			this.interfaceMethod.setLevel(this.interfaceMethodLevel);
			Tab.chainLocalSymbols(this.interfaceMethod);
			Tab.closeScope();
		}
		this.interfaceMethod = null;
		this.interfaceMethod = null;
		this.interfaceMethodLevel = 0;
	}

	private Struct methodType = null;

	private Obj method = null;

	private int methodLevel = 0;

	public void visit(MethodType methodType) {
		Struct type = methodType.getType().struct;
		this.methodType = (type != Tab.noType) ? type : null;
	}

	public void visit(NoMethodType noMethodType) {
		this.methodType = Tab.noType;
	}

	public void visit(MethodName methodName) {
		if (this.methodType == null) {
			this.method = Tab.noObj;
			return;
		}
		Obj symbol = Tab.currentScope().findSymbol(methodName.getName());
		if (symbol != null && symbol != Tab.noObj) {
			reportError("Symbol \"" + methodName.getName() + "\" is already defined", methodName);
			this.method = Tab.noObj;
		} else {
			// Correct method
			this.method = Tab.insert(Obj.Meth, methodName.getName(), this.methodType);
			
			// For generating code
			methodName.obj = this.method;
			
			this.methodLevel = 0;
			this.returnTypes = new ArrayList<Struct>();
			Tab.openScope();
		}
	}

	public void visit(MethodDeclarationEnd methodDeclarationEnd) {
		if (this.method == Tab.noObj)
			return;
		this.method.setLevel(this.methodLevel);
		Tab.chainLocalSymbols(this.method);
	}

	public void visit(MethodDeclaration methodDeclaration) {
		if (this.method != Tab.noObj) {
			if (!hasSameReturnTypes()) {
				reportError("Return expressions differ in type", methodDeclaration);
			} else {
				Struct returnType = this.returnTypes.isEmpty() ? Tab.noType : this.returnTypes.get(0);
				if (this.methodType != returnType) {
					reportError("Method and return expression(s) differ in type", methodDeclaration);
				}
			}
			Tab.closeScope();
		}
		this.methodType = null;
		this.method = null;
		this.methodLevel = 0;
		this.returnTypes = null;
	}

	private List<Struct> returnTypes = null;

	public void visit(TypeReturnStatement typeReturnStatement) {
		if (this.method == Tab.noObj) {
			reportError("Return statement is not in global function declaration", typeReturnStatement);
		} else if (this.classMethod == Tab.noObj) {
			reportError("Return statement is not in class method", typeReturnStatement);
		} else {
			Struct returnExpressionType = typeReturnStatement.getExpression().struct;
			if (returnExpressionType == Tab.noType)
				return;
			this.returnTypes.add(returnExpressionType);
		}
	}

	public void visit(VoidReturnStatement voidReturnStatement) {
		if (this.method == Tab.noObj) {
			reportError("Return statement is not in global function declaration", voidReturnStatement);
		} else if (this.classMethod == Tab.noObj) {
			reportError("Return statement is not in class method", voidReturnStatement);
		} else {
			this.returnTypes.add(Tab.noType);
		}
	}

	private boolean hasSameReturnTypes() {
		for (int index = 0; index < this.returnTypes.size() - 1; ++index) {
			if (this.returnTypes.get(index) != this.returnTypes.get(index + 1)) {
				return false;
			}
		}
		return true;
	}

	public void visit(ArrayFormalParameter arrayFormalParameter) {
		if (this.method == Tab.noObj || this.interfaceMethod == Tab.noObj || this.classMethod == Tab.noObj)
			return;
		Obj parameter = Tab.currentScope().findSymbol(arrayFormalParameter.getName());
		if (parameter != null && parameter != Tab.noObj) {
			reportError("Symbol \"" + arrayFormalParameter.getName() + "\" is already defined", arrayFormalParameter);
		} else {
			Struct parameterType = arrayFormalParameter.getType().struct;
			if (parameterType != Tab.noType) {
				// Correct array formal parameter
				parameter = Tab.insert(Obj.Var, arrayFormalParameter.getName(), this.getArrayType(parameterType));
				if (this.method != null && this.method != Tab.noObj) {
					parameter.setFpPos(this.methodLevel++);
				} else if (this.interfaceMethod != null && this.interfaceMethod != Tab.noObj) {
					parameter.setFpPos(this.interfaceMethodLevel++);
				} else if (this.classMethod != null && this.classMethod != Tab.noObj) {
					parameter.setFpPos(this.classMethodLevel++);
				}
			}
		}
	}

	public void visit(FormalParameter formalParameter) {
		if (this.method == Tab.noObj || this.interfaceMethod == Tab.noObj || this.classMethod == Tab.noObj)
			return;
		Obj parameter = Tab.currentScope().findSymbol(formalParameter.getName());
		if (parameter != null && parameter != Tab.noObj) {
			reportError("Symbol \"" + formalParameter.getName() + "\" is already defined", formalParameter);
		} else {
			Struct parameterType = formalParameter.getType().struct;
			if (parameterType != Tab.noType) {
				// Correct formal parameter
				parameter = Tab.insert(Obj.Var, formalParameter.getName(), parameterType);
				if (this.method != null && this.method != Tab.noObj) {
					parameter.setFpPos(this.methodLevel++);
				} else if (this.interfaceMethod != null && this.interfaceMethod != Tab.noObj) {
					parameter.setFpPos(this.interfaceMethodLevel++);
				} else if (this.classMethod != null && this.classMethod != Tab.noObj) {
					parameter.setFpPos(this.classMethodLevel++);
				}
			}
		}
	}

	public void visit(LastArrayFormalParameter lastArrayFormalParameter) {
		if (this.method == Tab.noObj || this.interfaceMethod == Tab.noObj || this.classMethod == Tab.noObj)
			return;
		Obj parameter = Tab.currentScope().findSymbol(lastArrayFormalParameter.getName());
		if (parameter != null && parameter != Tab.noObj) {
			reportError("Symbol \"" + lastArrayFormalParameter.getName() + "\" is already defined",
					lastArrayFormalParameter);
		} else {
			Struct parameterType = lastArrayFormalParameter.getType().struct;
			if (parameterType != Tab.noType) {
				// Correct array formal parameter
				parameter = Tab.insert(Obj.Var, lastArrayFormalParameter.getName(), this.getArrayType(parameterType));
				if (this.method != null && this.method != Tab.noObj) {
					parameter.setFpPos(this.methodLevel++);
				} else if (this.interfaceMethod != null && this.interfaceMethod != Tab.noObj) {
					parameter.setFpPos(this.interfaceMethodLevel++);
				} else if (this.classMethod != null && this.classMethod != Tab.noObj) {
					parameter.setFpPos(this.classMethodLevel++);
				}
			}
		}
	}

	public void visit(LastSingleFormalParameter lastSingleFormalParameter) {
		if (this.method == Tab.noObj || this.interfaceMethod == Tab.noObj || this.classMethod == Tab.noObj)
			return;
		Obj parameter = Tab.currentScope().findSymbol(lastSingleFormalParameter.getName());
		if (parameter != null && parameter != Tab.noObj) {
			reportError("Symbol \"" + lastSingleFormalParameter.getName() + "\" is already defined",
					lastSingleFormalParameter);
		} else {
			Struct parameterType = lastSingleFormalParameter.getType().struct;
			if (parameterType != Tab.noType) {
				// Correct formal parameter
				parameter = Tab.insert(Obj.Var, lastSingleFormalParameter.getName(), parameterType);
				if (this.method != null && this.method != Tab.noObj) {
					parameter.setFpPos(this.methodLevel++);
				} else if (this.interfaceMethod != null && this.interfaceMethod != Tab.noObj) {
					parameter.setFpPos(this.interfaceMethodLevel++);
				} else if (this.classMethod != null && this.classMethod != Tab.noObj) {
					parameter.setFpPos(this.classMethodLevel++);
				}
			}
		}
	}

	private Stack<Obj> functions = new Stack<Obj>();

	private Stack<Integer> argumentsCounts = new Stack<Integer>();

	private boolean checkParametersCount(Obj function, int argumentsCount) {
		int parametersCount = function.getLevel();
		Collection<Obj> parameters = function.getLocalSymbols();
		for (Obj parameter : parameters) {
			if (parameter.getName().equals("this")) {
				--parametersCount;
				break;
			}
		}
		return parametersCount == argumentsCount;
	}

	public void visit(ActualParameters actualParameters) {
		if (this.functions.peek() != Tab.noObj) {
			// Correct function designator
			Obj function = this.functions.peek();
			int argumentsCount = this.argumentsCounts.peek();
			if (!checkParametersCount(function, argumentsCount)) {
				reportError("Arguments count and parameters count of function \"" + function.getName() + "\" differ",
						actualParameters);
			}
		}
		this.functions.pop();
		this.argumentsCounts.pop();
	}

	public void visit(NoActualParameters noActualParameters) {
		if (this.functions.peek() != Tab.noObj) {
			// Correct function designator
			Obj function = this.functions.peek();
			int argumentsCount = this.argumentsCounts.peek();
			if (!checkParametersCount(function, argumentsCount)) {
				reportError("Arguments count and parameters count of function \"" + function.getName() + "\" differ",
						noActualParameters.getParent());
			}
		}
		this.functions.pop();
		this.argumentsCounts.pop();
	}

	private static boolean assignableTo(Struct sourceType, Struct destinationType) {
		if (sourceType.assignableTo(destinationType)) {
			return true;
		}
		if (sourceType.getKind() == Struct.Class) {
			if (destinationType.getKind() == Struct.Class) {
				// Class-Class assignable
				Struct baseClass = destinationType;
				Struct derivedClass = sourceType;
				for (; derivedClass != Tab.noType; derivedClass = derivedClass.getElemType()) {
					if (derivedClass == baseClass) {
						return true;
					}
				}
				return false;
			}
			if (destinationType.getKind() == Struct.Interface) {
				// Class-Interface assignable
				Struct baseInterface = destinationType;
				Struct derivedClass = sourceType;
				for (; derivedClass != Tab.noType; derivedClass = derivedClass.getElemType()) {
					Collection<Struct> interfaces = derivedClass.getImplementedInterfaces();
					for (Struct implementedInterface : interfaces) {
						if (implementedInterface == baseInterface) {
							return true;
						}
					}
				}
				return false;
			}
		}
		if (sourceType.getKind() == Struct.Enum && destinationType.getKind() == Struct.Int) {
			// Enum-Int assignable (implicitly)
			return true;
		}
		return false;
	}

	private boolean checkParameterType(Obj function, int argumentsCount, Struct argumentType) {
		Collection<Obj> parameters = function.getLocalSymbols();
		for (Obj parameter : parameters) {
			if (parameter.getName().equals("this"))
				++argumentsCount;
			if (parameter.getFpPos() == argumentsCount) {
				Struct parameterType = parameter.getType();
				return MJSemanticAnalyzer.assignableTo(argumentType, parameterType);
			}
		}
		return false;
	}

	public void visit(ActualParameterExpressions actualParameterExpressions) {
		if (this.functions.peek() == Tab.noObj) {
			// Incorrect function designator
			return;
		}
		Obj function = this.functions.peek();
		int argumentsCount = this.argumentsCounts.peek();
		Struct argumentExpressionType = actualParameterExpressions.getExpression().struct;
		if (!checkParameterType(function, argumentsCount, argumentExpressionType)) {
			reportError("Argument #" + (argumentsCount + 1) + " is not assignable to its parameter of function \""
					+ function.getName() + "\"", actualParameterExpressions);
		}
		this.argumentsCounts.push(this.argumentsCounts.pop() + 1);
	}

	public void visit(FirstActualParameterExpression firstActualParameterExpression) {
		if (this.functions.peek() == Tab.noObj) {
			// Incorrect function designator
			return;
		}
		Obj function = this.functions.peek();
		int argumentsCount = this.argumentsCounts.peek();
		Struct argumentExpressionType = firstActualParameterExpression.getExpression().struct;
		if (!checkParameterType(function, argumentsCount, argumentExpressionType)) {
			reportError("Argument #" + (argumentsCount + 1) + " is not assignable to its parameter of function \""
					+ function.getName() + "\"", firstActualParameterExpression);
		}
		this.argumentsCounts.push(this.argumentsCounts.pop() + 1);
	}

	public void visit(AssignDesignatorStatement assignDesignatorStatement) {
		Obj designator = assignDesignatorStatement.getDesignator().obj;
		Struct expressionType = assignDesignatorStatement.getExpression().struct;
		if (designator == Tab.noObj || expressionType == Tab.noType)
			return;
		if (designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem && designator.getKind() != Obj.Fld) {
			reportError(
					"Designator \"" + designator.getName() + "\" is not a variable, an array element nor a class field",
					assignDesignatorStatement);
		} else if (!MJSemanticAnalyzer.assignableTo(expressionType, designator.getType())) {
			if (designator.getKind() == Obj.Elem) {
				reportError("Expression is not assignable to an element of an array \"" + designator.getName() + "\"",
						assignDesignatorStatement);
			} else {
				reportError("Expression is not assignable to variable/field \"" + designator.getName() + "\"",
						assignDesignatorStatement);
			}
		}
	}

	public void visit(LastForAssignDesignatorStatement lastForAssignDesignatorStatement) {
		Obj designator = lastForAssignDesignatorStatement.getDesignator().obj;
		Struct expressionType = lastForAssignDesignatorStatement.getExpression().struct;
		if (designator == Tab.noObj || expressionType == Tab.noType)
			return;
		if (designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem && designator.getKind() != Obj.Fld) {
			reportError(
					"Designator \"" + designator.getName() + "\" is not a variable, an array element nor a class field",
					lastForAssignDesignatorStatement);
		} else if (!MJSemanticAnalyzer.assignableTo(expressionType, designator.getType())) {
			if (designator.getKind() == Obj.Elem) {
				reportError("Expression is not assignable to an element of an array \"" + designator.getName() + "\"",
						lastForAssignDesignatorStatement);
			} else {
				reportError("Expression is not assignable to variable/field \"" + designator.getName() + "\"",
						lastForAssignDesignatorStatement);
			}
		}
	}

	public void visit(FunctionDesignatorStatement functionDesignatorStatement) {
		Obj designator = functionDesignatorStatement.getDesignator().obj;
		if (designator == Tab.noObj) {
			return;
		}
		if (designator.getKind() != Obj.Meth) {
			reportError("Designator \"" + designator.getName() + "\" is not a class method/global function",
					functionDesignatorStatement);
		}
	}

	public void visit(LastForFunctionDesignatorStatement lastForFunctionDesignatorStatement) {
		Obj designator = lastForFunctionDesignatorStatement.getDesignator().obj;
		if (designator == Tab.noObj) {
			return;
		}
		if (designator.getKind() != Obj.Meth) {
			reportError("Designator \"" + designator.getName() + "\" is not an class method/global function",
					lastForFunctionDesignatorStatement);
		}
	}

	public void visit(IncDesignatorStatement incDesignatorStatement) {
		Obj designator = incDesignatorStatement.getDesignator().obj;
		if (designator == Tab.noObj)
			return;
		if (designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem && designator.getKind() != Obj.Fld) {
			reportError(
					"Designator \"" + designator.getName() + "\" is not a variable, an array element nor a class field",
					incDesignatorStatement);
		} else if (designator.getType() != Tab.intType) {
			reportError("Designator \"" + designator.getName() + "\"  is not an integer", incDesignatorStatement);
		}
	}

	public void visit(LastForIncDesignatorStatement lastForIncDesignatorStatement) {
		Obj designator = lastForIncDesignatorStatement.getDesignator().obj;
		if (designator == Tab.noObj)
			return;
		if (designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem && designator.getKind() != Obj.Fld) {
			reportError(
					"Designator \"" + designator.getName() + "\" is not a variable, an array element nor a class field",
					lastForIncDesignatorStatement);
		} else if (designator.getType() != Tab.intType) {
			reportError("Designator \"" + designator.getName() + "\" is not an integer", lastForIncDesignatorStatement);
		}
	}

	public void visit(DecDesignatorStatement decDesignatorStatement) {
		Obj designator = decDesignatorStatement.getDesignator().obj;
		if (designator == Tab.noObj)
			return;
		if (designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem && designator.getKind() != Obj.Fld) {
			reportError(
					"Designator \"" + designator.getName() + "\" is not a variable, an array element nor a class field",
					decDesignatorStatement);
		} else if (designator.getType() != Tab.intType) {
			reportError("Designator \"" + designator.getName() + "\" is not an integer", decDesignatorStatement);
		}
	}

	public void visit(LastForDecDesignatorStatement lastForDecDesignatorStatement) {
		Obj designator = lastForDecDesignatorStatement.getDesignator().obj;
		if (designator == Tab.noObj)
			return;
		if (designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem && designator.getKind() != Obj.Fld) {
			reportError(
					"Designator \"" + designator.getName() + "\" is not a variable, an array element nor a class field",
					lastForDecDesignatorStatement);
		} else if (designator.getType() != Tab.intType) {
			reportError("Designator \"" + designator.getName() + "\" is not an integer", lastForDecDesignatorStatement);
		}
	}

	public void visit(ReadStatement readStatement) {
		Obj designator = readStatement.getDesignator().obj;
		if (designator == Tab.noObj)
			return;
		if (designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem && designator.getKind() != Obj.Fld) {
			reportError(
					"Designator \"" + designator.getName() + "\" is not a variable, an array element nor a class field",
					readStatement);
		} else if (designator.getType() != Tab.intType && designator.getType() != Tab.charType
				&& designator.getType() != MJSemanticAnalyzer.boolType) {
			reportError("Designator \"" + designator.getName() + "\" is not an integer, a char nor a bool",
					readStatement);
		}
	}

	public void visit(SimplePrintStatement simplePrintStatement) {
		Struct expressionType = simplePrintStatement.getExpression().struct;
		if (expressionType == Tab.noType)
			return;
		if (expressionType != Tab.intType && expressionType != Tab.charType
				&& expressionType != MJSemanticAnalyzer.boolType && expressionType.getKind() != Struct.Enum) {
			reportError("Print expression is not an integer, a char nor a bool", simplePrintStatement);
		}
	}

	public void visit(NumConstPrintStatement numConstPrintStatement) {
		Struct expressionType = numConstPrintStatement.getExpression().struct;
		if (expressionType == Tab.noType)
			return;
		if (expressionType != Tab.intType && expressionType != Tab.charType
				&& expressionType != MJSemanticAnalyzer.boolType && expressionType.getKind() != Struct.Enum) {
			reportError("Print expression is not an integer, a char nor a bool", numConstPrintStatement);
		}
	}

	public void visit(ConditionTerms conditionTerms) {
		Struct conditionType = conditionTerms.getCondition().struct;
		Struct conditionTermType = conditionTerms.getCondTerm().struct;
		if (conditionType != MJSemanticAnalyzer.boolType || conditionTermType != MJSemanticAnalyzer.boolType) {
			conditionTerms.struct = Tab.noType;
		} else {
			conditionTerms.struct = MJSemanticAnalyzer.boolType;
		}
	}

	public void visit(FirstConditionTerm firstConditionTerm) {
		Struct conditionTermType = firstConditionTerm.getCondTerm().struct;
		if (conditionTermType != MJSemanticAnalyzer.boolType) {
			firstConditionTerm.struct = Tab.noType;
		} else {
			firstConditionTerm.struct = MJSemanticAnalyzer.boolType;
		}
	}

	public void visit(ConditionFacts conditionFacts) {
		Struct conditionTermType = conditionFacts.getCondTerm().struct;
		Struct conditionFactType = conditionFacts.getCondFact().struct;
		if (conditionTermType != MJSemanticAnalyzer.boolType || conditionFactType != MJSemanticAnalyzer.boolType) {
			conditionFacts.struct = Tab.noType;
		} else {
			conditionFacts.struct = MJSemanticAnalyzer.boolType;
		}
	}

	public void visit(FirstConditionFact firstConditionFact) {
		Struct conditionFactType = firstConditionFact.getCondFact().struct;
		if (conditionFactType != MJSemanticAnalyzer.boolType) {
			firstConditionFact.struct = Tab.noType;
		} else {
			firstConditionFact.struct = MJSemanticAnalyzer.boolType;
		}
	}

	public void visit(ConditionExpressions conditionExpressions) {
		Struct leftExpressionType = conditionExpressions.getExpression().struct;
		Struct rightExpressionType = conditionExpressions.getExpression1().struct;
		if (leftExpressionType == Tab.noType || rightExpressionType == Tab.noType) {
			conditionExpressions.struct = Tab.noType;
		} else if (!leftExpressionType.compatibleWith(rightExpressionType)) {
			reportError("Condition expression types are not compatible", conditionExpressions);
			conditionExpressions.struct = Tab.noType;
		} else {
			RelOp operator = conditionExpressions.getRelOp();
			if (leftExpressionType.getKind() == Struct.Class || rightExpressionType.getKind() == Struct.Class) {
				if (!(operator instanceof Equal) && !(operator instanceof NotEqual)) {
					reportError("Class variables are only compatible with == and !=", conditionExpressions);
					conditionExpressions.struct = Tab.noType;
					return;
				}
			}
			if (leftExpressionType.getKind() == Struct.Array || rightExpressionType.getKind() == Struct.Array) {
				if (!(operator instanceof Equal) && !(operator instanceof NotEqual)) {
					reportError("Array variables are only compatible with == and !=", conditionExpressions);
					conditionExpressions.struct = Tab.noType;
					return;
				}
			}
			conditionExpressions.struct = MJSemanticAnalyzer.boolType;
		}
	}

	public void visit(SimpleConditionExpression simpleConditionExpression) {
		Struct expressionType = simpleConditionExpression.getExpression().struct;
		if (expressionType == Tab.noType) {
			simpleConditionExpression.struct = Tab.noType;
		} else if (expressionType != MJSemanticAnalyzer.boolType) {
			reportError("Condition expression type is not boolean", simpleConditionExpression);
			simpleConditionExpression.struct = Tab.noType;
		} else {
			simpleConditionExpression.struct = MJSemanticAnalyzer.boolType;
		}
	}

	public void visit(IfCondition ifCondition) {
		Struct conditionType = ifCondition.getCondition().struct;
		if (conditionType != MJSemanticAnalyzer.boolType) {
			reportError("If condition type is not boolean", ifCondition);
		}
	}

	private int loopCounter = 0;

	public void visit(ForCondition forCondition) {
		Struct conditionType = forCondition.getCondition().struct;
		if (conditionType != MJSemanticAnalyzer.boolType) {
			reportError("For condition type is not boolean", forCondition);
		}
	}

	public void visit(ForDeclarationEnd forDeclarationEnd) {
		this.loopCounter++;
	}

	public void visit(BreakStatement breakStatement) {
		if (loopCounter == 0) {
			reportError("Break statement is not in a for loop", breakStatement.getParent());
		}
	}

	public void visit(ContinueStatement continueStatement) {
		if (loopCounter == 0) {
			reportError("Continue statement is not in a for loop", continueStatement.getParent());
		}
	}

	public void visit(ForStatement forStatement) {
		this.loopCounter--;
	}

	public void visit(Terms terms) {
		Struct expressionType = terms.getExpression().struct;
		Struct termType = terms.getTerm().struct;
		if ((expressionType != Tab.intType && expressionType.getKind() != Struct.Enum)
				|| (termType != Tab.intType && termType.getKind() != Struct.Enum)) {
			reportError("Expression/Term type is(are) not integer nor enumeration", terms);
			terms.struct = Tab.noType;
		} else {
			// Correct expression
			terms.struct = Tab.intType;
		}
	}

	public void visit(NegFirstTerm negFirstTerm) {
		Struct termType = negFirstTerm.getTerm().struct;
		if (termType != Tab.intType && termType.getKind() != Struct.Enum) {
			reportError("Term type is not integer nor enumeration", negFirstTerm);
			negFirstTerm.struct = Tab.noType;
		} else {
			// Correct negative expression
			negFirstTerm.struct = Tab.intType;
		}
	}

	public void visit(FirstTerm firstTerm) {
		firstTerm.struct = firstTerm.getTerm().struct;
	}

	public void visit(Factors factors) {
		Struct termType = factors.getTerm().struct;
		Struct factorType = factors.getFactor().struct;
		if ((termType != Tab.intType && termType.getKind() != Struct.Enum)
				|| (factorType != Tab.intType && factorType.getKind() != Struct.Enum)) {
			reportError("Term/Factor type is(are) not integer nor enumeration", factors);
			factors.struct = Tab.noType;
		} else {
			// Correct term
			factors.struct = Tab.intType;
		}
	}

	public void visit(FirstFactor firstFactor) {
		firstFactor.struct = firstFactor.getFactor().struct;
	}

	public void visit(FunctionFactor functionFactor) {
		Obj functionDesignator = functionFactor.getDesignator().obj;
		if (functionDesignator == Tab.noObj) {
			return;
		}
		if (functionDesignator.getKind() != Obj.Meth) {
			reportError("Designator \"" + functionDesignator.getName() + "\" is not a class method/global function",
					functionFactor);
			functionFactor.struct = Tab.noType;
		} else {
			// Correct function factor
			functionFactor.struct = functionDesignator.getType();
		}
	}

	public void visit(FieldDesignator fieldDesignator) {
		Obj designator = fieldDesignator.getDesignator().obj;
		String fieldName = fieldDesignator.getField();
		if (designator == Tab.noObj) {
			fieldDesignator.obj = Tab.noObj;
		} else if (designator.getType().getKind() == Struct.Class) {
			Collection<Obj> classMembers = designator.getType().getMembers();
			Obj field = null;
			for (Obj member : classMembers) {
				if (member.getName().equals(fieldName)) {
					field = member;
					break;
				}
			}
			if (field == null) {
				reportError("Class " + designator.getName() + " does not have a \"" + fieldName + "\" field/method",
						fieldDesignator);
				fieldDesignator.obj = Tab.noObj;
			} else {
				if (this.method != null && this.method != Tab.noObj) {
					if (field.getFpPos() < MJSemanticAnalyzer.Modifer.PUBLIC.ordinal()) {
						reportError("Non-public field/method \"" + fieldName + "\" is not accessible", fieldDesignator);
						fieldDesignator.obj = Tab.noObj;
					} else {
						reportInfo("Field/method \"" + fieldName + "\" of class designator \"" + designator.getName()
								+ "\" found", fieldDesignator);
						fieldDesignator.obj = field;
					}
				} else if (this.classMethod != null && this.classMethod != Tab.noObj) {
					if (designator.getType() == this.classObj.getType()) {
						// Private fields/methods are accessible within its class definition
						if (field.getFpPos() < MJSemanticAnalyzer.Modifer.PRIVATE.ordinal()) {
							reportError("Private base class field/method \"" + fieldName + "\" is not accessible",
									fieldDesignator);
							fieldDesignator.obj = Tab.noObj;
						} else {
							reportInfo("Field/method \"" + fieldName + "\" of class designator \""
									+ designator.getName() + "\" found", fieldDesignator);
							fieldDesignator.obj = field;
						}
					} else {
						if (field.getFpPos() < MJSemanticAnalyzer.Modifer.PUBLIC.ordinal()) {
							reportError("Non-public field/method \"" + designator.getName() + "\" is not accessible",
									fieldDesignator);
							fieldDesignator.obj = Tab.noObj;
						} else {
							reportInfo("Field/method \"" + fieldName + "\" of class designator \""
									+ designator.getName() + "\" found", fieldDesignator);
							fieldDesignator.obj = field;
						}
					}
				}
			}
		} else if (designator.getType().getKind() == Struct.Interface) {
			Collection<Obj> interfaceMembers = designator.getType().getMembers();
			Obj field = null;
			for (Obj member : interfaceMembers) {
				if (member.getName().equals(fieldName)) {
					field = member;
					break;
				}
			}
			if (field == null) {
				reportError("Interface " + designator.getName() + " does not have a \"" + fieldName + "\" method",
						fieldDesignator);
				fieldDesignator.obj = Tab.noObj;
			} else {
				reportInfo(
						"Method \"" + fieldName + "\" of interface designator \"" + designator.getName() + "\" found",
						fieldDesignator);
				fieldDesignator.obj = field;
			}
		} else if (designator.getType().getKind() == Struct.Enum) {
			Collection<Obj> enumerationConstants = designator.getType().getMembers();
			Obj field = null;
			for (Obj constant : enumerationConstants) {
				if (constant.getName().equals(fieldName)) {
					field = constant;
					break;
				}
			}
			if (field == null) {
				reportError("Enumeration " + designator.getName() + " does not have a \"" + fieldName + "\" constant",
						fieldDesignator);
				fieldDesignator.obj = Tab.noObj;
			} else {
				reportInfo("Constant \"" + fieldName + "\" of enumeration designator \"" + designator.getName()
						+ "\" found", fieldDesignator);
				fieldDesignator.obj = field;
			}
		} else {
			reportError("Designator \"" + designator.getName() + "\" is not a class, an interface nor an enum",
					fieldDesignator);
			fieldDesignator.obj = Tab.noObj;
		}

		SyntaxNode parent = fieldDesignator.getParent();
		if (parent instanceof FunctionDesignatorStatement || parent instanceof LastForFunctionDesignatorStatement
				|| parent instanceof FunctionFactor) {
			if (fieldDesignator.obj.getKind() == Obj.Meth) {
				// Correct function call
				this.functions.push(fieldDesignator.obj);
				this.argumentsCounts.push(0);
			} else {
				// Incorrect function call
				this.functions.push(Tab.noObj);
				this.argumentsCounts.push(0);
			}
		}
	}

	public void visit(ArrayDesignator arrayDesignator) {
		Obj designator = arrayDesignator.getDesignator().obj;
		Struct expressionType = arrayDesignator.getExpression().struct;
		if (designator.getType() == Tab.noType || expressionType == Tab.noType) {
			arrayDesignator.obj = Tab.noObj;
		} else if (designator.getType().getKind() != Struct.Array) {
			reportError("Designator \"" + designator.getName() + "\" is not an array", arrayDesignator);
			arrayDesignator.obj = Tab.noObj;
		} else if (expressionType != Tab.intType && expressionType.getKind() != Struct.Enum) {
			reportError("Expression type is not integer", arrayDesignator);
			arrayDesignator.obj = Tab.noObj;
		} else {
			if (this.method != null && this.method != Tab.noObj) {
				reportInfo("Element of an array \"" + designator.getName() + "\" found", arrayDesignator);
				arrayDesignator.obj = new Obj(Obj.Elem, designator.getName(), designator.getType().getElemType());
			} else if (this.classMethod != null && this.classMethod != Tab.noObj) {
				if (designator.getKind() == Obj.Fld) {
					if (designator.getFpPos() < MJSemanticAnalyzer.Modifer.PRIVATE.ordinal()) {
						reportError("Private base class array field \"" + designator.getName() + "\" is not accessible",
								arrayDesignator);
						arrayDesignator.obj = Tab.noObj;
					} else {
						reportInfo("Element of an array \"" + designator.getName() + "\" found", arrayDesignator);
						arrayDesignator.obj = new Obj(Obj.Elem, designator.getName(),
								designator.getType().getElemType());
					}
				} else {
					reportInfo("Element of an array \"" + designator.getName() + "\" found", arrayDesignator);
					arrayDesignator.obj = new Obj(Obj.Elem, designator.getName(), designator.getType().getElemType());
				}
			}
		}
	}

	public void visit(SimpleDesignator simpleDesignator) {
		Obj variable = Tab.find(simpleDesignator.getName());
		if (variable == Tab.noObj) {
			reportError("Symbol \"" + simpleDesignator.getName() + "\" is not defined", simpleDesignator);
			simpleDesignator.obj = Tab.noObj;
		} else {
			if (this.method != null && this.method != Tab.noObj) {
				reportInfo("Symbol \"" + simpleDesignator.getName() + "\" found", simpleDesignator);
				simpleDesignator.obj = variable;
			} else if (this.classMethod != null && this.classMethod != Tab.noObj) {
				if (variable.getKind() == Obj.Fld || variable.getKind() == Obj.Meth) {
					if (variable.getFpPos() < MJSemanticAnalyzer.Modifer.PRIVATE.ordinal()) {
						reportError("Private base class field/method \"" + simpleDesignator.getName()
								+ "\" is not accessible", simpleDesignator);
						simpleDesignator.obj = Tab.noObj;
					} else {
						reportInfo("Symbol \"" + simpleDesignator.getName() + "\" found", simpleDesignator);
						simpleDesignator.obj = variable;
					}
				} else {
					reportInfo("Symbol \"" + simpleDesignator.getName() + "\" found", simpleDesignator);
					simpleDesignator.obj = variable;
				}
			}
		}

		SyntaxNode parent = simpleDesignator.getParent();
		if (parent instanceof FunctionDesignatorStatement || parent instanceof LastForFunctionDesignatorStatement
				|| parent instanceof FunctionFactor) {
			if (variable.getKind() == Obj.Meth) {
				// Correct function call
				this.functions.push(variable);
				this.argumentsCounts.push(0);
			} else {
				// Incorrect function call
				this.functions.push(Tab.noObj);
				this.argumentsCounts.push(0);
			}
		}
	}

	public void visit(DesignatorFactor designatorFactor) {
		designatorFactor.struct = designatorFactor.getDesignator().obj.getType();
	}

	public void visit(NumFactor numFactor) {
		numFactor.struct = Tab.intType;
	}

	public void visit(CharFactor charFactor) {
		charFactor.struct = Tab.charType;
	}

	public void visit(BoolFactor boolFactor) {
		boolFactor.struct = MJSemanticAnalyzer.boolType;
	}

	public void visit(NewArrayFactor newArrayFactor) {
		Struct arrayElementType = newArrayFactor.getType().struct;
		Struct expressionType = newArrayFactor.getExpression().struct;
		if (arrayElementType == Tab.noType) {
			newArrayFactor.struct = Tab.noType;
		} else if (expressionType != Tab.intType && expressionType.getKind() != Struct.Enum) {
			reportError("Expression type is not integer", newArrayFactor);
			newArrayFactor.struct = Tab.noType;
		} else {
			// Correct new array factor
			newArrayFactor.struct = this.getArrayType(arrayElementType);
		}
	}

	private boolean checkNumFactor(Expression expression) {
		if (!(expression instanceof FirstTerm)) {
			return false;
		} else {
			Term term = ((FirstTerm) expression).getTerm();
			if (!(term instanceof FirstFactor)) {
				return false;
			} else {
				Factor factor = ((FirstFactor) term).getFactor();
				if (!(factor instanceof NumFactor)) {
					return false;
				}
				return true;
			}
		}
	}

	private boolean checkInitializationListLength(Expression expression) {
		if (!checkNumFactor(expression))
			return false;
		if (this.initializationExpressionTypes == null)
			return false;
		int arrayLength = ((NumFactor) ((FirstFactor) ((FirstTerm) expression).getTerm()).getFactor()).getValue();
		int initializationListLength = this.initializationExpressionTypes.size();
		return arrayLength == initializationListLength;
	}

	public void visit(NewInitializedArrayFactor newInitializedArrayFactor) {
		Struct arrayElementType = newInitializedArrayFactor.getType().struct;
		Expression expression = newInitializedArrayFactor.getExpression();
		Struct initializationListType = getInitializationListType();
		if (arrayElementType == Tab.noType) {
			newInitializedArrayFactor.struct = Tab.noType;
		} else if (initializationListType == Tab.noType) {
			reportError("Initialization list element types differ", newInitializedArrayFactor);
			newInitializedArrayFactor.struct = Tab.noType;
		} else if (expression.struct != Tab.intType) {
			reportError("Array length expression type is not integer", newInitializedArrayFactor);
			newInitializedArrayFactor.struct = Tab.noType;
		} else if (!checkNumFactor(expression)) {
			reportError("Array length expression is not an integer constant", newInitializedArrayFactor);
			newInitializedArrayFactor.struct = Tab.noType;
		} else if (!checkInitializationListLength(expression)) {
			reportError("Array length and initialization list length differ", newInitializedArrayFactor);
			newInitializedArrayFactor.struct = Tab.noType;
		} else if (arrayElementType != initializationListType) {
			reportError("Array elemement type and initialization list type differ", newInitializedArrayFactor);
			newInitializedArrayFactor.struct = Tab.noType;
		} else {
			// Correct new initialized array factor
			newInitializedArrayFactor.struct = this.getArrayType(arrayElementType);
		}
		this.initializationExpressionTypes = null;
	}

	private List<Struct> initializationExpressionTypes = new ArrayList<Struct>();

	private Struct getInitializationListType() {
		if (this.initializationExpressionTypes == null)
			return Tab.noType;
		for (int index = 0; index < this.initializationExpressionTypes.size() - 1; ++index) {
			Struct initializationExpressionType1 = this.initializationExpressionTypes.get(index);
			if (initializationExpressionType1.getKind() == Struct.Enum) {
				initializationExpressionType1 = Tab.intType;
			}
			Struct initializationExpressionType2 = this.initializationExpressionTypes.get(index + 1);
			if (initializationExpressionType2.getKind() == Struct.Enum) {
				initializationExpressionType2 = Tab.intType;
			}
			if (initializationExpressionType1 != initializationExpressionType2) {
				return Tab.noType;
			}
		}
		return this.initializationExpressionTypes.get(0);
	}

	public void visit(InitializationExpressions initializationExpressions) {
		if (this.initializationExpressionTypes == null)
			return;
		Struct initializationExpressionType = initializationExpressions.getExpression().struct;
		if (initializationExpressionType == Tab.noType)
			return;
		this.initializationExpressionTypes.add(initializationExpressionType);
	}

	public void visit(FirstInitializationExpression firstInitializationExpression) {
		this.initializationExpressionTypes = new ArrayList<Struct>();
		Struct initializationExpressionType = firstInitializationExpression.getExpression().struct;
		if (initializationExpressionType == Tab.noType)
			return;
		this.initializationExpressionTypes.add(initializationExpressionType);
	}

	public void visit(NewFactor newFactor) {
		Struct type = newFactor.getType().struct;
		if (type.getKind() != Struct.Class) {
			reportError("Type is not a class", newFactor);
			newFactor.struct = Tab.noType;
		} else {
			// Correct new factor
			newFactor.struct = type;
		}
	}

	public void visit(ExpressionFactor expressionFactor) {
		expressionFactor.struct = expressionFactor.getExpression().struct;
	}
}