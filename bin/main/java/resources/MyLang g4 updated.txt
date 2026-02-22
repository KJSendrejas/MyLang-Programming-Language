grammar MyLang;

program : stmt* EOF ;

stmt
    : varDecl ';'
    | assign ';'
    | ifStmt
    | whileStmt
    | funcDecl
    | returnStmt ';'
    | printStmt ';'
    | expr ';'
    ;

varDecl : 'var' ID (':' type)? ('=' expr)? ;
assign  : ID '=' expr ;
printStmt : 'print' '(' expr ')' ;
ifStmt  : 'if' '(' expr ')' block ('else' block)? ;
whileStmt : 'while' '(' expr ')' block ;

block : '{' stmt* '}' ;

funcDecl
    : 'func' ID '(' params? ')' (':' type)? block
    ;
params : param (',' param)* ;
param  : ID ':' type ;
returnStmt : 'return' expr? ;

expr
    : left=expr op=('*'|'/') right=expr              			
    | left=expr op=('+'|'-') right=expr              			
    | left=expr op=('=='|'!='|'<'|'<='|'>'|'>=') right=expr   	
    | left=expr op=('&&'|'||') right=expr            			
    | op='-' expr                                    			
    | ID '(' args? ')'                               			
    | '(' expr ')'                                   			
    | literal                                        			
    | ID                                             			
    ;

args : expr (',' expr)* ;

literal : INT | FLOAT | BOOL ;

type : 'int' | 'float' | 'bool' | 'void';

ID    : [a-zA-Z_][a-zA-Z0-9_]* ;
INT   : [0-9]+ ;
FLOAT : [0-9]+'.'[0-9]+ ;
BOOL  : 'true'|'false' ;
WS    : [ \t\r\n]+ -> skip ;
COMMENT : '//' ~[\r\n]* -> skip ;