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
@Table(name = "categoria", catalog = "fad", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c")
    , @NamedQuery(name = "Categoria.findByIdCategoria", query = "SELECT c FROM Categoria c WHERE c.idCategoria = :idCategoria")
    , @NamedQuery(name = "Categoria.findByNombreCat", query = "SELECT c FROM Categoria c WHERE c.nombreCat = :nombreCat")
    , @NamedQuery(name = "Categoria.findByDescripcionCat", query = "SELECT c FROM Categoria c WHERE c.descripcionCat = :descripcionCat")
    , @NamedQuery(name = "Categoria.findBySiglaCat", query = "SELECT c FROM Categoria c WHERE c.siglaCat = :siglaCat")})
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_categoria")
    private Integer idCategoria;
    @Column(name = "nombre_cat")
    private String nombreCat;
    @Column(name = "descripcion_cat")
    private String descripcionCat;
    @Basic(optional = false)
    @Column(name = "sigla_cat")
    private String siglaCat;
    @OneToMany(mappedBy = "idCategoria")
    private Collection<Existencia> existenciaCollection;

    public Categoria() {
    }

    public Categoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Categoria(Integer idCategoria, String siglaCat) {
        this.idCategoria = idCategoria;
        this.siglaCat = siglaCat;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCat() {
        return nombreCat;
    }

    public void setNombreCat(String nombreCat) {
        this.nombreCat = nombreCat;
    }

    public String getDescripcionCat() {
        return descripcionCat;
    }

    public void setDescripcionCat(String descripcionCat) {
        this.descripcionCat = descripcionCat;
    }

    public String getSiglaCat() {
        return siglaCat;
    }

    public void setSiglaCat(String siglaCat) {
        this.siglaCat = siglaCat;
    }

    @XmlTransient
    public Collection<Existencia> getExistenciaCollection() {
        return existenciaCollection;
    }

    public void setExistenciaCollection(Collection<Existencia> existenciaCollection) {
        this.existenciaCollection = existenciaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoria != null ? idCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.idCategoria == null && other.idCategoria != null) || (this.idCategoria != null && !this.idCategoria.equals(other.idCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreCat;
    }

    
    
    
    
}
