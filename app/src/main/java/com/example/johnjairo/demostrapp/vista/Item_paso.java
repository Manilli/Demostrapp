package com.example.johnjairo.demostrapp.vista;

/**
 * Created by rocka on 8/06/2017.
 */


public class Item_paso {

    private String pasoId;
    private String paso;
    private String expresion;
    private String justificacion;

    public Item_paso() {
        super();
    }

    public Item_paso(String pasoId, String paso, String expresion, String justificacion) {
        super();
        this.paso = paso;
        this.expresion = expresion;
        this.justificacion = justificacion;
        this.pasoId = pasoId;
    }


    public String getPaso() {
        return paso;
    }

    public void setPaso(String paso) {
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

    public String getPasoId(){return pasoId;}

    public void setPasoId(String pasoId){this.pasoId = pasoId;}

}