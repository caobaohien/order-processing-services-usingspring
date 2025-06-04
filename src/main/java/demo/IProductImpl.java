package demo;

public class IProductImpl implements IProduct {
	String id;
    String name;
    int size;
    String color;
    Double price;
    int quantity;
    float discount;
	public IProductImpl() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IProductImpl(String id, String name, int size, String color, Double price) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
		this.color = color;
		this.price = price;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public int getQuantity() {
		// TODO Auto-generated method stub
		return quantity;
	}
	@Override
	public void setQuantity(int quantity) {
		// TODO Auto-generated method stub
		this.quantity = quantity;
	}
	@Override
	public float getDiscount() {
		// TODO Auto-generated method stub
		return discount;
	}
	@Override
	public void setDiscount(float discount) {
		// TODO Auto-generated method stub
		this.discount = discount;
	}
    
}
