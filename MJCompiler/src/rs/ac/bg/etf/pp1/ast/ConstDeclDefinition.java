// generated with ast extension for cup
// version 0.8
// 11/5/2019 2:45:34


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclDefinition extends ConstDecl {

    private Type Type;
    private ConstIdentList ConstIdentList;

    public ConstDeclDefinition (Type Type, ConstIdentList ConstIdentList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ConstIdentList=ConstIdentList;
        if(ConstIdentList!=null) ConstIdentList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ConstIdentList getConstIdentList() {
        return ConstIdentList;
    }

    public void setConstIdentList(ConstIdentList ConstIdentList) {
        this.ConstIdentList=ConstIdentList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstIdentList!=null) ConstIdentList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstIdentList!=null) ConstIdentList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstIdentList!=null) ConstIdentList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclDefinition(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstIdentList!=null)
            buffer.append(ConstIdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclDefinition]");
        return buffer.toString();
    }
}
