package com.wggy.useful.part.rpc0.client;

/***
 * created by wange on 2019/12/25 19:05
 */
public class ApiServiceImpl implements ApiService {
    @Override
    public void sayHi(String name) {
        System.out.println("Hi " + name + "！");
    }
}
