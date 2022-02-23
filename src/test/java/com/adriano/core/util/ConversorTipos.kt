/*
 * #source  ConversorTipos.java
 * #date    14/02/2002
 * #version 1.0
 *
 * Copyright (c) 2002 by NEUS Tecnologia da Informacao Ltda. All Rights Reserved.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. NEUS AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL NEUS OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF NEUS HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 */
package com.adriano.core.util

import java.lang.NumberFormatException
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Conversor de tipos primitivos
 *
 * @author thiago.freire
 */
object ConversorTipos {
    /**
     * Verifica se a string e não-nula e não-vazia
     */
    fun check(s: String?): Boolean {
        return s != null && s != ""
    }

    /**
     * Verifica se a string e não-nula e não-vazia
     */
    fun checkComTrim(s: String?): Boolean {
        return s != null && s.trim { it <= ' ' } != ""
    }

    /**
     * Verifica se o array de bytes e não-nulo e não-vazio
     */
    fun check(s: ByteArray?): Boolean {
        return s != null && s.size != 0
    }

    /**
     * Verifica se a string pode ser convertida em um inteiro
     */
    fun isInteger(s: String): Boolean {
        return try {
            s.toInt()
            true
        } catch (error: NumberFormatException) {
            false
        }
    }

    /**
     * Verifica se a string pode ser convertida em um double
     */
    fun isDouble(s: String?): Boolean {
        return try {
            if (s == null) return false
            s.toDouble()
            true
        } catch (error: NumberFormatException) {
            false
        }
    }

    /**
     * Verifica se a string pode ser convertida em um long
     */
    fun isLong(s: String?): Boolean {
        return try {
            if (s == null) return false
            s.toLong()
            true
        } catch (error: NumberFormatException) {
            false
        }
    }

    /**
     * Verifica se a string pode ser convertida em um double
     */
    fun isDouble(s: String?, nf: NumberFormat): Boolean {
        return try {
            if (s == null) return false
            nf.parse(s)
            true
        } catch (error: ParseException) {
            false
        }
    }

    /**
     * Verifica se a data e válido
     */
    fun isDate(s: String?, sdf: SimpleDateFormat): Boolean {
        return try {
            if (s != null) {
                if (sdf.toPattern().trim { it <= ' ' }.length == s.trim { it <= ' ' }.length) {
                    sdf.isLenient = false
                    sdf.parse(s)
                    return true
                }
            }
            false
        } catch (error: ParseException) {
            false
        }
    }

    /**
     * Verifica se a hora e válido
     */
    fun isHour(s: String?, sdf: SimpleDateFormat): Boolean {
        return try {
            sdf.parse(s)
            true
        } catch (error: ParseException) {
            false
        }
    }
}