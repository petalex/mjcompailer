// generated with ast extension for cup
// version 0.8
// 11/5/2019 2:45:34


package rs.ac.bg.etf.pp1.ast;

public class ClassFieldDeclDefinition extends ClassFieldDecl {

    private ClassModifier ClassModifier;
    private VarDecl VarDecl;

    public ClassFieldDeclDefinition (ClassModifier ClassModifier, VarDecl VarDecl) {
        this.ClassModifier=ClassModifier;
        if(ClassModifier!=null) ClassModifier.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public ClassModifier getClassModifier() {
        return ClassModifier;
    }

    public void setClassModifier(ClassModifier ClassModifier) {
        this.ClassModifier=ClassModifier;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassModifier!=null) ClassModifier.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassModifier!=null) ClassModifier.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassModifier!=null) ClassModifier.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassFieldDeclDefinition(\n");

        if(ClassModifier!=null)
            buffer.append(ClassModifier.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassFieldDeclDefinition]");
        return buffer.toString();
    }
}
