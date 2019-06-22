// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:7


package rs.ac.bg.etf.pp1.ast;

public class ActualParameters extends ActualParams {

    private ActualParamExprList ActualParamExprList;

    public ActualParameters (ActualParamExprList ActualParamExprList) {
        this.ActualParamExprList=ActualParamExprList;
        if(ActualParamExprList!=null) ActualParamExprList.setParent(this);
    }

    public ActualParamExprList getActualParamExprList() {
        return ActualParamExprList;
    }

    public void setActualParamExprList(ActualParamExprList ActualParamExprList) {
        this.ActualParamExprList=ActualParamExprList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActualParamExprList!=null) ActualParamExprList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActualParamExprList!=null) ActualParamExprList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActualParamExprList!=null) ActualParamExprList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActualParameters(\n");

        if(ActualParamExprList!=null)
            buffer.append(ActualParamExprList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActualParameters]");
        return buffer.toString();
    }
}
