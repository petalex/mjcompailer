// generated with ast extension for cup
// version 0.8
// 2/1/2019 22:51:10


package rs.ac.bg.etf.pp1.ast;

public class ForStatement extends Statement {

    private ForDesignatorStmt ForDesignatorStmt;
    private ForCond ForCond;
    private ForDesignatorStmt ForDesignatorStmt1;
    private Statement Statement;

    public ForStatement (ForDesignatorStmt ForDesignatorStmt, ForCond ForCond, ForDesignatorStmt ForDesignatorStmt1, Statement Statement) {
        this.ForDesignatorStmt=ForDesignatorStmt;
        if(ForDesignatorStmt!=null) ForDesignatorStmt.setParent(this);
        this.ForCond=ForCond;
        if(ForCond!=null) ForCond.setParent(this);
        this.ForDesignatorStmt1=ForDesignatorStmt1;
        if(ForDesignatorStmt1!=null) ForDesignatorStmt1.setParent(this);
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

    public ForDesignatorStmt getForDesignatorStmt1() {
        return ForDesignatorStmt1;
    }

    public void setForDesignatorStmt1(ForDesignatorStmt ForDesignatorStmt1) {
        this.ForDesignatorStmt1=ForDesignatorStmt1;
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
        if(ForDesignatorStmt1!=null) ForDesignatorStmt1.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForDesignatorStmt!=null) ForDesignatorStmt.traverseTopDown(visitor);
        if(ForCond!=null) ForCond.traverseTopDown(visitor);
        if(ForDesignatorStmt1!=null) ForDesignatorStmt1.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForDesignatorStmt!=null) ForDesignatorStmt.traverseBottomUp(visitor);
        if(ForCond!=null) ForCond.traverseBottomUp(visitor);
        if(ForDesignatorStmt1!=null) ForDesignatorStmt1.traverseBottomUp(visitor);
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

        if(ForDesignatorStmt1!=null)
            buffer.append(ForDesignatorStmt1.toString("  "+tab));
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
