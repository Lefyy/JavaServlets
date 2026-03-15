package app.model;

import java.math.BigDecimal;

public class Product {
    private Integer id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private Integer categoryId;
    private String imageUrl;


    public Product() {}

    public Product(Integer id, String name, BigDecimal price, int quantity, Integer categoryId, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
