// generated with ast extension for cup
// version 0.8
// 11/5/2019 2:45:35


package rs.ac.bg.etf.pp1.ast;

public class ClassFieldDeclarations extends ClassFieldDeclList {

    private ClassFieldDeclList ClassFieldDeclList;
    private ClassFieldDecl ClassFieldDecl;

    public ClassFieldDeclarations (ClassFieldDeclList ClassFieldDeclList, ClassFieldDecl ClassFieldDecl) {
        this.ClassFieldDeclList=ClassFieldDeclList;
        if(ClassFieldDeclList!=null) ClassFieldDeclList.setParent(this);
        this.ClassFieldDecl=ClassFieldDecl;
        if(ClassFieldDecl!=null) ClassFieldDecl.setParent(this);
    }

    public ClassFieldDeclList getClassFieldDeclList() {
        return ClassFieldDeclList;
    }

    public void setClassFieldDeclList(ClassFieldDeclList ClassFieldDeclList) {
        this.ClassFieldDeclList=ClassFieldDeclList;
    }

    public ClassFieldDecl getClassFieldDecl() {
        return ClassFieldDecl;
    }

    public void setClassFieldDecl(ClassFieldDecl ClassFieldDecl) {
        this.ClassFieldDecl=ClassFieldDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassFieldDeclList!=null) ClassFieldDeclList.accept(visitor);
        if(ClassFieldDecl!=null) ClassFieldDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassFieldDeclList!=null) ClassFieldDeclList.traverseTopDown(visitor);
        if(ClassFieldDecl!=null) ClassFieldDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassFieldDeclList!=null) ClassFieldDeclList.traverseBottomUp(visitor);
        if(ClassFieldDecl!=null) ClassFieldDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassFieldDeclarations(\n");

        if(ClassFieldDeclList!=null)
            buffer.append(ClassFieldDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassFieldDecl!=null)
            buffer.append(ClassFieldDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassFieldDeclarations]");
        return buffer.toString();
    }
}
