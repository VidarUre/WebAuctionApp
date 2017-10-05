/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import beans.User;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Vidar
 */
@Stateless
//@LocalBean
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
        //User foundUser = em.find(User.class, email);
        return foundUser;
    }
    
    public void deleteUser(User user){
        em.remove(user);
    }
    
    public boolean isValidLogin(String username, String password) {
       return true;
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
