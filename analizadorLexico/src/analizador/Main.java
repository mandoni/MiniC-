/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analizador;

import java.io.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;

/**
 *
 * @author TonyTaze
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //String path = "C:/Users/TonyTaze/Downloads/Universidad/Sexto Ciclo/COMPILADORES/MiniC-/analizadorLexico/src/analizador/Lexer.flex";
        //(generarLexer(path);
        
        int result;
        String fileName, path, code="", line, newPath = "";
        JFileChooser selectedfile = new JFileChooser();
        result = selectedfile.showOpenDialog(selectedfile);
        if(result == JFileChooser.APPROVE_OPTION){
            try{
                File file = selectedfile.getSelectedFile();
                fileName = file.getName();
                path = file.getPath();
                BufferedReader read = new BufferedReader(new FileReader(file));
                while((line = read.readLine())!=null){
                    code += line+"\n";
                }
                read.close();
                
                String[] ruta = path.split(Pattern.quote("\\"));
                newPath = fileName.split(Pattern.quote("."))[0]+".out";
                for(int i = ruta.length-2;i>-1;i--){
                    newPath = ruta[i]+"\\"+newPath;
                }
            }catch(IOException ex){}
        }
        
        File out = new File(newPath);
        try {
            out.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        interfaz inter = new interfaz();
        String salida="";
        try {
            salida = inter.probarLexerFile(code);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            BufferedWriter write = new BufferedWriter(new FileWriter(out));
            //inter.setVisible(true);
            write.write(salida);
            write.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
    public static void generarLexer(String path){
        File file=new File(path);
        JFlex.Main.generate(file);
    }
}
