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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "pasos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pasos.findAll", query = "SELECT p FROM Pasos p"),
    @NamedQuery(name = "Pasos.findById", query = "SELECT p FROM Pasos p WHERE p.id = :id"),
    @NamedQuery(name = "Pasos.findByNombreDemostracion", query = "SELECT p FROM Pasos p WHERE p.nombreDemostracion = :nombreDemostracion"),
    @NamedQuery(name = "Pasos.findByPaso", query = "SELECT p FROM Pasos p WHERE p.paso = :paso"),
    @NamedQuery(name = "Pasos.findByExpresion", query = "SELECT p FROM Pasos p WHERE p.expresion = :expresion"),
    @NamedQuery(name = "Pasos.findByJustificacion", query = "SELECT p FROM Pasos p WHERE p.justificacion = :justificacion")})
    */
public class Pasos implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")

    @Basic(optional = false)
    @Column(name = "nombreDemostracion")

    @Basic(optional = false)
    @Column(name = "paso")

    @Basic(optional = false)
    @Column(name = "expresion")

    @Basic(optional = false)
    @Column(name = "justificacion")
    */
    private Integer id;
    private String nombreDemostracion;
    private int paso;
    private String expresion;
    private String justificacion;

    public Pasos() {
    }

    public Pasos(Integer id) {
        this.id = id;
    }

    public Pasos(Integer id, String nombreDemostracion, int paso, String expresion, String justificacion) {
        this.id = id;
        this.nombreDemostracion = nombreDemostracion;
        this.paso = paso;
        this.expresion = expresion;
        this.justificacion = justificacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreDemostracion() {
        return nombreDemostracion;
    }

    public void setNombreDemostracion(String nombreDemostracion) {
        this.nombreDemostracion = nombreDemostracion;
    }

    public int getPaso() {
        return paso;
    }

    public void setPaso(int paso) {
        this.paso = paso;
    }

    public String getExpresion() {
        return expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
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
        if (!(object instanceof Pasos)) {
            return false;
        }
        Pasos other = (Pasos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udea.PI.modelo.Pasos[ id=" + id + " ]";
    }
    
}
