package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import beans.Bid;
import database.BidCM;
import javax.inject.Inject;

/**
 *
 * @author TorkelNes
 */
@Named(value = "bidController")
@SessionScoped
public class BidController implements Serializable {

    @Inject
    private ProductCatalogController productCatalogController;
    
    @EJB private BidCM bidCM;
    
    private double newBid;
    
    private Bid bid;

    /**
     * Creates a new instance of Bid
     */
    public BidController() {
    }
    
   
    
}
