
package rs.ac.bg.etf.pp1;
import java_cup.runtime.Symbol;

%%

%{
	private Symbol createSymbol(int type) {
		return new Symbol(type, yyline + 1, yycolumn);
	}
	
	private Symbol createSymbol(int type, Object value) {
		return new Symbol(type, yyline + 1, yycolumn, value);
	}
%}

%class MJLexer
%cup
%line
%column

%xstate YYCOMMENT

%eofval{
	return createSymbol(sym.EOF);
%eofval}

%%

"\t"							{ }
"\r\n"							{ }
" "								{ }
"\b"							{ }
"\f"							{ }

"program"						{ return createSymbol(sym.PROGRAM, yytext()); }
"break"							{ return createSymbol(sym.BREAK, yytext()); }
"class"							{ return createSymbol(sym.CLASS, yytext()); }
"interface"						{ return createSymbol(sym.INTERFACE, yytext()); }
"enum"							{ return createSymbol(sym.ENUM, yytext()); }
"else"							{ return createSymbol(sym.ELSE, yytext()); }
"const"							{ return createSymbol(sym.CONST, yytext()); }
"if"							{ return createSymbol(sym.IF, yytext()); }
"new"							{ return createSymbol(sym.NEW, yytext()); }
"print"							{ return createSymbol(sym.PRINT, yytext()); }
"read"							{ return createSymbol(sym.READ, yytext()); }
"return"						{ return createSymbol(sym.RETURN, yytext()); }
"void"							{ return createSymbol(sym.VOID, yytext()); }
"for"							{ return createSymbol(sym.FOR, yytext()); }
"extends"						{ return createSymbol(sym.EXTENDS, yytext()); }
"implements"					{ return createSymbol(sym.IMPLEMENTS, yytext()); }
"continue"						{ return createSymbol(sym.CONTINUE, yytext()); }
"public"						{ return createSymbol(sym.PUBLIC, yytext()); }
"protected"						{ return createSymbol(sym.PROTECTED, yytext()); }
"private"						{ return createSymbol(sym.PRIVATE, yytext()); }

("true"|"false")				{ return createSymbol(sym.BOOL, Boolean.valueOf(yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]*	{ return createSymbol(sym.IDENT, yytext()); }
[0-9]+							{ return createSymbol(sym.NUM, new Integer(yytext())); }
"'"[ -~]"'"						{ return createSymbol(sym.CHAR, yytext().charAt(1)); }

"+"								{ return createSymbol(sym.ADD, yytext()); }
"-"								{ return createSymbol(sym.SUB, yytext()); }
"*"								{ return createSymbol(sym.MUL, yytext()); }
"/"								{ return createSymbol(sym.DIV, yytext()); }
"%"								{ return createSymbol(sym.MOD, yytext()); }
"=="							{ return createSymbol(sym.EQU, yytext()); }
"!="							{ return createSymbol(sym.NEQ, yytext()); }
">"								{ return createSymbol(sym.GRT, yytext()); }
">="							{ return createSymbol(sym.GTE, yytext()); }
"<"								{ return createSymbol(sym.LST, yytext()); }
"<="							{ return createSymbol(sym.LTE, yytext()); }
"&&"							{ return createSymbol(sym.AND, yytext()); }
"||"							{ return createSymbol(sym.OR, yytext()); }
"="								{ return createSymbol(sym.ASSIGN, yytext()); }
"++"							{ return createSymbol(sym.INC, yytext()); }
"--"							{ return createSymbol(sym.DEC, yytext()); }
";"								{ return createSymbol(sym.SEMICOLON, yytext()); }
","								{ return createSymbol(sym.COMMA, yytext()); }
"."								{ return createSymbol(sym.PERIOD, yytext());}
"("								{ return createSymbol(sym.LPARENT, yytext()); }
")"								{ return createSymbol(sym.RPARENT, yytext()); }
"["								{ return createSymbol(sym.LSQUARE, yytext()); }
"]"								{ return createSymbol(sym.RSQUARE, yytext()); }
"{"								{ return createSymbol(sym.LCURLY, yytext()); }
"}"								{ return createSymbol(sym.RCURLY, yytext()); }

"//" 							{ yybegin(YYCOMMENT); }
<YYCOMMENT> "\r\n" 				{ yybegin(YYINITIAL); }
<YYCOMMENT> . 					{ }

. 								{ System.err.println("[Lexer] Error:  Token \"" + yytext() + "\" could not be identified (Line: " + (yyline + 1) + ", Column: " + yycolumn + ")"); }
