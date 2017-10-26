package webservices;

import beans.Product;
import beans.User;
import controller.UserController;
import database.ProductCM;
import database.UserCM;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Vidar
 */
@WebService(endpointInterface = "webservices.AuctionWebService")
public class AuctionWebServiceImpl implements AuctionWebService {
    
    ProductCM productCM;
    UserCM userCM;
    
    @Inject
    UserController userController;
    
    @PersistenceContext(unitName = "WebAuctionAppPU")
    private EntityManager em;
    
    @Override
    public List<Product> getActiveProducts() {
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
    public Message bidForProduct(@WebParam(name = "bidderName") String bidderName, @WebParam(name = "productId") long productId, @WebParam(name = "amount") double amount) {
        User bidder = findUserByUsername(bidderName);
        Product product = findProduct(productId);
        
        Message successMessage = new Message();
        Message failureMessage = new Message();
        
        String successMessageBody = "Customer " + bidderName + "'s bid has successfully been placed for product " + product.getName();
        String failureMessageBody = "The bid by customer " + bidderName + " has not been placed on product " + product.getName();
        
        successMessage.setExplanation(successMessageBody);
        successMessage.setStatusCode("OK");
        failureMessage.setExplanation(failureMessageBody);
        failureMessage.setStatusCode("FAIL");
        
        try {
            userController.setUser(bidder);
            userController.setNewAmount(amount);
            userController.placeBid(product);
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
    
    public Product findProduct(Long id) {
        Product product = em.find(Product.class, id);
        return product;
    }
    
    public User findUserByUsername(String username) {
        User foundUser = null;
        Query createNamedQuery = em.createNamedQuery("User.findByUsername");
        
        createNamedQuery.setParameter("username", username);

        if (createNamedQuery.getResultList().size() > 0) {
            foundUser = (User) createNamedQuery.getSingleResult();
        }
        return foundUser;
    }
}