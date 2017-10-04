/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import beans.AuctionPlace;
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
public class AuctionPlaceCM {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PersistenceContext(unitName = "WebAuctionAppPU")
    private EntityManager em;
    
    public void storeAuctionPlace(AuctionPlace auctionPlace){
        em.persist(auctionPlace);
    }
    
     public AuctionPlace getAuctionPlace(Long id) {
        AuctionPlace auctionPlace = em.find(AuctionPlace.class, id);
        return auctionPlace;
    }

}
