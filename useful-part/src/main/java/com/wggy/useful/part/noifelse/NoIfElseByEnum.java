package com.wggy.useful.part.noifelse;


/***
 * created by wange on 2019/12/19 16:40
 */
public class NoIfElseByEnum {

    public static void main(String[] args) {
        System.out.println(lookfor("java"));
    }

    static String lookfor(String language) {
        LanguageEnum languageEnum = LanguageEnum.valueOf(language);
        return languageEnum.op();
    }


    interface BaseLanguage {
        String op();
    }

    enum LanguageEnum implements BaseLanguage {
        Java {
            @Override
            public String op() {
                return "我要学Java";
            }
        },

        Python {
            @Override
            public String op() {
                return "我要学Python";
            }
        },

        NodeJS {
            @Override
            public String op() {
                return "我要学NodeJS";
            }
        },

        Golang {
            @Override
            public String op() {
                return "我要学Go语言";
            }
        }

        ;
        @Override
        public String op() {
            return "我很懒，什么也不想学";
        }
    }
}
