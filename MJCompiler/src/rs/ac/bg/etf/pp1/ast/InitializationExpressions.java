// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:7


package rs.ac.bg.etf.pp1.ast;

public class InitializationExpressions extends InitExprList {

    private InitExprList InitExprList;
    private Expression Expression;

    public InitializationExpressions (InitExprList InitExprList, Expression Expression) {
        this.InitExprList=InitExprList;
        if(InitExprList!=null) InitExprList.setParent(this);
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
    }

    public InitExprList getInitExprList() {
        return InitExprList;
    }

    public void setInitExprList(InitExprList InitExprList) {
        this.InitExprList=InitExprList;
    }

    public Expression getExpression() {
        return Expression;
    }

    public void setExpression(Expression Expression) {
        this.Expression=Expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(InitExprList!=null) InitExprList.accept(visitor);
        if(Expression!=null) Expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InitExprList!=null) InitExprList.traverseTopDown(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InitExprList!=null) InitExprList.traverseBottomUp(visitor);
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InitializationExpressions(\n");

        if(InitExprList!=null)
            buffer.append(InitExprList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InitializationExpressions]");
        return buffer.toString();
    }
}
