// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclDefinition extends ClassDecl {

    private ClssName ClssName;
    private ExtndsImplmnts ExtndsImplmnts;
    private ExtndsImplmntsEnd ExtndsImplmntsEnd;
    private FieldDeclList FieldDeclList;
    private FieldDeclEnd FieldDeclEnd;
    private ClassMethodDeclBlock ClassMethodDeclBlock;

    public ClassDeclDefinition (ClssName ClssName, ExtndsImplmnts ExtndsImplmnts, ExtndsImplmntsEnd ExtndsImplmntsEnd, FieldDeclList FieldDeclList, FieldDeclEnd FieldDeclEnd, ClassMethodDeclBlock ClassMethodDeclBlock) {
        this.ClssName=ClssName;
        if(ClssName!=null) ClssName.setParent(this);
        this.ExtndsImplmnts=ExtndsImplmnts;
        if(ExtndsImplmnts!=null) ExtndsImplmnts.setParent(this);
        this.ExtndsImplmntsEnd=ExtndsImplmntsEnd;
        if(ExtndsImplmntsEnd!=null) ExtndsImplmntsEnd.setParent(this);
        this.FieldDeclList=FieldDeclList;
        if(FieldDeclList!=null) FieldDeclList.setParent(this);
        this.FieldDeclEnd=FieldDeclEnd;
        if(FieldDeclEnd!=null) FieldDeclEnd.setParent(this);
        this.ClassMethodDeclBlock=ClassMethodDeclBlock;
        if(ClassMethodDeclBlock!=null) ClassMethodDeclBlock.setParent(this);
    }

    public ClssName getClssName() {
        return ClssName;
    }

    public void setClssName(ClssName ClssName) {
        this.ClssName=ClssName;
    }

    public ExtndsImplmnts getExtndsImplmnts() {
        return ExtndsImplmnts;
    }

    public void setExtndsImplmnts(ExtndsImplmnts ExtndsImplmnts) {
        this.ExtndsImplmnts=ExtndsImplmnts;
    }

    public ExtndsImplmntsEnd getExtndsImplmntsEnd() {
        return ExtndsImplmntsEnd;
    }

    public void setExtndsImplmntsEnd(ExtndsImplmntsEnd ExtndsImplmntsEnd) {
        this.ExtndsImplmntsEnd=ExtndsImplmntsEnd;
    }

    public FieldDeclList getFieldDeclList() {
        return FieldDeclList;
    }

    public void setFieldDeclList(FieldDeclList FieldDeclList) {
        this.FieldDeclList=FieldDeclList;
    }

    public FieldDeclEnd getFieldDeclEnd() {
        return FieldDeclEnd;
    }

    public void setFieldDeclEnd(FieldDeclEnd FieldDeclEnd) {
        this.FieldDeclEnd=FieldDeclEnd;
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
        if(ClssName!=null) ClssName.accept(visitor);
        if(ExtndsImplmnts!=null) ExtndsImplmnts.accept(visitor);
        if(ExtndsImplmntsEnd!=null) ExtndsImplmntsEnd.accept(visitor);
        if(FieldDeclList!=null) FieldDeclList.accept(visitor);
        if(FieldDeclEnd!=null) FieldDeclEnd.accept(visitor);
        if(ClassMethodDeclBlock!=null) ClassMethodDeclBlock.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClssName!=null) ClssName.traverseTopDown(visitor);
        if(ExtndsImplmnts!=null) ExtndsImplmnts.traverseTopDown(visitor);
        if(ExtndsImplmntsEnd!=null) ExtndsImplmntsEnd.traverseTopDown(visitor);
        if(FieldDeclList!=null) FieldDeclList.traverseTopDown(visitor);
        if(FieldDeclEnd!=null) FieldDeclEnd.traverseTopDown(visitor);
        if(ClassMethodDeclBlock!=null) ClassMethodDeclBlock.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClssName!=null) ClssName.traverseBottomUp(visitor);
        if(ExtndsImplmnts!=null) ExtndsImplmnts.traverseBottomUp(visitor);
        if(ExtndsImplmntsEnd!=null) ExtndsImplmntsEnd.traverseBottomUp(visitor);
        if(FieldDeclList!=null) FieldDeclList.traverseBottomUp(visitor);
        if(FieldDeclEnd!=null) FieldDeclEnd.traverseBottomUp(visitor);
        if(ClassMethodDeclBlock!=null) ClassMethodDeclBlock.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclDefinition(\n");

        if(ClssName!=null)
            buffer.append(ClssName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExtndsImplmnts!=null)
            buffer.append(ExtndsImplmnts.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExtndsImplmntsEnd!=null)
            buffer.append(ExtndsImplmntsEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FieldDeclList!=null)
            buffer.append(FieldDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FieldDeclEnd!=null)
            buffer.append(FieldDeclEnd.toString("  "+tab));
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
