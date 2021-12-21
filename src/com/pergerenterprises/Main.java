package com.pergerenterprises;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        int classSize;
        int numberOfSimulations;

        Scanner s = new Scanner(System.in);

        System.out.print("How many students are there in the class? ");
        classSize = s.nextInt();

        System.out.print("How many times should the simulation be ran? ");
        numberOfSimulations = s.nextInt();

        Simulation simulation = new Simulation(classSize, false);
        final Map<Integer, Integer> results = new HashMap<Integer, Integer>();

        for (int i = 0; i < numberOfSimulations; i++) {
            final Integer currentResult = simulation.run();

            if(results.containsKey(currentResult)) {
                results.put(currentResult, results.get(currentResult) + 1);
            }
            else {
                results.put(currentResult, 1);
            }

            System.out.println((i+1) + ". simulation ran.");
        }

        results.forEach((k, v) -> {
            System.out.println("There are " + v + " simulations with " + k + " cycles");
        });
    }
}
