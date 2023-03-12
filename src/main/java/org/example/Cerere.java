package org.example;

import java.time.LocalDateTime;

/**
 * Reprezinta o cerere.
 */
public class Cerere implements Comparable<Cerere> {
    private String continut;
    private LocalDateTime dateTime;
    private int prioritate;
    private Utilizator creatDe;

    public enum CerereTip {
        INLOCUIRE_BULETIN,
        INREGISTRARE_VENIT_SALARIAL,
        INLOCUIRE_PERMIS,
        INLOCUIRE_CARNET,
        CREARE_ACT_CONSTITUTIV,
        REINNOIRE_AUTORIZATIE,
        INREGISTRARE_CUPOANE_PENSIE,
        OK
    }

    Cerere(String continut, LocalDateTime dateTime, int prioritate) {
        this.continut = continut;
        this.dateTime = dateTime;
        this.prioritate = prioritate;
    }

    public Utilizator obtineCreatDe() {
        return creatDe;
    }

    public void setCreatDe(Utilizator creatDe) {
        this.creatDe = creatDe;
    }

    public String obtineContinut() {
        return continut;
    }

    public void seteazaContinut(String continut) {
        this.continut = continut;
    }

    public LocalDateTime obtineDateTime() {
        return dateTime;
    }

    public void seteazaDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int obtinePrioritate() {
        return prioritate;
    }

    public void seteazaPrioritate(int prioritate) {
        this.prioritate = prioritate;
    }


    public int compareTo(Cerere o) {
        if (this.obtineDateTime().isBefore(o.obtineDateTime()))
            return -1;

        if (this.obtineDateTime().isAfter(o.obtineDateTime()))
            return 1;

        return 0;
    }

    public String toString() {
        return dateTime.format(Parser.formatter).toString() + " - " + continut;
    }
}
