/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bootsystem.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author jhonatan
 */
@Entity
@Table(name = "sysuser")
@NamedQueries({
    @NamedQuery(name = "Sysuser.findAll", query = "SELECT s FROM Sysuser s"),
    @NamedQuery(name = "Sysuser.findById", query = "SELECT s FROM Sysuser s WHERE s.id = :id"),
    @NamedQuery(name = "Sysuser.findByAddress", query = "SELECT s FROM Sysuser s WHERE s.address = :address"),
    @NamedQuery(name = "Sysuser.findByCreated", query = "SELECT s FROM Sysuser s WHERE s.created = :created"),
    @NamedQuery(name = "Sysuser.findByDeleted", query = "SELECT s FROM Sysuser s WHERE s.deleted = :deleted"),
    @NamedQuery(name = "Sysuser.findByDni", query = "SELECT s FROM Sysuser s WHERE s.dni = :dni"),
    @NamedQuery(name = "Sysuser.findByEmail", query = "SELECT s FROM Sysuser s WHERE s.email = :email"),
    @NamedQuery(name = "Sysuser.findByLastnameOne", query = "SELECT s FROM Sysuser s WHERE s.lastnameOne = :lastnameOne"),
    @NamedQuery(name = "Sysuser.findByLastnameTwo", query = "SELECT s FROM Sysuser s WHERE s.lastnameTwo = :lastnameTwo"),
    @NamedQuery(name = "Sysuser.findByModified", query = "SELECT s FROM Sysuser s WHERE s.modified = :modified"),
    @NamedQuery(name = "Sysuser.findByName", query = "SELECT s FROM Sysuser s WHERE s.name = :name"),
    @NamedQuery(name = "Sysuser.findByPassword", query = "SELECT s FROM Sysuser s WHERE s.password = :password"),
    @NamedQuery(name = "Sysuser.findByStatus", query = "SELECT s FROM Sysuser s WHERE s.status = :status")})
public class Sysuser implements Serializable {

    @Size(max = 255)
    @Column(name = "address")
    private String address;
    @Column(name = "created")
   
    private LocalDateTime created;
    @Column(name = "deleted")
   
    private LocalDateTime deleted;
    @Size(max = 255)
    
    @Column(name = "dni")
    private String dni;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "lastname_one")
    private String lastnameOne;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "lastname_two")
    private String lastnameTwo;
    @Column(name = "modified")
   
    private LocalDateTime modified;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    
    @Column(name = "password")
    private String password;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "status")
    private Boolean status;
    @JoinColumn(name = "fk_lvlusr", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Levlusr fkLvlusr;

    public Sysuser() {
    }

    public Sysuser(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getLastnameOne() {
        return lastnameOne;
    }

    public void setLastnameOne(String lastnameOne) {
        this.lastnameOne = lastnameOne;
    }

    public String getLastnameTwo() {
        return lastnameTwo;
    }

    public void setLastnameTwo(String lastnameTwo) {
        this.lastnameTwo = lastnameTwo;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Levlusr getFkLvlusr() {
        return fkLvlusr;
    }

    public void setFkLvlusr(Levlusr fkLvlusr) {
        this.fkLvlusr = fkLvlusr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof Sysuser)) {
            return false;
        }
        Sysuser other = (Sysuser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootsystem.entities.Sysuser[ id=" + id + " ]";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getDeleted() {
        return deleted;
    }

    public void setDeleted(LocalDateTime deleted) {
        this.deleted = deleted;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
