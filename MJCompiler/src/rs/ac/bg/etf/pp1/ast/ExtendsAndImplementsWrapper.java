// generated with ast extension for cup
// version 0.8
// 11/5/2019 2:45:34


package rs.ac.bg.etf.pp1.ast;

public class ExtendsAndImplementsWrapper extends ExtndsImplmnts {

    private ExtendsImplements ExtendsImplements;
    private TypeList TypeList;

    public ExtendsAndImplementsWrapper (ExtendsImplements ExtendsImplements, TypeList TypeList) {
        this.ExtendsImplements=ExtendsImplements;
        if(ExtendsImplements!=null) ExtendsImplements.setParent(this);
        this.TypeList=TypeList;
        if(TypeList!=null) TypeList.setParent(this);
    }

    public ExtendsImplements getExtendsImplements() {
        return ExtendsImplements;
    }

    public void setExtendsImplements(ExtendsImplements ExtendsImplements) {
        this.ExtendsImplements=ExtendsImplements;
    }

    public TypeList getTypeList() {
        return TypeList;
    }

    public void setTypeList(TypeList TypeList) {
        this.TypeList=TypeList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExtendsImplements!=null) ExtendsImplements.accept(visitor);
        if(TypeList!=null) TypeList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExtendsImplements!=null) ExtendsImplements.traverseTopDown(visitor);
        if(TypeList!=null) TypeList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExtendsImplements!=null) ExtendsImplements.traverseBottomUp(visitor);
        if(TypeList!=null) TypeList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExtendsAndImplementsWrapper(\n");

        if(ExtendsImplements!=null)
            buffer.append(ExtendsImplements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TypeList!=null)
            buffer.append(TypeList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExtendsAndImplementsWrapper]");
        return buffer.toString();
    }
}
