// hack to detect the SUB character as the last input character
\u001a                           { if(sub_line == 0 && sub_column == 0) {
                                     sub_line = yyline; sub_column = yycolumn;
                                   } 
                                 }
// fall through errors
.|\n                             { throw new LexicalError("Illegal character \""+str()+ "\" at line "+yyline+", column "+yycolumn); }
<<EOF>>                          { // detect position of first SUB character
                                   if(!(sub_line == 0 && sub_column == 0) && (sub_line != yyline || sub_column != yycolumn-1))
                                     throw new LexicalError("Error");
                                   return sym(Terminals.EOF);
                                 }
