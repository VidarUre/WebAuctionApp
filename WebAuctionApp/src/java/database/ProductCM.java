/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import beans.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Vidar
 */
@Stateless
@LocalBean
public class ProductCM {

    @PersistenceContext(unitName = "WebAuctionAppPU")
    private EntityManager em;
    
    public EntityManager getEntityManager() {
        return this.em;
    }
    
    public List<Product> findAllProducts() {
        TypedQuery<Product> query = getEntityManager().createNamedQuery("Product.findAll", Product.class);
        List<Product> allProducts = query.getResultList();
        return allProducts;
    }
    
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
