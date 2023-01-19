package com.splitwiser.splitwiserclient.auxiliary.transitivity;

import java.math.BigDecimal;
import java.util.Arrays;

public class TransitivitySolver {

    private GraphMatrixTransformer graphRepresentation;

    public TransitivitySolver(GraphMatrixTransformer transformerAndData){
        this.graphRepresentation = transformerAndData;
    }

    public void solve(){
        //temp to check this shit out!
        BigDecimal[][] initialGraphMatrix = this.graphRepresentation.getMatrixInitialGraphRepresentation();

        for (int row = 0; row < initialGraphMatrix.length; row++){
                System.out.println(Arrays.toString(initialGraphMatrix[row]));
        }
        this.graphRepresentation.setMatrixFinalGraphRepresentation(initialGraphMatrix);
    }
}
