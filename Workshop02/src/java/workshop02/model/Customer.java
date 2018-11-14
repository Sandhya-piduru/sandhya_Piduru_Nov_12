/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workshop02.model;

import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import static workshop02.model.PurchaseOrder_.customer;

@Entity
public class Customer {

@Id
        @Column(name = "customer_id")
        private Integer customerId;
        private String  Name;
        private String Addressline1;
        private String Addressline2;
        private String City;
        private String State;
        private String Zip;
        private String Phone;
        private String Fax;
        private String Email;
        
        @Column(name = "credit_limit")
        private Integer Creditlimit;
        
        @ManyToOne
        @JoinColumn(name = "discount_code",
                referencedColumnName = "discount_code")
        private DiscountCode discountCode;
        
        @OneToMany(mappedBy = "customer")
        private List<PurchaseOrder> purchaseOrders;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAddressline1() {
        return Addressline1;
    }

    public void setAddressline1(String Addressline1) {
        this.Addressline1 = Addressline1;
    }

    public String getAddressline2() {
        return Addressline2;
    }

    public void setAddressline2(String Addressline2) {
        this.Addressline2 = Addressline2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getZip() {
        return Zip;
    }
    public void setZip(String Zip) {
        this.Zip = Zip;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String Fax) {
        this.Fax = Fax;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public Integer getCreditlimit() {
        return Creditlimit;
    }

    public void setCreditlimit(Integer Creditlimit) {
        this.Creditlimit = Creditlimit;
    }

    public DiscountCode getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }  
    
    public JsonObject toJson() {
        return (Json.createObjectBuilder()
                .add("customerId", customerId)
                .add("name", Name)
                .add("addressline1", Addressline1)
                .add("addressline2", Addressline2)
                .add("city", City)
                .add("state", State)
                .add("Zip", Zip)
                .add("Phone", Phone)
                .add("fax", Fax)
                .add("email", Email)
                .add("discountCode", discountCode.getDiscountCode().toString()))
                .add("creditLimit", Creditlimit)
                .build();
                }
}

 
        
    

