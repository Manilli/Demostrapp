package com.example.johnjairo.demostrapp.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by John Jairo on 1/06/2017.
 */

public class Demostration implements Serializable {
    ArrayList<ArrayList> demostracion1=new ArrayList<>();
    ArrayList<ArrayList> demostracion2=new ArrayList<>();
    String hipotesis1;
    String hipotesis2;

    public String getHipotesis1() {
        return hipotesis1;
    }

    public void setHipotesis1(String hipotesis1) {
        this.hipotesis1 = hipotesis1;
    }

    public String getHipotesis2() {
        return hipotesis2;
    }

    public void setHipotesis2(String hipotesis2) {
        this.hipotesis2 = hipotesis2;
    }

    boolean terminado;
    boolean bicondicional;

    public Demostration(ArrayList<ArrayList> demostracion1, boolean terminado) {
        this.demostracion1 = demostracion1;
        this.terminado = terminado;
        this.bicondicional=false;
        this.hipotesis1="";
        this.hipotesis2="";
    }

    public Demostration(ArrayList<ArrayList> demostracion1, ArrayList<ArrayList> demostracion2, String hipotesis1, String hipotesis2, boolean terminado) {
        this.demostracion1 = demostracion1;
        this.demostracion2 = demostracion2;
        this.hipotesis1 = hipotesis1;
        this.hipotesis2 = hipotesis2;
        this.terminado = terminado;
        this.bicondicional=true;
    }

    public ArrayList<ArrayList> getDemostracion1() {
        return demostracion1;
    }

    public void setDemostracion1(ArrayList<ArrayList> demostracion1) {
        this.demostracion1 = demostracion1;
    }

    public ArrayList<ArrayList> getDemostracion2() {
        return demostracion2;
    }

    public void setDemostracion2(ArrayList<ArrayList> demostracion2) {
        this.demostracion2 = demostracion2;
    }

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public boolean isBicondicional() {
        return bicondicional;
    }

    public void setBicondicional(boolean bicondicional) {
        this.bicondicional = bicondicional;
    }
}
