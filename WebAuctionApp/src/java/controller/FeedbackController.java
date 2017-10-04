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
import beans.Feedback;

/**
 *
 * @author TorkelNes
 */
@Named(value = "feedback")
@SessionScoped
public class FeedbackController implements Serializable {

    @EJB
    private Feedback feedback;

    /**
     * Creates a new instance of Feedback
     */
    public FeedbackController() {
    }
    
    public String submit() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        // Create new feedback
        
        double rating = Double.parseDouble(request.getParameter("rating"));
        if(feedback.ratingIsValid(rating)) {
            feedback.setRating(rating);
        } else {
            // Error message
        }
        
        return ""; // Same user screen
    }
    
}
