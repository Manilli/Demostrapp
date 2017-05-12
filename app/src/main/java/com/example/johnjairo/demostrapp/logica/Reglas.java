/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.example.johnjairo.demostrapp.logica;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prog-labs
 */
public class Reglas {
    private ArrayList<FBF> rfp5;
    private ArrayList<FBF> rfp6;
    private ArrayList<FBF> rfp7;
    private ArrayList<FBF> conmutatividad;
    ArrayList hijosRegla;
    ArrayList hijosExpresion;
    
    public Reglas(){
        rfp5 = new ArrayList<>();
        rfp6 = new ArrayList<>();
        rfp7 = new ArrayList<>();
        conmutatividad = new ArrayList<>();
        setupReglas();
    }
    
    public void setupReglas(){
        try {
            rfp5.add(new FBF("r∧s"));
            rfp5.add(new FBF("¬(¬r∨¬s)"));
            rfp6.add(new FBF("r→s"));
            rfp6.add(new FBF("¬r∨s"));
            rfp7.add(new FBF("r↔s"));
            rfp7.add(new FBF("(r→s)∧(s→r)"));
            conmutatividad.add(new FBF("r∨s"));
            conmutatividad.add(new FBF("s∨r"));
        } catch (Exception ex) {
            ex.printStackTrace();
            //Logger.getLogger(Reglas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public String[] separarModusPonems(String expresion){
        int j=0;
        String[]arreglo = new String[2];
        for (int i = 0; i<expresion.length(); i++) {
            if(expresion.charAt(i)=='('){
                j++;
            }else if(expresion.charAt(i)==')'){
                j--;
            }
            if(j==0 && expresion.charAt(i)=='→'){
                arreglo[0] = expresion.substring(0, i);
                arreglo[1] = expresion.substring(i+1);
                return arreglo;
            }
        }
        return arreglo;
    }
    public String modusPonems(String fbf1, String fbf2){
        String[] fbf;
        String[] arreglo;
        System.out.println("fbf1 : " + fbf1 + " fbf2: " + fbf2);
        if(fbf1.contains("→")){
            fbf= fbf1.split("→",2);
            if(fbf2.equals(fbf[0])) return fbf[1];
            arreglo = separarModusPonems(fbf1);
            if(arreglo[0].contains("(")){
                if(quitarParentesis(arreglo[0], fbf2)){
                    return arreglo[1];
                }
            }
            
        }
        if(fbf2.contains("→")){
            fbf= fbf2.split("→",2);
            if(fbf1.equals(fbf[0])) return fbf[1];
            arreglo = separarModusPonems(fbf2);
            if(fbf[0].contains("(")){
                if(quitarParentesis(arreglo[0], fbf1)){
                    return arreglo[1];
                }
            }
        }
        return null;
    }
    
    public boolean quitarParentesis(String fbf1, String fbf2){
        if(fbf1.charAt(0)=='(' && fbf1.charAt(fbf1.length()-1)==')'){
            String f= fbf1.substring(1,fbf1.length()-1);
            if(f.equals(fbf2))return true;
        }
        return false;
        
    }
    
    
    private boolean compararArboles(FBF regla, FBF expresion){
        
        if(regla == null && expresion == null){
            return true;
        }
        if(regla.getEsAtomo()==true){
            hijosRegla.add(regla.toString());
            hijosExpresion.add(expresion.toString());
            return true;
        }
        
        if(Objects.equals(regla.getOperador(), expresion.getOperador()) &&
                compararArboles(regla.getFbfD(), expresion.getFbfD()) &&
                compararArboles(regla.getFbfI(), expresion.getFbfI()) ){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean  compararExpresiones(FBF expresion1, FBF expresion2, ArrayList<FBF> rfp){
        if(compararArboles( rfp.get(0),expresion1)){
            if(compararArboles( rfp.get(1),expresion2)){
                return true;
            }else {
                return false;
            }
        } else if (compararArboles( rfp.get(1),expresion1)){
            if(compararArboles( rfp.get(0),expresion2)){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
    
    public boolean validar(int regla, FBF expresion ,FBF expresion2){
        
        hijosRegla = new ArrayList();
        hijosExpresion = new ArrayList();
        
        switch (regla){
            case 5: if(!compararExpresiones(expresion, expresion2, rfp5))return false;
            break;
            case 6: if(!compararExpresiones(expresion, expresion2, rfp6))return false;
            break;
            case 7: if(!compararExpresiones(expresion, expresion2, rfp7))return false;
            break;
            case 8: if(!compararExpresiones(expresion, expresion2, conmutatividad))return false;
            break;
                
        }
        
        
        for(int i=0;i<hijosRegla.size();i++){
            for(int j=i+1;j<hijosRegla.size();j++){
                if(hijosRegla.get(i).equals(hijosRegla.get(j))){
                    if(!(hijosExpresion.get(i).equals(hijosExpresion.get(j)))){
                        return false;
                    }
                }
            }
        }
        
        
        return true;
        
    }
    
}
