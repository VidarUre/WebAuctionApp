/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.ProductCatalog;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author TorkelNes
 */
@Named(value = "productCatalogController")
@SessionScoped
public class ProductCatalogController implements Serializable {

    @EJB
    private ProductCatalog productCatalog;

    /**
     * Creates a new instance of ProductCatalog
     */
    public ProductCatalogController() {
    }
    
    
    
}
