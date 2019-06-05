// generated with ast extension for cup
// version 0.8
// 5/5/2019 2:11:39


package rs.ac.bg.etf.pp1.ast;

public class PrintStatement extends Statement {

    private Expression Expression;
    private PrintNumConstList PrintNumConstList;

    public PrintStatement (Expression Expression, PrintNumConstList PrintNumConstList) {
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
        this.PrintNumConstList=PrintNumConstList;
        if(PrintNumConstList!=null) PrintNumConstList.setParent(this);
    }

    public Expression getExpression() {
        return Expression;
    }

    public void setExpression(Expression Expression) {
        this.Expression=Expression;
    }

    public PrintNumConstList getPrintNumConstList() {
        return PrintNumConstList;
    }

    public void setPrintNumConstList(PrintNumConstList PrintNumConstList) {
        this.PrintNumConstList=PrintNumConstList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expression!=null) Expression.accept(visitor);
        if(PrintNumConstList!=null) PrintNumConstList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
        if(PrintNumConstList!=null) PrintNumConstList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        if(PrintNumConstList!=null) PrintNumConstList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintStatement(\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(PrintNumConstList!=null)
            buffer.append(PrintNumConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PrintStatement]");
        return buffer.toString();
    }
}
