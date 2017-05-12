/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.johnjairo.demostrapp.logica;

import java.util.ArrayList;
import java.util.Objects;

public class Axiomas {
    FBF axioma1;
    FBF axioma2;
    FBF axioma3;
    FBF axioma4;
    
    ArrayList hijosAxioma;
    ArrayList hijosExpresion;
    
    public Axiomas() throws Exception {
        axioma1 = new FBF("p∨p→p");
        axioma2 = new FBF("p→p∨q");
        axioma3 = new FBF("p∨q→q∨p");
        axioma4 = new FBF("(p→q)→(r∨p→r∨q)");
    }
    
    private boolean compararArboles(FBF axioma, FBF expresion){
        
        if(axioma == null && expresion == null){
            return true;
        }
        if(axioma.getEsAtomo()==true){
            hijosAxioma.add(axioma.toString());
            hijosExpresion.add(expresion.toString());
            return true;
        }
        
        if(axioma.getOperador().equals(expresion.getOperador()) && //Objects.equals(axioma.getOperador(), expresion.getOperador())
            compararArboles(axioma.getFbfD(), expresion.getFbfD()) &&
            compararArboles(axioma.getFbfI(), expresion.getFbfI()) ){
            return true;
        }else{
            return false; 
        }        
    }
    
    public boolean validar(int axioma, FBF expresion){
        
        hijosAxioma = new ArrayList();
        hijosExpresion = new ArrayList();
        
        switch (axioma){
            case 1:
                if(!compararArboles(axioma1, expresion)){
                    return false;
                }                    
                break;
            case 2:
                if(!compararArboles(axioma2, expresion)){
                    return false;
                }  
                break;
            case 3:
                if(!compararArboles(axioma3, expresion)){
                    return false;
                }  
                break;
            case 4:
                if(!compararArboles(axioma4, expresion)){
                    return false;
                }  
                break;
        }
        
        for(int i=0;i<hijosAxioma.size();i++){
            for(int j=i+1;j<hijosAxioma.size();j++){
                if(hijosAxioma.get(i).equals(hijosAxioma.get(j))){
                    if(!(hijosExpresion.get(i).equals(hijosExpresion.get(j)))){
                         return false;
                    }                        
                }            
            }            
        }
        
        return true;
    }
    
    public boolean equivalenciaArboles(FBF expresion1, FBF expresion2){
        hijosAxioma = new ArrayList();
        hijosExpresion = new ArrayList();
        
        if(!compararArboles(expresion1, expresion2)) return false;
        
        for(int i=0;i<hijosAxioma.size();i++){
            for(int j=i+1;j<hijosAxioma.size();j++){
                if(hijosAxioma.get(i).equals(hijosAxioma.get(j))){
                    if(!(hijosExpresion.get(i).equals(hijosExpresion.get(j)))){
                         return false;
                    }                        
                }            
            }            
        }
        
        return true;
    }
        
}
