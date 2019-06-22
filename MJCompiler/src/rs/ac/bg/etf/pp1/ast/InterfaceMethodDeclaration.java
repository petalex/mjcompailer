// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class InterfaceMethodDeclaration extends InterfaceMethodDecl {

    private InterfaceMethType InterfaceMethType;
    private InterfaceMethName InterfaceMethName;
    private FormParams FormParams;

    public InterfaceMethodDeclaration (InterfaceMethType InterfaceMethType, InterfaceMethName InterfaceMethName, FormParams FormParams) {
        this.InterfaceMethType=InterfaceMethType;
        if(InterfaceMethType!=null) InterfaceMethType.setParent(this);
        this.InterfaceMethName=InterfaceMethName;
        if(InterfaceMethName!=null) InterfaceMethName.setParent(this);
        this.FormParams=FormParams;
        if(FormParams!=null) FormParams.setParent(this);
    }

    public InterfaceMethType getInterfaceMethType() {
        return InterfaceMethType;
    }

    public void setInterfaceMethType(InterfaceMethType InterfaceMethType) {
        this.InterfaceMethType=InterfaceMethType;
    }

    public InterfaceMethName getInterfaceMethName() {
        return InterfaceMethName;
    }

    public void setInterfaceMethName(InterfaceMethName InterfaceMethName) {
        this.InterfaceMethName=InterfaceMethName;
    }

    public FormParams getFormParams() {
        return FormParams;
    }

    public void setFormParams(FormParams FormParams) {
        this.FormParams=FormParams;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(InterfaceMethType!=null) InterfaceMethType.accept(visitor);
        if(InterfaceMethName!=null) InterfaceMethName.accept(visitor);
        if(FormParams!=null) FormParams.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InterfaceMethType!=null) InterfaceMethType.traverseTopDown(visitor);
        if(InterfaceMethName!=null) InterfaceMethName.traverseTopDown(visitor);
        if(FormParams!=null) FormParams.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InterfaceMethType!=null) InterfaceMethType.traverseBottomUp(visitor);
        if(InterfaceMethName!=null) InterfaceMethName.traverseBottomUp(visitor);
        if(FormParams!=null) FormParams.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InterfaceMethodDeclaration(\n");

        if(InterfaceMethType!=null)
            buffer.append(InterfaceMethType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InterfaceMethName!=null)
            buffer.append(InterfaceMethName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParams!=null)
            buffer.append(FormParams.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InterfaceMethodDeclaration]");
        return buffer.toString();
    }
}
