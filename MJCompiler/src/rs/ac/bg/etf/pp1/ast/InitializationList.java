// generated with ast extension for cup
// version 0.8
// 11/5/2019 2:45:35


package rs.ac.bg.etf.pp1.ast;

public class InitializationList extends InitList {

    private ExpressionList ExpressionList;

    public InitializationList (ExpressionList ExpressionList) {
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
        buffer.append("InitializationList(\n");

        if(ExpressionList!=null)
            buffer.append(ExpressionList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InitializationList]");
        return buffer.toString();
    }
}
