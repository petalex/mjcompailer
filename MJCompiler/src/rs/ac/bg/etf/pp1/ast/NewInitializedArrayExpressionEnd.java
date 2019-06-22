// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:7


package rs.ac.bg.etf.pp1.ast;

public class NewInitializedArrayExpressionEnd extends NewInitArrExprEnd {

    public NewInitializedArrayExpressionEnd () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NewInitializedArrayExpressionEnd(\n");

        buffer.append(tab);
        buffer.append(") [NewInitializedArrayExpressionEnd]");
        return buffer.toString();
    }
}
