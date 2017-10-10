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
