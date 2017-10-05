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

/**
 *
 * @author TorkelNes
 */
@Named(value = "bidController")
@SessionScoped
public class BidController implements Serializable {

    @EJB
    private Bid bid;

    /**
     * Creates a new instance of Bid
     */
    public BidController() {
    }
    
    public String placeBid() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        Double incomingBid = Double.parseDouble(request.getParameter("newBid"));
        if(incomingBid > bid.getAmount()) {
            bid.setAmount(incomingBid);
            // Update bid amount in database
        }
        return ""; // The same product screen
    }
    
}
