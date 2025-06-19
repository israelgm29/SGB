/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bootsystem.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author jhonatan
 */
@Entity
@Table(name = "levlusr")
@NamedQueries({
    @NamedQuery(name = "Levlusr.findAll", query = "SELECT l FROM Levlusr l"),
    @NamedQuery(name = "Levlusr.findById", query = "SELECT l FROM Levlusr l WHERE l.id = :id"),
    @NamedQuery(name = "Levlusr.findByDescription", query = "SELECT l FROM Levlusr l WHERE l.description = :description"),
    @NamedQuery(name = "Levlusr.findByName", query = "SELECT l FROM Levlusr l WHERE l.name = :name")})
public class Levlusr implements Serializable {

    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Size(max = 255)
    @Column(name = "name")
    private String name;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkLvlusr")
    private Collection<Sysuser> sysuserCollection;

    public Levlusr() {
    }

    public Levlusr(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Collection<Sysuser> getSysuserCollection() {
        return sysuserCollection;
    }

    public void setSysuserCollection(Collection<Sysuser> sysuserCollection) {
        this.sysuserCollection = sysuserCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Levlusr)) {
            return false;
        }
        Levlusr other = (Levlusr) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
