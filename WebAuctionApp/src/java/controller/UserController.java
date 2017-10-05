/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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

/**
 *
 * @author TorkelNes
 */
@Named(value = "userController")
@ManagedBean
@SessionScoped
public class UserController implements Serializable {

    private User user;
    
    private ProductCatalog productCatalog;
    private ProductCatalog soldProducts;
    private ProductCatalog aquiredProducts;
    //private List<Feedback> feedback; 
    
    private String username;
    private String email;
    private String phonenumber;
    private String password;
    
    @EJB
    private UserCM userCM;
    
    @EJB
    private ProductCatalogCM productCatalogCM;
    
    @EJB
    private FeedbackCM feedbackCM;
    
    ProductController productController;

    /**
     * Creates a new instance of UserController
     */
    public UserController() {
        productController = new ProductController();
    }

    public String navigate() {
        return "register";
    }
    
    public String home() {
        return "products";
    }
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String result;
        boolean isValid;
        
        isValid = this.userCM.isValidLogin(this.username, this.password);
        
        if(isValid) {
            this.user = this.userCM.findUserByUsername(this.getUsername());
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
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
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
            
            this.userCM.storeUser(this.user);
            
            storeCatalogs();
            
            return "login";
        } else return "register";
    }
    
    private void createCatalogs() {
        this.productCatalog = new ProductCatalog();
        this.soldProducts = new ProductCatalog();
        this.aquiredProducts = new ProductCatalog();
        this.productCatalog.setOwner(this.user);
        this.soldProducts.setOwner(this.user);
        this.aquiredProducts.setOwner(this.user);
        this.user.setProductCatalog(productCatalog);
        this.user.setSoldProducts(soldProducts);
        this.user.setAcquiredProducts(aquiredProducts);
        //this.feedbackCM.storeFeedback(feedback);
    }
    
    private void storeCatalogs() {
        this.productCatalogCM.storeProductCatalog(productCatalog);
        this.productCatalogCM.storeProductCatalog(soldProducts);
        this.productCatalogCM.storeProductCatalog(aquiredProducts);
    }
    
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
