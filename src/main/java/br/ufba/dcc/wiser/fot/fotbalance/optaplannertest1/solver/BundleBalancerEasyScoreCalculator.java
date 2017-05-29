/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.solver;

import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.Controller;
import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.entity.Host;
import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.entity.Bundle;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

/**
 *
 * @author jeferson
 */
public class BundleBalancerEasyScoreCalculator implements EasyScoreCalculator<Controller> {

    /**
     * A very simple implementation. The double loop can easily be removed by using Maps as shown in
     * {@link CloudBalancingMapBasedEasyScoreCalculator#calculateScore(CloudBalance)}.
     * @param controller
     */
    @Override
    public HardSoftScore calculateScore(Controller controller) {
        int hardScore = 0;
        int numHostUsed = 0;
        
        for (Host host : controller.getHostList()) {
            int capacityUsed = 0;
            boolean used = false;

            /* Calcula o uso */
            for (Bundle bundle : controller.getBundleList()) {
                if (host.equals(bundle.getHostAssociated())) {
                    capacityUsed += bundle.getCapacityRequired();
                    used = true;
                }
            }

            /* Pontuação **Hard** é a capacidade dos nós que não está sendo excedida */
            int capacityAvailable = host.getCapacity() - capacityUsed;
            if (capacityAvailable < 0) {
                hardScore += capacityAvailable;
            }

            /* Incrementa o numero de host usados */
            if(used){
                numHostUsed++;
            }
        }
        
        /* Pontuação **Soft** é o número de nós que não estão sendo utilizados quanto mais nós utilizados melhor */
        int softScore = numHostUsed - controller.getHostList().size();
        
        /* Imprime o resultado da rodada */
        Controller.CONTADOR_RODADA++;
        System.out.println( "Rodada " + Controller.CONTADOR_RODADA + " => "
                + "Hard Score = " + hardScore + " | Soft Score = " + softScore);
        
        /* Retorna a avaliação das pontuações */
        return HardSoftScore.valueOf(hardScore, softScore);
    }
}