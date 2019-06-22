// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class FormalParameters extends FormParamList {

    private FormParam FormParam;
    private FormParamList FormParamList;

    public FormalParameters (FormParam FormParam, FormParamList FormParamList) {
        this.FormParam=FormParam;
        if(FormParam!=null) FormParam.setParent(this);
        this.FormParamList=FormParamList;
        if(FormParamList!=null) FormParamList.setParent(this);
    }

    public FormParam getFormParam() {
        return FormParam;
    }

    public void setFormParam(FormParam FormParam) {
        this.FormParam=FormParam;
    }

    public FormParamList getFormParamList() {
        return FormParamList;
    }

    public void setFormParamList(FormParamList FormParamList) {
        this.FormParamList=FormParamList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParam!=null) FormParam.accept(visitor);
        if(FormParamList!=null) FormParamList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParam!=null) FormParam.traverseTopDown(visitor);
        if(FormParamList!=null) FormParamList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParam!=null) FormParam.traverseBottomUp(visitor);
        if(FormParamList!=null) FormParamList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormalParameters(\n");

        if(FormParam!=null)
            buffer.append(FormParam.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParamList!=null)
            buffer.append(FormParamList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormalParameters]");
        return buffer.toString();
    }
}
