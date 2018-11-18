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
public class logs {
    public String operacion;
    public String symbol; 
    public String tipo;
    public String retorno;
    public String parametros;
    public String valor;
    public String clase;
    public int ambito;
    
    logs (String operacion){
        this.operacion = operacion;
    }
    logs(String operacion, String symbol, String tipo, String valor, int ambito, String clase){
        this.operacion = operacion;
        this.symbol = symbol;
        this.tipo = tipo;
        this.valor = valor;
        this.ambito = ambito;
        this.clase = clase;
    }
    logs(String operacion, String symbol, String tipo, String retorno, String parametros, int ambito, String clase){
        this.operacion = operacion;
        this.symbol = symbol;
        this.tipo = tipo;
        this.valor = valor;
        this.ambito = ambito;
        this.clase = clase;
    }
}
