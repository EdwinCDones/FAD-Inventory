/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fad.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GabuAndLemo
 */
@Entity
@Table(name = "organizacion", catalog = "fad", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Organizacion.findAll", query = "SELECT o FROM Organizacion o")
    , @NamedQuery(name = "Organizacion.findByIdOrganizacion", query = "SELECT o FROM Organizacion o WHERE o.idOrganizacion = :idOrganizacion")
    , @NamedQuery(name = "Organizacion.findByNombreOrg", query = "SELECT o FROM Organizacion o WHERE o.nombreOrg = :nombreOrg")
    , @NamedQuery(name = "Organizacion.findByDescripcionOrg", query = "SELECT o FROM Organizacion o WHERE o.descripcionOrg = :descripcionOrg")
    , @NamedQuery(name = "Organizacion.findByTelefono1Org", query = "SELECT o FROM Organizacion o WHERE o.telefono1Org = :telefono1Org")
    , @NamedQuery(name = "Organizacion.findByTelefono2Org", query = "SELECT o FROM Organizacion o WHERE o.telefono2Org = :telefono2Org")
    , @NamedQuery(name = "Organizacion.findByCelularOrg", query = "SELECT o FROM Organizacion o WHERE o.celularOrg = :celularOrg")
    , @NamedQuery(name = "Organizacion.findByEmailOrg", query = "SELECT o FROM Organizacion o WHERE o.emailOrg = :emailOrg")
    , @NamedQuery(name = "Organizacion.findByRucOrg", query = "SELECT o FROM Organizacion o WHERE o.rucOrg = :rucOrg")
    , @NamedQuery(name = "Organizacion.findByDireccionOrg", query = "SELECT o FROM Organizacion o WHERE o.direccionOrg = :direccionOrg")})
public class Organizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_organizacion")
    private Integer idOrganizacion;
    @Column(name = "nombre_org")
    private String nombreOrg;
    @Column(name = "descripcion_org")
    private String descripcionOrg;
    @Column(name = "telefono1_org")
    private String telefono1Org;
    @Column(name = "telefono2_org")
    private String telefono2Org;
    @Column(name = "celular_org")
    private String celularOrg;
    @Column(name = "email_org")
    private String emailOrg;
    @Column(name = "ruc_org")
    private String rucOrg;
    @Column(name = "direccion_org")
    private String direccionOrg;

    public Organizacion() {
    }

    public Organizacion(Integer idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }

    public Integer getIdOrganizacion() {
        return idOrganizacion;
    }

    public void setIdOrganizacion(Integer idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }

    public String getNombreOrg() {
        return nombreOrg;
    }

    public void setNombreOrg(String nombreOrg) {
        this.nombreOrg = nombreOrg;
    }

    public String getDescripcionOrg() {
        return descripcionOrg;
    }

    public void setDescripcionOrg(String descripcionOrg) {
        this.descripcionOrg = descripcionOrg;
    }

    public String getTelefono1Org() {
        return telefono1Org;
    }

    public void setTelefono1Org(String telefono1Org) {
        this.telefono1Org = telefono1Org;
    }

    public String getTelefono2Org() {
        return telefono2Org;
    }

    public void setTelefono2Org(String telefono2Org) {
        this.telefono2Org = telefono2Org;
    }

    public String getCelularOrg() {
        return celularOrg;
    }

    public void setCelularOrg(String celularOrg) {
        this.celularOrg = celularOrg;
    }

    public String getEmailOrg() {
        return emailOrg;
    }

    public void setEmailOrg(String emailOrg) {
        this.emailOrg = emailOrg;
    }

    public String getRucOrg() {
        return rucOrg;
    }

    public void setRucOrg(String rucOrg) {
        this.rucOrg = rucOrg;
    }

    public String getDireccionOrg() {
        return direccionOrg;
    }

    public void setDireccionOrg(String direccionOrg) {
        this.direccionOrg = direccionOrg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrganizacion != null ? idOrganizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Organizacion)) {
            return false;
        }
        Organizacion other = (Organizacion) object;
        if ((this.idOrganizacion == null && other.idOrganizacion != null) || (this.idOrganizacion != null && !this.idOrganizacion.equals(other.idOrganizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fad.entities.Organizacion[ idOrganizacion=" + idOrganizacion + " ]";
    }
    
}
