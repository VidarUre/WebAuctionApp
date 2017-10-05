/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import beans.Bid;
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
public class BidCM {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "WebAuctionAppPU")
    private EntityManager em;
    
    public void storeBid(Bid bid){
        em.persist(bid);
        em.flush();
    }
    
     public Bid findBid(Long id) {
        Bid bid = em.find(Bid.class, id);
        return bid;
    }
     
    public void UpdateBid(Bid bid, Double amount){
        bid.setAmount(amount);
        em.merge(bid);
    }

}
