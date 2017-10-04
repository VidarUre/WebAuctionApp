/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.AuctionPlace;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author TorkelNes
 */
@Named(value = "auctionPlace")
@SessionScoped
public class AuctionPlaceController implements Serializable {

    @EJB
    private AuctionPlace auctionPlace;

    /**
     * Creates a new instance of AuctionPlace
     */
    public AuctionPlaceController() {
    }
    
}
