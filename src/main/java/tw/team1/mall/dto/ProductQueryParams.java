package tw.team1.mall.dto;

import tw.team1.mall.constant.ProductCategory;

public class ProductQueryParams {
    private ProductCategory category;
    private String search;

    private String orderByl;
    private  String sort;

    private Integer limit;
    private Integer offset;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getOrderByl() {
        return orderByl;
    }

    public void setOrderByl(String orderByl) {
        this.orderByl = orderByl;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
