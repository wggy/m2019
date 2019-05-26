package com.wggy.feign.service;

import com.wggy.feign.FeignApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = FeignApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ApiServiceTest {
    @Autowired
    private ApiService apiService;
    @Autowired
    private TestService testService;
    @Test
    public void test(){
        try {
            System.out.println("testA" + testService.getCnt());
            System.out.println(apiService.index());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testB() {
        System.out.println("testB"  + testService.getCnt());
    }
}
