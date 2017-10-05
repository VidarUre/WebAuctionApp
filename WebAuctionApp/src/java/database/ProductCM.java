/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import beans.Product;
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
public class ProductCM {

    @PersistenceContext(unitName = "WebAuctionAppPU")
    private EntityManager em;
    
    /*
    public void storeProduct(Product product){
        em.persist(product);
    }
    */
    
     public Product findProduct(Long id) {
        Product product = em.find(Product.class, id);
        return product;
    }
     
    public void UpdateName(Product product, String name){
        product.setName(name);
        em.merge(product);
    }
    
    public void UpdateFeatures(Product product, String feature){
        product.setFeatures(feature);
        em.merge(feature);
    }
    
    public void UpdateRating(Product product, Double rating){
        product.setRating(rating);
        em.merge(product);  
    }

}
