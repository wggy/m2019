package com.wggy.useful.part.noifelse;

/***
 * created by wange on 2019/12/19 17:26
 */
public class NoIfElseByStrategy {

    static class LanguageContext {
        NoIfElseByEnum.BaseLanguage baseLanguage;

        LanguageContext(NoIfElseByEnum.BaseLanguage language) {
            this.baseLanguage = language;
        }

        String execute() {
            return baseLanguage.op();
        }
    }

    public static void main(String[] args) {
        NoIfElseByEnum.BaseLanguage javaLang = new NoIfElseByFactory.JavaLang();
        LanguageContext context = new LanguageContext(javaLang);
        System.out.println(context.execute());
    }
}
