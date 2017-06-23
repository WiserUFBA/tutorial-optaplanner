/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1;

import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.entity.Host;
import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.entity.Bundle;
import java.util.ArrayList;
import java.util.List;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

/**
 *
 * @author jeferson
 */
@PlanningSolution
public class Controller{

    public static int CONTADOR_RODADA = 0;
    
    private List<Host> hostList;
    
    private List<Bundle> bundleList;
    
    private HardSoftScore score;
    
    public Controller(){
        hostList = new ArrayList<>();
        bundleList = new ArrayList<>();
    }
    
    public void addBundle(Bundle bundle){
        bundleList.add(bundle);
    }
    
    public void addHost(Host host){
        hostList.add(host);
    }
    
    @ValueRangeProvider(id = "hostRange")
    @ProblemFactCollectionProperty
    public List<Host> getHostList() {
        return hostList;
    }
    
    public void setHostList(List<Host> hostList) {
        this.hostList = hostList;
    }
    
    @PlanningEntityCollectionProperty
    public List<Bundle> getBundleList() {
        return bundleList;
    }
    
    public void setBundleList(List<Bundle> bundleList) {
        this.bundleList = bundleList;
    }
    
    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }   
    public void setScore(HardSoftScore score) {
        this.score = score;
    }
        
    void displayResultado() {
        for(Host host : hostList){
            System.out.println("Bundles Associados => " + host.getLabel());
            for(Bundle bundle : bundleList){
                if(bundle.getHostAssociated() != null && bundle.getHostAssociated().equals(host)){
                    System.out.println("\t" + bundle.getLabel());
                }
            }
        }
        System.out.println("Bundles Desassociados");
        for(Bundle bundle : bundleList){
            if(bundle.getHostAssociated() == null){
                System.out.println("\t" + bundle.getLabel());
            }
        }
    }
    
}
