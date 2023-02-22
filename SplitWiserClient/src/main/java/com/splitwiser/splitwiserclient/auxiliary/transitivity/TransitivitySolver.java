package com.splitwiser.splitwiserclient.auxiliary.transitivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransitivitySolver {

    private final GraphMatrixTransformer graphRepresentation;

    public TransitivitySolver(GraphMatrixTransformer transformerAndData) {
        this.graphRepresentation = transformerAndData;
    }

    public void solve() {
        BigDecimal[][] graphMatrix = this.graphRepresentation.getMatrixInitialGraphRepresentation();

        System.out.println("INITIAL GRAPH:");
        //diagnostic print
        for (BigDecimal[] matrix : graphMatrix) {
            System.out.println(Arrays.toString(matrix));
        }
        System.out.println("############################################");


        for (int vertex = 0; vertex < graphMatrix.length; vertex++) {
            boolean hasUpdated = true;
            System.out.println("Working with Vertex: " + this.graphRepresentation.getMembers().get(vertex).getFirstName());

            while (hasUpdated) {
                hasUpdated = false;
                List<Integer> pathVertices = new ArrayList<>();
                pathVertices.add(vertex);

                BigDecimal minimalEdgeValue = this.findImprovablePath(pathVertices, graphMatrix.length, graphMatrix);
                if (minimalEdgeValue.compareTo(BigDecimal.valueOf(0)) > 0) {
                    System.out.println("Found Path: "
                            + this.graphRepresentation.getMembers().get(pathVertices.get(0)).getFirstName() + "->"
                            + this.graphRepresentation.getMembers().get(pathVertices.get(1)).getFirstName() + "->"
                            + this.graphRepresentation.getMembers().get(pathVertices.get(2)).getFirstName()
                    );
                    this.adjustPathEdgesValues(pathVertices, minimalEdgeValue, graphMatrix);
                    this.updateBegToEndEdge(pathVertices, minimalEdgeValue, graphMatrix);
                    hasUpdated = true;

                    System.out.println("The graph has changed to:");
                    //diagnostic print
                    for (BigDecimal[] matrix : graphMatrix) {
                        System.out.println(Arrays.toString(matrix));
                    }
                    System.out.println("############################################");
                }
            }
        }

        this.graphRepresentation.setMatrixFinalGraphRepresentation(graphMatrix);
    }

    private void adjustPathEdgesValues(List<Integer> pathVertices, BigDecimal minimalEdgeValue, BigDecimal[][] graphMatrix) {
        graphMatrix[pathVertices.get(0)][pathVertices.get(1)] = graphMatrix[pathVertices.get(0)][pathVertices.get(1)].subtract(minimalEdgeValue);
        graphMatrix[pathVertices.get(1)][pathVertices.get(2)] = graphMatrix[pathVertices.get(1)][pathVertices.get(2)].subtract(minimalEdgeValue);
    }

    private void updateBegToEndEdge(List<Integer> pathVertices, BigDecimal minimalEdgeValue, BigDecimal[][] graphMatrix) {
        graphMatrix[pathVertices.get(0)][pathVertices.get(2)] = graphMatrix[pathVertices.get(0)][pathVertices.get(2)].add(minimalEdgeValue);
        BigDecimal begToEndEdge = graphMatrix[pathVertices.get(0)][pathVertices.get(2)];
        BigDecimal endToBegEdge = graphMatrix[pathVertices.get(2)][pathVertices.get(0)];


        //if there is a "counter edge"
        if (endToBegEdge.compareTo(BigDecimal.valueOf(0)) > 0) {
            //we have to reduce those 2 edges
            //if the "counter edge" has a bigger value
            if (endToBegEdge.compareTo(begToEndEdge) > 0) {
                graphMatrix[pathVertices.get(2)][pathVertices.get(0)] = graphMatrix[pathVertices.get(2)][pathVertices.get(0)].subtract(begToEndEdge);
                graphMatrix[pathVertices.get(0)][pathVertices.get(2)] = BigDecimal.valueOf(0);
            } else {
                graphMatrix[pathVertices.get(0)][pathVertices.get(2)] = graphMatrix[pathVertices.get(0)][pathVertices.get(2)].subtract(endToBegEdge);
                graphMatrix[pathVertices.get(2)][pathVertices.get(0)] = BigDecimal.valueOf(0);
            }
        }
    }

    private BigDecimal findImprovablePath(List<Integer> pathVertices, int totalAmountOfVertices, BigDecimal[][] graphMatrix) {
        BigDecimal minimalEdgeValue;

        for (int secondVertex = 0; secondVertex < totalAmountOfVertices; secondVertex++) {
            BigDecimal firstEdgeValue = graphMatrix[pathVertices.get(0)][secondVertex];
            if (firstEdgeValue.compareTo(BigDecimal.valueOf(0)) > 0) {
                System.out.println("Found First Edge: "+ this.graphRepresentation.getMembers().get(pathVertices.get(0)).getFirstName() + "->" + this.graphRepresentation.getMembers().get(secondVertex).getFirstName());
                minimalEdgeValue = firstEdgeValue;
                for (int thirdVertex = 0; thirdVertex < totalAmountOfVertices; thirdVertex++) {
                    BigDecimal secondEdgeValue = graphMatrix[secondVertex][thirdVertex];
                    if (secondEdgeValue.compareTo(BigDecimal.valueOf(0)) > 0) {
                        System.out.println("Found Second Edge: "+ this.graphRepresentation.getMembers().get(secondVertex).getFirstName() + "->" + this.graphRepresentation.getMembers().get(thirdVertex).getFirstName());
                        pathVertices.add(secondVertex);
                        pathVertices.add(thirdVertex);
                        return minimalEdgeValue.min(secondEdgeValue);
                    }
                }
            }
        }
        return BigDecimal.valueOf(0);
    }
}
