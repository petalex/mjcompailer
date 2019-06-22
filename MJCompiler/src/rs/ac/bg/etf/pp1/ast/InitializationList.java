// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:7


package rs.ac.bg.etf.pp1.ast;

public class InitializationList extends InitList {

    private InitExprList InitExprList;

    public InitializationList (InitExprList InitExprList) {
        this.InitExprList=InitExprList;
        if(InitExprList!=null) InitExprList.setParent(this);
    }

    public InitExprList getInitExprList() {
        return InitExprList;
    }

    public void setInitExprList(InitExprList InitExprList) {
        this.InitExprList=InitExprList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(InitExprList!=null) InitExprList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InitExprList!=null) InitExprList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InitExprList!=null) InitExprList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InitializationList(\n");

        if(InitExprList!=null)
            buffer.append(InitExprList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InitializationList]");
        return buffer.toString();
    }
}
