package demo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class ProductClient {
    public List<IProductImpl> getAllProducts(String nameOfProductCategory) {
    	String address = "http://localhost:8080/cxf/rest/api/fetch/allproductsofspecificcategory";
    	JacksonJsonProvider provider = new JacksonJsonProvider();
    	WebClient client = WebClient.create(address, Collections.singletonList(provider));
    	client.path("/{nameOfProductCategory}", nameOfProductCategory);
    	client.accept(MediaType.APPLICATION_JSON);
    	Collection<IProductImpl> products = client.get(new GenericType<Collection<IProductImpl>>() {});
    	return (List<IProductImpl>) products;
    }
}
