package controller;

import beans.AuctionPlace;
import beans.Feedback;
import beans.ProductCatalog;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import beans.User;
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
    private User seller;
    
    private ProductCatalog productsForSale;
    private ProductCatalog soldProducts;
    private ProductCatalog boughtProducts;
    private List<Feedback> feedback;
    private List<Feedback> sFeedback;
    
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

    /**
     * Navigates to the register page
     * @return the register page
     */
    public String navigate() {
        return "register";
    }
    
    /**
     * Navigates to the home page
     * @return the home page
     */
    public String home() {
        return "products";
    }
    
    /**
     * Logs in the user
     * @return the product page if success, login page if not
     */
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
    
    /**
     * Logs out the user.
     * @return the login page
     */
    public String logOut() {
        this.user.setLoggedIn(false);
        this.user = null;
        return "login";
    }
    
    /**
     * Initializes the registering
     * @return the register page
     */
    public String InitRegister(){
        this.user = new User();
        return "register";
    }
    
    /**
     * Goes to a specific user's profile
     * @param id The ID of the specific user
     * @return the userscreen page
     */
    public String goToUser(Long id) {
        this.feedback = userCM.getFeedbackByUser(this.user);
        return "userscreen";
    }
    
    /**
     * Goes to a specific seller's profile
     * @param seller the specific seller
     * @return  the sellerscreen page
     */
    public String goToSeller(User seller){
        this.sFeedback = userCM.getFeedbackByUser(seller);
        return "sellerscreen";
    }
 
    /**
     * Registers a new user.
     * @return The login page if successful, register page if not
     */
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
            
            return "login";
        } else return "register";
    }
    
    /**
     * Creates all the user's product catalogs.
     */
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
    }
    
    /**
     * Submits a feedback.
     * @return  The same page.
     */
    public String submitFeedback() {
        if(this.feedbackContent != null && this.feedbackRating != null) {
            Feedback newFeedback = new Feedback();
            newFeedback.setAuthor(this.user);
            newFeedback.setContent(this.feedbackContent);
            newFeedback.setRating(this.feedbackRating);
            this.sFeedback.add(newFeedback);
            this.seller.setFeedback(this.sFeedback);
            userCM.updateUser(this.seller);
        }
        return "";
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
        public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
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
    
    
    public List<Feedback> getSFeedback() {
        return this.sFeedback;
    }
    
    public void setSFeedback(List<Feedback> sFeedback) {
        this.sFeedback = sFeedback;
    }
    
    /**
     * Checks if the info from the registering is valid.
     * @param un username
     * @param em email
     * @param pn phone number
     * @param pw password
     * @return true if valid, false if not
     */
    public boolean isValidRegister(String un, String em, String pn, String pw) {
        return isValidUsername(un) && isValidEmail(em) && isValidPhonenumber(pn) && isValidPassword(pw);
    }
    
    /**
     * Checks if given username is valid. (NOT IMPLEMENTED)
     * @param username The username
     * @return true if valid, false if not
     */
    private boolean isValidUsername(String username) {
        return true;
    }
    
    /**
     * Checks if given password is valid. (NOT IMPLEMENTED)
     * @param password The password
     * @return true if valid, false if not
     */
    private boolean isValidPassword(String password) {
        return true;
    }
    
    /**
     * Checks if given email is valid. (NOT IMPLEMENTED)
     * @param email The email
     * @return true if valid, false if not
     */
    private boolean isValidEmail(String email) {
        return true;
    }
    
    /**
     * Checks if given phone number is valid. (NOT IMPLEMENTED)
     * @param phonenumber The phone number
     * @return true if valid, false if not
     */
    private boolean isValidPhonenumber(String phonenumber) {
        return true;
    }
}
