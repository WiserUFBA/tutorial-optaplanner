package br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jeferson
 */
public class Host {
    private String id;
    private int capacity;
    
    public Host(String id, int capacity){
        this.id = id;
        this.capacity = capacity;
    }
    
    public Host(){
        this("", 0);
    }
    
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }
    
    public String getLabel(){
        return "Host " + id;
    }
}
