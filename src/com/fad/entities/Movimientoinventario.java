/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fad.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GabuAndLemo
 */
@Entity
@Table(name = "movimientoinventario", catalog = "fad", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movimientoinventario.findAll", query = "SELECT m FROM Movimientoinventario m")
    , @NamedQuery(name = "Movimientoinventario.findByIdMovimiento", query = "SELECT m FROM Movimientoinventario m WHERE m.idMovimiento = :idMovimiento")
    , @NamedQuery(name = "Movimientoinventario.findByFechaMov", query = "SELECT m FROM Movimientoinventario m WHERE m.fechaMov = :fechaMov")
    , @NamedQuery(name = "Movimientoinventario.findByObservacionMov", query = "SELECT m FROM Movimientoinventario m WHERE m.observacionMov = :observacionMov")
    , @NamedQuery(name = "Movimientoinventario.findByCantidadMov", query = "SELECT m FROM Movimientoinventario m WHERE m.cantidadMov = :cantidadMov")
    , @NamedQuery(name = "Movimientoinventario.findByIncremMov", query = "SELECT m FROM Movimientoinventario m WHERE m.incremMov = :incremMov")
    , @NamedQuery(name = "Movimientoinventario.findByDismMov", query = "SELECT m FROM Movimientoinventario m WHERE m.dismMov = :dismMov")})
public class Movimientoinventario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_movimiento")
    private String idMovimiento;
    @Column(name = "fecha_mov")
    @Temporal(TemporalType.DATE)
    private Date fechaMov;
    @Column(name = "observacion_mov")
    private String observacionMov;
    @Column(name = "cantidad_mov")
    private BigInteger cantidadMov;
    @Column(name = "increm_mov")
    private BigInteger incremMov;
    @Column(name = "dism_mov")
    private BigInteger dismMov;
    @JoinColumn(name = "id_existencia", referencedColumnName = "id_existencia")
    @ManyToOne
    private Existencia idExistencia;
    @JoinColumn(name = "id_ordeninventario", referencedColumnName = "id_ordeninventario")
    @ManyToOne
    private Ordeninventario idOrdeninventario;

    public Movimientoinventario() {
    }

    public Movimientoinventario(String idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(String idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }

    public String getObservacionMov() {
        return observacionMov;
    }

    public void setObservacionMov(String observacionMov) {
        this.observacionMov = observacionMov;
    }

    public BigInteger getCantidadMov() {
        return cantidadMov;
    }

    public void setCantidadMov(BigInteger cantidadMov) {
        this.cantidadMov = cantidadMov;
    }

    public BigInteger getIncremMov() {
        return incremMov;
    }

    public void setIncremMov(BigInteger incremMov) {
        this.incremMov = incremMov;
    }

    public BigInteger getDismMov() {
        return dismMov;
    }

    public void setDismMov(BigInteger dismMov) {
        this.dismMov = dismMov;
    }

    public Existencia getIdExistencia() {
        return idExistencia;
    }

    public void setIdExistencia(Existencia idExistencia) {
        this.idExistencia = idExistencia;
    }

    public Ordeninventario getIdOrdeninventario() {
        return idOrdeninventario;
    }

    public void setIdOrdeninventario(Ordeninventario idOrdeninventario) {
        this.idOrdeninventario = idOrdeninventario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMovimiento != null ? idMovimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimientoinventario)) {
            return false;
        }
        Movimientoinventario other = (Movimientoinventario) object;
        if ((this.idMovimiento == null && other.idMovimiento != null) || (this.idMovimiento != null && !this.idMovimiento.equals(other.idMovimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fad.entities.Movimientoinventario[ idMovimiento=" + idMovimiento + " ]";
    }
    
}
