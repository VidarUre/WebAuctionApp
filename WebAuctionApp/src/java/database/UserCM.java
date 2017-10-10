package database;

import beans.Feedback;
import beans.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Vidar
 */
@Stateless
public class UserCM {

    @PersistenceContext(unitName = "WebAuctionAppPU")
    private EntityManager em;
    
    protected EntityManager getEntityManager() {
        return em;
   }
    
    public void storeUser(User user) {
        em.persist(user);
    }
    
    public User findUserByUsername(String username) {
        User foundUser = null;
        Query createNamedQuery = getEntityManager().createNamedQuery("User.findByUsername");
        
        createNamedQuery.setParameter("username", username);

        if (createNamedQuery.getResultList().size() > 0) {
            foundUser = (User) createNamedQuery.getSingleResult();
        }
        return foundUser;
    }
    
    public User getUserById(Long id) {
        User foundUser = null;
        Query createNamedQuery = getEntityManager().createNamedQuery("User.findById");
        
        createNamedQuery.setParameter("id", id);
        
        if (createNamedQuery.getResultList().size() > 0) {
            foundUser = (User) createNamedQuery.getSingleResult();
        }
        return foundUser;
    }
    
    public List<Feedback> getFeedbackByUser(User user) {
        List<Feedback> foundFeedback = null;
        TypedQuery query = getEntityManager().createNamedQuery("Feedback.findByAuthorId", Feedback.class);
        query.setParameter("user", user);
        foundFeedback = query.getResultList();
        return foundFeedback;
    }
    
    public void deleteUser(User user){
        em.remove(user);
    }
    
    public boolean isValidLogin(String username, String password) {
       return true;
    }
    
    public void updateUser(User user) {
        em.merge(user);
    }
    
    public void UpdateUserRating(User user, Double rating){
        user.setRating(rating);
        em.merge(user);
    }
    
    public void UpdateUsername(User user, String username){
        user.setUsername(username);
        em.merge(user);
    }
    
    public void UpdateEmail(User user, String email){
        user.setEmail(email);
        em.merge(email);
    }
    
    public void UpdatePhonenumber(User user, String phonenumber){
        user.setPhoneNumber(phonenumber);
        em.merge(user);
    }
}
