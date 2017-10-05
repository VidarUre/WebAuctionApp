/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Product;
import beans.ProductCatalog;
import beans.User;
import database.ProductCatalogCM;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author TorkelNes
 */
@Named(value = "productCatalogController")
@ManagedBean
@SessionScoped
public class ProductCatalogController implements Serializable {
    
    @Inject
    private UserController userController;
    
    private Product product;
    private String name;
    private String picture; // May change
    private String features;

    private ProductCatalog productsForSale;
    private ProductCatalog soldProducts;
    private ProductCatalog boughtProducts;
    
    @EJB
    private ProductCatalogCM productCatalogCM;

    /**
     * Creates a new instance of ProductCatalog
     */
    public ProductCatalogController() {
    }
    
    public String publishProduct() {
        return submitProduct(true);
    }
    
    public String saveWithoutPublishing() {
        return submitProduct(false);
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
    
    private String submitProduct(Boolean shouldPublish) {
        String result;
        
        this.product = createProduct(shouldPublish);
        if(this.product != null) {
            this.productsForSale = this.userController.getUser().getProductsForSale();
            this.productsForSale.addProduct(product);
            productCatalogCM.updateProductCatalog(productsForSale);
            result = "products";
        } else {
            result = "publishproduct";
        }
        return result;
    }
    
    public boolean productIsValid(String name, String picture, String features) {
        return name != null && name.length() > 0 && picture != null && picture.length() > 0 && features != null && features.length() > 0;
    }
    
    public void addProduct(Product product) {
        productsForSale.addProduct(product);
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
