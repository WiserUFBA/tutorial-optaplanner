/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.entity;

import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.solver.BundleDifficultyComparator;
import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.solver.HostStrengthComparator;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 *
 * @author jeferson
 */
@PlanningEntity(difficultyComparatorClass = BundleDifficultyComparator.class)
public class Bundle {
    private String id;
    private int capacityRequired;
    
    private Host hostAssociated;

    public Bundle(String id, int capacityRequired){
        this.id = id;
        this.capacityRequired = capacityRequired;
        this.hostAssociated = null;
    }
    
    public Bundle(){
        this("", 0);
    }
    
    public String getId() {
        return id;
    }

    public String getLabel(){
        return "Bundle " + id;
    }
    
    public int getCapacityRequired() {
        return capacityRequired;
    }

    public void setCapacityRequired(int capacityRequired) {
        this.capacityRequired = capacityRequired;
    }

    @PlanningVariable(valueRangeProviderRefs = {"hostRange"},
            strengthComparatorClass = HostStrengthComparator.class)
    public Host getHostAssociated() {
        return hostAssociated;
    }

    public void setHostAssociated(Host hostAssociated) {
        this.hostAssociated = hostAssociated;
    }
    
}
