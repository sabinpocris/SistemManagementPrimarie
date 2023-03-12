package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Subtip Utilizator, reprezinta o persoana
 */
public class Persoana extends Utilizator {

    public Persoana(String nume) {
        super.seteazaNume(nume);
        super.seteazaCereriInAsteptare(new PriorityQueue<>());
        super.seteazaCereriSolutionate(new ArrayList<>());
    }

    @Override
    public String scrieTextCerere(Cerere.CerereTip cerereTip) {
        return "Subsemnatul " + super.obtineNume() +
                ", va rog sa-mi aprobati urmatoarea solicitare: " + Parser.tipCerereToString(cerereTip);
    }

    @Override
    public Cerere creeazaCerere(Cerere.CerereTip cerereTip, int prioritate, LocalDateTime dateTime) throws TipCerereEronat {
        if (cerereTip == Cerere.CerereTip.REINNOIRE_AUTORIZATIE)
            throw new TipCerereEronat();

        if (cerereTip == Cerere.CerereTip.INREGISTRARE_CUPOANE_PENSIE)
            throw new TipCerereEronat();

        if (cerereTip == Cerere.CerereTip.INREGISTRARE_VENIT_SALARIAL) {
            throw new TipCerereEronat();
        }

        if (cerereTip == Cerere.CerereTip.CREARE_ACT_CONSTITUTIV)
            throw new TipCerereEronat();

        if (cerereTip == Cerere.CerereTip.INLOCUIRE_CARNET) {
            throw new TipCerereEronat();
        }


        Cerere cerere = new Cerere(scrieTextCerere(cerereTip), dateTime, prioritate);
        super.obtineCereriInAsteptare().add(cerere);

        return cerere;
    }

    public String obtineTip() {
        return "persoana";
    }
}
