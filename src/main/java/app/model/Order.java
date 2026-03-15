package app.model;

import java.time.LocalDateTime;

public class Order {
    private Integer id;
    private Integer customerId;
    private Integer statusId;
    private LocalDateTime createdAt;

    public Order() {}

    public Order(Integer id, Integer customerId, Integer statusId, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.statusId = statusId;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
