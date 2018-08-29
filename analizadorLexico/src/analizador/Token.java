/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analizador;

/**
 *
 * @author TonyTaze
 */
public enum Token {
    ERROR, T_Identifier, T_Reserved_Word, T_Comment, T_BoolConstant,
    T_IntConstant, T_DoubleConstant, T_StringConstant, T_Symbol, newLine,
    SPACE, TAB, T_EComment, T_EString
}
