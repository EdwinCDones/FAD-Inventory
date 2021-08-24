/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fad.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GabuAndLemo
 */
@Entity
@Table(name = "ordeninventario", catalog = "fad", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordeninventario.findAll", query = "SELECT o FROM Ordeninventario o")
    , @NamedQuery(name = "Ordeninventario.findByIdOrdeninventario", query = "SELECT o FROM Ordeninventario o WHERE o.idOrdeninventario = :idOrdeninventario")
    , @NamedQuery(name = "Ordeninventario.findByResponsableOi", query = "SELECT o FROM Ordeninventario o WHERE o.responsableOi = :responsableOi")
    , @NamedQuery(name = "Ordeninventario.findByFechaOi", query = "SELECT o FROM Ordeninventario o WHERE o.fechaOi = :fechaOi")
    , @NamedQuery(name = "Ordeninventario.findByDescripcionOi", query = "SELECT o FROM Ordeninventario o WHERE o.descripcionOi = :descripcionOi")})
public class Ordeninventario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_ordeninventario")
    private String idOrdeninventario;
    @Column(name = "responsable_oi")
    private String responsableOi;
    @Column(name = "fecha_oi")
    @Temporal(TemporalType.DATE)
    private Date fechaOi;
    @Column(name = "descripcion_oi")
    private String descripcionOi;
    @JoinColumn(name = "id_tipordeninv", referencedColumnName = "id_tipordeninv")
    @ManyToOne
    private Tipordeninv idTipordeninv;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idUsuario;
    @OneToMany(mappedBy = "idOrdeninventario")
    private Collection<Movimientoinventario> movimientoinventarioCollection;

    public Ordeninventario() {
    }

    public Ordeninventario(String idOrdeninventario) {
        this.idOrdeninventario = idOrdeninventario;
    }

    public String getIdOrdeninventario() {
        return idOrdeninventario;
    }

    public void setIdOrdeninventario(String idOrdeninventario) {
        this.idOrdeninventario = idOrdeninventario;
    }

    public String getResponsableOi() {
        return responsableOi;
    }

    public void setResponsableOi(String responsableOi) {
        this.responsableOi = responsableOi;
    }

    public Date getFechaOi() {
        return fechaOi;
    }

    public void setFechaOi(Date fechaOi) {
        this.fechaOi = fechaOi;
    }

    public String getDescripcionOi() {
        return descripcionOi;
    }

    public void setDescripcionOi(String descripcionOi) {
        this.descripcionOi = descripcionOi;
    }

    public Tipordeninv getIdTipordeninv() {
        return idTipordeninv;
    }

    public void setIdTipordeninv(Tipordeninv idTipordeninv) {
        this.idTipordeninv = idTipordeninv;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
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
        hash += (idOrdeninventario != null ? idOrdeninventario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordeninventario)) {
            return false;
        }
        Ordeninventario other = (Ordeninventario) object;
        if ((this.idOrdeninventario == null && other.idOrdeninventario != null) || (this.idOrdeninventario != null && !this.idOrdeninventario.equals(other.idOrdeninventario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fad.entities.Ordeninventario[ idOrdeninventario=" + idOrdeninventario + " ]";
    }
    
}
