package org.example;

/**
 * Exceptie cand utilizatorul nu permite un anumit tip de cerere.
 */
public class TipCerereEronat extends Exception {
    public TipCerereEronat() {
        super("Tipul cererii nu se preteaza!");
    }
}
