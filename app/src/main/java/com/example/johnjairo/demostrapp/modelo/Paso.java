package com.example.johnjairo.demostrapp.modelo;

import java.io.Serializable;

/**
 * Created by John Jairo on 30/05/2017.
 */

public class Paso implements Serializable{
    private Integer numeroPaso;
    private String expresion;
    private String justificacion;
    public Paso(Integer numeroPaso, String expresion, String justificacion) {
        this.numeroPaso = numeroPaso;
        this.expresion = expresion;
        this.justificacion = justificacion;
    }

    public void setNumeroPaso(Integer numeroPaso) {
        this.numeroPaso = numeroPaso;
    }

    public Integer getNumeroPaso() {
        return numeroPaso;
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
    public Paso(){

    }
}

