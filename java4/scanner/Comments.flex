/* 3.7 Comments */
<YYINITIAL> {
  {DocumentationComment} { return sym(Terminals.DOC_COMMENT); }
  {Comment} { }
}

