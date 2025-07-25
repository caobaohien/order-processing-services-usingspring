package demo;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.json.JsonMapper;
//import com.fasterxml.jackson.datatype.joda.JodaModule;

import geekup.db.sql.dbutils;

public class OrderService {
	public Order createOrder(String orderRequest) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Order order = objectMapper.readValue(orderRequest, Order.class);
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		ResultSet rs = null;
		dbutils db = new dbutils();
		List<IProductImpl> productlist = new ArrayList<IProductImpl>();
		List<Double> Amount = new ArrayList<Double>();
		double TotalAmount = 0;
		String query = "INSERT INTO [ORDER] (CUSTOMER_FK) VALUES ('"+order.getCustomerId()+"')";
		try {
			//disable auto-commit mode
			db.getConnection().setAutoCommit(false);
			String new_order_id = "";
			if(!db.update(query)) {
				db.getConnection().rollback();
				System.out.println("can not insert the data: " +query+"\n");
			}else {
				String query_key = "SELECT SCOPE_IDENTITY() AS ORDER_ID";
				rs = db.get(query_key);
				if(rs.next()) {
					new_order_id = rs.getString("ORDER_ID");
				}
				
			}
//			stmt = db.getConnection().prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
//			stmt.setString(1, order.getCustomerId());
//			stmt.executeUpdate();
//			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    order_id = generatedKeys.getInt(1); // Get auto-generated order ID
//                } else {
//                    throw new SQLException("Failed to retrieve order ID.");
//                }
//            }
			order.setOrderId(Integer.parseInt(new_order_id));
			//close connection and set auto-commit mode
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		final StringBuilder query1 = new StringBuilder();
		final StringBuilder query2 = new StringBuilder();
		query1.append("SELECT PRICE,PRODUCT_NAME, SIZE, COLOR FROM PRODUCT WHERE PRODUCT_ID=? ");
		query2.append("INSERT INTO ORDER_DETAIL (ORDER_FK, PRODUCT_FK, QUANTITY, PRICE, DISCOUNT, AMOUNT) VALUES (?, ?, ?, ?, ?, ?)");
		try {
			for(IProductImpl prod : order.getItems()) {
				stmt = db.getConnection().prepareStatement(query1.toString());
				stmt.setString(1, prod.getId());
				rs = stmt.executeQuery();
				while(rs.next()) {
					Double a = rs.getDouble("PRICE");
					Amount.add((a - a*prod.getDiscount())*prod.getQuantity());
					try {
						//disable auto-commit mode
						db.getConnection().setAutoCommit(false);
						stmt1 = db.getConnection().prepareStatement(query2.toString());
						stmt1.setInt(1, order.getOrderId());
						stmt1.setString(2, prod.getId());
						stmt1.setInt(3, prod.getQuantity());
						stmt1.setDouble(4, a);
						stmt1.setFloat(5, prod.getDiscount());
						stmt1.setDouble(6, (a - a*prod.getDiscount())*prod.getQuantity());
						prod.setName(rs.getString("PRODUCT_NAME"));
						prod.setPrice(a);
						prod.setSize(rs.getInt("SIZE"));
						prod.setColor(rs.getString("COLOR"));
						productlist.add(prod);
						order.setItems(productlist);
						if(!db.update(stmt1)) {
							db.getConnection().rollback();
							System.out.println("can not insert the data: " +stmt1+"\n");
						}
						db.getConnection().commit();
						db.getConnection().setAutoCommit(true);
						stmt1.close();
					}catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		for(double amount : Amount) {
			TotalAmount += amount;
		}
		final StringBuilder query3 = new StringBuilder();
		query3.append("UPDATE [ORDER] SET TOTAL_AMOUNT =? WHERE ORDER_ID =?");
		final StringBuilder query4 = new StringBuilder();
		query4.append("SELECT ORDER_DATE FROM [ORDER] WHERE ORDER_ID =?");
		try {
			//disable auto-commit mode
			db.getConnection().setAutoCommit(false);
			stmt = db.getConnection().prepareStatement(query3.toString());
			stmt1 = db.getConnection().prepareStatement(query4.toString());
			stmt.setDouble(1, TotalAmount);
			stmt.setInt(2, order.getOrderId());
			stmt1.setInt(1, order.getOrderId());
			if(!db.update(stmt)) {
				db.getConnection().rollback();
				System.out.println("can not update the data: " +stmt+"\n");
			}
			rs = stmt1.executeQuery();
			order.setTotalAmount(TotalAmount);
			if(rs.next()) {
				order.setCreatedAt(rs.getTimestamp("ORDER_DATE").toLocalDateTime());
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			rs.close();
			stmt.close();
			stmt1.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.shutDown();
		}
		ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.registerModule(new JavaTimeModule());
		/* to serialize in standard iso-8601 */
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(order);
		Order order1 = mapper.readValue(result, Order.class);
		return order1;
	}
}
