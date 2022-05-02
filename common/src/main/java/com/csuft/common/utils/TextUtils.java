package com.csuft.common.utils;

public class TextUtils {

    /**
     * 判断字符串是否有内容
     * @param text
     * @return
     */
    public static boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }
}
