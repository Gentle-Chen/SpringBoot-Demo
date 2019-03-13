package com.oocl.springbootdemo.service;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author:QIUHU
 * @description:
 * @date:Create in 8:27 PM 3/13/2019
 * @modified By:
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AutoConfigServiceTest {


    @Autowired
    AutoConfigService autoConfigService;

    @Test
    public void testAutoConfig() {
        assertEquals(autoConfigService.testAutoConfig().getCode(),200);
        assertEquals(autoConfigService.testAutoConfig().getMsg(),"SUCCESS");
    }
}