package com.chouchase.service.impl;

import com.chouchase.common.ServerResponse;
import com.chouchase.dao.CategoryDao;
import com.chouchase.dao.ProductDao;
import com.chouchase.domain.Category;
import com.chouchase.domain.Product;
import com.chouchase.service.ProductService;
import com.chouchase.util.DateUtil;
import com.chouchase.util.PropertiesUtil;
import com.chouchase.vo.ProductDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CategoryDao categoryDao;
    @Override
    public ServerResponse<String> updateOrAddProduct(Product product) {
        //校验商品id的合法性、所属类别的合法性
        if((product.getId() != null && productDao.checkId(product.getId()) < 1) || product.getCategoryId() == null || categoryDao.checkCategoryId(product.getCategoryId()) < 1){
            return ServerResponse.createFailResponseByMsg("参数错误");
        }
        //选取subImage的第一张图片作为子图
        if(!StringUtils.isBlank(product.getSubImages())){
            String[] images = product.getSubImages().split(",");
            product.setMainImage(images[0]);
        }
        //如果id不等于空，代表更新商品
        if(product.getId() != null){
            //更新商品信息
            int cnt = productDao.updateProductSelective(product);
            if(cnt > 0){
                return ServerResponse.createSuccessResponseByMsg("商品更新成功");
            }
            return ServerResponse.createFailResponseByMsg("商品更新失败");
        }else{//否则添加新商品
            int cnt = productDao.insertProduct(product);
            if(cnt > 0){
                return ServerResponse.createSuccessResponseByMsg("商品添加成功");
            }
            return ServerResponse.createFailResponseByMsg("商品添加失败");
        }
    }

    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        //查询商品是否存在
        if(productDao.checkId(productId) < 1){
            return ServerResponse.createFailResponseByMsg("商品不存在");
        }
        //更新商品状态
        if(productDao.updateProductStatus(productId,status) > 0){
            return ServerResponse.createSuccessResponseByMsg("商品状态更新成功");
        }
        return ServerResponse.createSuccessResponseByMsg("商品状态更新失败");
    }

    @Override
    public ServerResponse<ProductDetail> manageProductDetail(Integer productId) {
        //从数据库获取product
        Product product = productDao.selectProductByPrimaryKey(productId);
        //如果商品不存在
        if(product == null){
            return ServerResponse.createFailResponseByMsg("商品不存在");
        }
        //组装成ProductDetail对象，并将其放入响应消息对象中
        return ServerResponse.createSuccessResponseByMsgAndData("获取成功",assembleProductDetail(product));

    }
    private ProductDetail assembleProductDetail(Product product){
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(product.getId());
        productDetail.setCategoryId(product.getCategoryId());
        productDetail.setName(product.getName());
        productDetail.setSubtitle(product.getSubtitle());
        productDetail.setMainImage(product.getMainImage());
        productDetail.setSubImages(product.getSubImages());
        productDetail.setDetail(product.getDetail());
        productDetail.setPrice(product.getPrice());
        productDetail.setStatus(product.getStatus());
        productDetail.setStock(product.getStock());

        productDetail.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        Category category = categoryDao.selectCategoryById(product.getCategoryId());
        if(category == null){
            productDetail.setParentCategoryId(0);
        }else{
            productDetail.setParentCategoryId(category.getParentId());
        }
        productDetail.setCreateTime(DateUtil.dateToStr(product.getCreateTime()));
        productDetail.setUpdateTime(DateUtil.dateToStr(product.getUpdateTime()));
        return productDetail;
    }
}
