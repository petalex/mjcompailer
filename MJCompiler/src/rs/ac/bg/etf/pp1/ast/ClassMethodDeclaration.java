// generated with ast extension for cup
// version 0.8
// 11/5/2019 2:45:35


package rs.ac.bg.etf.pp1.ast;

public class ClassMethodDeclaration extends ClassMethodDecl {

    private ClassModifier ClassModifier;
    private MethodDecl MethodDecl;

    public ClassMethodDeclaration (ClassModifier ClassModifier, MethodDecl MethodDecl) {
        this.ClassModifier=ClassModifier;
        if(ClassModifier!=null) ClassModifier.setParent(this);
        this.MethodDecl=MethodDecl;
        if(MethodDecl!=null) MethodDecl.setParent(this);
    }

    public ClassModifier getClassModifier() {
        return ClassModifier;
    }

    public void setClassModifier(ClassModifier ClassModifier) {
        this.ClassModifier=ClassModifier;
    }

    public MethodDecl getMethodDecl() {
        return MethodDecl;
    }

    public void setMethodDecl(MethodDecl MethodDecl) {
        this.MethodDecl=MethodDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassModifier!=null) ClassModifier.accept(visitor);
        if(MethodDecl!=null) MethodDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassModifier!=null) ClassModifier.traverseTopDown(visitor);
        if(MethodDecl!=null) MethodDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassModifier!=null) ClassModifier.traverseBottomUp(visitor);
        if(MethodDecl!=null) MethodDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassMethodDeclaration(\n");

        if(ClassModifier!=null)
            buffer.append(ClassModifier.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDecl!=null)
            buffer.append(MethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassMethodDeclaration]");
        return buffer.toString();
    }
}
