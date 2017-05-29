/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.solver;

import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.entity.Bundle;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 *
 * @author jeferson
 */
public class BundleDifficultyComparator implements Comparator<Bundle>, Serializable{

    @Override
    public int compare(Bundle o1, Bundle o2) {
        return new CompareToBuilder()
                .append(o1.getCapacityRequired(), o2.getCapacityRequired())
                .append(o1.getId(), o2.getId())
                .toComparison();
    }
    
}
