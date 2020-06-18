package com.icss.happyfarm.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.text.*;

/**
 *
 * @author GF4
 */
public class NumberLenghtLimitedDmt extends PlainDocument {

    private int limit;

    public NumberLenghtLimitedDmt(int limit) {
        super();
        this.limit = limit;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (str == null) {
            return;
        }
        if ((getLength() + str.length()) <= limit) {

            char[] upper = str.toCharArray();
            int length = 0;
            for (int i = 0; i < upper.length; i++) {
                if (upper[i] >= '0' && upper[i] <= '9') {
                    upper[length++] = upper[i];
                }
            }
            super.insertString(offset, new String(upper, 0, length), attr);
        }
    }
}

