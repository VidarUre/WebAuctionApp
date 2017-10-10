package beans;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author S1ndr3
 */
@Stateless
@Entity
public class Bid implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    
    @OneToOne (mappedBy="currentBid")
    private Product product;
    
    private Double amount;
    
    public Bid() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}
