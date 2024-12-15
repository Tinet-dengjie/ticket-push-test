package com.tinet.pushtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类说明
 *
 * @author DengJie
 * @date 2024/04/30
 */
@Service
public class TestService {



    public void test1() {


    }

    public void test3() {
        System.out.println("test3");
        throw new RuntimeException("test");
    }

    //    @Recover
    public void test2() {
        System.out.println("test2 recover");
    }
}
