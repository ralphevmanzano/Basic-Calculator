package com.ralphevmanzano.calculator;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.mXparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ralphemerson on 11/30/2017.
 */

public class Try {
    public static void main(String[] args) {
//        String num = "58.5+98*78/96/78.7-3443*12-3";
//        String[] strings = (num.split("[+|*|/|-]"));
//        String[] strings2 = (num.split("[^+|*|/|-]"));
//
//        Pattern r = Pattern.compile("[+|*|/|-]");
//        Matcher m = r.matcher(num);
//
//        for (String string : strings2) {
//            if (!string.equals("")) {
//                System.out.println(string);
//            }
//        }
//
//        for (String string : strings) {
//            System.out.println(string);
//        }
//
//        String sample = "(-923)";
//        String parsed = sample.substring(1, sample.length()-1);
//        System.out.println(Double.valueOf(parsed) + 2);
        Expression eh = new Expression("2.1-3*5/(-5.36)");
//        Expression ew = new Expression("71^1 * 218549^1 * 6195547^1");
//        String h = mXparser.numberToAsciiString( eh.calculate() );
//        String w = mXparser.numberToAsciiString( ew.calculate() );
//        System.out.println(h + " " + w);
        System.out.println(eh.calculate());
    }
}
