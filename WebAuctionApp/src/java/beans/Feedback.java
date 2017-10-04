/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author S1ndr3
 */
@Stateless
@LocalBean
@Entity
@Table(name = "feedback")
@NamedQueries({
    //@NamedQuery(name = "Feedback.findByAuthorID", query = "SELECT f from Feedback f WHERE f.author_id = user.id")
})
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
