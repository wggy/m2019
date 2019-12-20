package com.wggy.useful.part.noifelse;

import java.util.HashMap;
import java.util.Map;

/***
 * created by wange on 2019/12/19 17:16
 */
public class NoIfElseByFactory {

    private static Map<String, NoIfElseByEnum.BaseLanguage> CONTAINER = new HashMap<>();

    static {
        CONTAINER.put("Java", new JavaLang());
        CONTAINER.put("Python", new PythonLang());
        CONTAINER.put("NodeJS", new NodeJSLang());
        CONTAINER.put("Golang", new Golang());
    }

    static NoIfElseByEnum.BaseLanguage getOp(String language) {
        return CONTAINER.get(language);
    }

    public static void main(String[] args) {
        NoIfElseByEnum.BaseLanguage op = getOp("Java");
        if (op == null) {
            System.out.println("我很懒，什么都不想学");
        } else {
            System.out.println(op.op());
        }
    }

    static class JavaLang implements NoIfElseByEnum.BaseLanguage {

        @Override
        public String op() {
            return "我要学Java";
        }
    }

    static class PythonLang implements NoIfElseByEnum.BaseLanguage {
        @Override
        public String op() {
            return "我要学Python";
        }
    }

    static class NodeJSLang implements NoIfElseByEnum.BaseLanguage {
        @Override
        public String op() {
            return "我要学NodeJS";
        }
    }

    static class Golang implements NoIfElseByEnum.BaseLanguage {
        @Override
        public String op() {
            return "我要学Go语言";
        }
    }
}
