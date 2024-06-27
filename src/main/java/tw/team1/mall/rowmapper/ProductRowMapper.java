package tw.team1.mall.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import tw.team1.mall.constant.ProductCategory;
import tw.team1.mall.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();

        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));

//        String categoryStr = rs.getString("category");
//        ProductCategory category = ProductCategory.valueOf(categoryStr);
//        product.setCategory(category);

        product.setCategory(ProductCategory.valueOf(rs.getString("category")));

//        product.setCategory(rs.getString("category"));

        product.setImageUrl(rs.getString("image_url"));
        product.setPrice(rs.getInt("price"));
        product.setStock(rs.getInt("stock"));
        product.setDescription(rs.getString("description"));
        product.setCreatedDate(rs.getTimestamp("created_date"));
        product.setLastModifiedDate(rs.getTimestamp("last_modified_date"));


        return product;

    }
}

