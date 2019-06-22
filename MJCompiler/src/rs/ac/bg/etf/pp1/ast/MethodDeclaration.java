// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclaration extends MethodDecl {

    private MethType MethType;
    private MethName MethName;
    private FormParams FormParams;
    private VarDeclList VarDeclList;
    private MethDeclEnd MethDeclEnd;
    private StatementList StatementList;

    public MethodDeclaration (MethType MethType, MethName MethName, FormParams FormParams, VarDeclList VarDeclList, MethDeclEnd MethDeclEnd, StatementList StatementList) {
        this.MethType=MethType;
        if(MethType!=null) MethType.setParent(this);
        this.MethName=MethName;
        if(MethName!=null) MethName.setParent(this);
        this.FormParams=FormParams;
        if(FormParams!=null) FormParams.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.MethDeclEnd=MethDeclEnd;
        if(MethDeclEnd!=null) MethDeclEnd.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MethType getMethType() {
        return MethType;
    }

    public void setMethType(MethType MethType) {
        this.MethType=MethType;
    }

    public MethName getMethName() {
        return MethName;
    }

    public void setMethName(MethName MethName) {
        this.MethName=MethName;
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

    public MethDeclEnd getMethDeclEnd() {
        return MethDeclEnd;
    }

    public void setMethDeclEnd(MethDeclEnd MethDeclEnd) {
        this.MethDeclEnd=MethDeclEnd;
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
        if(MethType!=null) MethType.accept(visitor);
        if(MethName!=null) MethName.accept(visitor);
        if(FormParams!=null) FormParams.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(MethDeclEnd!=null) MethDeclEnd.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethType!=null) MethType.traverseTopDown(visitor);
        if(MethName!=null) MethName.traverseTopDown(visitor);
        if(FormParams!=null) FormParams.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(MethDeclEnd!=null) MethDeclEnd.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethType!=null) MethType.traverseBottomUp(visitor);
        if(MethName!=null) MethName.traverseBottomUp(visitor);
        if(FormParams!=null) FormParams.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(MethDeclEnd!=null) MethDeclEnd.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclaration(\n");

        if(MethType!=null)
            buffer.append(MethType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethName!=null)
            buffer.append(MethName.toString("  "+tab));
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

        if(MethDeclEnd!=null)
            buffer.append(MethDeclEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclaration]");
        return buffer.toString();
    }
}
