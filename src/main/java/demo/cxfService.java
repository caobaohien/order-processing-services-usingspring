package demo;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface cxfService {
	@GET
	@Path("/api/fetch/allcategories")
	@Produces("application/json")
	public List<IProductCategories> getAllCategories();
	
	@GET
	@Path("/api/fetch/allproductsofspecificcategory/{nameOfProductCategory}")
	@Produces("application/json")
	public List<IProduct> getAllProducts(@PathParam("nameOfProductCategory") String nameOfProductCategory);
	
	@GET
	@Path("/products/search/{nameOfProductCategory}")
	@Produces("application/json")
	@Consumes("application/json")
    public Response searchProducts(@PathParam("nameOfProductCategory") String nameOfProductCategory,
            @QueryParam("query") String query,
            @QueryParam("price_min") Double priceMin,
            @QueryParam("price_max") Double priceMax,
            @QueryParam("sort_by") String sortBy,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("limit") @DefaultValue("10") int limit);
	
	@POST
	@Path("/api/create/order")
	@Produces("application/json")
	@Consumes("application/json")
    public Response createOrder(String orderRequest);
}
