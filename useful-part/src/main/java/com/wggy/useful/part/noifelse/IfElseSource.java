package com.wggy.useful.part.noifelse;

/***
 * created by wange on 2019/12/19 16:47
 */
public class IfElseSource {

    static String lookforLanguage(String language) {
        if ("Python".equals(language)) {
            return "我要学Python";
        } else if ("Java".equals(language)) {
            return "我要学Java";
        } else if ("NodeJS".equals(language)) {
            return "我要学NodeJS";
        } else if ("Golang".equals(language)) {
            return "我要学Go语言";
        }
        return "我很懒，什么也不想学";
    }
}
