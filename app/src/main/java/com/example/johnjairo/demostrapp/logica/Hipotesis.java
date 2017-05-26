package com.example.johnjairo.demostrapp.logica;

import java.util.ArrayList;


public class Hipotesis {
    
    private ArrayList<FBF> antecedentes = new ArrayList();
    private ArrayList<FBF> consecuentes = new ArrayList();
    private String ante;
    private String conse;
    
    public String getConse() {
        return conse;
    }
    
    public void setConse(String conse) {
        this.conse = conse;
    }
    
    public Hipotesis(String expresion){
        
        int i=0;
        while(i< expresion.length()){
            if (expresion.charAt(i)=='⊢'){
                if(i==0){
                    ante=null;
                    conse = expresion.substring(1);
                } else {
                    ante= expresion.substring(0, i);
                    conse = expresion.substring(i+1);
                    try {
                        FBF consecuente = new FBF(conse);
                        consecuentes.add(consecuente);
                    } catch (Exception e) {
                    }
                }
                conse = expresion.substring(i+1);
                break;
            }else {
                i++;
            }
        }
        if(ante!=null){
            separarComas(ante);
        }
    }
    
    
    public void separarComas(String expresion){
        int i=0;
        int comaAnterior=0;
        FBF antecedente;
        while(i< expresion.length()){
            if (expresion.charAt(i)==','){
                try{
                    String s= expresion.substring(comaAnterior, i);
                    System.out.println(s);
                    antecedente = new FBF(s);
                    antecedentes.add(antecedente);
                } catch (Exception e){
                    System.out.println("error creando antecedentes");
                }
                comaAnterior= i+1;
            }
            i++;
            if (expresion.length()==i){
                try{
                    String s= expresion.substring(comaAnterior);
                    System.out.println(s);
                    antecedente = new FBF(s);
                    antecedentes.add(antecedente);
                } catch (Exception e){
                    System.out.println("error creando antecedentes");
                }
                
            }
            
        }
    }
    
    /**
     * @return the antecedentes
     */
    public ArrayList<FBF> getAntecedentes() {
        return antecedentes;
    }
    
    /**
     * @param antecedentes the antecedentes to set
     */
    public void setAntecedentes(ArrayList<FBF> antecedentes) {
        this.antecedentes = antecedentes;
    }
    
    /**
     * @return the consecuentes
     */
    public ArrayList<FBF> getConsecuentes() {
        return consecuentes;
    }
    
    /**
     * @param consecuentes the consecuentes to set
     */
    public void setConsecuentes(ArrayList<FBF> consecuentes) {
        this.consecuentes = consecuentes;
    }
    
    /**
     * @return the ante
     */
    public String getAnte() {
        return ante;
    }
    
    /**
     * @param ante the ante to set
     */
    public void setAnte(String ante) {
        this.ante = ante;
    }
    
}
