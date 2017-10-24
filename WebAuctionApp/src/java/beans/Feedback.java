package beans;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author S1ndr3
 */
@Stateless
@Entity
@NamedQueries({
    @NamedQuery(name = "Feedback.findByAuthorId", query = "SELECT f FROM Feedback f WHERE f.author = :user")
})
@XmlRootElement
public class Feedback implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    
    @ManyToOne
    private User author; 
  
    private Double rating;
    private String content;
    
    public Feedback() {
    }  

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean ratingIsValid(double rating) {
        return rating >= 0 && rating <= 100;
    }
}
