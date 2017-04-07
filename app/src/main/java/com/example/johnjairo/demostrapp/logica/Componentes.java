package com.example.johnjairo.demostrapp.logica;

/**
 * Created by rocka on 6/04/2017.
 */
import java.util.ArrayList;
import java.util.Set;

public class Componentes {

    private ArrayList<Character> alfabeto = new ArrayList<Character>();
    private ArrayList<Character> operadores = new ArrayList<Character>();

    public Componentes() {

        Character []alf = {'q','w','e','r','t','y','u','i','o','p','a','s',
                'd','f','g','h','j','k','l','z','x','c','v','b','n','m'};  	//Atomos permitidos
        Character []ops = {'∨','∧','¬','→','↔','≡','('};      //Operadores permitidos

        for (int i=0;i<ops.length;i++) {
            operadores.add(ops[i]);
        }
        for (int i=0;i<alf.length;i++) {
            alfabeto.add(alf[i]);
        }
    }

    public ArrayList<Character> getAlfabeto() {
        return alfabeto;
    }



    public ArrayList<Character> getOperadores() {
        return operadores;
    }
}

