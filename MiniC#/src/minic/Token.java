/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minic;

/**
 *
 * @author TonyTaze
 */
public enum Token {
    ERROR, T_Identifier, T_Reserved_Word, T_Comment, T_BoolConstant,
    T_IntConstant, T_DoubleConstant, T_StringConstant, T_Symbol, newLine,
    SPACE, TAB, T_EComment, T_EString
}
