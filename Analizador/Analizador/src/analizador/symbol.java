/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

/**
 *
 * @author TonyTaze
 */
public class symbol {
    
    public boolean constante = false;
    public String lexeme;
    public String value;
    public String type;
    public String rType;
    public int ambito; 
    public String parametros;
    public String clase;
    public String[] valArray = new String[100];
    
    symbol(String lexeme, String type, int ambito, String clase){
        this.lexeme = lexeme;
        this.type = type; 
        this.ambito = ambito;
        this.clase = clase;
    }
    
    symbol(String lexeme, String type, int ambito, String clase, String value){
        this.lexeme = lexeme;
        this.type = type; 
        this.ambito = ambito;
        this.value = value;
    }
    
    public void ValArray(String valor, int posicion){
        valArray[posicion] = valor;
    }
    
    public void SymbolValue(String value){
        this.value = value;
    }
    
    public void AddFunction (String rType, String parametros){
        this.rType = rType;
        this.parametros =parametros;
    }
}