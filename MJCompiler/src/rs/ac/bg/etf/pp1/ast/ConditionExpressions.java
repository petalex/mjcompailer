// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:7


package rs.ac.bg.etf.pp1.ast;

public class ConditionExpressions extends CondFact {

    private Expression Expression;
    private RelOp RelOp;
    private Expression Expression1;

    public ConditionExpressions (Expression Expression, RelOp RelOp, Expression Expression1) {
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
        this.RelOp=RelOp;
        if(RelOp!=null) RelOp.setParent(this);
        this.Expression1=Expression1;
        if(Expression1!=null) Expression1.setParent(this);
    }

    public Expression getExpression() {
        return Expression;
    }

    public void setExpression(Expression Expression) {
        this.Expression=Expression;
    }

    public RelOp getRelOp() {
        return RelOp;
    }

    public void setRelOp(RelOp RelOp) {
        this.RelOp=RelOp;
    }

    public Expression getExpression1() {
        return Expression1;
    }

    public void setExpression1(Expression Expression1) {
        this.Expression1=Expression1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expression!=null) Expression.accept(visitor);
        if(RelOp!=null) RelOp.accept(visitor);
        if(Expression1!=null) Expression1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
        if(RelOp!=null) RelOp.traverseTopDown(visitor);
        if(Expression1!=null) Expression1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        if(RelOp!=null) RelOp.traverseBottomUp(visitor);
        if(Expression1!=null) Expression1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionExpressions(\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RelOp!=null)
            buffer.append(RelOp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression1!=null)
            buffer.append(Expression1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionExpressions]");
        return buffer.toString();
    }
}
