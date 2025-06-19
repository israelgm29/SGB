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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author jhonatan
 */
@Entity
@Table(name = "folder")
@NamedQueries({
    @NamedQuery(name = "Folder.findAll", query = "SELECT f FROM Folder f"),
    @NamedQuery(name = "Folder.findById", query = "SELECT f FROM Folder f WHERE f.id = :id"),
    @NamedQuery(name = "Folder.findByCode", query = "SELECT f FROM Folder f WHERE f.code = :code"),
    @NamedQuery(name = "Folder.findByCreated", query = "SELECT f FROM Folder f WHERE f.created = :created"),
    @NamedQuery(name = "Folder.findByDeleted", query = "SELECT f FROM Folder f WHERE f.deleted = :deleted"),
    @NamedQuery(name = "Folder.findByModified", query = "SELECT f FROM Folder f WHERE f.modified = :modified"),
    @NamedQuery(name = "Folder.findByRetired", query = "SELECT f FROM Folder f WHERE f.retired = :retired"),
    @NamedQuery(name = "Folder.findByStatus", query = "SELECT f FROM Folder f WHERE f.status = :status")})
public class Folder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "code")
    private String code;
    @Column(name = "created")
    
    private LocalDate created;
    @Column(name = "deleted")
    
    private LocalDate deleted;
    @Column(name = "modified")
    
    private LocalDate modified;
    @Column(name = "retired")
    private Boolean retired;
    @Column(name = "status")
    private Boolean status;
    @JoinColumn(name = "fk_student", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Student fkStudent;

    public Folder() {
    }

    public Folder(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Boolean getRetired() {
        return retired;
    }

    public void setRetired(Boolean retired) {
        this.retired = retired;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Student getFkStudent() {
        return fkStudent;
    }

    public void setFkStudent(Student fkStudent) {
        this.fkStudent = fkStudent;
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
        if (!(object instanceof Folder)) {
            return false;
        }
        Folder other = (Folder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootsystem.entities.Folder[ id=" + id + " ]";
    }
    
}
