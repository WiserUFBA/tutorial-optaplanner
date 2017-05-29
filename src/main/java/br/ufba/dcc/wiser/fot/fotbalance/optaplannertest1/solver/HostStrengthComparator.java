/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.solver;

import br.ufba.dcc.wiser.fot.fotbalance.optaplannertest1.entity.Host;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 *
 * @author jeferson
 */
public class HostStrengthComparator implements Comparator<Host>, Serializable{

    @Override
    public int compare(Host a, Host b) {
        return new CompareToBuilder()
                .append(a.getCapacity(), b.getCapacity())
                .append(a.getId(), b.getId())
                .toComparison();
    }
    
}
