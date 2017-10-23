/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import beans.Bid;
import beans.Product;
import controller.ProductController;
import controller.UserController;
import database.ProductCM;
import java.util.ArrayList;
import java.util.List;
//import javax.jms.Message;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Vidar
 */
@WebService(endpointInterface = "webservices.AuctionWebService")
public class AuctionWebServiceImpl implements AuctionWebService {
    
    ProductCM productCM;
    UserController userController;
    
    @PersistenceContext(unitName = "WebAuctionAppPU")
    private EntityManager em;
    
    @Override
    public List<Product> getActiveProducts() {
        //productCM = new ProductCM();
        List<Product> allProducts = findAllProducts();
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
        Message successMessage = new Message();
        Message failureMessage = new Message();
        
        String successMessageBody = "Customer " + newBid.getBidder() + "'s bid has successfully been placed for product " + newBid.getProduct();
        String failureMessageBody = "The bid by customer " + newBid.getBidder() + " has not been placed on product " + newBid.getProduct();
        
        successMessage.setExplanation(successMessageBody);
        successMessage.setStatusCode("OK");
        failureMessage.setExplanation(failureMessageBody);
        failureMessage.setStatusCode("FAIL");
        
        try {
            userController.setUser(newBid.getBidder());
            userController.setNewAmount(newBid.getAmount());
            userController.placeBid(newBid.getProduct());
            return successMessage;
        } catch(Exception e) {
            return failureMessage;
        }
    }
    
    public List<Product> findAllProducts() {
        TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
        List<Product> allProducts = query.getResultList();
        return allProducts;
    }
}