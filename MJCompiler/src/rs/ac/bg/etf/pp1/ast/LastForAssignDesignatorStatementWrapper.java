// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:7


package rs.ac.bg.etf.pp1.ast;

public class LastForAssignDesignatorStatementWrapper extends LastForDesignatorStmt {

    private LastForAssignDesignatorStmt LastForAssignDesignatorStmt;

    public LastForAssignDesignatorStatementWrapper (LastForAssignDesignatorStmt LastForAssignDesignatorStmt) {
        this.LastForAssignDesignatorStmt=LastForAssignDesignatorStmt;
        if(LastForAssignDesignatorStmt!=null) LastForAssignDesignatorStmt.setParent(this);
    }

    public LastForAssignDesignatorStmt getLastForAssignDesignatorStmt() {
        return LastForAssignDesignatorStmt;
    }

    public void setLastForAssignDesignatorStmt(LastForAssignDesignatorStmt LastForAssignDesignatorStmt) {
        this.LastForAssignDesignatorStmt=LastForAssignDesignatorStmt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LastForAssignDesignatorStmt!=null) LastForAssignDesignatorStmt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LastForAssignDesignatorStmt!=null) LastForAssignDesignatorStmt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LastForAssignDesignatorStmt!=null) LastForAssignDesignatorStmt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LastForAssignDesignatorStatementWrapper(\n");

        if(LastForAssignDesignatorStmt!=null)
            buffer.append(LastForAssignDesignatorStmt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LastForAssignDesignatorStatementWrapper]");
        return buffer.toString();
    }
}
