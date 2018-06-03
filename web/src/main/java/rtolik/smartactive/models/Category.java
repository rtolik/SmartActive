package rtolik.smartactive.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Anatoliy on 07.10.2017.
 */
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(50)")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Opportunities> services;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public Category setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public List<Opportunities> getServices() {
        return services;
    }

    public Category setServices(List<Opportunities> services) {
        this.services = services;
        return this;
    }
}
