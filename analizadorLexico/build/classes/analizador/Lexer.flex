package analizador;
import static analizador.Token.*;
import java_cup.runtime.*;
import java.io.Reader;
%%
%class Lexer
%type Token
%line
%column
%cup 
%{
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
ALL     =   .*
ESP     =   [ \t\r]+
NWL     =   [\n]+
IDF     =   [a-zA-Z][_0-9a-zA-Z]*
INT     =   [0-9]+
HEX     =   ("0x"|"0X")[0-9a-fA-F]+
DBL     =   [0-9]+"."[0-9]*
EXP     =   (e|E)("+"|"-")?
BOOL    =  true|false
TC      =    "/*" [^*] ~"*/" | "/*" "*"+ "/"
IC      =    [^\r\n]
LT      =    \r|\n|\r\n
LC      =    "//" {IC}* {LT}?

Am = [a]
Bm = [b]
Cm = [c]
Dm = [d]
Em = [e]
Fm = [f]
Gm = [g]
Hm = [h]
Im = [i]
LMm = [L]
MMm = [M]
GMm = [G]
SMm = [S]
BMm = [B]
IMm = [I]
Km = [k]
Lm = [l]
Mm = [m]
Nm = [n]
Om = [o]
Pm = [p]
PMm = [P]
Rm = [r]
RMm = [R]
Sm = [s]
Tm = [t]
Um = [u]
Vm = [v]
Wm = [w]
Xm = [x]
Ym = [y]
NMm = [N]
AMm = [M]
ALL = [ \t\n\r]
PC = [;]
Suma = [+] 
Resta = [-]
Punto = [.] 

Multi = [*] 
Divi = [/]
Mod = [%]
Mod = [%]
Myq = [<]
Meq = [>] 
Igual = [=]  
Y = [&] 
O = [|] 
Not = [!] 
Coma = [,] 

CorA = [\[] 
CorC = [\]] 
ParA = [(]
ParC = [)] 
LlaA = [{]
LlaC = [}] 

C = [\"]

%{
public String lexeme;
%}
%%

<YYINITIAL>{
	{NWL}                       					{	}
	{Vm}{Om}{Im}{Dm}                            	{return symbol(sym.vip_void);}
	{Im}{Nm}{Tm}                                	{return symbol(sym.vip_int);}
	{Dm}{Om}{Um}{Bm}{Lm}{Em}                    	{return symbol(sym.vip_double);}
	{Bm}{Om}{Om}{Lm}                            	{return symbol(sym.vip_bool);}
	{Sm}{Tm}{Rm}{Im}{Nm}{Gm}                    	{return symbol(sym.vip_string);}
	{Cm}{Lm}{Am}{Sm}{Sm}                        	{return symbol(sym.vip_class);}
	{Im}{Nm}{Tm}{Em}{Rm}{Fm}{Am}{Cm}{Em}        	{return symbol(sym.vip_interface);}
	{Nm}{Um}{Lm}{Lm}                            	{return symbol(sym.vip_null);}
	{Tm}{Hm}{Im}{Sm}                            	{return symbol(sym.vip_this);}
	{Em}{Xm}{Tm}{Em}{Nm}{Dm}{Sm}                	{return symbol(sym.vip_extends);}
	{Im}{Mm}{Pm}{Lm}{Em}{Mm}{Em}{Nm}{Tm}{Sm}    	{return symbol(sym.vip_implements);}
	{Fm}{Om}{Rm}                                	{return symbol(sym.vip_for);}
	{Wm}{Hm}{Im}{Lm}{Em}                        	{return symbol(sym.vip_while);}
	{Im}{Fm}                                    	{return symbol(sym.vip_if);}
	{Em}{Lm}{Sm}{Em}                            	{return symbol(sym.vip_else);}
	{Rm}{Em}{Tm}{Um}{Rm}{Nm}                    	{return symbol(sym.vip_return);}
	{Bm}{Rm}{Em}{Am}{Km}                        	{return symbol(sym.vip_break);}
	{NMm}{Em}{Wm}                               	{return symbol(sym.vip_New);}
	{NMm}{Em}{Wm}{AMm}{Rm}{Rm}{Am}{Ym}          	{return symbol(sym.vip_NewArray);}
	{PMm}{Rm}{Im}{Nm}{Tm}                       	{return symbol(sym.vip_Print);}
	{RMm}{Em}{Am}{Dm}{IMm}{Nm}{Tm}{Em}{Gm}{Em}{Rm}	{return symbol(sym.vip_ReadInteger);}
	{RMm}{Em}{Am}{Dm}{LMm}{Im}{Nm}{Em}          	{return symbol(sym.vip_ReadLine);}
	{MMm}{Am}{Lm}{Lm}{Om}{Cm}                   	{return symbol(sym.vip_Malloc);}
	{GMm}{Em}{Tm}{BMm}{Ym}{Tm}{Em}              	{return symbol(sym.vip_GetByte);}
	{SMm}{Em}{Tm}{BMm}{Ym}{Tm}{Em}              	{return symbol(sym.vip_SetByte);}
	{BOOL}                                      	{return symbol(sym.val_bool, new Boolean(yytext()));}
	{IDF}                                       	{return symbol(sym.identifier);}
	{ESP}                                      		{	}
	{INT}                                       	{return symbol(sym.num_int, new Double(yytext()));}
	{HEX}                                       	{return symbol(sym.num_hex, new Integer(yytext()));}
	{DBL}|{DBL}{EXP}{INT}                       	{return symbol(sym.num_double, new Double(yytext()));}
	{TC}                                        	{return symbol(sym.comment);}
	{LC}                                        	{return symbol(sym.comment);}
	{Suma}                                      	{return symbol(sym.opt_plus);}
	{Resta}                                     	{return symbol(sym.opt_minus);}
	{Multi}                                     	{return symbol(sym.opt_times);}
	{Divi}                                      	{return symbol(sym.opt_divide);}
	{Mod}                                       	{return symbol(sym.opt_mod);}
	{Myq}                                       	{return symbol(sym.opt_lower);}
	{Myq}{Igual}                                	{return symbol(sym.opt_lower_equal);}
	{Meq}                                       	{return symbol(sym.opt_greater);}
	{Meq}{Igual}                                	{return symbol(sym.opt_greater_equal);}
	{Igual}                                     	{return symbol(sym.opt_assign);}
	{Igual}{Igual}                              	{return symbol(sym.opt_equal);}
	{Not}{Igual}                                	{return symbol(sym.opt_not_equal);}
	{Y}{Y}                                      	{return symbol(sym.opt_and);}
	{O}{O}                                      	{return symbol(sym.opt_or);}
	{Not}                                       	{return symbol(sym.opt_not);}
	{PC}                                        	{return symbol(sym.opt_semicolon);}
	{Punto}                                     	{return symbol(sym.opt_dot);}
	{Coma}                                      	{return symbol(sym.opt_coma);}
	{CorA}                                      	{return symbol(sym.opt_left_bracket);}
	{CorC}                                      	{return symbol(sym.opt_right_bracket);}
	{LlaA}                                      	{return symbol(sym.opt_left_brace);}
	{LlaC}                                      	{return symbol(sym.opt_right_brace);}
	{ParA}                                      	{return symbol(sym.opt_left_parentheses);}
	{ParC}                                      	{return symbol(sym.opt_right_parentheses);}
	{C}{ALL}{C}                                 	{return symbol(sym.val_string, new String(yytext()));}
}

	[^]                                           {throw new(Error("Entrada Ilegal <"+yytext()+">"));}