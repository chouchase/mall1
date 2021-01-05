package com.chouchase.dao;

import com.chouchase.domain.Shipping;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ShippingDao {
    public int insert(Shipping shipping);

    public int updateByPrimaryKeySelective(Shipping shipping);

    public int delete(@Param("userId") Integer userId, @Param("id") Integer shippingId);

    public List<Shipping> select(@Param("userId") Integer userId, @Param("id") Integer shippingId);

}
