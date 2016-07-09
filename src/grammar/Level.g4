grammar Level;
file : row row row row row row row EOF ;
row : value Separator value Separator value Separator value Separator value Separator value Separator value (LineBreak | EOF) ;
value : SimpleValue;
Separator : ';' ;
// line break maybe with preceding line feed
LineBreak : '\r'?'\n' | '\r';
// Only 'w' and 's' are allowed
SimpleValue : 'w' | 's';