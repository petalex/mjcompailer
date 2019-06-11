// generated with ast extension for cup
// version 0.8
// 11/5/2019 2:45:35


package rs.ac.bg.etf.pp1.ast;

public class LastFormalParameter extends FormParamList {

    private LastFormParam LastFormParam;

    public LastFormalParameter (LastFormParam LastFormParam) {
        this.LastFormParam=LastFormParam;
        if(LastFormParam!=null) LastFormParam.setParent(this);
    }

    public LastFormParam getLastFormParam() {
        return LastFormParam;
    }

    public void setLastFormParam(LastFormParam LastFormParam) {
        this.LastFormParam=LastFormParam;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LastFormParam!=null) LastFormParam.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LastFormParam!=null) LastFormParam.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LastFormParam!=null) LastFormParam.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LastFormalParameter(\n");

        if(LastFormParam!=null)
            buffer.append(LastFormParam.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LastFormalParameter]");
        return buffer.toString();
    }
}
