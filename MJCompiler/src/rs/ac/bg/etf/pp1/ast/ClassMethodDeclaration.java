// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class ClassMethodDeclaration extends ClassMethodDecl {

    private ClassMethModifier ClassMethModifier;
    private ClassMethType ClassMethType;
    private ClassMethName ClassMethName;
    private FormParams FormParams;
    private VarDeclList VarDeclList;
    private ClassMethDeclEnd ClassMethDeclEnd;
    private StatementList StatementList;

    public ClassMethodDeclaration (ClassMethModifier ClassMethModifier, ClassMethType ClassMethType, ClassMethName ClassMethName, FormParams FormParams, VarDeclList VarDeclList, ClassMethDeclEnd ClassMethDeclEnd, StatementList StatementList) {
        this.ClassMethModifier=ClassMethModifier;
        if(ClassMethModifier!=null) ClassMethModifier.setParent(this);
        this.ClassMethType=ClassMethType;
        if(ClassMethType!=null) ClassMethType.setParent(this);
        this.ClassMethName=ClassMethName;
        if(ClassMethName!=null) ClassMethName.setParent(this);
        this.FormParams=FormParams;
        if(FormParams!=null) FormParams.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.ClassMethDeclEnd=ClassMethDeclEnd;
        if(ClassMethDeclEnd!=null) ClassMethDeclEnd.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public ClassMethModifier getClassMethModifier() {
        return ClassMethModifier;
    }

    public void setClassMethModifier(ClassMethModifier ClassMethModifier) {
        this.ClassMethModifier=ClassMethModifier;
    }

    public ClassMethType getClassMethType() {
        return ClassMethType;
    }

    public void setClassMethType(ClassMethType ClassMethType) {
        this.ClassMethType=ClassMethType;
    }

    public ClassMethName getClassMethName() {
        return ClassMethName;
    }

    public void setClassMethName(ClassMethName ClassMethName) {
        this.ClassMethName=ClassMethName;
    }

    public FormParams getFormParams() {
        return FormParams;
    }

    public void setFormParams(FormParams FormParams) {
        this.FormParams=FormParams;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public ClassMethDeclEnd getClassMethDeclEnd() {
        return ClassMethDeclEnd;
    }

    public void setClassMethDeclEnd(ClassMethDeclEnd ClassMethDeclEnd) {
        this.ClassMethDeclEnd=ClassMethDeclEnd;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassMethModifier!=null) ClassMethModifier.accept(visitor);
        if(ClassMethType!=null) ClassMethType.accept(visitor);
        if(ClassMethName!=null) ClassMethName.accept(visitor);
        if(FormParams!=null) FormParams.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(ClassMethDeclEnd!=null) ClassMethDeclEnd.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassMethModifier!=null) ClassMethModifier.traverseTopDown(visitor);
        if(ClassMethType!=null) ClassMethType.traverseTopDown(visitor);
        if(ClassMethName!=null) ClassMethName.traverseTopDown(visitor);
        if(FormParams!=null) FormParams.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(ClassMethDeclEnd!=null) ClassMethDeclEnd.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassMethModifier!=null) ClassMethModifier.traverseBottomUp(visitor);
        if(ClassMethType!=null) ClassMethType.traverseBottomUp(visitor);
        if(ClassMethName!=null) ClassMethName.traverseBottomUp(visitor);
        if(FormParams!=null) FormParams.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(ClassMethDeclEnd!=null) ClassMethDeclEnd.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassMethodDeclaration(\n");

        if(ClassMethModifier!=null)
            buffer.append(ClassMethModifier.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassMethType!=null)
            buffer.append(ClassMethType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassMethName!=null)
            buffer.append(ClassMethName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParams!=null)
            buffer.append(FormParams.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassMethDeclEnd!=null)
            buffer.append(ClassMethDeclEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassMethodDeclaration]");
        return buffer.toString();
    }
}
