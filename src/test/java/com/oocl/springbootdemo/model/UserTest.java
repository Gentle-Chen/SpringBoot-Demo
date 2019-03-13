package com.oocl.springbootdemo.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author:QIUHU
 * @description:
 * @date:Create in 8:31 PM 3/13/2019
 * @modified By:
 */
public class UserTest {

    User user = null ;

    @Before
    public void before(){
        user = new User("test@oocl.com","test","male");
    }


    @Test
    public void should_return_string_when_call_toString() {
        Assert.assertEquals(user.toString(),"User [account=test@oocl.com, name=test, gender=male]");
    }
}