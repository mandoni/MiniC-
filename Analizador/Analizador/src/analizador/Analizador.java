/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import java.util.Scanner;

/**
 *
 * @author TonyTaze
 */
public class Analizador {

    /**
     * @param args the command line arguments
     */
   
    private static String path2="text.txt", fileName="";
    
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner s = new Scanner(System.in);
        Diccionario exec = new Diccionario();
        Hashtable<String, symbol> symbolTable = new Hashtable<String, symbol>();
        List<logs> Log = new  ArrayList<logs>();

        int valor = 0;
        do{
            System.out.println("Insertar numero\n"
                + "1. Generar JFlex\n"
                + "2. Generar CUP\n"
                + "3. Ejecutar \n"
                + "4. Salir");
            valor = s.nextInt();
            
            switch(valor){
                case 1:
                    File f = new File("C:\\Users\\TonyTaze\\Downloads\\Universidad\\Sexto Ciclo\\COMPILADORES\\MiniC-\\Analizador\\Analizador\\src\\analizador\\LexicalScanner.java");
                    f.delete();
                    String path = "C:\\Users\\TonyTaze\\Downloads\\Universidad\\Sexto Ciclo\\COMPILADORES\\MiniC-\\Analizador\\Analizador\\src\\analizador\\Lexer.flex";
                    File file=new File(path);
                    JFlex.Main.generate(file);
                    break;
                case 2:
                    File t = new File("C:\\Users\\TonyTaze\\Downloads\\Universidad\\Sexto Ciclo\\COMPILADORES\\MiniC-\\Analizador\\Analizador\\src\\analizador\\Sintactico.java");
                    t.delete();
                    String opciones[] = new String[7]; 
                    //Seleccionamos la opción de dirección de destino
                    opciones[0] = "-destdir";
                    opciones[1] = "C:\\Users\\TonyTaze\\Downloads\\Universidad\\Sexto Ciclo\\COMPILADORES\\MiniC-\\Analizador\\Analizador\\src\\analizador";
                    opciones[2] = "-symbols"; 
                    opciones[3] = "sym";
                    opciones[4] = "-parser";         
                    opciones[5] = "Sintactico"; 
                    opciones[6] = "C:\\Users\\TonyTaze\\Downloads\\Universidad\\Sexto Ciclo\\COMPILADORES\\MiniC-\\Analizador\\Analizador\\src\\analizador\\syntax.cup"; 
                    try 
                    {
                        java_cup.Main.main(opciones);
                    } 
                    catch (Exception ex)
                    {
                        System.out.print(ex);
                    }
                    break;
                    
                case 3:
                    //=======================================================
                    String code="", line, newPath = "";
                    File file2 = new File("test.txt");
                    JFileChooser selectedfile = new JFileChooser();
                    int result = selectedfile.showOpenDialog(selectedfile);
                    if(result == JFileChooser.APPROVE_OPTION){
                        try{
                            file2 = selectedfile.getSelectedFile();
                            fileName = file2.getName();
                            path2 = file2.getPath();
                            BufferedReader read = new BufferedReader(new FileReader(file2));
                            while((line = read.readLine())!=null){
                                code += line+"\n";
                            }
                            read.close();
                        }catch(IOException ex){}
                    }
                    //=======================================================
                    ArrayList<Yytoken> lexicalErrors = null;
                    ArrayList<String> syntacticErrors = null;
                    boolean lexErrors = false;

                    BufferedReader reader = null;

                    try {
                        reader = new BufferedReader(new FileReader(path2));
                        String fullCode = getIncludes(reader, path2.split(fileName)[0]);
                        Reader input = new StringReader(fullCode);
                        reader = new BufferedReader(input);
                        LexicalScanner lexer = new LexicalScanner(reader);
                        Sintactico parser = new Sintactico(lexer);
                        parser.parse();
                        lexicalErrors = lexer.tokens;
                        syntacticErrors = parser.SyntacticErrors;
                        symbolTable = parser.getHashTable();
                        Log = parser.getLog();
                        reader.close();
                        //===================================================
                        for(Yytoken element: lexicalErrors){
                            if(element.error){
                                System.out.println(element + "\r\n");
                            }
                        }
                        for(String element: syntacticErrors){
                            System.out.println(element + "\r\n");
                        }
                        String ruta = path2.split(fileName)[0];
                        File tabla = new File(ruta+"\\tablaDeSimbolos.txt");
                        BufferedWriter bw;
                        if(tabla.exists()){
                           bw = new BufferedWriter(new FileWriter(tabla));
                           bw.write("Simbolo\t\t\tTipo\t\t\tValor\t\t\tRetorno\t\t\tParametros\t\t\t\tÁmbito");
                           for(Map.Entry<String, symbol> entry :symbolTable.entrySet()){
                               symbol sy = entry.getValue();
                               //String syTipo = (sy.constante?"const "+sy.type:sy.type);
                               bw.write(sy.lexeme +"\t"+ (sy.constante?"const "+sy.type:sy.type) + "\t" + sy.value +"\t" + sy.rType +"\t" + sy.parametros + "\t" + sy.ambito);
                           }
                           bw.close();
                        }else{
                            tabla.createNewFile();
                            bw = new BufferedWriter(new FileWriter(tabla));
                            bw.write("Simbolo\t\t\tTipo\t\t\tValor\t\t\tRetorno\t\t\tParametros\t\t\t\tÁmbito");
                            for(Map.Entry<String, symbol> entry :symbolTable.entrySet()){
                                symbol sy = entry.getValue();
                                //String syTipo = (sy.constante?"const "+sy.type:sy.type);
                                bw.write(sy.lexeme +"\t"+ (sy.constante?"const "+sy.type:sy.type) + "\t" + sy.value +"\t" + sy.rType +"\t" + sy.parametros + "\t" + sy.ambito);
                            }
                           bw.close();
                        }
                        
                        tabla = new File(ruta+"\\logTabla.txt");
                        if(tabla.exists()){
                           bw = new BufferedWriter(new FileWriter(tabla));
                           bw.write("Simbolo\t\t\tTipo\t\t\tValor\t\t\tRetorno\t\t\tParametros\t\t\t\tÁmbito");
                           for(logs sy : Log){
                                //String syTipo = (sy.constante?"const "+sy.type:sy.type);
                                bw.write(sy.parametros + "\t" + sy.symbol +"\t"+ sy.tipo + "\t" + sy.valor +"\t" + sy.retorno +"\t" + sy.parametros + "\t" + sy.ambito);
                            }
                           bw.close();
                        }else{
                            tabla.createNewFile();
                            bw = new BufferedWriter(new FileWriter(tabla));
                            bw.write("Operación\t\t\tSimbolo\t\t\tTipo\t\t\tValor\t\t\tRetorno\t\t\tParametros\t\t\t\tÁmbito");
                            for(logs sy : Log){
                                //String syTipo = (sy.constante?"const "+sy.type:sy.type);
                                bw.write(sy.parametros + "\t" + sy.symbol +"\t"+ sy.tipo + "\t" + sy.valor +"\t" + sy.retorno +"\t" + sy.parametros + "\t" + sy.ambito);
                            }
                           bw.close();
                        }

                        if((!lexErrors) &&(syntacticErrors.isEmpty())){
                            System.out.println("\033[32m***SYNTACTIC ANALISIS SUCCESSFUL***");     
                        }
                        else{
                            System.out.println("\033[31m***SYNTACTIC ANALISIS FAILED***");
                        }     
                    } catch (FileNotFoundException ex) {
                        System.out.println("\033[31m***FILE NOT FOUND***");
                    } catch (Exception ex) {
                        System.out.println("\033[31m***SYNTACTIC ANALISIS FAILED***");
                    }
                    //=======================================================
                    
                    break;
            }
        }while(valor != 4);
        
        
    }
    
    private static String getIncludes(BufferedReader code, String path){
        String fullCode = "", line, name;
        String[] lineaSeparada;
        try{
            while((line = code.readLine()) != null){
                lineaSeparada = line.trim().split(" ");
                if(lineaSeparada[0].compareTo("#include")==0){
                    name = Espacio(lineaSeparada);
                    fullCode += getLinesIncude((path+name));
                }
                else{
                    fullCode += line+"\n";
                }
            }
        }catch(IOException e ){
            System.out.println("\033[31m***I/O EXEPTION***\n"+e);
        }
        
        return fullCode;
    }
    
    private static String Espacio(String[] line){
        int x = 0;
        for(int i = 1; i<line.length; i++){
            if(line[i]!=" "){
                x = i;
                break;
            }
        }
        return line[x].split("<")[1].split(">")[0];
    }
    
    private static String getLinesIncude(String path){
        String lines = ""; 
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            return getIncludes(reader, path2.split(fileName)[0]);
        }catch(IOException e){
            System.out.println("\033[31m***I/O EXEPTION***\n"+e);
        }
        return lines;
    }
    
}
