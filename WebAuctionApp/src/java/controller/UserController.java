/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import beans.User;
import database.UserCM;

/**
 *
 * @author TorkelNes
 */
@Named(value = "userController")
@ManagedBean
@SessionScoped
public class UserController implements Serializable {

    @EJB
    private User user;
    private String username;
    private String email;
    private String phonenumber;
    private String password;
    
    private UserCM userCM;

    /**
     * Creates a new instance of UserController
     */
    public UserController() {
    }

    public String navigate() {
        return "register";
    }
    
    public String home() {
        return "products";
    }
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String result;
        boolean isValid;
        
        this.username = request.getParameter("username");
        this.password = request.getParameter("password");
        
        isValid = this.userCM.isValidLogin(this.username, this.password);
        
        if(isValid) {
            userCM.findUser(this.username);
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("username", this.username);
            session.setAttribute("password", this.password);
            result = "/products";
        } else {
            //Bruker er ugyldig
            result = "login";
        }
       return null; 
    }
    
    public String InitRegister(){
        this.user = new User();
        return "register";
    }
    
    public String register() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String result;
        
        this.username = request.getParameter("username");
        this.email = request.getParameter("email");
        this.phonenumber = request.getParameter("phonenumber");
        this.password = request.getParameter("password");
        
        if(this.user.isValidRegister(this.username, this.email, this.phonenumber, this.password)) {
            this.user.setUsername(this.username);
            this.user.setEmail(this.email);
            this.user.setPhoneNumber(this.phonenumber);
            this.user.setPassword(this.password);
          //  this.user.storeUser(this.user);
            return "login";
        } else return "register";
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
