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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

/**
 *
 * @author jhonatan
 */
@Entity
@Table(name = "unite")
@NamedQueries({
    @NamedQuery(name = "Unite.findAll", query = "SELECT u FROM Unite u"),
    @NamedQuery(name = "Unite.findById", query = "SELECT u FROM Unite u WHERE u.id = :id"),
    @NamedQuery(name = "Unite.findByCreated", query = "SELECT u FROM Unite u WHERE u.created = :created"),
    @NamedQuery(name = "Unite.findByDeleted", query = "SELECT u FROM Unite u WHERE u.deleted = :deleted"),
    @NamedQuery(name = "Unite.findByModified", query = "SELECT u FROM Unite u WHERE u.modified = :modified"),
    @NamedQuery(name = "Unite.findByName", query = "SELECT u FROM Unite u WHERE u.name = :name")})
public class Unite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "created")
    
    private LocalDate created;
    @Column(name = "deleted")
    
    private LocalDate deleted;
    @Column(name = "modified")
    
    private LocalDate modified;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkUnit")
    private Collection<Student> studentCollection;
    @JoinColumn(name = "fk_district", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private District fkDistrict;

    public Unite() {
    }

    public Unite(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getDeleted() {
        return deleted;
    }

    public void setDeleted(LocalDate deleted) {
        this.deleted = deleted;
    }

    public LocalDate getModified() {
        return modified;
    }

    public void setModified(LocalDate modified) {
        this.modified = modified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Student> getStudentCollection() {
        return studentCollection;
    }

    public void setStudentCollection(Collection<Student> studentCollection) {
        this.studentCollection = studentCollection;
    }

    public District getFkDistrict() {
        return fkDistrict;
    }

    public void setFkDistrict(District fkDistrict) {
        this.fkDistrict = fkDistrict;
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
        if (!(object instanceof Unite)) {
            return false;
        }
        Unite other = (Unite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootsystem.entities.Unite[ id=" + id + " ]";
    }
    
}
