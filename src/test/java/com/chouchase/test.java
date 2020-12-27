package com.chouchase;

import com.chouchase.common.TokenCache;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class test {
    @Test
    public void myTest(){
        System.out.println(89.06 + 0.01);
        System.out.println(new BigDecimal(89.06).add(new BigDecimal(0.01)));
    }
}
