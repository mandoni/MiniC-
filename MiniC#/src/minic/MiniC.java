/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minic;
import java.io.*;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
/**
 *
 * @author TonyTaze
 */
public class MiniC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
             probarLexerFile();
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        
        JFileChooser selectedfile = new JFileChooser();
        int result;
        result = selectedfile.showOpenDialog(selectedfile);
        String fileName;
        if(result == JFileChooser.APPROVE_OPTION){
            try{
                File file = selectedfile.getSelectedFile();
                fileName = file.getName();
                BufferedReader read = new BufferedReader(new FileReader(file));
            }catch(IOException ex){}
        }
    }
    static int linea, column, l;
    public static void probarLexerFile() throws IOException{
        int contIDs=0;
        
        linea=1;
        column = l =0;
        
        //tokenslist = new LinkedList<identificador>();
        
        Reader reader = new BufferedReader(new FileReader("fichero.txt"));
        String stringCode ="";
        try{
            BufferedReader code = new BufferedReader(new FileReader("fichero.txt"));
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
    
    
    private static void comment (char[] code){
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
