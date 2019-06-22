// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:7


package rs.ac.bg.etf.pp1.ast;

public class AssignDesignatorStatementWrapper extends DesignatorStmt {

    private AssignDesignatorStmt AssignDesignatorStmt;

    public AssignDesignatorStatementWrapper (AssignDesignatorStmt AssignDesignatorStmt) {
        this.AssignDesignatorStmt=AssignDesignatorStmt;
        if(AssignDesignatorStmt!=null) AssignDesignatorStmt.setParent(this);
    }

    public AssignDesignatorStmt getAssignDesignatorStmt() {
        return AssignDesignatorStmt;
    }

    public void setAssignDesignatorStmt(AssignDesignatorStmt AssignDesignatorStmt) {
        this.AssignDesignatorStmt=AssignDesignatorStmt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AssignDesignatorStmt!=null) AssignDesignatorStmt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AssignDesignatorStmt!=null) AssignDesignatorStmt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AssignDesignatorStmt!=null) AssignDesignatorStmt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AssignDesignatorStatementWrapper(\n");

        if(AssignDesignatorStmt!=null)
            buffer.append(AssignDesignatorStmt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AssignDesignatorStatementWrapper]");
        return buffer.toString();
    }
}
