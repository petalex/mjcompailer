// generated with ast extension for cup
// version 0.8
// 11/5/2019 2:45:35


package rs.ac.bg.etf.pp1.ast;

public class ClassMethodDeclarationsBlock extends ClassMethodDeclBlock {

    private ClassMethodDeclList ClassMethodDeclList;

    public ClassMethodDeclarationsBlock (ClassMethodDeclList ClassMethodDeclList) {
        this.ClassMethodDeclList=ClassMethodDeclList;
        if(ClassMethodDeclList!=null) ClassMethodDeclList.setParent(this);
    }

    public ClassMethodDeclList getClassMethodDeclList() {
        return ClassMethodDeclList;
    }

    public void setClassMethodDeclList(ClassMethodDeclList ClassMethodDeclList) {
        this.ClassMethodDeclList=ClassMethodDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassMethodDeclList!=null) ClassMethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassMethodDeclList!=null) ClassMethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassMethodDeclList!=null) ClassMethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassMethodDeclarationsBlock(\n");

        if(ClassMethodDeclList!=null)
            buffer.append(ClassMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassMethodDeclarationsBlock]");
        return buffer.toString();
    }
}
