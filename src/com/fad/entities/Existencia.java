/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fad.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "existencia", catalog = "fad", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Existencia.findAll", query = "SELECT e FROM Existencia e")
    , @NamedQuery(name = "Existencia.findByIdExistencia", query = "SELECT e FROM Existencia e WHERE e.idExistencia = :idExistencia")
    , @NamedQuery(name = "Existencia.findByExistenciaIniE", query = "SELECT e FROM Existencia e WHERE e.existenciaIniE = :existenciaIniE")
    , @NamedQuery(name = "Existencia.findByExistenciaActualE", query = "SELECT e FROM Existencia e WHERE e.existenciaActualE = :existenciaActualE")
    , @NamedQuery(name = "Existencia.findByValorTotalE", query = "SELECT e FROM Existencia e WHERE e.valorTotalE = :valorTotalE")})
public class Existencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_existencia")
    private String idExistencia;
    @Column(name = "existencia_ini_e")
    private BigInteger existenciaIniE;
    @Column(name = "existencia_actual_e")
    private BigInteger existenciaActualE;
    @Column(name = "valor_total_e")
    private Double valorTotalE;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    @ManyToOne
    private Categoria idCategoria;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto idProducto;
    @OneToMany(mappedBy = "idExistencia")
    private Collection<Movimientoinventario> movimientoinventarioCollection;

    public Existencia() {
    }

    public Existencia(String idExistencia) {
        this.idExistencia = idExistencia;
    }

    public String getIdExistencia() {
        return idExistencia;
    }

    public void setIdExistencia(String idExistencia) {
        this.idExistencia = idExistencia;
    }

    public BigInteger getExistenciaIniE() {
        return existenciaIniE;
    }

    public void setExistenciaIniE(BigInteger existenciaIniE) {
        this.existenciaIniE = existenciaIniE;
    }

    public BigInteger getExistenciaActualE() {
        return existenciaActualE;
    }

    public void setExistenciaActualE(BigInteger existenciaActualE) {
        this.existenciaActualE = existenciaActualE;
    }

    public Double getValorTotalE() {
        return valorTotalE;
    }

    public void setValorTotalE(Double valorTotalE) {
        this.valorTotalE = valorTotalE;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    @XmlTransient
    public Collection<Movimientoinventario> getMovimientoinventarioCollection() {
        return movimientoinventarioCollection;
    }

    public void setMovimientoinventarioCollection(Collection<Movimientoinventario> movimientoinventarioCollection) {
        this.movimientoinventarioCollection = movimientoinventarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExistencia != null ? idExistencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Existencia)) {
            return false;
        }
        Existencia other = (Existencia) object;
        if ((this.idExistencia == null && other.idExistencia != null) || (this.idExistencia != null && !this.idExistencia.equals(other.idExistencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fad.entities.Existencia[ idExistencia=" + idExistencia + " ]";
    }
    
}
