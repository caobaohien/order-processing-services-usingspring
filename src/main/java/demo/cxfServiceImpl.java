package demo;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import geekup.db.sql.dbutils;

public class cxfServiceImpl implements cxfService {
	private ProductService productService = new ProductService();
	private OrderService orderService = new OrderService();  
	public List<IProductCategories> getAllCategories() {
		
		IProductCategories gmhipf = null;
		List<IProductCategories> gmhipflist = new ArrayList<IProductCategories>();
		final StringBuilder query = new StringBuilder();

		query.append("select PRODUCT_CATEGORY_ID,PRODUCT_CATEGORY_NAME from PRODUCT_CATEGORIES");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		dbutils db = new dbutils();
		try {
			stmt = db.getConnection().prepareStatement(query.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				gmhipf = new IProductCategoriesImpl();
				gmhipf.setId(rs.getInt("PRODUCT_CATEGORY_ID"));
				gmhipf.setName(rs.getString("PRODUCT_CATEGORY_NAME"));
				gmhipflist.add(gmhipf);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.shutDown();
		}
		return gmhipflist;
	}

	public List<IProduct> getAllProducts(String nameOfProductCategory) {
		// TODO Auto-generated method stub
		IProduct product = null;
		List<IProduct> productlist = new ArrayList<IProduct>();
		final StringBuilder query = new StringBuilder();

		query.append("select PRODUCT_ID,PRODUCT_NAME,SIZE,COLOR,PRICE ");
		query.append("from PRODUCT a inner join PRODUCT_CATEGORIES b on a.PRODUCT_CATEGORY_FK = b.PRODUCT_CATEGORY_ID ");
		query.append("where b.PRODUCT_CATEGORY_NAME ='" + nameOfProductCategory +"'");
		query.append(" order by a.PRODUCT_ID");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		dbutils db = new dbutils();
		try {
			stmt = db.getConnection().prepareStatement(query.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				product = new IProductImpl();
				product.setId(rs.getString("PRODUCT_ID"));
				product.setName(rs.getString("PRODUCT_NAME"));
				product.setSize(rs.getInt("SIZE"));
				product.setColor(rs.getString("COLOR"));
				product.setPrice(rs.getDouble("PRICE"));
				productlist.add(product);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.shutDown();
		}
		return productlist;
	}

	public Response searchProducts(String nameOfProductCategory, String query, Double priceMin, Double priceMax,
			String sortBy, int page, int limit) {
		// TODO Auto-generated method stub
		List<IProductImpl> products = productService.searchProducts(nameOfProductCategory, query, priceMin, priceMax, sortBy, page, limit);
        return Response.ok(products).build();
	}

	@Override
	public Response createOrder(String orderRequest) {
		// TODO Auto-generated method stub
		Order order = null;
		try {
			order = orderService.createOrder(orderRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return Response.status(Response.Status.CREATED).entity(order).build();
	}
	
}
