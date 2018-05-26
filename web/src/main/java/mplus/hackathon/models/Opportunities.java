package mplus.hackathon.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mplus.hackathon.models.enums.Status;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Anatoliy on 07.10.2017.
 */
@Entity
    public class Opportunities {

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

    @JsonIgnore
    @OneToMany(mappedBy = "opportunity")
    private List<Rate> rates;

    public Opportunities() {
        this.rating=0.0;
    }

    public Opportunities(String name, String offerDescription, String photoPath, Double price, Status status, Boolean isActive) {
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

    public Opportunities setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Opportunities setName(String name) {
        this.name = name;
        return this;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public Opportunities setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
        return this;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public Opportunities setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Opportunities setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Opportunities setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Opportunities setActive(Boolean active) {
        isActive = active;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Opportunities setCategory(Category category) {
        this.category = category;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getRating() {
        return rating;
    }

    public Opportunities setRating(Double rating) {
        this.rating = rating;
        return this;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public Opportunities setRates(List<Rate> rates) {
        this.rates = rates;
        return this;
    }
}
