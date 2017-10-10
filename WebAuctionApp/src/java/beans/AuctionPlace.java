package beans;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author S1ndr3
 */
@Stateless
@Entity
public class AuctionPlace implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    
    @OneToMany (mappedBy="auctionplace")
    private List<User> users;
    
    public AuctionPlace() {
    }
    
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
    
    public void addUser(User user) {
        users.add(user);
    }
}
