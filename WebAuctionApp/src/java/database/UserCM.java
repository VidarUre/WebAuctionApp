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

/**
 *
 * @author Vidar
 */
@Stateless
@LocalBean
public class UserCM {

    @PersistenceContext(unitName = "WebAuctionAppPU")
    private EntityManager em;
    
    protected EntityManager getEntityManager() {
        return em;
   }
    
    public void storeUser(User user) {
        em.persist(user);
    }
    
    public User getUser(String username) {
        User foundUser = em.find(User.class, username);
        return foundUser;
    }
    
    public void deleteUser(User user){
        em.remove(user);
    }
    
    public boolean isValidLogin(String username, String password) {
       return true;
    }
}
