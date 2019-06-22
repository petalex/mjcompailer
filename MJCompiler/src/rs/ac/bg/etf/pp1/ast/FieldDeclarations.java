// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class FieldDeclarations extends FieldDeclList {

    private FieldDeclList FieldDeclList;
    private FieldDecl FieldDecl;

    public FieldDeclarations (FieldDeclList FieldDeclList, FieldDecl FieldDecl) {
        this.FieldDeclList=FieldDeclList;
        if(FieldDeclList!=null) FieldDeclList.setParent(this);
        this.FieldDecl=FieldDecl;
        if(FieldDecl!=null) FieldDecl.setParent(this);
    }

    public FieldDeclList getFieldDeclList() {
        return FieldDeclList;
    }

    public void setFieldDeclList(FieldDeclList FieldDeclList) {
        this.FieldDeclList=FieldDeclList;
    }

    public FieldDecl getFieldDecl() {
        return FieldDecl;
    }

    public void setFieldDecl(FieldDecl FieldDecl) {
        this.FieldDecl=FieldDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FieldDeclList!=null) FieldDeclList.accept(visitor);
        if(FieldDecl!=null) FieldDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FieldDeclList!=null) FieldDeclList.traverseTopDown(visitor);
        if(FieldDecl!=null) FieldDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FieldDeclList!=null) FieldDeclList.traverseBottomUp(visitor);
        if(FieldDecl!=null) FieldDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FieldDeclarations(\n");

        if(FieldDeclList!=null)
            buffer.append(FieldDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FieldDecl!=null)
            buffer.append(FieldDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FieldDeclarations]");
        return buffer.toString();
    }
}
