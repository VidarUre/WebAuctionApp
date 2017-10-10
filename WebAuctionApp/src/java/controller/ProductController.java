/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Bid;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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
    private Double newAmount;
    
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
    
    @EJB BidCM bidCM;

    /**
     * Creates a new instance of Product
     */
    public ProductController() {
    }
    
     public String placeBid() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
       
            //Bid bid = bidCM.findBid(bid.getBid().getID());
            if(product.getCurrentBid().getAmount() < this.newAmount) {
                //Update bid
                bidCM.UpdateBid(product.getCurrentBid(), this.newAmount);
            }
        return "product"; // The same product screen
    }
    
    public List<Product> findAllProducts() {
        this.allProducts = productCM.findAllProducts();
        return this.allProducts;
    }
    
    public String goToProduct(long productId) {
        this.product = productCM.findProduct(productId);
        return "product";
    }
    
    public Product createProduct(boolean shouldPublish) {
        if(productIsValid(this.name, this.picture, this.features)) {
            this.product = new Product();
            this.product.setName(this.name);
            this.product.setPicture(this.picture);
            this.product.setFeatures(this.features);
            if(shouldPublish == true) {
                this.product.setPublished(true);
            } else {
                this.product.setPublished(false);
            }
            this.product.setRemainingTime(1000000);
            // Start nedtelling
        }
        return this.product;
    }

    
    public String goToSeller(){
        ProductCatalog pc = this.product.getCatalog();
        //Long sellerID = pc.getOwner().getId();
        User seller = pc.getOwner();
        //User user = userCM.getUserById(sellerID);
        this.userController.setSeller(seller);
        return this.userController.goToSeller(seller);
    }
    
    
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

    public Double getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(Double newAmount) {
        this.newAmount = newAmount;
    }
    
    
    
    
}
