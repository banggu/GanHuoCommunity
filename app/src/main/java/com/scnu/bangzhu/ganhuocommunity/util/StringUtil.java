package com.scnu.bangzhu.ganhuocommunity.util;

/**
 * Created by bangzhu on 2017/3/8.
 */

public class StringUtil {
    /**
     * 去除带冒号的url中的冒号， 如 ""abcd""  ->  "abcd"
     * @param url
     * @return
     */
    public static String getOriginUrl(String url) {
        while(url.contains("\"")) {
            int index = url.indexOf("\"");
            url = url.substring(index+1, url.length()-1);
        }
        return url;
    }
}
