// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class ExtendsClassWrapper extends ExtndsImplmnts {

    private Extends Extends;

    public ExtendsClassWrapper (Extends Extends) {
        this.Extends=Extends;
        if(Extends!=null) Extends.setParent(this);
    }

    public Extends getExtends() {
        return Extends;
    }

    public void setExtends(Extends Extends) {
        this.Extends=Extends;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Extends!=null) Extends.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Extends!=null) Extends.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Extends!=null) Extends.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExtendsClassWrapper(\n");

        if(Extends!=null)
            buffer.append(Extends.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExtendsClassWrapper]");
        return buffer.toString();
    }
}
