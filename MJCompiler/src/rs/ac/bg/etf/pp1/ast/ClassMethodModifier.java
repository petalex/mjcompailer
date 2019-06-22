// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class ClassMethodModifier extends ClassMethModifier {

    private Modifier Modifier;

    public ClassMethodModifier (Modifier Modifier) {
        this.Modifier=Modifier;
        if(Modifier!=null) Modifier.setParent(this);
    }

    public Modifier getModifier() {
        return Modifier;
    }

    public void setModifier(Modifier Modifier) {
        this.Modifier=Modifier;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Modifier!=null) Modifier.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Modifier!=null) Modifier.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Modifier!=null) Modifier.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassMethodModifier(\n");

        if(Modifier!=null)
            buffer.append(Modifier.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassMethodModifier]");
        return buffer.toString();
    }
}
