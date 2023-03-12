package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Subtip Utilizator, reprezinta un elev
 */
public class Elev extends Utilizator {
    String scoala;

    Elev(String nume, String scoala) {
        super.seteazaNume(nume);
        this.scoala = scoala;
        super.seteazaCereriInAsteptare(new PriorityQueue<>());
        super.seteazaCereriSolutionate(new ArrayList<>());
    }

    public ArrayList<Cerere> obtineCereriSolutionate() {
        return super.obtineCereriSolutionate();
    }

    public void seteazaCereriSolutionate(ArrayList<Cerere> cereriSolutionate) {
        super.seteazaCereriSolutionate(cereriSolutionate);
    }

    public Queue<Cerere> obtineCereriInAsteptare() {
        return super.obtineCereriInAsteptare();
    }

    public void seteazaCereriInAsteptare(Queue<Cerere> cereriInAsteptare) {
        super.seteazaCereriInAsteptare(cereriInAsteptare);
    }

    public String obtineNume() {
        return super.obtineNume();
    }

    public void seteazaNume(String nume) {
        super.seteazaNume(nume);
    }

    public String scrieTextCerere(Cerere.CerereTip cerereTip) {
        return "Subsemnatul " + super.obtineNume() + ", elev la scoala " + this.scoala + "," +
                " va rog sa-mi aprobati urmatoarea solicitare: " + Parser.tipCerereToString(cerereTip);
    }

    public Cerere creeazaCerere(Cerere.CerereTip cerereTip, int prioritate, LocalDateTime dateTime) throws TipCerereEronat {
        if (cerereTip == Cerere.CerereTip.INREGISTRARE_CUPOANE_PENSIE)
            throw new TipCerereEronat();

        if (cerereTip == Cerere.CerereTip.REINNOIRE_AUTORIZATIE)
            throw new TipCerereEronat();

        if (cerereTip == Cerere.CerereTip.INLOCUIRE_PERMIS)
            throw new TipCerereEronat();

        if (cerereTip == Cerere.CerereTip.INREGISTRARE_VENIT_SALARIAL)
            throw new TipCerereEronat();

        if (cerereTip == Cerere.CerereTip.CREARE_ACT_CONSTITUTIV)
            throw new TipCerereEronat();

        Cerere cerere = new Cerere(scrieTextCerere(cerereTip), dateTime, prioritate);
        super.obtineCereriInAsteptare().add(cerere);

        return cerere;
    }

    public String obtineTip() {
        return "elev";
    }

    public void stergeCerere(LocalDateTime dateTime) {
        super.stergeCerere(dateTime);
    }
}
