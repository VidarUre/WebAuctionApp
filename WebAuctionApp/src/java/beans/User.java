package beans;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author TorkelNes
 */
@Stateless
@Entity
@NamedQueries({
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
})
@Table(name = "\"User\"") //User is a reservered SQL keyword - this escapes this
@XmlRootElement
public class User implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    
    @ManyToOne
    private AuctionPlace auctionplace;
    
    private Double rating;  
    private String username;
    private String password;
    private Boolean loggedIn;
    private String email;
    private String phonenumber; 
    
    @OneToOne (cascade = CascadeType.PERSIST, mappedBy="owner", orphanRemoval=true)
    private ProductCatalog productsForSale;
    
    @OneToOne (cascade = CascadeType.PERSIST, mappedBy="owner", orphanRemoval=true)
    private ProductCatalog soldProducts;
    
    @OneToOne (cascade = CascadeType.PERSIST, mappedBy="owner", orphanRemoval=true)
    private ProductCatalog boughtProducts;
    
    @OneToMany (mappedBy = "author", orphanRemoval = true)
    private List<Feedback> feedback;
    
    public User() {
    }   
    
    
    public Long getId() {
        return ID;
    }

    public void setId(Long id) {
        this.ID = id;
    }

    public double getRating() {
        return this.fetchAverageFeedback(this.feedback);
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    public String logIn() {
        return ""; // "SUCCESS" or "FAIL"
    }

    @XmlTransient
    public List<Feedback> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<Feedback> feedback) {
        this.feedback = feedback;
    }
    
    public void setPhoneNumber(String n){
        this.phonenumber = n;
    }
    
    public String getPhoneNumber(){
        return phonenumber; 
    }
    
    public void setEmail(String n){
        this.email = n;
    }
    
    public String getEmail(){
        return email;
    } 
    
    public AuctionPlace getAuctionplace() {
        return auctionplace;
    }

    public void setAuctionplace(AuctionPlace auctionplace) {
        this.auctionplace = auctionplace;
    }
    
    @XmlTransient
    public ProductCatalog getProductsForSale() {
        return productsForSale;
    }

    public void setProductsForSale(ProductCatalog productsForSale) {
        this.productsForSale = productsForSale;
    }
    
    @XmlTransient
    public ProductCatalog getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductCatalog soldProducts) {
        this.soldProducts = soldProducts;
    }
    
    @XmlTransient
    public ProductCatalog getBoughtProducts() {
        return boughtProducts;
    }

    public void setBoughtProducts(ProductCatalog boughtProducts) {
        this.boughtProducts = boughtProducts;
    }
    
    /**
     * Calculates the average feedback from all the user's feedback.
     * @param feedback The list of feedback
     * @return An average score of the user's feedback
     */
    private Double fetchAverageFeedback(List<Feedback> feedback) {
        Double sum = 0.0;
        if(feedback.isEmpty()){
            return 0.0;
        }
        for(Feedback f : feedback) {
            sum += f.getRating();
        }
        Double average = sum/feedback.size();
        return average;
    }
}
