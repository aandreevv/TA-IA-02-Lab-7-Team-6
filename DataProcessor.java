package com.company;

import java.util.concurrent.ThreadLocalRandom;

public class DataProcessor {
    private int operations;
    private BTree tree;
    private int[] randomArray;

    public DataProcessor(int operations, BTree tree) {
        this.operations = operations;
        this.tree = tree;
        this.randomArray = new int[operations];
        for (int i = 0; i < operations; i++) {
            randomArray[i] = ThreadLocalRandom.current().nextInt(operations);
        }
    }

    public void insertRandom() {
        for (int i = 0; i < operations; i++) {
            tree.insert(randomArray[i]);
        }
    }

    public void insertSorted() {
        for (int i = 0; i < operations; i ++) {
            tree.insert(i);
        }
    }

    public void remove() {
        for (int i = 0; i < operations; i++) {
            tree.remove(i);
        }
    }

    public void balance() {
        for (int i = 0; i < operations; i++) {
            tree.balance(0);
        }
    }

    public void search() {
        long startTime = System.nanoTime();
        int val = ThreadLocalRandom.current().nextInt(operations);
        tree.contains(val);
    }

    public long timeInsertionRandom() {
        long startTime = System.nanoTime();
        insertRandom();
        return (System.nanoTime() - startTime) / 1000;
    }

    public long timeInsertionSorted() {
        long startTime = System.nanoTime();
        insertSorted();
        return (System.nanoTime() - startTime) / 1000;
    }

    public long timeRemoving() {
        long startTime = System.nanoTime();
        remove();
        return (System.nanoTime() - startTime) / 1000;
    }

    public long timeBalancing() {
        long startTime = System.nanoTime();
        balance();
        return (System.nanoTime() - startTime) / 1000;
    }

    public long timeSearching() {
        long startTime = System.nanoTime();
        search();
        return (System.nanoTime() - startTime) / 1000;
    }
}
