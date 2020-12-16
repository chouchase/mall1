package com.chouchase;

import com.chouchase.common.TokenCache;
import org.junit.Test;

import java.util.UUID;

public class test {
    @Test
    public void myTest(){
        TokenCache.put("haha","ahha");
        System.out.println(TokenCache.get("haha"));
    }
}
