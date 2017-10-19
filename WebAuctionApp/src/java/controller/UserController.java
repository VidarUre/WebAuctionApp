package controller;

import beans.Feedback;
import beans.Product;
import beans.ProductCatalog;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import beans.User;
import database.BidCM;
import database.UserCM;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;

/**
 * 
 * @author TorkelNes
 */
@Named(value = "userController")
@ManagedBean
@SessionScoped
public class UserController implements Serializable {
    
    private User user;
    private User seller;
    
    private ProductCatalog productsForSale;
    private ProductCatalog soldProducts;
    private ProductCatalog boughtProducts;
    private List<Feedback> feedback;
    private List<Feedback> sFeedback;
    private Double newAmount;
    
    private String username;
    private String email;
    private String phonenumber;
    private String password;
    
    private Double feedbackRating;
    private String feedbackContent;
    
    @EJB
    private UserCM userCM;
    
    @EJB 
    BidCM bidCM;
    
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
        
        isValid = isValidLogin(this.username, this.password);
        
        if(isValid) {
            this.user = this.userCM.findUserByUsername(this.getUsername());
            if(!passwordMatches(this.user, this.password)) {
                this.user = null;
            } else {
                this.user.setLoggedIn(true);
            }
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
     * Places a new bid on the product.
     * @param product The product to bid on
     * @return The same page of the product
     */
    public String placeBid(Product product) {   
        if(product.getCurrentBid().getAmount() < this.newAmount) {
            bidCM.UpdateBid(product.getCurrentBid(), this.newAmount, this.user);
            }
        return "product"; // The same product screen
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
     * Checks if the given login credentials are of valid format
     * @param username The username
     * @param password The password
     * @return true if valid, false if not
     */
    public boolean isValidLogin(String username, String password) {
       return username != null && username.trim().length() > 0 && password != null && password.trim().length() > 0;
    }
    
    /**
     * Checks if the given passord matches that of the user
     * @param user The user
     * @param password The given password
     * @return true if passwords match, false if not
     */
    public boolean passwordMatches(User user, String password) {
        return user.getPassword().equals(password);
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
     * Checks if given username is valid.
     * @param username The username
     * @return true if valid, false if not
     */
    private boolean isValidUsername(String username) {
        return username != null && username.trim().length() > 0 && username.length() < 20;
    }
    
    /**
     * Checks if given password is valid.
     * @param password The password
     * @return true if valid, false if not
     */
    private boolean isValidPassword(String password) {
        return password != null && password.trim().length() > 0 && password.length() < 20;
    }
    
    /**
     * Checks if given email is valid.
     * @param email The email
     * @return true if valid, false if not
     */
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        return mat.matches();
    }
    
    /**
     * Checks if given phone number is valid.
     * @param phonenumber The phone number
     * @return true if valid, false if not
     */
    private boolean isValidPhonenumber(String phonenumber) {
        return phonenumber != null && phonenumber.trim().length() > 0 && phonenumber.trim().length() < 20 && phonenumber.matches("[0-9]+");
    }
    
    public Double getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(Double newAmount) {
        this.newAmount = newAmount;
    }
}
