package org.example;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/** Implementeaza functionalitatea unui birou.
 * @param <T> Subtip Utilizator
 */
public class Birou<T extends Utilizator> {
    private Queue<Dosar> cereriInAsteptare;
    private ArrayList<Dosar> cereriSolutionate;
    private ArrayList<FunctionarPublic> functionari;


    public Birou() {
        this.cereriInAsteptare = new PriorityQueue<>();
        this.cereriSolutionate = new ArrayList<>();
        this.functionari = new ArrayList<>();
    }

    /** Adauga cererea intr-o coada de prioritate.
     * @param cerere Cererea ce va fi adaugata.
     * @param utilizator Utilizatorul care a scris cererea.
     * @param <E> subtip Utilizator
     */
    public <E extends Utilizator> void adaugaCerere(Cerere cerere, E utilizator) {
        cereriInAsteptare.add(new Dosar(cerere, utilizator));
    }

    /** Afiseaza cererile ordonate dupa prioritate si vechime.
     * @return String ce contine o forma printabila a cererilor
     */
    public String afisareCereriInAsteptare() {
        Queue<Dosar> cereri = new PriorityQueue<>(cereriInAsteptare);
        StringBuilder result = new StringBuilder();

        while (!cereri.isEmpty()) {
            Dosar temp = cereri.poll();

            result.append(temp.obtineCerere().obtinePrioritate()).append(" - ");
            result.append(temp.obtineCerere().toString()).append("\n");
        }

        return result.toString();
    }

    public void adaugaFunctionar(FunctionarPublic functionar) {
        functionari.add(functionar);
    }

    /** Functionarul specificat va rezolva o cerere din birou, o va adauga la cereri solutionate,
     * atat in birou cat si in lista utilizatorului.
     * @param numeFunctionar Numele functionarului ce va solutiona o cerere.
     */
    public void rezolvaDosar(String numeFunctionar) {
        Dosar dosar = cereriInAsteptare.poll();
        FunctionarPublic functionar = findFunctionarByName(numeFunctionar);

        String output = Parser.localDateTimeToString(dosar.obtineCerere().obtineDateTime());
        output += " - ";
        output += dosar.obtineAutor().obtineNume();
        output += "\n";

        try {
            functionar.obtineWriter().write(output);
            functionar.obtineWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        cereriSolutionate.add(dosar);

        dosar.obtineAutor().adaugaCerereSolutionata(dosar.obtineCerere());
    }

    /** Cauta si returneaza functionarul public cu un nume specific.
     * @param nume Numele functionarului cautat.
     * @return Obiectul care are acelasi nume cu parametrul "nume", sau null daca nu exista
     */
    public FunctionarPublic findFunctionarByName(String nume) {
        for (var f : functionari) {
            if (f.obtineNume().equals(nume)) {
                return f;
            }
        }

        return null;
    }

    /** Sterge o cerere din birou, dupa data si ora la care a fost creeata.
     * @param dateTime Data si ora la care a fost creeata cererea.
     */
    public void stergeCerere(LocalDateTime dateTime) {
        Queue<Dosar> tempQueue = new PriorityQueue<>();

        while (!cereriInAsteptare.isEmpty()) {
            Dosar dosar = cereriInAsteptare.poll();
            if (dosar.obtineCerere().obtineDateTime().isEqual(dateTime)) {
                continue; // sar peste cererea pe care vreau sa o sterg
            }

            tempQueue.add(dosar);
        }

        cereriInAsteptare = tempQueue;
    }
}
