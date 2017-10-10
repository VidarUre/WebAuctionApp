package database;

import beans.Feedback;
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
public class FeedbackCM {
    
    @PersistenceContext(unitName = "WebAuctionAppPU")
    private EntityManager em;
    
    public void storeFeedback(Feedback feedback){
        em.persist(feedback);
        em.flush();
    }
    
     public Feedback findFeedback(Long id) {
        Feedback feedback = em.find(Feedback.class, id);
        return feedback;
    }
     
    public void UpdateRating(Feedback feedback, Double rating){
        feedback.setRating(rating);
        em.merge(feedback);
    }
    
    public void UpdateContent(Feedback feedback, String content){
        feedback.setContent(content);
        em.merge(feedback);
    }

}
