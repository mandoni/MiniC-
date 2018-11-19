/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;


/**
 *
 * @author TonyTaze
 */
public class Diccionario {
    public boolean AlreadyExists(Hashtable<String, symbol> table, String newLexeme, int contexto, int cMin){
        if(isNumber(newLexeme.getBytes()))
            return false;
        if (table.entrySet().stream().anyMatch((entry) -> (entry.getKey() == newLexeme && (entry.getValue().ambito <= contexto && entry.getValue().ambito >= cMin)))) {
            return true;
        }
        return false;
        /*
        for(Map.Entry<String, symbol> entry : table.entrySet()) {
            if(entry.getKey() == newLexeme && (entry.getValue().ambito <= contexto && entry.getValue().ambito >= cMin))
                return true;
        }
        */
    }
    
    public boolean ExistsFunction(Hashtable<String, symbol> table, String lexeme){
         for(Map.Entry<String, symbol> entry : table.entrySet()) {
            if(entry.getKey() == lexeme)
                return true;
        }
        return false;
    }
    
    public symbol FindFunction(Hashtable<String, symbol> table, String lexeme){
        for(Map.Entry<String, symbol> entry : table.entrySet()) {
            if(entry.getKey() == lexeme)
                return entry.getValue();
        }
        return new symbol("", "", -1, "");
    }
    
    public symbol FindSymbol(Hashtable<String, symbol> table, String lexeme, int contexto, int cMin){
        for(Map.Entry<String, symbol> entry : table.entrySet()) {
            if(entry.getKey() == lexeme && (entry.getValue().ambito <= contexto && entry.getValue().ambito >= cMin))
                return entry.getValue();
        }
        return new symbol("", "", -1, "");
    }
    
    public Hashtable<String, symbol> DeleteAmbit(Hashtable<String, symbol> table, int ambit){
        Hashtable<String, symbol> retorno = new Hashtable<>();
         for(Map.Entry<String, symbol> entry : table.entrySet()) {
            if(entry.getValue().ambito != ambit)
                retorno.put(entry.getKey(), entry.getValue());
        }
        return retorno;
        /*
        for(Map.Entry<String, symbol> entry : table.entrySet()) {
            if(entry.getValue().ambito == ambit)
                table.remove(entry.getKey());
        }
        */
    }
    
    public boolean isNumber(byte[] lexeme){
        for(int i = 0; i < lexeme.length; i++){
           if(!CompareNumber(lexeme[i]))
               return false;
        }
        return true;
    }
    
    public boolean CompareNumber(byte lexeme){
        byte[] numbers = "1234567890".getBytes();
        for(int i=0;i<10;i++){
            if(lexeme == numbers[i])
                return true;
        }
        return false;
    }
}
