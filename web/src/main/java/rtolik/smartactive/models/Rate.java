package rtolik.smartactive.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Anatoliy on 08.10.2017.
 */
@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer grade;

    private Integer voices;

    @JsonIgnore
    @ManyToOne
    private Opportunity opportunity;

    public Rate() {
        this.voices = 0;
    }

    public Rate(Integer grade) {
        this.grade = grade;
        this.voices = 0;
    }


    public Integer getId() {
        return id;
    }

    public Rate setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getGrade() {
        return grade;
    }

    public Rate setGrade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public Integer getVoices() {
        return voices;
    }

    public Rate setVoices(Integer voices) {
        this.voices = voices;
        return this;
    }

    public Opportunity getOpportunity() {
        return opportunity;
    }

    public Rate setOpportunity(Opportunity opportunity) {
        this.opportunity = opportunity;
        return this;
    }
}
