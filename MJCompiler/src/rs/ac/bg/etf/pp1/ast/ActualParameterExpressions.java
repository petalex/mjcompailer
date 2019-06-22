// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:7


package rs.ac.bg.etf.pp1.ast;

public class ActualParameterExpressions extends ActualParamExprList {

    private ActualParamExprList ActualParamExprList;
    private Expression Expression;

    public ActualParameterExpressions (ActualParamExprList ActualParamExprList, Expression Expression) {
        this.ActualParamExprList=ActualParamExprList;
        if(ActualParamExprList!=null) ActualParamExprList.setParent(this);
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
    }

    public ActualParamExprList getActualParamExprList() {
        return ActualParamExprList;
    }

    public void setActualParamExprList(ActualParamExprList ActualParamExprList) {
        this.ActualParamExprList=ActualParamExprList;
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
        if(ActualParamExprList!=null) ActualParamExprList.accept(visitor);
        if(Expression!=null) Expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActualParamExprList!=null) ActualParamExprList.traverseTopDown(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActualParamExprList!=null) ActualParamExprList.traverseBottomUp(visitor);
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActualParameterExpressions(\n");

        if(ActualParamExprList!=null)
            buffer.append(ActualParamExprList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActualParameterExpressions]");
        return buffer.toString();
    }
}
