package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Reprezinta utilizatorul.
 * Poti face o noua clasa ce extinde clasa asta pentru a face un tip de utilizator nou.
 */
public abstract class Utilizator {
    private String nume;
    private ArrayList<Cerere> cereriSolutionate;
    private Queue<Cerere> cereriInAsteptare;

    public ArrayList<Cerere> obtineCereriSolutionate() {
        return cereriSolutionate;
    }

    public void seteazaCereriSolutionate(ArrayList<Cerere> cereriSolutionate) {
        this.cereriSolutionate = cereriSolutionate;
    }

    public Queue<Cerere> obtineCereriInAsteptare() {
        return cereriInAsteptare;
    }

    public void seteazaCereriInAsteptare(Queue<Cerere> cereriInAsteptare) {
        this.cereriInAsteptare = cereriInAsteptare;
    }

    public String obtineNume() {
        return this.nume;
    }

    public void seteazaNume(String nume) {
        this.nume = nume;
    }

    /** Scrie o cerere personalizata cu datele utilizatorului, in functie de tipul cererii.
     * @param cerereTip Tipul cererii.
     * @return Textul cererii.
     */
    public abstract String scrieTextCerere(Cerere.CerereTip cerereTip);

    /** Functia creeaza o cerere si o adauga in lista de cereri in asteptare ale utilizatorului.
     * @param cerereTip Tipul cererii.
     * @param prioritate Numar ce reprezinta prioritatea cererii.
     * @param dateTime Data si ora la care a fost facuta cererea.
     * @return cererea noua ce contine datele specificate
     * @throws TipCerereEronat Exceptie cand utilizatorul nu suporta tipul cererii
     */
    public abstract Cerere creeazaCerere(Cerere.CerereTip cerereTip, int prioritate, LocalDateTime dateTime) throws TipCerereEronat;


    /** Afiseaza cererile utilizatorului in asteptare, dupa o anumita ordine.
     * @return String ce contine toate cererile in format printabil.
     */
    public String afiseazaCereriInAsteptare() {
        Queue<Cerere> cereri = new PriorityQueue<>(cereriInAsteptare);
        StringBuilder result = new StringBuilder();

        result.append(this.nume).append(" - cereri in asteptare:\n");

        while (!cereri.isEmpty()) {
            result.append(cereri.poll().toString()).append("\n");
        }

        return result.toString();
    }

    /** Afiseaza cererile finalizate ale utilizatorului. dupa o anumita ordine.
     * @return String ce contine toate cererile in format printabil.
     */
    public String afiseazaCereriFinalizate() {
        StringBuilder resultat = new StringBuilder();

        resultat.append(this.nume).append(" - cereri in finalizate:\n");

        for (var cerere : cereriSolutionate) {
            resultat.append(cerere.toString()).append("\n");
        }

        return resultat.toString();
    }

    public String obtineTip() {
        return "utilizator";
    }

    /** Sterge cererea din lista, dupa data si timp.
     * @param dateTime Data si timpul la care a fost facuta cererea.
     */
    public void stergeCerere(LocalDateTime dateTime) {
        Queue<Cerere> tempQueue = new PriorityQueue<>();

        while (!cereriInAsteptare.isEmpty()) {
            Cerere cerere = cereriInAsteptare.poll();
            if (cerere.obtineDateTime().isEqual(dateTime)) {
                continue; // sar peste cererea pe care vreau sa o sterg
            }

            tempQueue.add(cerere);
        }

        cereriInAsteptare = tempQueue;
    }

    /** Muta cererea din lista de asteptare in lista de cereri solutionate.
     * @param cerere Cererea ce a fost solutionata.
     */
    public void adaugaCerereSolutionata(Cerere cerere) {
        cereriSolutionate.add(cerere);

        stergeCerere(cerere.obtineDateTime());
    }
}
