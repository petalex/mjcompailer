// generated with ast extension for cup
// version 0.8
// 11/5/2019 2:45:35


package rs.ac.bg.etf.pp1.ast;

public class Expressions extends ExpressionList {

    private ExpressionList ExpressionList;
    private Expression Expression;

    public Expressions (ExpressionList ExpressionList, Expression Expression) {
        this.ExpressionList=ExpressionList;
        if(ExpressionList!=null) ExpressionList.setParent(this);
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
    }

    public ExpressionList getExpressionList() {
        return ExpressionList;
    }

    public void setExpressionList(ExpressionList ExpressionList) {
        this.ExpressionList=ExpressionList;
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
        if(ExpressionList!=null) ExpressionList.accept(visitor);
        if(Expression!=null) Expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExpressionList!=null) ExpressionList.traverseTopDown(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExpressionList!=null) ExpressionList.traverseBottomUp(visitor);
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Expressions(\n");

        if(ExpressionList!=null)
            buffer.append(ExpressionList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Expressions]");
        return buffer.toString();
    }
}
