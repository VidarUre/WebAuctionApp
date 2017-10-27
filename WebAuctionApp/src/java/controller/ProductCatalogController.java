package controller;

import beans.Bid;
import beans.Product;
import beans.ProductCatalog;
import beans.User;
import database.BidCM;
import database.ProductCM;
import database.ProductCatalogCM;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Inject;
import messageservices.EmailProducer;

/**
 * Handles the product catalogs.
 *
 * @author TorkelNes
 */
@Named(value = "productCatalogController")
@ManagedBean
@SessionScoped
public class ProductCatalogController implements Serializable {

    @Inject
    private UserController userController;
    
    @Inject
    private EmailProducer emailProd;

    private Product product;
    private String name;
    private String picture; // May change
    private String features;
    private Bid bid;

    private ProductCatalog productsForSale;
    private ProductCatalog soldProducts;
    private ProductCatalog boughtProducts;

    @EJB
    private ProductCatalogCM productCatalogCM;
    
    @EJB
    private ProductCM productCM;

    @EJB
    private BidCM bidCM;
    /**
     * Creates a new instance of ProductCatalog
     */
    public ProductCatalogController() {
    }

    /**
     * Publishes the product.
     *
     * @return the resulting page of submitProduct
     */
    public String publishProduct() {
        return submitProduct(true);
    }

    /**
     * Saves the product without publishing.
     *
     * @return the resulting page of submitProduct
     */
    public String saveWithoutPublishing() {
        return submitProduct(false);
    }

    /**
     * Creates a product.
     *
     * @param shouldPublish true if product should be published, false if not
     * @return the created product
     */
    public Product createProduct(boolean shouldPublish) {
        if (productIsValid(this.name, this.picture, this.features)) {
            this.product = new Product();
            this.product.setName(this.name);
            this.product.setPicture(this.picture);
            this.product.setFeatures(this.features);
            this.product.setPublished(shouldPublish);
            this.product.setRemainingTime(1000000);
            // Start nedtelling

            //Oppretter et bud med 0 i verdi
            this.bid = new Bid();
            this.bid.setAmount(0.0);
            this.bid.setBidder(null);
            this.bid.setProduct(product);
            this.product.setCurrentBid(bid);

        }
        return this.product;
    }

    /**
     * Submits a product for storing.
     *
     * @param shouldPublish true if product should be published, false if not
     * @return products page if success, publishproduct page if not
     */
    private String submitProduct(Boolean shouldPublish) {
        String result;

        this.product = createProduct(shouldPublish);
        if (this.product != null) {
            this.productsForSale = this.userController.getUser().getProductsForSale();
            this.productsForSale.addProduct(product);
            this.userController.getUser().setProductsForSale(this.productsForSale);
            this.product.setCatalog(this.productsForSale);
            productCatalogCM.updateProductCatalog(this.productsForSale);
            result = "products";
        } else {
            result = "publishproduct";
        }
        return result;
    }

    /**
     * Checks if a given product has valid data.
     *
     * @param name The product's name
     * @param picture The product's picture
     * @param features The product's features info
     * @return true if valid, false if not
     */
    public boolean productIsValid(String name, String picture, String features) {
        return name != null && name.length() > 0 && picture != null && picture.length() > 0 && features != null && features.length() > 0;
    }

    /**
     * Adds a product to the "for sale" catalog.
     *
     * @param product The product to be added
     */
    public void addProduct(Product product) {
        productsForSale.addProduct(product);
    }

    public String endAuction(Product product) {
        //product.setCatalog(this.soldProducts);
        //productCatalogCM.updateProductCatalog(this.productsForSale);
        //productCatalogCM.updateProductCatalog(this.soldProducts);
        productCM.UpdatePublished(product, false);

        User winner = product.getCurrentBid().getBidder();
        this.emailProd.sendEmail(winner, product);
        return "products";
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

    public Bid getBid() {
        return bid;
    }

}
