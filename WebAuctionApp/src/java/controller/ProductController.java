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
import database.ProductCM;

/**
 *
 * @author TorkelNes
 */
@Named(value = "productController")
@SessionScoped
public class ProductController implements Serializable {

    private Product product;
    private String name;
    private String picture; // May change
    private String features;
    
    @EJB
    ProductCM productCM;

    /**
     * Creates a new instance of Product
     */
    public ProductController() {
    }
    
    public Product createProduct(boolean shouldPublish) {
        if(productIsValid(this.name, this.picture, this.features)) {
            this.product = new Product();
            this.product.setName(this.name);
            this.product.setPicture(this.picture);
            this.product.setFeatures(this.features);
            if(shouldPublish == true) {
                this.product.setPublished(true);
            } else {
                this.product.setPublished(false);
            }
            this.product.setRemainingTime(1000000);
            // Start nedtelling
        }
        return this.product;
    }
    
    public boolean productIsValid(String name, String picture, String features) {
        return name != null && name.length() > 0 && picture != null && picture.length() > 0 && features != null && features.length() > 0;
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
