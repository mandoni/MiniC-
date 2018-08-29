package analizador;
import static analizador.Token.*;
%%
%class Lexer
%type Token
Am = [a]
Bm = [b]
Cm = [c]
Dm = [d]
Em = [e]
EMm = [E]
Fm = [f]
Gm = [g]
Hm = [h]
Im = [i]
Km = [k]
Lm = [l]
Mm = [m]
Nm = [n]
Om = [o]
Pm = [p]
Rm = [r]
Sm = [s]
Tm = [t]
Um = [u]
Vm = [v]
Wm = [w]
Xm = [x]
Ym = [y]
NMm = [N]
AMm = [M]
WHITE=[\r]
LINE = [\n]
TAB = [\t]
SPACE = [ ]
ALL = [ \t\n\r]
PC = [;]
Comment1 = [/]
Comment2 = [*]
Suma = [+] 
Resta = [-]
Punto = [.] 

Multi = [*] 
Divi = [/]

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
Comilla = [\"]
L = [a-zA-Z_]
Ln = [a-zA-Z]
Ls = [ \t]
D = [0-9]
Z = [a-zA-Z0-9]+
Hex1 = “0x”[0-9|A-F|a-f]+
Hex2 = “0X”[0-9|A-F|a-f]+
Des = [0-9]+

%{
public String lexeme;
%}
%%
{Tm}{Rm}{Um}{Em}|{Fm}{Am}{Lm}{Sm}{Em} {lexeme=yytext(); return T_BoolConstant;}
{NMm}{Em}{Wm}{AMm}{Rm}{Rm}{Am}{Ym} {lexeme=yytext(); return T_Reserved_Word;}
{Vm}{Om}{Im}{Dm} {lexeme=yytext(); return T_Reserved_Word;}
{Im}{Nm}{Tm} {lexeme=yytext(); return T_Reserved_Word;}
{Dm}{Om}{Um}{Bm}{Lm}{Em} {lexeme=yytext(); return T_Reserved_Word;}
{Bm}{Om}{Om}{Lm} {lexeme=yytext(); return T_Reserved_Word;}
{Sm}{Tm}{Rm}{Im}{Nm}{Gm} {lexeme=yytext(); return T_Reserved_Word;}
{Cm}{Lm}{Am}{Sm}{Sm} {lexeme=yytext(); return T_Reserved_Word;}
{Im}{Nm}{Tm}{Em}{Rm}{Fm}{Am}{Cm}{Em} {lexeme=yytext(); return T_Reserved_Word;}
{Nm}{Um}{Lm}{Lm} {lexeme=yytext(); return T_Reserved_Word;}
{Tm}{Hm}{Im}{Sm} {lexeme=yytext(); return T_Reserved_Word;}
{Em}{Xm}{Tm}{Em}{Nm}{Dm}{Sm} {lexeme=yytext(); return T_Reserved_Word;}
{Im}{Mm}{Pm}{Lm}{Em}{Mm}{Em}{Nm}{Tm}{Sm} {lexeme=yytext(); return T_Reserved_Word;}
{Fm}{Om}{Rm} {lexeme=yytext(); return T_Reserved_Word;}
{Wm}{Hm}{Im}{Lm}{Em} {lexeme=yytext(); return T_Reserved_Word;}
{Im}{Fm} {lexeme=yytext(); return T_Reserved_Word;}
{Em}{Lm}{Sm}{Em} {lexeme=yytext(); return T_Reserved_Word;}
{Rm}{Em}{Tm}{Um}{Rm}{Nm} {lexeme=yytext(); return T_Reserved_Word;}
{Bm}{Rm}{Em}{Am}{Km} {lexeme=yytext(); return T_Reserved_Word;}
{NMm}{Em}{Wm} {lexeme=yytext(); return T_Reserved_Word;}
{Ln}({D}|{L})* {lexeme=yytext(); return T_Identifier;}
{Z}+{Ln}({D}|{L})* {lexeme=yytext(); return ERROR;}
({Des}|{Hex1}|{Hex2}){L}+ {lexeme=yytext(); return ERROR;}
{Comment1}{Comment1}({L}|{Ls}|{D}|({Suma}|{Resta}|{Multi}|{Divi}|{Mod}|{Meq}|{Myq}|{Igual}|{Not}|{Y}|{O}|{PC}|{Coma}|{Punto}|{CorA}|{CorC}|{LlaA}|{LlaC}|{ParA}|{ParC}|{Comilla}))* {lexeme=yytext(); return T_Comment;}
{Comment1}{Comment2}({LINE}|{ALL}|{L}|{D}|({Suma}|{Resta}|{Multi}|{Divi}|{Mod}|{Meq}|{Myq}|{Igual}|{Not}|{Y}|{O}|{PC}|{Coma}|{Punto}|{CorA}|{CorC}|{LlaA}|{LlaC}|{ParA}|{ParC}|{Comilla}))* {lexeme=yytext(); return T_EComment;}
{Comment1}{Comment2}({LINE}|{ALL}|{L}|{D}|({Suma}|{Resta}|{Multi}|{Divi}|{Mod}|{Meq}|{Myq}|{Igual}|{Not}|{Y}|{O}|{PC}|{Coma}|{Punto}|{CorA}|{CorC}|{LlaA}|{LlaC}|{ParA}|{ParC}|{Comilla}))*{Comment2}{Comment1} {lexeme=yytext(); return T_Comment;}
{Des}{Punto}{D}*|{Des}{Punto}{D}*({Em}|{EMm})({Suma}|{Resta})*{Des}|{Des}{Punto}{D}*({Em}|{EMm}){Des} {lexeme=yytext(); return T_DoubleConstant;}
{L}+{Des}{Punto}{D}*|{L}+{Des}{Punto}{D}*({Em}|{EMm})({Suma}|{Resta})*{Des}|{L}+{Des}{Punto}{D}*({Em}|{EMm}){Des} {lexeme=yytext(); return ERROR;}
{Des}{Punto}{D}*{Z}+|{Des}{Punto}{D}*({Em}|{EMm})({Suma}|{Resta})*{Des}{Z}+|{Des}{Punto}{D}*({Em}|{EMm}){Des}{Z}+ {lexeme=yytext(); return ERROR;}

{Des}|{Hex1}|{Hex2} {lexeme=yytext(); return T_IntConstant;}
{Comilla}({L}|{D}|{Ls}|({Suma}|{Resta}|{Multi}|{Divi}|{Mod}|{Meq}|{Myq}|{Igual}|{Not}|{Y}|{O}|{PC}|{Coma}|{Punto}|{CorA}|{CorC}|{LlaA}|{LlaC}|{ParA}|{ParC}))*{Comilla} {lexeme=yytext(); return T_StringConstant;}
{Comilla} {lexeme=yytext(); return T_EString;}
{Suma}|{Resta}|{Multi}|{Divi}|{Mod}|{Meq}|{Myq}|{Igual}|{Not}|{Y}|{O}|{PC}|{Coma}|{Punto}|{CorA}|{CorC}|{LlaA}|{LlaC}|{ParA}|{ParC}|
{Myq}{Igual}|{Meq}{Igual}|{Igual}{Igual}|{Not}{Igual}|{Y}{Y}|{O}{O}|{CorA}{CorC}|{ParA}{ParC}|{LlaA}{LlaC} {lexeme=yytext(); return T_Symbol;}
{WHITE}+ {/*Ignore*/}
{SPACE} {lexeme=yytext();return SPACE;}
{LINE}+ {lexeme=yytext();return newLine;}
{TAB}+ {lexeme=yytext();return TAB;}
. {lexeme=yytext(); return ERROR;}