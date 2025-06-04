package demo;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class ProductService {
	private ProductClient productClient = new ProductClient();
	public List<IProductImpl> searchProducts(String nameOfProductCategory, String query, Double priceMin, Double priceMax, String sortBy, int page, int limit) {
        List<IProductImpl> products = productClient.getAllProducts(nameOfProductCategory);

        List<IProductImpl> filteredProducts = new ArrayList<IProductImpl>();
        for (IProductImpl product : products) {
            if (query != null && !query.isEmpty() && priceMin != null && priceMax != null 
            		&& product.getName().toLowerCase().contains(query.toLowerCase()) 
            		&& product.getPrice() >= priceMin && product.getPrice() <= priceMax) {
                filteredProducts.add(product);
            }
        }

        // Sorting
        if ("price_asc".equalsIgnoreCase(sortBy)) {
            Collections.sort(filteredProducts, new Comparator<IProductImpl>() {
            	@Override
                public int compare(IProductImpl p1, IProductImpl p2) {
                    return Double.compare(p1.getPrice(), p2.getPrice());
                }
            });
        } else if ("price_desc".equalsIgnoreCase(sortBy)) {
            Collections.sort(filteredProducts, new Comparator<IProductImpl>() {
            	@Override
                public int compare(IProductImpl p1, IProductImpl p2) {
                    return Double.compare(p2.getPrice(), p1.getPrice());
                }
            });
        }

        // Pagination
        int startIndex = (page - 1) * limit;
        int endIndex = Math.min(startIndex + limit, filteredProducts.size());

        List<IProductImpl> paginatedProducts = new ArrayList<IProductImpl>();
        for (int i = startIndex; i < endIndex; i++) {
            IProductImpl product = filteredProducts.get(i);
            paginatedProducts.add(new IProductImpl(product.getId(), product.getName(), product.getSize(), product.getColor(), product.getPrice()));
        }

        return paginatedProducts;
    }
}
