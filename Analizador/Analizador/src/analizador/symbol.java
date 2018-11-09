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
    
    public String lexeme;
    public String value;
    public String type;
    public String rType;
    public int ambito; 
    public int nParametros;
    public String[] parametros;  
    
    symbol(String lexeme, String type, int ambito){
        this.lexeme = lexeme;
        this.type = type; 
        this.ambito = ambito;
    }
    
    public void SymbolValue(String value){
        this.value = value;
    }
    
    public void AddFunction (String rType, int nParametros, String[] parametros){
        this.rType = rType;
        this.nParametros = nParametros;
        this.parametros=new String[nParametros];
        for(int i = 0; i<nParametros; i++){
            this.parametros[i] = parametros[i];
        }
    }
}