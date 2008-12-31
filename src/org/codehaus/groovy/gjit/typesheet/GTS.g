grammar GTS;

options {
  output=AST;
  ASTLabelType=CommonTree;
  backtrack=true;
  k=4;
}

tokens {
  UNIT;
  ASPECT;
  CLASS_MATCHER;
  METHOD_BLOCK;
  METHOD_MATCHER;
  VAR_DECL_LIST;
  VAR_DECL;
  VAR_NAME;
  TYPE_NAME;
  
  ADVICE;
  PCD_BLOCK;
  PCD;
}

@parser::header {package org.codehaus.groovy.gjit.typesheet;}
@lexer::header  {package org.codehaus.groovy.gjit.typesheet;}

compilationUnit
  : aspect+
  
    -> ^(UNIT aspect+)
  ;
  
aspect
	:	pcd '{' member+ '}'
    
		-> ^(ASPECT pcd member+)
  ;
  
member	:
//	:	fieldIntervene
//	|	methodMatcher
	;
	
pcd	:
		'class' '(' pat ')'
	|	
	;	

pat
	:	 Identifier
	;  
  
Identifier
  : 'A'..'Z' | 'a'..'z' 
  ;

WS
  :   (' '| '\t'| '\r' | '\n' | '\u000C') {$channel=HIDDEN;}
  ;
