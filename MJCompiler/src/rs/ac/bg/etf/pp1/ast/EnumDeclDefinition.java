// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class EnumDeclDefinition extends EnumDecl {

    private EnumName EnumName;
    private EnumIdentList EnumIdentList;

    public EnumDeclDefinition (EnumName EnumName, EnumIdentList EnumIdentList) {
        this.EnumName=EnumName;
        if(EnumName!=null) EnumName.setParent(this);
        this.EnumIdentList=EnumIdentList;
        if(EnumIdentList!=null) EnumIdentList.setParent(this);
    }

    public EnumName getEnumName() {
        return EnumName;
    }

    public void setEnumName(EnumName EnumName) {
        this.EnumName=EnumName;
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
        if(EnumName!=null) EnumName.accept(visitor);
        if(EnumIdentList!=null) EnumIdentList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumName!=null) EnumName.traverseTopDown(visitor);
        if(EnumIdentList!=null) EnumIdentList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumName!=null) EnumName.traverseBottomUp(visitor);
        if(EnumIdentList!=null) EnumIdentList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumDeclDefinition(\n");

        if(EnumName!=null)
            buffer.append(EnumName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

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
