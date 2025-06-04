package demo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geekup.db.sql.dbutils;

public class IProductCategoriesImpl implements IProductCategories {

	int id;
    String name;

    public IProductCategoriesImpl() {
		// TODO Auto-generated constructor stub
	}
    
	public IProductCategoriesImpl(int id, String name) {
        this.id = id;
        this.name = name;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
    
	public void setName(String name) {
		this.name = name;
	}

}
