/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fad.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GabuAndLemo
 */
@Entity
@Table(name = "tipordeninv", catalog = "fad", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipordeninv.findAll", query = "SELECT t FROM Tipordeninv t")
    , @NamedQuery(name = "Tipordeninv.findByIdTipordeninv", query = "SELECT t FROM Tipordeninv t WHERE t.idTipordeninv = :idTipordeninv")
    , @NamedQuery(name = "Tipordeninv.findByNombreToi", query = "SELECT t FROM Tipordeninv t WHERE t.nombreToi = :nombreToi")
    , @NamedQuery(name = "Tipordeninv.findByDescripcionToi", query = "SELECT t FROM Tipordeninv t WHERE t.descripcionToi = :descripcionToi")})
public class Tipordeninv implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipordeninv")
    private Integer idTipordeninv;
    @Column(name = "nombre_toi")
    private String nombreToi;
    @Column(name = "descripcion_toi")
    private String descripcionToi;
    @OneToMany(mappedBy = "idTipordeninv")
    private Collection<Ordeninventario> ordeninventarioCollection;

    public Tipordeninv() {
    }

    public Tipordeninv(Integer idTipordeninv) {
        this.idTipordeninv = idTipordeninv;
    }

    public Integer getIdTipordeninv() {
        return idTipordeninv;
    }

    public void setIdTipordeninv(Integer idTipordeninv) {
        this.idTipordeninv = idTipordeninv;
    }

    public String getNombreToi() {
        return nombreToi;
    }

    public void setNombreToi(String nombreToi) {
        this.nombreToi = nombreToi;
    }

    public String getDescripcionToi() {
        return descripcionToi;
    }

    public void setDescripcionToi(String descripcionToi) {
        this.descripcionToi = descripcionToi;
    }

    @XmlTransient
    public Collection<Ordeninventario> getOrdeninventarioCollection() {
        return ordeninventarioCollection;
    }

    public void setOrdeninventarioCollection(Collection<Ordeninventario> ordeninventarioCollection) {
        this.ordeninventarioCollection = ordeninventarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipordeninv != null ? idTipordeninv.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipordeninv)) {
            return false;
        }
        Tipordeninv other = (Tipordeninv) object;
        if ((this.idTipordeninv == null && other.idTipordeninv != null) || (this.idTipordeninv != null && !this.idTipordeninv.equals(other.idTipordeninv))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreToi;
    }
    
}
