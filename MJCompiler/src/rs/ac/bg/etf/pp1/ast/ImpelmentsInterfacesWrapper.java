// generated with ast extension for cup
// version 0.8
// 11/5/2019 2:45:34


package rs.ac.bg.etf.pp1.ast;

public class ImpelmentsInterfacesWrapper extends ExtndsImplmnts {

    private Implements Implements;

    public ImpelmentsInterfacesWrapper (Implements Implements) {
        this.Implements=Implements;
        if(Implements!=null) Implements.setParent(this);
    }

    public Implements getImplements() {
        return Implements;
    }

    public void setImplements(Implements Implements) {
        this.Implements=Implements;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Implements!=null) Implements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Implements!=null) Implements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Implements!=null) Implements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ImpelmentsInterfacesWrapper(\n");

        if(Implements!=null)
            buffer.append(Implements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ImpelmentsInterfacesWrapper]");
        return buffer.toString();
    }
}
