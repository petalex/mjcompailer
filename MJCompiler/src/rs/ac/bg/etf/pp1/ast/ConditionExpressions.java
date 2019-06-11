// generated with ast extension for cup
// version 0.8
// 11/5/2019 2:45:35


package rs.ac.bg.etf.pp1.ast;

public class ConditionExpressions extends CondFact {

    private CondFact CondFact;
    private RelOp RelOp;
    private Expression Expression;

    public ConditionExpressions (CondFact CondFact, RelOp RelOp, Expression Expression) {
        this.CondFact=CondFact;
        if(CondFact!=null) CondFact.setParent(this);
        this.RelOp=RelOp;
        if(RelOp!=null) RelOp.setParent(this);
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
    }

    public CondFact getCondFact() {
        return CondFact;
    }

    public void setCondFact(CondFact CondFact) {
        this.CondFact=CondFact;
    }

    public RelOp getRelOp() {
        return RelOp;
    }

    public void setRelOp(RelOp RelOp) {
        this.RelOp=RelOp;
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
        if(CondFact!=null) CondFact.accept(visitor);
        if(RelOp!=null) RelOp.accept(visitor);
        if(Expression!=null) Expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondFact!=null) CondFact.traverseTopDown(visitor);
        if(RelOp!=null) RelOp.traverseTopDown(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondFact!=null) CondFact.traverseBottomUp(visitor);
        if(RelOp!=null) RelOp.traverseBottomUp(visitor);
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionExpressions(\n");

        if(CondFact!=null)
            buffer.append(CondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RelOp!=null)
            buffer.append(RelOp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionExpressions]");
        return buffer.toString();
    }
}
