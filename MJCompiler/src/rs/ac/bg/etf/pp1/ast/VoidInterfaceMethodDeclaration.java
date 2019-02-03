// generated with ast extension for cup
// version 0.8
// 2/1/2019 22:51:10


package rs.ac.bg.etf.pp1.ast;

public class VoidInterfaceMethodDeclaration extends InterfaceMethodDecl {

    private FormParams FormParams;

    public VoidInterfaceMethodDeclaration (FormParams FormParams) {
        this.FormParams=FormParams;
        if(FormParams!=null) FormParams.setParent(this);
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
        if(FormParams!=null) FormParams.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParams!=null) FormParams.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParams!=null) FormParams.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VoidInterfaceMethodDeclaration(\n");

        if(FormParams!=null)
            buffer.append(FormParams.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VoidInterfaceMethodDeclaration]");
        return buffer.toString();
    }
}
