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
    private List<Feedback> feedback; 
    
    private String username;
    private String email;
    private String phonenumber;
    private String password;
    
    private Double feedbackRating;
    private String feedbackContent;
    
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
    
    public String goToUser(Long id) {
        this.feedback = userCM.getFeedbackByUser(this.user);
        return "userscreen";
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
    
    public String submitFeedback() {
        if(this.feedbackContent != null && this.feedbackRating != null) {
            Feedback newFeedback = new Feedback();
            newFeedback.setAuthor(this.user);
            newFeedback.setContent(this.feedbackContent);
            newFeedback.setRating(this.feedbackRating);
            this.feedback.add(newFeedback);
            this.user.setFeedback(this.feedback);
            userCM.updateUser(this.user);
        }
        return "";
    }
    
    public Double fetchAverageFeedback() {
        Double sum = 0.0;
        for(Feedback f : this.feedback) {
            sum += f.getRating();
        }
        Double average = sum/this.feedback.size();
        return average;
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
    
    public List<Feedback> getFeedback() {
        return this.feedback;
    }
    
    public void setFeedback(List<Feedback> feedback) {
        this.feedback = feedback;
    }
    
    public Double getFeedbackRating() {
        return feedbackRating;
    }

    public void setFeedbackRating(Double feedbackRating) {
        this.feedbackRating = feedbackRating;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
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
