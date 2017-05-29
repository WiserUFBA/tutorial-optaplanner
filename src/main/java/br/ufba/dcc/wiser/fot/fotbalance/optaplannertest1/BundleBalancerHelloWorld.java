/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1;

import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.entity.Host;
import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.entity.Bundle;
import java.util.concurrent.TimeUnit;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

/**
 *
 * @author Jeferson Lima(jefersonlimaa@dcc.ufba.br)
 */
public class BundleBalancerHelloWorld {
    
    /* Caminho para as configurações do solucionador */
    private static final String SOLVER_CONFIGURATION_FILEPATH = "br/ufba/dcc/wiser/fot/fotbalance/optaplannertest1/bundleBalancerSolverConfig.xml";
    
    public static void main(String[] args){
        /* Cria a fabrica de solucionadores */
        SolverFactory<Controller> solverFactory = SolverFactory.createFromXmlResource(SOLVER_CONFIGURATION_FILEPATH);
        /* Cria o solucionador */
        Solver<Controller> solver = solverFactory.buildSolver();
        
        /* Cria um solucionador desorganizado */
        Controller unsolvedController = new Controller();
        
        System.out.println("Iniciamento Rotinas de Balanceamento teste");
        
        /* Adiciona alguns hosts e bundles */
        Host _temp_host1 = new Host("A", 5);
        Host _temp_host2 = new Host("B", 5);
        Bundle _temp_bundle1 = new Bundle("1", 3);
        Bundle _temp_bundle2 = new Bundle("2", 2);
        unsolvedController.addHost(_temp_host1);
        unsolvedController.addHost(_temp_host2);
        unsolvedController.addBundle(_temp_bundle1);
        unsolvedController.addBundle(_temp_bundle2);
        /* A CONFIGURAÇÃO FINAL DEVE SER HOST A { BUNDLE 1 } HOST B { BUNDLE 2 } */
        
        /* Tempo inicio */
        long millis_inicio = System.currentTimeMillis();
        
        /* Soluciona o arranjo */
        Controller solvedController = solver.solve(unsolvedController);
        
        /* Tempo execução */
        long millis_fim = System.currentTimeMillis() - millis_inicio;
        String tempo_execucao = String.format("%02d:%02d.%04d", 
            TimeUnit.MILLISECONDS.toMinutes(millis_fim),
            TimeUnit.MILLISECONDS.toSeconds(millis_fim) - 
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis_fim)),
            (millis_fim % 1000)
        );
        System.out.println("\nRotina de Balanceamento finalizada em " + tempo_execucao + " min");
        
        /* Mostra o resultado */
        System.out.println("Controlador sem balanceamento");
        unsolvedController.displayResultado();
        System.out.println("\nControlador com balanceamento");
        solvedController.displayResultado();
    }
}
