// generated with ast extension for cup
// version 0.8
// 2/1/2019 22:51:10


package rs.ac.bg.etf.pp1.ast;

public class PrintNumConsts extends PrintNumConstList {

    private PrintNumConstList PrintNumConstList;
    private Integer N2;

    public PrintNumConsts (PrintNumConstList PrintNumConstList, Integer N2) {
        this.PrintNumConstList=PrintNumConstList;
        if(PrintNumConstList!=null) PrintNumConstList.setParent(this);
        this.N2=N2;
    }

    public PrintNumConstList getPrintNumConstList() {
        return PrintNumConstList;
    }

    public void setPrintNumConstList(PrintNumConstList PrintNumConstList) {
        this.PrintNumConstList=PrintNumConstList;
    }

    public Integer getN2() {
        return N2;
    }

    public void setN2(Integer N2) {
        this.N2=N2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(PrintNumConstList!=null) PrintNumConstList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(PrintNumConstList!=null) PrintNumConstList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(PrintNumConstList!=null) PrintNumConstList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintNumConsts(\n");

        if(PrintNumConstList!=null)
            buffer.append(PrintNumConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+N2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PrintNumConsts]");
        return buffer.toString();
    }
}
