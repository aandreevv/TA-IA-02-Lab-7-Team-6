package com.company;

public class Main {

    public static void main(String[] args) {
        BTree bTree = new BTree(3);
        DataProcessor dp1 = new DataProcessor(10000, bTree);
        printResults(dp1);
    }

    public static void printResults(DataProcessor dataProcessor) {
        System.out.println("RANDOM ELEMENTS:");
        System.out.println("Insertion: " + dataProcessor.timeInsertionRandom());
        System.out.println("Balancing: " + dataProcessor.timeBalancing());
        System.out.println("\nCONSECUTIVE ELEMENTS:");
        System.out.println("Insertion: " + dataProcessor.timeInsertionSorted());
        System.out.println("Balancing: " + dataProcessor.timeBalancing());
        System.out.println("\n");
        System.out.println("Searching: " + dataProcessor.timeSearching());
        System.out.println("Removing: " + dataProcessor.timeRemoving());
    }
}
