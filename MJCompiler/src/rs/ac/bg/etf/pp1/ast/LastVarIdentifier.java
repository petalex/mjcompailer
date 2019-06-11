// generated with ast extension for cup
// version 0.8
// 11/5/2019 2:45:34


package rs.ac.bg.etf.pp1.ast;

public class LastVarIdentifier extends VarIdentList {

    private LastVarIdent LastVarIdent;

    public LastVarIdentifier (LastVarIdent LastVarIdent) {
        this.LastVarIdent=LastVarIdent;
        if(LastVarIdent!=null) LastVarIdent.setParent(this);
    }

    public LastVarIdent getLastVarIdent() {
        return LastVarIdent;
    }

    public void setLastVarIdent(LastVarIdent LastVarIdent) {
        this.LastVarIdent=LastVarIdent;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LastVarIdent!=null) LastVarIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LastVarIdent!=null) LastVarIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LastVarIdent!=null) LastVarIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LastVarIdentifier(\n");

        if(LastVarIdent!=null)
            buffer.append(LastVarIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LastVarIdentifier]");
        return buffer.toString();
    }
}
