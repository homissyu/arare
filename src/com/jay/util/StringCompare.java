/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jay.util;

import java.util.Comparator;

/**
 *
 * @author Jay
 */
public class StringCompare implements Comparator<String> {

    @Override
    public int compare(String s1, String s2) {
        return s1.compareToIgnoreCase(s2);
    }
}
