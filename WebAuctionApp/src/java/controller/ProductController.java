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
import beans.Product;

/**
 *
 * @author TorkelNes
 */
@Named(value = "product")
@SessionScoped
public class ProductController implements Serializable {

    @EJB
    private Product product;
    private String name;
    private String picture; // May change
    private String features;

    /**
     * Creates a new instance of Product
     */
    public ProductController() {
    }
    
    public String publish() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String result;
        
        this.name = request.getParameter("name");
        this.picture = request.getParameter("picture");
        this.features = request.getParameter("features");
        
        if(product.productIsValid(this.name, this.picture, this.features)) {
            // Lagre produkt i databasen med published = true
            result = "products";
        } else result = "publishProduct";
        
        return result;
    }
    
    public String saveWithoutPublishing() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String result;
        
        this.name = request.getParameter("name");
        this.picture = request.getParameter("picture");
        this.features = request.getParameter("features");
        
        if(product.productIsValid(this.name, this.picture, this.features)) {
            // Lagre produkt i databasen med published = false
            result = "products";
        } else result = "publishProduct";
        
        return result;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }
    
    
    
}
