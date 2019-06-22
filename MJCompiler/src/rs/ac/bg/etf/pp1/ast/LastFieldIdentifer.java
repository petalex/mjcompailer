// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class LastFieldIdentifer extends FieldIdentList {

    private LastFieldIdent LastFieldIdent;

    public LastFieldIdentifer (LastFieldIdent LastFieldIdent) {
        this.LastFieldIdent=LastFieldIdent;
        if(LastFieldIdent!=null) LastFieldIdent.setParent(this);
    }

    public LastFieldIdent getLastFieldIdent() {
        return LastFieldIdent;
    }

    public void setLastFieldIdent(LastFieldIdent LastFieldIdent) {
        this.LastFieldIdent=LastFieldIdent;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LastFieldIdent!=null) LastFieldIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LastFieldIdent!=null) LastFieldIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LastFieldIdent!=null) LastFieldIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LastFieldIdentifer(\n");

        if(LastFieldIdent!=null)
            buffer.append(LastFieldIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LastFieldIdentifer]");
        return buffer.toString();
    }
}
