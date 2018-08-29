/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minic;

import java.io.*;
import javax.swing.JFileChooser;

/**
 *
 * @author TonyTaze
 */

//guardar en archivo .out
public class analizador {
    private int linea, column, l;
    
    public void analizar() throws FileNotFoundException, IOException{
        //int contIDs=0;
        linea=1;
        column = l =0;
        
        java.io.Reader reader = new BufferedReader(new FileReader("fichero.txt"));
        BufferedReader code = new BufferedReader(new FileReader("fichero.txt"));
        BufferedWriter out = new BufferedWriter(new FileWriter("fichero.txt"));
        
        int result;
        String fileName="", path="";
        JFileChooser selectedfile = new JFileChooser();
        result = selectedfile.showOpenDialog(selectedfile);
        if(result == JFileChooser.APPROVE_OPTION){
            try{
                File file = selectedfile.getSelectedFile();
                fileName = file.getName();
                path = file.getPath();
                FileReader read = new FileReader(file);
                reader = new BufferedReader(new FileReader(file));
                code = new BufferedReader(new FileReader(file));
            }catch(IOException ex){}
        }else{
            return;
        }
        
        String stringCode ="";
        try{
            String line;
            while((line = code.readLine())!= null){
                stringCode += line+"\n";
            }
            code.close();
        }catch(IOException e){}
        
        char[] characters = stringCode.toCharArray();
        Lexer lexer = new Lexer (reader);
        String resultado="";
        
        while (true){
            Token token =lexer.yylex();
            int tamano = lexer.yylength();
            if(token == null){
                //resultado
                return;
            }
            switch (token){
                case ERROR:
                    resultado = resultado + "**Error line "+linea+".*** Unrecognized char: '"+lexer.lexeme+"'\n";
                    column = column + tamano;
                    break;
                case T_Reserved_Word: 
                    resultado = resultado + lexer.lexeme + "\t\tline "+linea+" cols "+(column+1)+"-"+(column+tamano)+" is "+token+"\n";
                    column = column + tamano + 1; 
                    break;
                case T_Comment:
                    comment(characters);
                    break;
                case T_Identifier:
                    if(lexer.lexeme.length()<32){
                        resultado = resultado + lexer.lexeme + "\t\tline "+linea+" cols "+(column+1)+"-"+(column+tamano)+" is "+token+"\n";
                        column = column + tamano + 1; 
                    }
                    else{
                        char[] cadena = lexer.lexeme.toCharArray();
                        String cadenaValida="";
                        for(int i = 0; i<32;i++){
                            cadenaValida+=cadena[i];
                        }
                        resultado = resultado + cadenaValida + "\t\tline "+linea+" cols "+(column+1)+"-"+(column+31)+" is "+token+"\n";
                        column = column + tamano + 32;
                    }
                    break;
                case T_BoolConstant: case T_IntConstant: case T_DoubleConstant: 
                    resultado = resultado + lexer.lexeme + "\t\tline "+linea+" cols "+(column+1)+"-"+(column+tamano)+" is "+token+" (value = "+lexer.lexeme+")\n";
                    column = column + tamano + 1; 
                    break;
                case T_StringConstant:
                    resultado = resultado + lexer.lexeme + "\t\tline "+linea+" cols "+(column+1)+"-"+(column+tamano)+" is "+token+" (value = "+lexer.lexeme+")\n";
                    column = column + tamano + 1;
                    break;
                case T_Symbol:
                    resultado = resultado + lexer.lexeme + "\t\tline "+linea+" cols "+(column+1)+"-"+(column+tamano)+" is "+lexer.lexeme+"\n";
                    column=column+tamano + 1;
                    break;
                case newLine:
                    linea = linea + tamano;
                    column = 0;
                    break;
                case SPACE:
                    column = column + tamano;
                    break;
                case TAB:
                    column = column + (tamano*4);
                    break;
                case T_EComment:
                     resultado = resultado + "**Error line "+linea+".*** Comment not finished: "+lexer.lexeme+"\n";
                    break;
                case T_EString:
                    resultado = resultado + "**Error line "+linea+".*** String not finished: "+lexer.lexeme+"\n";
                    break;
            }   
        }
    }
    
     private void comment (char[] code){
        if(code[l]=='/' && code[l+1]=='/'){
            linea++;
            return;
        }
        
        for(int i = l;i<code.length;i++){
            if(code[i]=='/'&&code[i+1]=='*'){
                l = i + 2;
                break;
            }
        }
        
        column = column + 2;
        
        for(int i = l;i<code.length;i++){
            if(code[i]=='\n'){
                linea++;
                column = 0;
            }
            else if(code[i] == '*' && code[i+1]=='/'){
                column = column + 2;
                l = i + 2;
                return;
            }
            else{
                column++;
            }
        }
    }
}
