// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class InterfaceDeclDefinition extends InterfaceDecl {

    private IntrfaceName IntrfaceName;
    private InterfaceMethodDeclList InterfaceMethodDeclList;

    public InterfaceDeclDefinition (IntrfaceName IntrfaceName, InterfaceMethodDeclList InterfaceMethodDeclList) {
        this.IntrfaceName=IntrfaceName;
        if(IntrfaceName!=null) IntrfaceName.setParent(this);
        this.InterfaceMethodDeclList=InterfaceMethodDeclList;
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.setParent(this);
    }

    public IntrfaceName getIntrfaceName() {
        return IntrfaceName;
    }

    public void setIntrfaceName(IntrfaceName IntrfaceName) {
        this.IntrfaceName=IntrfaceName;
    }

    public InterfaceMethodDeclList getInterfaceMethodDeclList() {
        return InterfaceMethodDeclList;
    }

    public void setInterfaceMethodDeclList(InterfaceMethodDeclList InterfaceMethodDeclList) {
        this.InterfaceMethodDeclList=InterfaceMethodDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IntrfaceName!=null) IntrfaceName.accept(visitor);
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IntrfaceName!=null) IntrfaceName.traverseTopDown(visitor);
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IntrfaceName!=null) IntrfaceName.traverseBottomUp(visitor);
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InterfaceDeclDefinition(\n");

        if(IntrfaceName!=null)
            buffer.append(IntrfaceName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InterfaceMethodDeclList!=null)
            buffer.append(InterfaceMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InterfaceDeclDefinition]");
        return buffer.toString();
    }
}
