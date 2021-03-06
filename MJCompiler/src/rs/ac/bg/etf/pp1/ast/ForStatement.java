// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class ForStatement extends Statement {

    private ForDesignatorStmt ForDesignatorStmt;
    private ForCond ForCond;
    private LastForDesignatorStmt LastForDesignatorStmt;
    private ForDeclEnd ForDeclEnd;
    private Statement Statement;

    public ForStatement (ForDesignatorStmt ForDesignatorStmt, ForCond ForCond, LastForDesignatorStmt LastForDesignatorStmt, ForDeclEnd ForDeclEnd, Statement Statement) {
        this.ForDesignatorStmt=ForDesignatorStmt;
        if(ForDesignatorStmt!=null) ForDesignatorStmt.setParent(this);
        this.ForCond=ForCond;
        if(ForCond!=null) ForCond.setParent(this);
        this.LastForDesignatorStmt=LastForDesignatorStmt;
        if(LastForDesignatorStmt!=null) LastForDesignatorStmt.setParent(this);
        this.ForDeclEnd=ForDeclEnd;
        if(ForDeclEnd!=null) ForDeclEnd.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForDesignatorStmt getForDesignatorStmt() {
        return ForDesignatorStmt;
    }

    public void setForDesignatorStmt(ForDesignatorStmt ForDesignatorStmt) {
        this.ForDesignatorStmt=ForDesignatorStmt;
    }

    public ForCond getForCond() {
        return ForCond;
    }

    public void setForCond(ForCond ForCond) {
        this.ForCond=ForCond;
    }

    public LastForDesignatorStmt getLastForDesignatorStmt() {
        return LastForDesignatorStmt;
    }

    public void setLastForDesignatorStmt(LastForDesignatorStmt LastForDesignatorStmt) {
        this.LastForDesignatorStmt=LastForDesignatorStmt;
    }

    public ForDeclEnd getForDeclEnd() {
        return ForDeclEnd;
    }

    public void setForDeclEnd(ForDeclEnd ForDeclEnd) {
        this.ForDeclEnd=ForDeclEnd;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForDesignatorStmt!=null) ForDesignatorStmt.accept(visitor);
        if(ForCond!=null) ForCond.accept(visitor);
        if(LastForDesignatorStmt!=null) LastForDesignatorStmt.accept(visitor);
        if(ForDeclEnd!=null) ForDeclEnd.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForDesignatorStmt!=null) ForDesignatorStmt.traverseTopDown(visitor);
        if(ForCond!=null) ForCond.traverseTopDown(visitor);
        if(LastForDesignatorStmt!=null) LastForDesignatorStmt.traverseTopDown(visitor);
        if(ForDeclEnd!=null) ForDeclEnd.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForDesignatorStmt!=null) ForDesignatorStmt.traverseBottomUp(visitor);
        if(ForCond!=null) ForCond.traverseBottomUp(visitor);
        if(LastForDesignatorStmt!=null) LastForDesignatorStmt.traverseBottomUp(visitor);
        if(ForDeclEnd!=null) ForDeclEnd.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForStatement(\n");

        if(ForDesignatorStmt!=null)
            buffer.append(ForDesignatorStmt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCond!=null)
            buffer.append(ForCond.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(LastForDesignatorStmt!=null)
            buffer.append(LastForDesignatorStmt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForDeclEnd!=null)
            buffer.append(ForDeclEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForStatement]");
        return buffer.toString();
    }
}
