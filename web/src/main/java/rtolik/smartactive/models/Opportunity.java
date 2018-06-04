package rtolik.smartactive.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import rtolik.smartactive.models.enums.Status;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

/**
 * Created by Anatoliy on 07.10.2017.
 */
@Entity
    public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(40)")
    private  String name;

    @Column(columnDefinition = "VARCHAR(4000)")
    private String offerDescription;

    @Column(columnDefinition = "VARCHAR(4000)")
    private String photoPath;

    private Double price;

    private Double rating;

    @Enumerated
    private Status status;

    private Boolean isActive;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @Cascade(CascadeType.DELETE)
    @JsonIgnore
    @OneToMany(mappedBy = "opportunity")
    private List<Rate> rates;

    public Opportunity() {
        this.rating=0.0;
    }

    public Opportunity(String name, String offerDescription, String photoPath, Double price, Status status, Boolean isActive) {
        this.name = name;
        this.offerDescription = offerDescription;
        this.photoPath = photoPath;
        this.price = price;
        this.status = status;
        this.isActive = isActive;
        this.rating=0.0;
    }

    public Integer getId() {
        return id;
    }

    public Opportunity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Opportunity setName(String name) {
        this.name = name;
        return this;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public Opportunity setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
        return this;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public Opportunity setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Opportunity setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Double getRating() {
        return rating;
    }

    public Opportunity setRating(Double rating) {
        this.rating = rating;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Opportunity setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Opportunity setActive(Boolean active) {
        isActive = active;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Opportunity setCategory(Category category) {
        this.category = category;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Opportunity setUser(User user) {
        this.user = user;
        return this;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public Opportunity setRates(List<Rate> rates) {
        this.rates = rates;
        return this;
    }
}
