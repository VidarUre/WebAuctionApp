/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import beans.ProductCatalog;
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
public class ProductCatalogCM {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "WebAuctionAppPU")
    private EntityManager em;
    
    public void storeProductCatalog(ProductCatalog productCatalog){
        em.persist(productCatalog);
    }
    
     public ProductCatalog getProductCatalog(Long id) {
        ProductCatalog catalog;
        catalog = em.find(ProductCatalog.class, id);
        return catalog;
    }

}
