/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.AuctionPlace;
import beans.Feedback;
import beans.Product;
import beans.ProductCatalog;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import beans.User;
import database.FeedbackCM;
import database.ProductCatalogCM;
import database.UserCM;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author TorkelNes
 */
@Named(value = "userController")
@ManagedBean
@SessionScoped
public class UserController implements Serializable {

    private AuctionPlace auctionPlace;
    
    private User user;
    
    private ProductCatalog productsForSale;
    private ProductCatalog soldProducts;
    private ProductCatalog boughtProducts;
    //private List<Feedback> feedback; 
    
    private String username;
    private String email;
    private String phonenumber;
    private String password;
    
    @EJB
    private UserCM userCM;
    
    @Inject
    ProductCatalogController pgController;

    /**
     * Creates a new instance of UserController
     */
    public UserController() {
        
    }

    public String navigate() {
        return "register";
    }
    
    public String home() {
        return "products";
    }
    
    public String login() {
        String result;
        boolean isValid;
        
        isValid = this.userCM.isValidLogin(this.username, this.password);
        
        if(isValid) {
            this.user = this.userCM.findUserByUsername(this.getUsername());
            this.user.setLoggedIn(true);
            if(user != null) {
                result = "/products";
            } else {
                result = "login";
            }
        } else {
            //Bruker er ugyldig
            result = "login";
        }
       return result; 
    }
    
    public String InitRegister(){
        this.user = new User();
        return "register";
    }
    
    public String register() {
        if(isValidRegister(this.getUsername(), this.getEmail(), this.getPhonenumber(), this.getPassword())) {
            this.user = new User();
            this.user.setUsername(this.getUsername());
            this.user.setEmail(this.getEmail());
            this.user.setPhoneNumber(this.getPhonenumber());
            this.user.setPassword(this.getPassword());
            
            this.user.setLoggedIn(false);
            this.user.setRating(0);
            
            // Creating the user's product catalogs and feedback
            createCatalogs();
            
            this.auctionPlace.
            
            this.userCM.storeUser(this.user);
            
            //storeCatalogs();
            
            return "login";
        } else return "register";
    }
    
    private void createCatalogs() {
        this.productsForSale = new ProductCatalog();
        this.productsForSale.setStatus("forSale");
        this.productsForSale.setOwner(this.user);
        this.user.setProductsForSale(productsForSale);
        
        this.soldProducts = new ProductCatalog();
        this.soldProducts.setStatus("sold");
        this.soldProducts.setOwner(this.user);
        this.user.setSoldProducts(soldProducts);
        
        this.boughtProducts = new ProductCatalog();
        this.boughtProducts.setStatus("bought");
        this.boughtProducts.setOwner(this.user);
        this.user.setBoughtProducts(boughtProducts);
        
        //this.feedbackCM.storeFeedback(feedback);
    }
    
    /*
    private void storeCatalogs() {
        this.productCatalogCM.storeProductCatalog(productsForSale);
        this.productCatalogCM.storeProductCatalog(soldProducts);
        this.productCatalogCM.storeProductCatalog(boughtProducts);
    }
    */
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isValidRegister(String un, String em, String pn, String pw) {
        return isValidUsername(un) && isValidEmail(em) && isValidPhonenumber(pn) && isValidPassword(pw);
    }
    
    private boolean isValidUsername(String username) {
        return true;
    }
    
    private boolean isValidPassword(String password) {
        return true;
    }
    
    private boolean isValidEmail(String email) {
        return true;
    }
    
    private boolean isValidPhonenumber(String phonenumber) {
        return true;
    }
}
