// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class FieldDeclDefinition extends FieldDecl {

    private FldModifier FldModifier;
    private FldType FldType;
    private FieldIdentList FieldIdentList;

    public FieldDeclDefinition (FldModifier FldModifier, FldType FldType, FieldIdentList FieldIdentList) {
        this.FldModifier=FldModifier;
        if(FldModifier!=null) FldModifier.setParent(this);
        this.FldType=FldType;
        if(FldType!=null) FldType.setParent(this);
        this.FieldIdentList=FieldIdentList;
        if(FieldIdentList!=null) FieldIdentList.setParent(this);
    }

    public FldModifier getFldModifier() {
        return FldModifier;
    }

    public void setFldModifier(FldModifier FldModifier) {
        this.FldModifier=FldModifier;
    }

    public FldType getFldType() {
        return FldType;
    }

    public void setFldType(FldType FldType) {
        this.FldType=FldType;
    }

    public FieldIdentList getFieldIdentList() {
        return FieldIdentList;
    }

    public void setFieldIdentList(FieldIdentList FieldIdentList) {
        this.FieldIdentList=FieldIdentList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FldModifier!=null) FldModifier.accept(visitor);
        if(FldType!=null) FldType.accept(visitor);
        if(FieldIdentList!=null) FieldIdentList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FldModifier!=null) FldModifier.traverseTopDown(visitor);
        if(FldType!=null) FldType.traverseTopDown(visitor);
        if(FieldIdentList!=null) FieldIdentList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FldModifier!=null) FldModifier.traverseBottomUp(visitor);
        if(FldType!=null) FldType.traverseBottomUp(visitor);
        if(FieldIdentList!=null) FieldIdentList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FieldDeclDefinition(\n");

        if(FldModifier!=null)
            buffer.append(FldModifier.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FldType!=null)
            buffer.append(FldType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FieldIdentList!=null)
            buffer.append(FieldIdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FieldDeclDefinition]");
        return buffer.toString();
    }
}
