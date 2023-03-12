package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Subtip Utilizator, reprezinta un angajat
 */
public class Angajat extends Utilizator {
    String companie;

    public Angajat(String nume, String companie) {
        super.seteazaNume(nume);
        this.companie = companie;
        super.seteazaCereriInAsteptare(new PriorityQueue<>());
        super.seteazaCereriSolutionate(new ArrayList<>());
    }

    public String scrieTextCerere(Cerere.CerereTip cerereTip) {
        return "Subsemnatul " + super.obtineNume() + ", angajat la compania " + this.companie + "," +
                " va rog sa-mi aprobati urmatoarea solicitare: " + Parser.tipCerereToString(cerereTip);
    }

    public Cerere creeazaCerere(Cerere.CerereTip cerereTip, int prioritate, LocalDateTime dateTime) throws TipCerereEronat {
        if (cerereTip == Cerere.CerereTip.INREGISTRARE_CUPOANE_PENSIE)
            throw new TipCerereEronat();

        if (cerereTip == Cerere.CerereTip.REINNOIRE_AUTORIZATIE)
            throw new TipCerereEronat();

        if (cerereTip == Cerere.CerereTip.INLOCUIRE_CARNET)
            throw new TipCerereEronat();

        if (cerereTip == Cerere.CerereTip.CREARE_ACT_CONSTITUTIV)
            throw new TipCerereEronat();

        Cerere cerere = new Cerere(scrieTextCerere(cerereTip), dateTime, prioritate);
        super.obtineCereriInAsteptare().add(cerere);

        return cerere;
    }

    @Override
    public String obtineTip() {
        return "angajat";
    }
}
