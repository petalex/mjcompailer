
package rs.ac.bg.etf.pp1;
import java_cup.runtime.Symbol;

%%

%{
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}
%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

"\t"							{ }
"\r\n"							{ }
" "								{ }
"\b"							{ }
"\f"							{ }

"program"						{ return new_symbol(sym.PROGRAM, yytext()); }
"break"							{ return new_symbol(sym.BREAK, yytext()); }
"class"							{ return new_symbol(sym.CLASS, yytext()); }
"interface"						{ return new_symbol(sym.INTERFACE, yytext()); }
"enum"							{ return new_symbol(sym.ENUM, yytext()); }
"else"							{ return new_symbol(sym.ELSE, yytext()); }
"const"							{ return new_symbol(sym.CONST, yytext()); }
"if"							{ return new_symbol(sym.IF, yytext()); }
"new"							{ return new_symbol(sym.NEW, yytext()); }
"print"							{ return new_symbol(sym.PRINT, yytext()); }
"read"							{ return new_symbol(sym.READ, yytext()); }
"return"						{ return new_symbol(sym.RETURN, yytext()); }
"void"							{ return new_symbol(sym.VOID, yytext()); }
"for"							{ return new_symbol(sym.FOR, yytext()); }
"extends"						{ return new_symbol(sym.EXTENDS, yytext()); }
"implements"					{ return new_symbol(sym.IMPLEMENTS, yytext()); }
"continue"						{ return new_symbol(sym.CONTINUE, yytext()); }

("true"|"false")				{ return new_symbol(sym.BOOL, yytext()); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]*	{ return new_symbol(sym.IDENT, yytext()); }
[0-9]+							{ return new_symbol(sym.NUM, new Integer(yytext())); }
"'"[ -~]"'"						{ return new_symbol(sym.CHAR, yytext()); }

"+"								{ return new_symbol(sym.ADD, yytext()); }
"-"								{ return new_symbol(sym.SUB, yytext()); }
"*"								{ return new_symbol(sym.MUL, yytext()); }
"/"								{ return new_symbol(sym.DIV, yytext()); }
"%"								{ return new_symbol(sym.MOD, yytext()); }
"=="							{ return new_symbol(sym.EQU, yytext()); }
"!="							{ return new_symbol(sym.NEQ, yytext()); }
">"								{ return new_symbol(sym.GRT, yytext()); }
">="							{ return new_symbol(sym.GTE, yytext()); }
"<"								{ return new_symbol(sym.LST, yytext()); }
"<="							{ return new_symbol(sym.LTE, yytext()); }
"&&"							{ return new_symbol(sym.AND, yytext()); }
"||"							{ return new_symbol(sym.OR, yytext()); }
"="								{ return new_symbol(sym.ASSIGN, yytext()); }
"++"							{ return new_symbol(sym.INC, yytext()); }
"--"							{ return new_symbol(sym.DEC, yytext()); }
";"								{ return new_symbol(sym.SEMICOLON, yytext()); }
","								{ return new_symbol(sym.COMMA, yytext()); }
"."								{ return new_symbol(sym.PERIOD, yytext());}
"("								{ return new_symbol(sym.LPARENT, yytext()); }
")"								{ return new_symbol(sym.RPARENT, yytext()); }
"["								{ return new_symbol(sym.LSQUARE, yytext()); }
"]"								{ return new_symbol(sym.RSQUARE, yytext()); }
"{"								{ return new_symbol(sym.LCURLY, yytext()); }
"}"								{ return new_symbol(sym.RCURLY, yytext()); }

"//" 							{ yybegin(COMMENT); }
<COMMENT> . 					{ yybegin(COMMENT); }
<COMMENT> "\r\n" 				{ yybegin(YYINITIAL); }

. 								{ System.err.println("[Lexer] Error:  Token \"" + yytext() + "\" could not be indentified (line: " + (yyline + 1) + ")"); }
