/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import beans.Bid;
import beans.Product;
import controller.ProductController;
import java.util.ArrayList;
import java.util.List;
import javax.jms.Message;
import javax.jws.WebService;

/**
 *
 * @author Vidar
 */
@WebService(endpointInterface = "webservices.AuctionWebService")
public class AuctionWebServiceImpl implements AuctionWebService {
    
    ProductController productController;
    
    @Override
    public List<Product> getActiveProducts() {
        List<Product> allProducts = productController.findAllProducts();
        List<Product> activeProducts = new ArrayList<>();
        for(Product p : allProducts) {
            if(p.getPublished()) {
                activeProducts.add(p);
            }
        }
        return activeProducts;
    }
    
    @Override
    public Message bidForProduct(Bid newBid) {
        return null;
    }
}
