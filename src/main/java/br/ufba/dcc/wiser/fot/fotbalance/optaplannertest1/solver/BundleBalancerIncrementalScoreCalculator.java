/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.solver;

import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.Controller;
import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.entity.Host;
import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.entity.Bundle;
import java.util.HashMap;
import java.util.Map;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.incremental.AbstractIncrementalScoreCalculator;

/**
 *
 * @author jeferson
 */
public class BundleBalancerIncrementalScoreCalculator extends AbstractIncrementalScoreCalculator<Controller> {

    private Map<Host, Integer> capacityUsedMap;
    private Map<Host, Integer> bundleCountMap;
    
    private int hardScore;
    private int softScore;

    private void insert(Bundle bundle) {
        /* Imprime o resultado da rodada */
        Controller.CONTADOR_RODADA++;
        System.out.println( "Rodada " + Controller.CONTADOR_RODADA + " => "
                + "Hard Score = " + hardScore + " | Soft Score = " + softScore);
        
        Host host = bundle.getHostAssociated();
        if (host != null) {
            int capacity = host.getCapacity();
            int oldCapacityUsed = capacityUsedMap.get(host);
            int oldCapacityAvailable = capacity - oldCapacityUsed;
            int newCapacityUsed = oldCapacityUsed + bundle.getCapacityRequired();
            int newCapacityAvailable = capacity - newCapacityUsed;
            hardScore += Math.min(newCapacityAvailable, 0) - Math.min(oldCapacityAvailable, 0);
            capacityUsedMap.put(host, newCapacityUsed);
            
            int oldBundleCount = bundleCountMap.get(host);
            if(oldBundleCount == 0){
                softScore += 1;
            }
            int newBundleCount = oldBundleCount + 1;
            bundleCountMap.put(host, newBundleCount);
        }
    }
    
    private void retract(Bundle bundle) {
        /* Imprime o resultado da rodada */
        Controller.CONTADOR_RODADA++;
        System.out.println( "Rodada " + Controller.CONTADOR_RODADA + " => "
                + "Hard Score = " + hardScore + " | Soft Score = " + softScore);
        
        Host host = bundle.getHostAssociated();
        if (host != null) {
            int capacity = host.getCapacity();
            int oldCapacityUsed = capacityUsedMap.get(host);
            int oldCapacityAvailable = capacity - oldCapacityUsed;
            int newCapacityUsed = oldCapacityUsed - bundle.getCapacityRequired();
            int newCapacityAvailable = capacity - newCapacityUsed;
            hardScore += Math.min(newCapacityAvailable, 0) - Math.min(oldCapacityAvailable, 0);
            capacityUsedMap.put(host, newCapacityUsed);

            int oldBundleCount = bundleCountMap.get(host);
            int newBundleCount = oldBundleCount - 1;
            if(newBundleCount == 0){
                softScore -= 1;
            }
            bundleCountMap.put(host, newBundleCount);
        }
    }
    
    @Override
    public void resetWorkingSolution(Controller workingSolution) {
        /* Inicializa a estrutura de balanceamento */
        System.out.println("\n#Reinicializando a estrutura de Balanceamento Incremental\n");
        
        int numberOfHosts = workingSolution.getHostList().size();
        capacityUsedMap = new HashMap<>(numberOfHosts);
        bundleCountMap = new HashMap<>(numberOfHosts);
        
        hardScore = 0;
        softScore = -numberOfHosts;
        
        for (Host host : workingSolution.getHostList()) {
            capacityUsedMap.put(host, 0);
            bundleCountMap.put(host, 0);
        }
        
        for (Bundle bundle : workingSolution.getBundleList()) {
            insert(bundle);
        }
    }

    @Override
    public void beforeEntityAdded(Object entity) {
        /* Do nothing */        
    }

    @Override
    public void afterEntityAdded(Object entity) {
        insert((Bundle) entity);
    }

    @Override
    public void beforeVariableChanged(Object entity, String variableName) {
        retract((Bundle) entity);
    }

    @Override
    public void afterVariableChanged(Object entity, String variableName) {
        insert((Bundle) entity);
    }

    @Override
    public void beforeEntityRemoved(Object entity) {
        retract((Bundle) entity);
    }

    @Override
    public void afterEntityRemoved(Object entity) {
        /* Do nothing */
    }

    @Override
    public Score calculateScore() {
        return HardSoftScore.valueOf(hardScore, softScore);
    }
   
}