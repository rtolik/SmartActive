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
    private Opportunities opportunity;

    public Rate() {
        this.voices = 0;
    }

    public Rate(Integer grade) {
        this.grade = grade;
        this.voices = 0;
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

    public Opportunities getOpportunity() {
        return opportunity;
    }

    public Rate setOpportunity(Opportunities opportunity) {
        this.opportunity = opportunity;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
