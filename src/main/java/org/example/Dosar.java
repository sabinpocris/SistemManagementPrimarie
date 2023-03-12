package org.example;

/**
 * Dosar ce contine o cerere si utilizatorul ei, pentru a fi depusa in birou..
 */
public class Dosar implements Comparable<Dosar> {
    private Cerere cerere;
    private Utilizator autor;

    public Dosar(Cerere cerere, Utilizator autor) {
        this.cerere = cerere;
        this.autor = autor;
    }

    public Cerere obtineCerere() {
        return cerere;
    }

    public void seteazaCerere(Cerere cerere) {
        this.cerere = cerere;
    }

    public Utilizator obtineAutor() {
        return autor;
    }

    public void seteazaAutor(Utilizator autor) {
        this.autor = autor;
    }

    public int compareTo(Dosar o) {
        if (this.obtineCerere().obtinePrioritate() > o.obtineCerere().obtinePrioritate())
            return -1;

        if (this.obtineCerere().obtinePrioritate() < o.obtineCerere().obtinePrioritate())
            return 1;

        if (this.obtineCerere().obtineDateTime().isBefore(o.obtineCerere().obtineDateTime()))
            return -1;

        if (this.obtineCerere().obtineDateTime().isAfter(o.obtineCerere().obtineDateTime()))
            return 1;

        return 0;
    }
}
