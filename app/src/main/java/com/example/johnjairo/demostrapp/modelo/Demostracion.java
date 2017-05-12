/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.johnjairo.demostrapp.modelo;

import java.io.Serializable;
/*
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
*/
/**
 *
 * @author Usuario
 */
/*
@Entity
@Table(name = "demostracion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Demostracion.findAll", query = "SELECT d FROM Demostracion d"),
    @NamedQuery(name = "Demostracion.findByNombre", query = "SELECT d FROM Demostracion d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "Demostracion.findByPremisas", query = "SELECT d FROM Demostracion d WHERE d.premisas = :premisas"),
    @NamedQuery(name = "Demostracion.findByConclusion", query = "SELECT d FROM Demostracion d WHERE d.conclusion = :conclusion"),
    @NamedQuery(name = "Demostracion.findByDemostracion", query = "SELECT d FROM Demostracion d WHERE d.demostracion = :demostracion")})
    */
public class Demostracion implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    @Id
    @Basic(optional = false)
    @Column(name = "nombre")

    @Basic(optional = false)
    @Column(name = "premisas")

    @Basic(optional = false)
    @Column(name = "conclusion")

    @Basic(optional = false)
    @Column(name = "demostracion")
    */

    private String nombre;
    private String premisas;
    private String conclusion;
    private String demostracion;

    public Demostracion() {
    }

    public Demostracion(String nombre) {
        this.nombre = nombre;
    }

    public Demostracion(String nombre, String premisas, String conclusion, String demostracion) {
        this.nombre = nombre;
        this.premisas = premisas;
        this.conclusion = conclusion;
        this.demostracion = demostracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPremisas() {
        return premisas;
    }

    public void setPremisas(String premisas) {
        this.premisas = premisas;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getDemostracion() {
        return demostracion;
    }

    public void setDemostracion(String demostracion) {
        this.demostracion = demostracion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Demostracion)) {
            return false;
        }
        Demostracion other = (Demostracion) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udea.PI.modelo.Demostracion[ nombre=" + nombre + " ]";
    }
    
}
