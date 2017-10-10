package controller;

import beans.AuctionPlace;
import beans.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author TorkelNes
 */
@Named(value = "auctionPlaceController")
@SessionScoped
public class AuctionPlaceController implements Serializable {

    @EJB
    private AuctionPlace auctionPlace;

    /**
     * Creates a new instance of AuctionPlace
     */
    public AuctionPlaceController() {
    }
    
    public List<User> getUsers(){
        return auctionPlace.getUsers();
    }
    
}
