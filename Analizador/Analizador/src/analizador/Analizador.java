/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner s = new Scanner(System.in);
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
                    String path = "C:\\Users\\TonyTaze\\Downloads\\Universidad\\Sexto Ciclo\\COMPILADORES\\Analizador\\Analizador\\src\\analizador\\Lexer.flex";
                    File file=new File(path);
                    JFlex.Main.generate(file);
                    break;
                case 2:
                    String opciones[] = new String[7]; 
                    //Seleccionamos la opción de dirección de destino
                    opciones[0] = "-destdir";
                    opciones[1] = "C:\\Users\\TonyTaze\\Downloads\\Universidad\\Sexto Ciclo\\COMPILADORES\\Analizador\\Analizador\\src\\analizador";
                    opciones[2] = "-symbols"; 
                    opciones[3] = "sym";
                    opciones[4] = "-parser";         
                    opciones[5] = "Sintactico"; 
                    opciones[6] = "C:\\Users\\TonyTaze\\Downloads\\Universidad\\Sexto Ciclo\\COMPILADORES\\Analizador\\Analizador\\src\\analizador\\syntax.cup"; 
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
                    String fileName, code="", path2="text.txt", line, newPath = "";
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
                        LexicalScanner lexer = new LexicalScanner(reader);
                        Sintactico parser = new Sintactico(lexer);
                        parser.parse();
                        lexicalErrors = lexer.tokens;
                        syntacticErrors = parser.SyntacticErrors;
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
    
}
