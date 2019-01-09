package com.example.mussabkamhieh.galgeleg;

public class spiller {
    String spillerNavnn;
    int forkerte;

    public spiller() {

    }

    public spiller(String spillerNavnn, int forkerte) {
        this.spillerNavnn = spillerNavnn;
        this.forkerte = forkerte;
    }

    public String getSpillerNavnn() {
        return spillerNavnn;
    }

    public int forkerte() {
        return forkerte;
    }
}
