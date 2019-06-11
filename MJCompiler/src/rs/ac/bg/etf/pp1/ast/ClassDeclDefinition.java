// generated with ast extension for cup
// version 0.8
// 11/5/2019 2:45:34


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclDefinition extends ClassDecl {

    private String I1;
    private ExtndsImplmnts ExtndsImplmnts;
    private ClassFieldDeclList ClassFieldDeclList;
    private ClassMethodDeclBlock ClassMethodDeclBlock;

    public ClassDeclDefinition (String I1, ExtndsImplmnts ExtndsImplmnts, ClassFieldDeclList ClassFieldDeclList, ClassMethodDeclBlock ClassMethodDeclBlock) {
        this.I1=I1;
        this.ExtndsImplmnts=ExtndsImplmnts;
        if(ExtndsImplmnts!=null) ExtndsImplmnts.setParent(this);
        this.ClassFieldDeclList=ClassFieldDeclList;
        if(ClassFieldDeclList!=null) ClassFieldDeclList.setParent(this);
        this.ClassMethodDeclBlock=ClassMethodDeclBlock;
        if(ClassMethodDeclBlock!=null) ClassMethodDeclBlock.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public ExtndsImplmnts getExtndsImplmnts() {
        return ExtndsImplmnts;
    }

    public void setExtndsImplmnts(ExtndsImplmnts ExtndsImplmnts) {
        this.ExtndsImplmnts=ExtndsImplmnts;
    }

    public ClassFieldDeclList getClassFieldDeclList() {
        return ClassFieldDeclList;
    }

    public void setClassFieldDeclList(ClassFieldDeclList ClassFieldDeclList) {
        this.ClassFieldDeclList=ClassFieldDeclList;
    }

    public ClassMethodDeclBlock getClassMethodDeclBlock() {
        return ClassMethodDeclBlock;
    }

    public void setClassMethodDeclBlock(ClassMethodDeclBlock ClassMethodDeclBlock) {
        this.ClassMethodDeclBlock=ClassMethodDeclBlock;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExtndsImplmnts!=null) ExtndsImplmnts.accept(visitor);
        if(ClassFieldDeclList!=null) ClassFieldDeclList.accept(visitor);
        if(ClassMethodDeclBlock!=null) ClassMethodDeclBlock.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExtndsImplmnts!=null) ExtndsImplmnts.traverseTopDown(visitor);
        if(ClassFieldDeclList!=null) ClassFieldDeclList.traverseTopDown(visitor);
        if(ClassMethodDeclBlock!=null) ClassMethodDeclBlock.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExtndsImplmnts!=null) ExtndsImplmnts.traverseBottomUp(visitor);
        if(ClassFieldDeclList!=null) ClassFieldDeclList.traverseBottomUp(visitor);
        if(ClassMethodDeclBlock!=null) ClassMethodDeclBlock.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclDefinition(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(ExtndsImplmnts!=null)
            buffer.append(ExtndsImplmnts.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassFieldDeclList!=null)
            buffer.append(ClassFieldDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassMethodDeclBlock!=null)
            buffer.append(ClassMethodDeclBlock.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclDefinition]");
        return buffer.toString();
    }
}
