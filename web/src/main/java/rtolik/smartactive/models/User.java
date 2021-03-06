package rtolik.smartactive.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rtolik.smartactive.models.enums.Role;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by Anatoliy on 07.10.2017.
 */
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "VARCHAR(50)")
    private String name;
    private String password;
    @Column(columnDefinition = "VARCHAR(100)")
    private String email;
    @Column(columnDefinition = "VARCHAR(7)")
    private String color;
    private Boolean isActive;
    private Integer numOfAppeals;
    private Integer bansCount;
    private String phone;
    private Role role;
    private String dateOfban;
    @JsonIgnore
    private String uuid;
    @Cascade(CascadeType.DELETE)
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Opportunity> services;
    @Cascade(CascadeType.DELETE)
    @JsonIgnore
    @ManyToMany
    private List<Opportunity> liked;

    public User() {
    }

    public User(String name, String password, String email,
                String color, Boolean isActive, String phone, String uuid) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.color = color;
        this.isActive = isActive;
        this.phone = phone;
        this.uuid = uuid;
        this.bansCount=5;
        this.numOfAppeals=0;
        this.role=Role.DEFAULT;
        this.dateOfban="";
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getColor() {
        return color;
    }

    public User setColor(String color) {
        this.color = color;
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public User setActive(Boolean active) {
        isActive = active;
        return this;
    }

    public Integer getNumOfAppeals() {
        return numOfAppeals;
    }

    public User setNumOfAppeals(Integer numOfAppeals) {
        this.numOfAppeals = numOfAppeals;
        return this;
    }

    public Integer getBansCount() {
        return bansCount;
    }

    public User setBansCount(Integer bansCount) {
        this.bansCount = bansCount;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    public String getDateOfban() {
        return dateOfban;
    }

    public User setDateOfban(String dateOfban) {
        this.dateOfban = dateOfban;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public User setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public List<Opportunity> getServices() {
        return services;
    }

    public User setServices(List<Opportunity> services) {
        this.services = services;
        return this;
    }

    public List<Opportunity> getLiked() {
        return liked;
    }

    public User setLiked(List<Opportunity> liked) {
        this.liked = liked;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
