package com.mindhub.homebanking.utils;

public final class CardUtils {

    //constructor por defecto
    private CardUtils() {
    }

    public static int getCVV() {
        int numeroRandomCvv = (int)(Math.random()*(999-100+1)+100);
        return numeroRandomCvv;
    }

    public static int getCardNumber() {
        int numeroRandom = (int)(Math.random()*(9999-1000+1)+1000);
        return numeroRandom;
    }
}
