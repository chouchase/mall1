package com.chouchase.service;

import com.chouchase.common.ServerResponse;
import com.chouchase.domain.Product;
import com.chouchase.vo.ProductDetail;

public interface ProductService {
    //更新或新增商品
    public ServerResponse<String> updateOrAddProduct(Product product);
    //更新商品状态
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status);
    //获取商品详细信息
    public ServerResponse<ProductDetail> manageProductDetail(Integer productId);
}
