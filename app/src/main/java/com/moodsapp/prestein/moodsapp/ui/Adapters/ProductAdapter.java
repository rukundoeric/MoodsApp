package com.moodsapp.prestein.moodsapp.ui.Adapters;

/**
 * Created by Prestein on 8/16/2017.
 */

public class ProductAdapter {

    private String product_name;
    private String product_status;
    private String product_price;
    private String product_image;

    public ProductAdapter() {
    }

    public ProductAdapter(String product_name, String product_status, String product_price, String product_image) {
        this.product_name = product_name;
        this.product_status = product_status;
        this.product_price = product_price;
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_status() {
        return product_status;
    }

    public void setProduct_status(String product_status) {
        this.product_status = product_status;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}
