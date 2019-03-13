package com.oocl.springbootdemo.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author:QIUHU
 * @description:
 * @date:Create in 8:35 PM 3/13/2019
 * @modified By:
 */
public class ResponseTest {
    Response response = null;

    @Before
    public void before(){
    response = new Response();
    }


    @Test
    public void should_return_string_when_call_toString() {
        response.setStatus("status");
        response.setErrorMsg("errorMsg");
        response.setUpdateCount(1);
        response.setResult(new User("test@oocl.com","test","male"));
        Assert.assertEquals(response.toString(),"Response [status=status, errorMsg=errorMsg, result=User [account=test@oocl.com, name=test, gender=male], updateCount=1]");
    }
}