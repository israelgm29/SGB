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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 *
 * @author jhonatan
 */
@Entity
@Table(name = "student")
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
    @NamedQuery(name = "Student.findById", query = "SELECT s FROM Student s WHERE s.id = :id"),
    @NamedQuery(name = "Student.findByAddress", query = "SELECT s FROM Student s WHERE s.address = :address"),
    @NamedQuery(name = "Student.findByAge", query = "SELECT s FROM Student s WHERE s.age = :age"),
    @NamedQuery(name = "Student.findByBirthday", query = "SELECT s FROM Student s WHERE s.birthday = :birthday"),
    @NamedQuery(name = "Student.findByCreated", query = "SELECT s FROM Student s WHERE s.created = :created"),
    @NamedQuery(name = "Student.findByDeleted", query = "SELECT s FROM Student s WHERE s.deleted = :deleted"),
    @NamedQuery(name = "Student.findByDni", query = "SELECT s FROM Student s WHERE s.dni = :dni"),
    @NamedQuery(name = "Student.findByEmail", query = "SELECT s FROM Student s WHERE s.email = :email"),
    @NamedQuery(name = "Student.findByLastnameOne", query = "SELECT s FROM Student s WHERE s.lastnameOne = :lastnameOne"),
    @NamedQuery(name = "Student.findByLastnameTwo", query = "SELECT s FROM Student s WHERE s.lastnameTwo = :lastnameTwo"),
    @NamedQuery(name = "Student.findByModified", query = "SELECT s FROM Student s WHERE s.modified = :modified"),
    @NamedQuery(name = "Student.findByName", query = "SELECT s FROM Student s WHERE s.name = :name"),
    @NamedQuery(name = "Student.findByStatus", query = "SELECT s FROM Student s WHERE s.status = :status")})
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 255)
    @NotNull
    @Column(name = "address")
    private String address;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "age")
    private int age;
    
    @Column(name = "birthday")
    @NotNull
    private LocalDate birthday;
    
    @Column(name = "created")
    private LocalDateTime created;
    
    @Column(name = "deleted")
    private LocalDateTime deleted;
    
    @Size(max = 10)
    @NotNull
    @Column(name = "dni")
    private String dni;
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 55)
    @Column(name = "email")
    private String email;
    
    @Size(max = 80)
    @NotNull
    @Column(name = "lastname_one")
    private String lastnameOne;
    
    @Size(max = 80)
    @NotNull
    @Column(name = "lastname_two")
    private String lastnameTwo;
    
    @Column(name = "modified")
    private LocalDateTime modified;
    
    @Size(max = 255)
    @NotNull
    @Column(name = "name")
    private String name;
    
    @Column(name = "status")
    private Boolean status;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkStudent")
    private Collection<Folder> folderCollection;
    @JoinColumn(name = "fk_unit", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Unite fkUnit;

    public Student() {
    }

    public Student(Integer id) {
        this.id = id;
    }

    public Student(Integer id, int age) {
        this.id = id;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Collection<Folder> getFolderCollection() {
        return folderCollection;
    }

    public void setFolderCollection(Collection<Folder> folderCollection) {
        this.folderCollection = folderCollection;
    }

    public Unite getFkUnit() {
        return fkUnit;
    }

    public void setFkUnit(Unite fkUnit) {
        this.fkUnit = fkUnit;
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
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootsystem.entities.Student[ id=" + id + " ]";
    }
    
}
