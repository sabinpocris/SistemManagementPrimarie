package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Subtip Utilizator, reprezinta o entitate juridica
 */
public class EntitateJuridica extends Utilizator {
    String reprezentant;

    public EntitateJuridica(String nume, String reprezentant) {
        super.seteazaNume(nume);
        this.reprezentant = reprezentant;
        super.seteazaCereriInAsteptare(new PriorityQueue<>());
        super.seteazaCereriSolutionate(new ArrayList<>());
    }

    @Override
    public String scrieTextCerere(Cerere.CerereTip cerereTip) {
        return "Subsemnatul " + this.reprezentant + ", reprezentant legal al companiei " + this.obtineNume() + "," +
                " va rog sa-mi aprobati urmatoarea solicitare: " + Parser.tipCerereToString(cerereTip);
    }

    @Override
    public Cerere creeazaCerere(Cerere.CerereTip cerereTip, int prioritate, LocalDateTime dateTime) throws TipCerereEronat {
        if (cerereTip == Cerere.CerereTip.INREGISTRARE_CUPOANE_PENSIE)
            throw new TipCerereEronat();

        if (cerereTip == Cerere.CerereTip.INLOCUIRE_BULETIN)
            throw new TipCerereEronat();

        if (cerereTip == Cerere.CerereTip.INLOCUIRE_CARNET)
            throw new TipCerereEronat();

        if (cerereTip == Cerere.CerereTip.INLOCUIRE_PERMIS)
            throw new TipCerereEronat();

        if (cerereTip == Cerere.CerereTip.INREGISTRARE_VENIT_SALARIAL)
            throw new TipCerereEronat();

        Cerere cerere = new Cerere(scrieTextCerere(cerereTip), dateTime, prioritate);
        super.obtineCereriInAsteptare().add(cerere);

        return cerere;
    }

    @Override
    public String obtineTip() {
        return "entitate juridica";
    }
}
