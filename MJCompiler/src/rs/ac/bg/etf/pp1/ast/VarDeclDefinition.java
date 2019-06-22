// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class VarDeclDefinition extends VarDecl {

    private VarType VarType;
    private VarIdentList VarIdentList;

    public VarDeclDefinition (VarType VarType, VarIdentList VarIdentList) {
        this.VarType=VarType;
        if(VarType!=null) VarType.setParent(this);
        this.VarIdentList=VarIdentList;
        if(VarIdentList!=null) VarIdentList.setParent(this);
    }

    public VarType getVarType() {
        return VarType;
    }

    public void setVarType(VarType VarType) {
        this.VarType=VarType;
    }

    public VarIdentList getVarIdentList() {
        return VarIdentList;
    }

    public void setVarIdentList(VarIdentList VarIdentList) {
        this.VarIdentList=VarIdentList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarType!=null) VarType.accept(visitor);
        if(VarIdentList!=null) VarIdentList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarType!=null) VarType.traverseTopDown(visitor);
        if(VarIdentList!=null) VarIdentList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarType!=null) VarType.traverseBottomUp(visitor);
        if(VarIdentList!=null) VarIdentList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclDefinition(\n");

        if(VarType!=null)
            buffer.append(VarType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarIdentList!=null)
            buffer.append(VarIdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclDefinition]");
        return buffer.toString();
    }
}
