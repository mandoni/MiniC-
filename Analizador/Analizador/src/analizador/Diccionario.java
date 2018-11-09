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
    public boolean AlreadyExists(Hashtable<String, symbol> table, String newLexeme){
        for(Map.Entry<String, symbol> entry : table.entrySet()) {
            if(entry.getKey() == newLexeme)
                return true;
        }
        return false;
    }
    
    public Hashtable<String, symbol> DeleteAmbit(Hashtable<String, symbol> table, int ambit){
        for(Map.Entry<String, symbol> entry : table.entrySet()) {
            if(entry.getValue().ambito == ambit)
                table.remove(entry.getKey());
        }
        return table;
    }
    
}
