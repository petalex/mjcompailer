// generated with ast extension for cup
// version 0.8
// 2/1/2019 22:51:10


package rs.ac.bg.etf.pp1.ast;

public class ActualParameters extends ActualParams {

    private ExpressionList ExpressionList;

    public ActualParameters (ExpressionList ExpressionList) {
        this.ExpressionList=ExpressionList;
        if(ExpressionList!=null) ExpressionList.setParent(this);
    }

    public ExpressionList getExpressionList() {
        return ExpressionList;
    }

    public void setExpressionList(ExpressionList ExpressionList) {
        this.ExpressionList=ExpressionList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExpressionList!=null) ExpressionList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExpressionList!=null) ExpressionList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExpressionList!=null) ExpressionList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActualParameters(\n");

        if(ExpressionList!=null)
            buffer.append(ExpressionList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActualParameters]");
        return buffer.toString();
    }
}
