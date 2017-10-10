/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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
