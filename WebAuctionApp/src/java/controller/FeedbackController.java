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
@Named(value = "feedbackController")
@SessionScoped
public class FeedbackController implements Serializable {

    @EJB
    private Feedback feedback;

    /**
     * Creates a new instance of Feedback
     */
    public FeedbackController() {
    }
    
    
}
