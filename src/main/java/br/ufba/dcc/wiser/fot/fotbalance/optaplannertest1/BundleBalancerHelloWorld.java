/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1;

import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.entity.Host;
import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.entity.Bundle;
import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.solver.BundleBalancerIncrementalScoreCalculator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.SolverConfigContext;
import org.optaplanner.core.config.domain.ScanAnnotatedClassesConfig;
import org.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.termination.TerminationConfig;

/**
 *
 * @author Jeferson Lima(jefersonlimaa@dcc.ufba.br)
 */
public class BundleBalancerHelloWorld {
    
    /* Caminho para as configurações do solucionador */
    private static final String SOLVER_CONFIGURATION_FILEPATH = "br/ufba/dcc/wiser/fot/fotbalance/optaplannertest1/bundleBalancerSolverConfig.xml";
    
    public static SolverConfig criarSolverConfig(){
        /* Inicialização complexa das configurações */
        /* Caso esteja em um contexto que  */
        SolverConfig solver_config = new SolverConfig();

        ScoreDirectorFactoryConfig score_director = new ScoreDirectorFactoryConfig();
        score_director.setIncrementalScoreCalculatorClass(BundleBalancerIncrementalScoreCalculator.class);

        TerminationConfig termination_config = new TerminationConfig();
        termination_config.setBestScoreLimit("0hard/0soft");
        termination_config.setSecondsSpentLimit(new Long(10));
        termination_config.setScoreCalculationCountLimit(new Long(100000));

        ScanAnnotatedClassesConfig scann_class_config = new ScanAnnotatedClassesConfig();

        List<String> import_list = new ArrayList();
        import_list.add("br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1");
        import_list.add("br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.entity");
        import_list.add("br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.solver");

        scann_class_config.setPackageIncludeList(import_list);

        solver_config.setTerminationConfig(termination_config);
        solver_config.setScanAnnotatedClassesConfig(scann_class_config);
        solver_config.setScoreDirectorFactoryConfig(score_director);
        
        return solver_config;
    }
    
    public static void main(String[] args){
        /* Cria a fabrica de solucionadores */
        SolverFactory<Controller> solver_factory = SolverFactory.createFromXmlResource(SOLVER_CONFIGURATION_FILEPATH);
        /* Cria a configuracao do solucionador */
        SolverConfig solver_config = criarSolverConfig();
        SolverConfigContext solver_config_context = new SolverConfigContext();
        solver_config_context.setClassLoader(BundleBalancerIncrementalScoreCalculator.class.getClassLoader());
        /* Cria o solucionador */
        //Solver<Controller> solver = solver_factory.buildSolver(); /* FORMATO RECOMENDADO */
        Solver<Controller> solver = solver_config.buildSolver(solver_config_context); /* FORMATO NÃO RECOMENDADO */
        
        /* 
            OBS.: Um solucionador pode ser construído de duas formas, que são através de um arquivo XML de configuração
                  que é o metodo recomendado pelo optaplanner e progamaticamente, através da instanciação das configurações
                  o que não é recomendado porém é um artíficio para contexto aonde o arquivo de configuração não esteja
                  funcionando como em alguns containers OSGi como é o caso do KARAF.
                  Para outros contextos deve se utilizar a forma padrão através de uma fábrica de solucionadores e um
                  um arquivo de configuração, mas para fins didáticos são apresentadas as duas formas.
        */
        
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
