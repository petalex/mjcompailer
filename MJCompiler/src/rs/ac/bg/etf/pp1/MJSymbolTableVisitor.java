package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class MJSymbolTableVisitor extends DumpSymbolTableVisitor {
	private Struct visitingEnumerationType = null;

	/**
	 * Visits additional kinds of structure node (boolean, enum, interface)
	 */
	@Override
	public void visitStructNode(Struct structToVisit) {
		switch (structToVisit.getKind()) {
		case Struct.Bool:
			output.append("boolean");
			break;
		case Struct.Enum:
			if (visitingEnumerationType == null) {
				output.append("enum [");
				for (Obj obj : structToVisit.getMembers()) {
					visitingEnumerationType = structToVisit;
					obj.accept(this);
				}
				visitingEnumerationType = null;
				output.append("]");
			} else {
				output.append("int");
			}
			break;
		case Struct.Interface:
			output.append("Interface [");
			for (Obj obj : structToVisit.getMembers()) {
				obj.accept(this);
			}
			output.append("]");
			break;
		case Struct.Array:
			output.append("Arr of ");

			switch (structToVisit.getElemType().getKind()) {
			case Struct.None:
				output.append("notype");
				break;
			case Struct.Int:
				output.append("int");
				break;
			case Struct.Char:
				output.append("char");
				break;
			case Struct.Class:
				output.append("Class");
				break;
			case Struct.Bool:
				output.append("boolean");
				break;
			case Struct.Enum:
				output.append("enum");
				break;
			case Struct.Interface:
				output.append("Interface");
				break;
			}
			return;
		}

		super.visitStructNode(structToVisit);
	}
}