package com.diedline.hiveDemo;

import org.apache.hadoop.hive.ql.exec.UDF;

public class MyHive extends UDF {

    public String evaluate(final String s) {
        if (s == null) {
            return null;
        }
        return s.toLowerCase();
    }
}
