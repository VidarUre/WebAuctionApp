package controller;

import beans.Bid;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import beans.Product;
import beans.ProductCatalog;
import beans.User;
import database.BidCM;
import database.ProductCM;
import database.ProductCatalogCM;
import database.UserCM;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author TorkelNes
 */
@Named(value = "productController")
@SessionScoped
public class ProductController implements Serializable {
    
    private List<Product> allProducts;

    private Product product;
    private String name;
    private String picture; // May change
    private String features;
    private Bid bid;
    
    @Inject
    private UserController userController;
    
    @Inject
    private ProductCatalogController pcc;
    
    @Inject
    private UserCM userCM;
    
    @EJB
    ProductCatalogCM productCatalogCM;
    
    @EJB
    ProductCM productCM;

    /**
     * Creates a new instance of Product
     */
    public ProductController() {
    }
    
    /**
     * Finds all the products in the database.
     * @return A list of all products
     */
    public List<Product> findAllProducts() {
        this.allProducts = productCM.findAllProducts();
        return this.allProducts;
    }
    
    /**
     * Goes to a specific product's page
     * @param productId The ID of the specific product
     * @return The page of the specific product
     */
    public String goToProduct(long productId) {
        this.product = productCM.findProduct(productId);
        ProductCatalog pc = this.product.getCatalog();
        User seller = pc.getOwner();
        this.userController.setSeller(seller);
        return "product";
    }

    /**
     * Goes the specific seller's page.
     * @return the page of the specific page
     */
    public String goToSeller(){
        return this.userController.goToSeller(this.userController.getSeller());
    }
    
    /**
     * Checks if the data of the given product is valid
     * @param name The product's name
     * @param picture The product's picture
     * @param features The product's features
     * @return true if product is valid, false if not
     */
    public boolean productIsValid(String name, String picture, String features) {
        return name != null && name.length() > 0 && picture != null && picture.length() > 0 && features != null && features.length() > 0;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }
    
    public Bid getBid() {
        return bid;
    }
    
    public Double getBidAmount() {
        return product.getCurrentBid().getAmount();
    }
    
    public void setBid (Bid bid) {
        this.bid = bid;
    }
}
