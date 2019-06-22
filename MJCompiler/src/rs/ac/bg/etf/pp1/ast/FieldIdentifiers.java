// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class FieldIdentifiers extends FieldIdentList {

    private FieldIdent FieldIdent;
    private FieldIdentList FieldIdentList;

    public FieldIdentifiers (FieldIdent FieldIdent, FieldIdentList FieldIdentList) {
        this.FieldIdent=FieldIdent;
        if(FieldIdent!=null) FieldIdent.setParent(this);
        this.FieldIdentList=FieldIdentList;
        if(FieldIdentList!=null) FieldIdentList.setParent(this);
    }

    public FieldIdent getFieldIdent() {
        return FieldIdent;
    }

    public void setFieldIdent(FieldIdent FieldIdent) {
        this.FieldIdent=FieldIdent;
    }

    public FieldIdentList getFieldIdentList() {
        return FieldIdentList;
    }

    public void setFieldIdentList(FieldIdentList FieldIdentList) {
        this.FieldIdentList=FieldIdentList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FieldIdent!=null) FieldIdent.accept(visitor);
        if(FieldIdentList!=null) FieldIdentList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FieldIdent!=null) FieldIdent.traverseTopDown(visitor);
        if(FieldIdentList!=null) FieldIdentList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FieldIdent!=null) FieldIdent.traverseBottomUp(visitor);
        if(FieldIdentList!=null) FieldIdentList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FieldIdentifiers(\n");

        if(FieldIdent!=null)
            buffer.append(FieldIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FieldIdentList!=null)
            buffer.append(FieldIdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FieldIdentifiers]");
        return buffer.toString();
    }
}
