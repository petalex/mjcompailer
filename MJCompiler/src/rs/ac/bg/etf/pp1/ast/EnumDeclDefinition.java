// generated with ast extension for cup
// version 0.8
// 2/1/2019 22:51:10


package rs.ac.bg.etf.pp1.ast;

public class EnumDeclDefinition extends EnumDecl {

    private EnumIdentList EnumIdentList;

    public EnumDeclDefinition (EnumIdentList EnumIdentList) {
        this.EnumIdentList=EnumIdentList;
        if(EnumIdentList!=null) EnumIdentList.setParent(this);
    }

    public EnumIdentList getEnumIdentList() {
        return EnumIdentList;
    }

    public void setEnumIdentList(EnumIdentList EnumIdentList) {
        this.EnumIdentList=EnumIdentList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumIdentList!=null) EnumIdentList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumIdentList!=null) EnumIdentList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumIdentList!=null) EnumIdentList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumDeclDefinition(\n");

        if(EnumIdentList!=null)
            buffer.append(EnumIdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumDeclDefinition]");
        return buffer.toString();
    }
}
