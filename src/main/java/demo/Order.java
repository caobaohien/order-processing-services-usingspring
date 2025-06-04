package demo;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Order {
	private int orderId;
	private String customerId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private Double TotalAmount;
    private List<IProductImpl> items;
    
	public Order() {};
	public Order(int orderId, String customerId, Double TotalAmount, LocalDateTime createdAt, List<IProductImpl> items) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.TotalAmount = TotalAmount;
		this.createdAt = createdAt;
		this.items = items;
	}
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt2) {
		this.createdAt = createdAt2;
	}
	public Double getTotalAmount() {
		return TotalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		TotalAmount = totalAmount;
	}
	public List<IProductImpl> getItems() {
		return items;
	}
	public void setItems(List<IProductImpl> items) {
		this.items = items;
	}
	
}
