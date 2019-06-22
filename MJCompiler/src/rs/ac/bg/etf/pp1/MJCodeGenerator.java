package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.mj.runtime.*;

public class MJCodeGenerator extends VisitorAdaptor {
	private int mainPc;

	private int dataSize;

	public int getMainPc() {
		return this.mainPc;
	}

	public int getDataSize() {
		return this.dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	private boolean isInClassMethod = false;

	public void visit(ClassMethodName classMethodName) {
		classMethodName.obj.setAdr(Code.pc);
		this.isInClassMethod = true;

		Code.put(Code.enter);
		Code.put(classMethodName.obj.getLevel());
		Code.put(classMethodName.obj.getLocalSymbols().size());
	}

	public void visit(ClassMethodDeclaration classMethodDeclaration) {
		Code.put(Code.exit);
		Code.put(Code.return_);
		this.isInClassMethod = false;
	}

	private List<Obj> classObjs = new ArrayList<Obj>();

	private void loadVirtualMethodTable(Obj classObj) {
		classObj.setAdr(this.dataSize);
		Collection<Obj> members = classObj.getType().getMembers();
		for (Obj memeber : members) {
			if (memeber.getKind() == Obj.Meth) {
				for (int position = 0; position < memeber.getName().length(); ++position) {
					Code.loadConst(memeber.getName().charAt(position));
					Code.put(Code.putstatic);
					Code.put2(this.dataSize++);
				}
				Code.loadConst(-1);
				Code.put(Code.putstatic);
				Code.put2(this.dataSize++);

				Code.loadConst(memeber.getAdr());
				Code.put(Code.putstatic);
				Code.put2(this.dataSize++);
			}
		}
		Code.loadConst(-2);
		Code.put(Code.putstatic);
		Code.put2(this.dataSize++);
	}

	private boolean checkMethodDeclarations(Obj method1, Obj method2) {
		int method1Modifier = method1.getFpPos();
		int method2dModifier = method2.getFpPos();
		if (method1Modifier < method2dModifier)
			return false;
		if (!method1.getName().equals(method2.getName()))
			return false;
		if (!(method1.getType() == method2.getType()))
			return false;
		if (!(method1.getLevel() == method2.getLevel()))
			return false;
		Collection<Obj> method1Locals = method1.getLocalSymbols();
		int method1ParameterCount = 0;
		for (Obj method1Local : method1Locals) {
			// Check only parameters
			if (method1Local.getName().equals("this"))
				continue;
			if (method1ParameterCount++ < method1.getLevel()) {
				Collection<Obj> method2Locals = method2.getLocalSymbols();
				for (Obj method2Local : method2Locals) {
					if (method2Local.getName().equals("this"))
						continue;
					if (method1Local.getFpPos() == method2Local.getFpPos())
						if (!(method1Local.getType() == method2Local.getType()))
							return false;
						else if (!(method1Local.getName().equals(method2Local.getName())))
							return false;
						else
							break;
				}
			} else
				break;
		}
		return true;
	}

	public void visit(ClassDeclDefinition classDeclDefinition) {
		Obj classObj = ((ClassName) classDeclDefinition.getClssName()).obj;

		this.classObjs.add(classObj);

		Struct baseClass = classObj.getType().getElemType();
		if (baseClass == null)
			return;
		// Patch all method addresses from base class
		Collection<Obj> baseClassMembers = baseClass.getMembers();
		for (Obj baseClassMember : baseClassMembers) {
			if (baseClassMember.getKind() != Obj.Meth)
				continue;
			Collection<Obj> classMembers = classObj.getType().getMembers();
			for (Obj classMember : classMembers) {
				if (classMember.getKind() != Obj.Meth)
					continue;
				if (checkMethodDeclarations(baseClassMember, classMember)) {
					if (classMember.getAdr() == 0)
						classMember.setAdr(baseClassMember.getAdr());
					break;
				}
			}
		}
	}

	public void visit(MethodName methodName) {
		if (methodName.getName().equals("main"))
			this.mainPc = Code.pc;
		methodName.obj.setAdr(Code.pc);

		Code.put(Code.enter);
		Code.put(methodName.obj.getLevel());
		Code.put(methodName.obj.getLocalSymbols().size());
		if (methodName.getName().equals("main")) {
			// Load all virtual method tables to data
			for (Obj classObj : this.classObjs) {
				this.loadVirtualMethodTable(classObj);
			}
		}
	}

	public void visit(MethodDeclaration methodDeclaration) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(TypeReturnStatement typeReturnStatement) {
		if (typeReturnStatement.getParent() instanceof Statements) {
			Statements statements = (Statements) typeReturnStatement.getParent();
			if (statements.getParent() instanceof MethodDeclaration
					|| statements.getParent() instanceof ClassMethodDeclaration) {
				// Return is last statement
				return;
			}
		}
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(VoidReturnStatement voidReturnStatement) {
		if (voidReturnStatement.getParent() instanceof Statements) {
			Statements statements = (Statements) voidReturnStatement.getParent();
			if (statements.getParent() instanceof MethodDeclaration
					|| statements.getParent() instanceof ClassMethodDeclaration) {
				// Return is last statement
				return;
			}
		}
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(AssignDesignatorStatement assignDesignatorStatement) {
		Expression expression = assignDesignatorStatement.getExpression();
		if (expression.struct == Tab.nullType)
			Code.loadConst(0);
		if (expression instanceof FirstTerm) {
			Term term = ((FirstTerm) expression).getTerm();
			if (term instanceof FirstFactor) {
				Factor factor = ((FirstFactor) term).getFactor();
				if (factor instanceof NewInitializedArrayFactor) {
					// Array is already stored before initialization
					return;
				}
			}
		}
		Code.store(assignDesignatorStatement.getDesignator().obj);
	}

	public void visit(LastForAssignDesignatorStatement lastForAssignDesignatorStatement) {
		Expression expression = lastForAssignDesignatorStatement.getExpression();
		if (expression.struct == Tab.nullType)
			Code.loadConst(0);
		if (expression instanceof FirstTerm) {
			Term term = ((FirstTerm) expression).getTerm();
			if (term instanceof FirstFactor) {
				Factor factor = ((FirstFactor) term).getFactor();
				if (factor instanceof NewInitializedArrayFactor) {
					// Array is already stored before initialization
					return;
				}
			}
		}
		Code.store(lastForAssignDesignatorStatement.getDesignator().obj);
	}

	public void visit(FunctionDesignatorStatement functionDesignatorStatement) {
		Obj function = functionDesignatorStatement.getDesignator().obj;
		if (function.getName().equals("chr") || function.getName().equals("ord")) {
			// No need for conversion, char and integer values are the same
		} else if (function.getName().equals("len")) {
			// No need for executing non-void function
		} else {
			if (functionDesignatorStatement.getDesignator() instanceof FieldDesignator) {
				// Virtual method call
				Designator designator = ((FieldDesignator) functionDesignatorStatement.getDesignator()).getDesignator();

				// Get virtual table pointer
				if (designator instanceof ArrayDesignator) {
					((ArrayDesignator) designator).traverseBottomUp(this);
				} else {
					Code.load(designator.obj);
				}
				Code.put(Code.getfield);
				Code.put2(0);

				Code.put(Code.invokevirtual);
				for (int position = 0; position < function.getName().length(); ++position)
					Code.put4((int) function.getName().charAt(position));
				Code.put4(-1);
			} else if (this.isInClassMethod
					&& functionDesignatorStatement.getDesignator() instanceof SimpleDesignator) {
				// Virtual method call
				// Get virtual table pointer
				Code.put(Code.load_n + 0);
				Code.put(Code.getfield);
				Code.put2(0);

				Code.put(Code.invokevirtual);
				for (int position = 0; position < function.getName().length(); ++position)
					Code.put4((int) function.getName().charAt(position));
				Code.put4(-1);

			} else {
				Code.put(Code.call);
				Code.put2(function.getAdr() - Code.pc + 1);
				if (function.getType() != Tab.noType)
					Code.put(Code.pop);
			}
		}
	}

	public void visit(LastForFunctionDesignatorStatement lastForFunctionDesignatorStatement) {
		Obj function = lastForFunctionDesignatorStatement.getDesignator().obj;
		if (function.getName().equals("chr") || function.getName().equals("ord")) {
			// No need for conversion, char and integer values are the same
		} else if (function.getName().equals("len")) {
			// No need for executing non-void function
		} else {
			if (lastForFunctionDesignatorStatement.getDesignator() instanceof FieldDesignator) {
				// Virtual method call
				Designator designator = ((FieldDesignator) lastForFunctionDesignatorStatement.getDesignator())
						.getDesignator();

				// Get virtual table pointer
				if (designator instanceof ArrayDesignator) {
					((ArrayDesignator) designator).traverseBottomUp(this);
				} else {
					Code.load(designator.obj);
				}
				Code.put(Code.getfield);
				Code.put2(0);

				Code.put(Code.invokevirtual);
				for (int position = 0; position < function.getName().length(); ++position)
					Code.put4((int) function.getName().charAt(position));
				Code.put4(-1);
			} else if (this.isInClassMethod
					&& lastForFunctionDesignatorStatement.getDesignator() instanceof SimpleDesignator) {
				// Virtual method call
				// Get virtual table pointer
				Code.put(Code.load_n + 0);
				Code.put(Code.getfield);
				Code.put2(0);

				Code.put(Code.invokevirtual);
				for (int position = 0; position < function.getName().length(); ++position)
					Code.put4((int) function.getName().charAt(position));
				Code.put4(-1);

			} else {
				Code.put(Code.call);
				Code.put2(function.getAdr() - Code.pc + 1);
				if (function.getType() != Tab.noType)
					Code.put(Code.pop);
			}
		}
	}

	public void visit(IncDesignatorStatement incDesignatorStatement) {
		Designator designator = incDesignatorStatement.getDesignator();
		if (designator instanceof ArrayDesignator)
			Code.put(Code.dup2);
		Code.load(designator.obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designator.obj);
	}

	public void visit(LastForIncDesignatorStatement lastForIncDesignatorStatement) {
		Designator designator = lastForIncDesignatorStatement.getDesignator();
		if (designator instanceof ArrayDesignator)
			Code.put(Code.dup2);
		Code.load(designator.obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designator.obj);
	}

	public void visit(DecDesignatorStatement decDesignatorStatement) {
		Designator designator = decDesignatorStatement.getDesignator();
		if (designator instanceof ArrayDesignator)
			Code.put(Code.dup2);
		Code.load(designator.obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designator.obj);
	}

	public void visit(LastForDecDesignatorStatement lastForDecDesignatorStatement) {
		Designator designator = lastForDecDesignatorStatement.getDesignator();
		if (designator instanceof ArrayDesignator)
			Code.put(Code.dup2);
		Code.load(designator.obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designator.obj);
	}

	public void visit(ReadStatement readStatement) {
		if (readStatement.getDesignator().obj.getType() == Tab.charType) {
			Code.put(Code.bread);
		} else {
			// Read an integer or a boolean
			Code.put(Code.read);
		}
		Code.store(readStatement.getDesignator().obj);
	}

	public void visit(SimplePrintStatement simplePrintStatement) {
		Code.loadConst(5);
		if (simplePrintStatement.getExpression().struct == Tab.charType) {
			Code.put(Code.bprint);
		} else {
			// Print an integer or a boolean
			Code.put(Code.print);
		}
	}

	public void visit(NumConstPrintStatement numConstPrintStatement) {
		Code.loadConst(numConstPrintStatement.getValue());
		if (numConstPrintStatement.getExpression().struct == Tab.charType) {
			Code.put(Code.bprint);
		} else {
			// Print an integer or a boolean
			Code.put(Code.print);
		}
	}

	public void visit(ConditionTerms conditionTerms) {
		// Check or condition
		Code.loadConst(1);
		Code.putFalseJump(Code.ne, Code.pc + 11);
		Code.loadConst(1); // First condition is false
		Code.putFalseJump(Code.ne, Code.pc + 8);
		Code.loadConst(0); // Second condition is false
		Code.putJump(Code.pc + 5); // => return false
		Code.put(Code.pop); // First condition is true => pop second factor and return true
		Code.loadConst(1); // Second condition is true => return true
	}

	public void visit(ConditionFacts conditionFacts) {
		// Check and condition
		Code.loadConst(1);
		Code.putFalseJump(Code.eq, Code.pc + 11);
		Code.loadConst(1); // First condition is true
		Code.putFalseJump(Code.eq, Code.pc + 8);
		Code.loadConst(1); // Second condition is true
		Code.putJump(Code.pc + 5); // => return true
		Code.put(Code.pop); // First condition is false => pop second factor and return false
		Code.loadConst(0); // Second condition is false => return false
	}

	private int getRelationOperator(RelOp relOp) {
		if (relOp instanceof Equal)
			return Code.eq;
		if (relOp instanceof NotEqual)
			return Code.ne;
		if (relOp instanceof GreaterThan)
			return Code.gt;
		if (relOp instanceof GreaterThanEqual)
			return Code.ge;
		if (relOp instanceof LessThan)
			return Code.lt;
		if (relOp instanceof LessThanEqual)
			return Code.le;
		return 0;
	}

	public void visit(ConditionExpressions conditionExpressions) {
		// Check condition
		Code.putFalseJump(getRelationOperator(conditionExpressions.getRelOp()), Code.pc + 7);
		Code.loadConst(1); // True
		Code.putJump(Code.pc + 4);
		Code.loadConst(0); // False
	}

	private Stack<Integer> ifFixupAddresses = new Stack<Integer>();

	private Stack<Integer> elseFixupAddresses = new Stack<Integer>();

	public void visit(IfCondition ifCondition) {
		// Check if condition
		Code.loadConst(1);
		Code.putFalseJump(Code.eq, 0);
		// Save if condition address for later fix up
		this.ifFixupAddresses.push(Code.pc - 2);
	}

	public void visit(IfStatementEnd ifStatementEnd) {
		if (ifStatementEnd.getParent() instanceof IfElseStatement) {
			Code.putJump(0);
			// Save else address for later fix up
			this.elseFixupAddresses.push(Code.pc - 2);
		}
		Code.fixup(this.ifFixupAddresses.pop());
	}

	public void visit(ElseStatementEnd elseStatementEnd) {
		Code.fixup(this.elseFixupAddresses.pop());
	}

	private Stack<Integer> forConditionAddresses = new Stack<Integer>();

	private Stack<Integer> forLastStatementAddresses = new Stack<Integer>();

	private Stack<Integer> forFalseConditionFixupAddresses = new Stack<Integer>();

	private Stack<Integer> forTrueConditionFixupAddresses = new Stack<Integer>();

	private Stack<List<Integer>> forBreakFixupAddressesLists = new Stack<List<Integer>>();

	public void visit(ForDesignatorStatement forDesignatorStatement) {
		this.forConditionAddresses.push(Code.pc);
	}

	public void visit(NoForDesignatorStatement noForDesignatorStatement) {
		this.forConditionAddresses.push(Code.pc);
	}

	public void visit(ForCondition forCondition) {
		// Check for condition
		Code.loadConst(1);
		Code.putFalseJump(Code.eq, 0); // Jump out of for
		// Save false for condition address for later fix up
		this.forFalseConditionFixupAddresses.push(Code.pc - 2);

		Code.putJump(0); // Jump to for statement
		// Save true for condition address for later fix up
		this.forTrueConditionFixupAddresses.push(Code.pc - 2);

		this.forLastStatementAddresses.push(Code.pc);
	}

	public void visit(NoForCondition noForCondition) {
		Code.putJump(0); // Jump to for statement
		// Save true for condition address for later fix up
		this.forTrueConditionFixupAddresses.push(Code.pc - 2);

		this.forLastStatementAddresses.push(Code.pc);
	}

	public void visit(ForDeclarationEnd forDeclarationEnd) {
		Code.putJump(this.forConditionAddresses.pop()); // Jump back to for condition
		Code.fixup(this.forTrueConditionFixupAddresses.pop());

		forBreakFixupAddressesLists.push(new ArrayList<Integer>());
	}

	public void visit(BreakStatement breakStatement) {
		Code.putJump(0); // Jump out of for
		// Save break address for later fix up
		forBreakFixupAddressesLists.peek().add(Code.pc - 2);
	}

	public void visit(ContinueStatement continueStatement) {
		Code.putJump(forLastStatementAddresses.peek()); // Jump to last for statement
	}

	public void visit(ForStatement forStatement) {
		Code.putJump(this.forLastStatementAddresses.pop()); // Jump to last for statement
		if (forStatement.getForCond() instanceof ForCondition) {
			Code.fixup(this.forFalseConditionFixupAddresses.pop());
		}

		// Fix up break statement addresses
		List<Integer> forBreakFixupAddresses = this.forBreakFixupAddressesLists.pop();
		for (Integer forBreakFixupAddress : forBreakFixupAddresses)
			Code.fixup(forBreakFixupAddress);
	}

	public void visit(FieldDesignator fieldDesignator) {
		SyntaxNode parent = fieldDesignator.getParent();
		if (parent instanceof AssignDesignatorStatement || parent instanceof LastForAssignDesignatorStatement
				|| parent instanceof IncDesignatorStatement || parent instanceof LastForIncDesignatorStatement
				|| parent instanceof DecDesignatorStatement || parent instanceof LastForDecDesignatorStatement
				|| parent instanceof FunctionDesignatorStatement || parent instanceof LastForFunctionDesignatorStatement
				|| parent instanceof FunctionFactor || parent instanceof ReadStatement) {
			// Do not load designator for statements where designator is stored
			// Do not load designator for function statements
		} else {
			Code.load(fieldDesignator.obj);
		}
	}

	public void visit(ArrayDesignator arrayDesignator) {
		SyntaxNode parent = arrayDesignator.getParent();
		if (parent instanceof AssignDesignatorStatement || parent instanceof LastForAssignDesignatorStatement
				|| parent instanceof IncDesignatorStatement || parent instanceof LastForIncDesignatorStatement
				|| parent instanceof DecDesignatorStatement || parent instanceof LastForDecDesignatorStatement
				|| parent instanceof ReadStatement) {
			// Do not load designator for statements where designator is stored
			// Do not check function statements, an array element cannot be a function
		} else {
			Code.load(arrayDesignator.obj);
		}
	}

	public void visit(SimpleDesignator simpleDesignator) {
		SyntaxNode parent = simpleDesignator.getParent();
		if (this.isInClassMethod
				&& (simpleDesignator.obj.getKind() == Obj.Fld || simpleDesignator.obj.getKind() == Obj.Meth))
			// Load hidden this argument for class method call
			Code.put(Code.load_n + 0);
		if (parent instanceof AssignDesignatorStatement || parent instanceof LastForAssignDesignatorStatement
				|| parent instanceof IncDesignatorStatement || parent instanceof LastForIncDesignatorStatement
				|| parent instanceof DecDesignatorStatement || parent instanceof LastForDecDesignatorStatement
				|| parent instanceof FunctionDesignatorStatement || parent instanceof LastForFunctionDesignatorStatement
				|| parent instanceof FunctionFactor || parent instanceof ReadStatement) {
			// Do not load designator for statements where designator is stored
			// Do not load designator for function statements (except for class methods)
		} else {
			if (simpleDesignator.obj.getType().getKind() == Struct.Enum && parent instanceof FieldDesignator) {
				// Do not load enumeration type object
			} else {
				Code.load(simpleDesignator.obj);
			}
		}
	}

	public void visit(Terms terms) {
		if (terms.getAddOp() instanceof Addition)
			Code.put(Code.add);
		else if (terms.getAddOp() instanceof Subtraction)
			Code.put(Code.sub);
	}

	public void visit(NegFirstTerm negFirstTerm) {
		Code.put(Code.neg);
	}

	public void visit(Factors factors) {
		if (factors.getMulOp() instanceof Multiplication)
			Code.put(Code.mul);
		else if (factors.getMulOp() instanceof Division)
			Code.put(Code.div);
		else if (factors.getMulOp() instanceof Modulus)
			Code.put(Code.rem);
	}

	public void visit(FunctionFactor functionFactor) {
		Obj function = functionFactor.getDesignator().obj;
		if (function.getName().equals("chr") || function.getName().equals("ord")) {
			// No need for conversion, char and integer values are the same
		} else if (function.getName().equals("len")) {
			Code.put(Code.arraylength);
		} else {
			if (functionFactor.getDesignator() instanceof FieldDesignator) {
				// Virtual method call
				Designator designator = ((FieldDesignator) functionFactor.getDesignator()).getDesignator();

				// Get virtual table pointer
				if (designator instanceof ArrayDesignator) {
					((ArrayDesignator) designator).traverseBottomUp(this);
				} else {
					Code.load(designator.obj);
				}
				Code.put(Code.getfield);
				Code.put2(0);

				Code.put(Code.invokevirtual);
				for (int position = 0; position < function.getName().length(); ++position)
					Code.put4((int) function.getName().charAt(position));
				Code.put4(-1);
			} else if (this.isInClassMethod && functionFactor.getDesignator() instanceof SimpleDesignator) {
				// Virtual method call
				// Get virtual table pointer
				Code.put(Code.load_n + 0);
				Code.put(Code.getfield);
				Code.put2(0);

				Code.put(Code.invokevirtual);
				for (int position = 0; position < function.getName().length(); ++position)
					Code.put4((int) function.getName().charAt(position));
				Code.put4(-1);

			} else {
				Code.put(Code.call);
				Code.put2(function.getAdr() - Code.pc + 1);
			}
		}
	}

	public void visit(NumFactor numFactor) {
		Obj constant = new Obj(Obj.Con, "constant", numFactor.struct);
		constant.setAdr(numFactor.getValue());
		Code.load(constant);
	}

	public void visit(CharFactor charFactor) {
		Obj constant = new Obj(Obj.Con, "constant", charFactor.struct);
		constant.setAdr(charFactor.getValue());
		Code.load(constant);
	}

	public void visit(BoolFactor boolFactor) {
		Obj constant = new Obj(Obj.Con, "constant", boolFactor.struct);
		constant.setAdr(boolFactor.getValue() ? 1 : 0);
		Code.load(constant);
	}

	public void visit(NewArrayFactor newArrayFactor) {
		Code.put(Code.newarray);
		if (newArrayFactor.struct.getElemType() == Tab.charType)
			Code.put(0); // Byte element
		else
			Code.put(1); // Word element
	}

	Struct arrayElementType = null;

	public void visit(NewInitializedArrayFactor newInitializedArrayFactor) {
		this.arrayElementType = null;
	}

	public void visit(NewInitializedArrayExpressionEnd newInitializedArrayExpressionEnd) {
		Code.put(Code.newarray);
		NewInitializedArrayFactor array = ((NewInitializedArrayFactor) newInitializedArrayExpressionEnd.getParent());
		this.arrayElementType = array.getType().struct;
		if (this.arrayElementType == Tab.charType)
			Code.put(0); // Byte element
		else
			Code.put(1); // Word element

		// Save array pointer for initialization
		Code.put(Code.dup);
		// Store array pointer for later use in initialization
		Expression expression = (Expression) ((FirstFactor) array.getParent()).getParent();
		if (expression.getParent() instanceof AssignDesignatorStatement) {
			Code.store(((AssignDesignatorStatement) expression.getParent()).getDesignator().obj);
		} else if (expression.getParent() instanceof LastForAssignDesignatorStatement) {
			Code.store(((LastForAssignDesignatorStatement) expression.getParent()).getDesignator().obj);
		}

		// Prepare expression stack
		NumFactor numFactor = ((NumFactor) ((FirstFactor) ((FirstTerm) array.getExpression()).getTerm()).getFactor());
		int arrayLength = numFactor.getValue();
		for (int arrayIndex = arrayLength - 1; arrayIndex >= 0; --arrayIndex) {
			Code.put(Code.dup);
			Code.loadConst(arrayIndex);
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
		}
		Code.put(Code.pop); // Remove duplicate array pointer
	}

	public void visit(InitializationExpressions initializationExpressions) {
		if (this.arrayElementType == Tab.charType)
			Code.put(Code.bastore); // Byte element
		else
			Code.put(Code.astore); // Word element
	}

	public void visit(FirstInitializationExpression firstInitializationExpression) {
		if (this.arrayElementType == Tab.charType)
			Code.put(Code.bastore); // Byte element
		else
			Code.put(Code.astore); // Word element
	}

	public void visit(NewFactor newFactor) {
		Code.put(Code.new_);
		int classObjSize = (newFactor.struct.getNumberOfFields() + 1) * 4;
		Code.put2(classObjSize);

		// Add virtual method table pointer to class object
		Code.put(Code.dup);
		String className = ((TypeName) newFactor.getType()).getName();
		Collection<Obj> universeSymbols = Tab.currentScope.getLocals().symbols();
		for (Obj universeSymbol : universeSymbols) {
			if (universeSymbol.getKind() == Obj.Prog) {
				Collection<Obj> programSymbols = universeSymbol.getLocalSymbols();
				for (Obj programSymbol : programSymbols) {
					if (programSymbol.getName().equals(className)) {
						Code.loadConst(programSymbol.getAdr()); // Load virtual table pointer
						Code.put(Code.putfield);
						Code.put2(0);
						return;
					}
				}
			}
		}

	}
}