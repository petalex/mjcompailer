// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:9:6


package rs.ac.bg.etf.pp1.ast;

public class IfElseStatement extends Statement {

    private IfCond IfCond;
    private Statement Statement;
    private IfStmtEnd IfStmtEnd;
    private Statement Statement1;
    private ElseStmtEnd ElseStmtEnd;

    public IfElseStatement (IfCond IfCond, Statement Statement, IfStmtEnd IfStmtEnd, Statement Statement1, ElseStmtEnd ElseStmtEnd) {
        this.IfCond=IfCond;
        if(IfCond!=null) IfCond.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.IfStmtEnd=IfStmtEnd;
        if(IfStmtEnd!=null) IfStmtEnd.setParent(this);
        this.Statement1=Statement1;
        if(Statement1!=null) Statement1.setParent(this);
        this.ElseStmtEnd=ElseStmtEnd;
        if(ElseStmtEnd!=null) ElseStmtEnd.setParent(this);
    }

    public IfCond getIfCond() {
        return IfCond;
    }

    public void setIfCond(IfCond IfCond) {
        this.IfCond=IfCond;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public IfStmtEnd getIfStmtEnd() {
        return IfStmtEnd;
    }

    public void setIfStmtEnd(IfStmtEnd IfStmtEnd) {
        this.IfStmtEnd=IfStmtEnd;
    }

    public Statement getStatement1() {
        return Statement1;
    }

    public void setStatement1(Statement Statement1) {
        this.Statement1=Statement1;
    }

    public ElseStmtEnd getElseStmtEnd() {
        return ElseStmtEnd;
    }

    public void setElseStmtEnd(ElseStmtEnd ElseStmtEnd) {
        this.ElseStmtEnd=ElseStmtEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfCond!=null) IfCond.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(IfStmtEnd!=null) IfStmtEnd.accept(visitor);
        if(Statement1!=null) Statement1.accept(visitor);
        if(ElseStmtEnd!=null) ElseStmtEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfCond!=null) IfCond.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(IfStmtEnd!=null) IfStmtEnd.traverseTopDown(visitor);
        if(Statement1!=null) Statement1.traverseTopDown(visitor);
        if(ElseStmtEnd!=null) ElseStmtEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfCond!=null) IfCond.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(IfStmtEnd!=null) IfStmtEnd.traverseBottomUp(visitor);
        if(Statement1!=null) Statement1.traverseBottomUp(visitor);
        if(ElseStmtEnd!=null) ElseStmtEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfElseStatement(\n");

        if(IfCond!=null)
            buffer.append(IfCond.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IfStmtEnd!=null)
            buffer.append(IfStmtEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement1!=null)
            buffer.append(Statement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ElseStmtEnd!=null)
            buffer.append(ElseStmtEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfElseStatement]");
        return buffer.toString();
    }
}
