package demo;

import java.io.Serializable;
import java.util.List;

public interface IProductCategories extends Serializable {
	public int getId();
	public void setId(int id);
	public String getName();
	public void setName(String name);
//	public List<IProductCategories> getListProductCategories();
//	public void setListProductCategories(List<IProductCategories> listProductCategories);
}
