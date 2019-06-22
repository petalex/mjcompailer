// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:7


package rs.ac.bg.etf.pp1.ast;

public class NewInitializedArrayFactor extends Factor {

    private Type Type;
    private Expression Expression;
    private NewInitArrExprEnd NewInitArrExprEnd;
    private InitList InitList;

    public NewInitializedArrayFactor (Type Type, Expression Expression, NewInitArrExprEnd NewInitArrExprEnd, InitList InitList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
        this.NewInitArrExprEnd=NewInitArrExprEnd;
        if(NewInitArrExprEnd!=null) NewInitArrExprEnd.setParent(this);
        this.InitList=InitList;
        if(InitList!=null) InitList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public Expression getExpression() {
        return Expression;
    }

    public void setExpression(Expression Expression) {
        this.Expression=Expression;
    }

    public NewInitArrExprEnd getNewInitArrExprEnd() {
        return NewInitArrExprEnd;
    }

    public void setNewInitArrExprEnd(NewInitArrExprEnd NewInitArrExprEnd) {
        this.NewInitArrExprEnd=NewInitArrExprEnd;
    }

    public InitList getInitList() {
        return InitList;
    }

    public void setInitList(InitList InitList) {
        this.InitList=InitList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(Expression!=null) Expression.accept(visitor);
        if(NewInitArrExprEnd!=null) NewInitArrExprEnd.accept(visitor);
        if(InitList!=null) InitList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
        if(NewInitArrExprEnd!=null) NewInitArrExprEnd.traverseTopDown(visitor);
        if(InitList!=null) InitList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        if(NewInitArrExprEnd!=null) NewInitArrExprEnd.traverseBottomUp(visitor);
        if(InitList!=null) InitList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NewInitializedArrayFactor(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NewInitArrExprEnd!=null)
            buffer.append(NewInitArrExprEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InitList!=null)
            buffer.append(InitList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NewInitializedArrayFactor]");
        return buffer.toString();
    }
}
