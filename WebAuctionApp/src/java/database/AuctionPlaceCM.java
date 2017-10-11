package database;

import beans.AuctionPlace;
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
public class AuctionPlaceCM {
    
    @PersistenceContext(unitName = "WebAuctionAppPU")
    private EntityManager em;
    
    public void storeAuctionPlace(AuctionPlace auctionPlace){
        em.persist(auctionPlace);
        em.flush();
    }
    
     public AuctionPlace findAuctionPlace(Long id) {
        AuctionPlace auctionPlace = em.find(AuctionPlace.class, id);
        return auctionPlace;
    }
     
     public void NewUsers(User[] users){
           for(User user : users){
               em.persist(user);
               em.flush();
           }
     }

}
