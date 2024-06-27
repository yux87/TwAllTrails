package tw.team1.mall.service;

import tw.team1.mall.dto.ProductQueryParams;
import tw.team1.mall.dto.ProductRequest;
import tw.team1.mall.model.Product;

import java.util.List;

public interface ProductService {


    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void  updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);


}
