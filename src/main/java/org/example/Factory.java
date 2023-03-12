package org.example;

import java.util.ArrayList;

/**
 * Design Pattern - Factory Pattern
 */
public class Factory {
    /** Creeaza un subtip de Utilizator, in functie de input.
     * @param args Cuvintele de pe linia ce adauga un utilizator nou.
     * @return O instanta a unui subtip de Utilizator cu datele din input.
     */
    public static Utilizator creareUtilizator(ArrayList<String> args) {
        if (args.get(1).equals("angajat")) {
            return new Angajat(args.get(2), args.get(3));
        }

        if (args.get(1).equals("elev")) {
            return new Elev(args.get(2), args.get(3));
        }

        if (args.get(1).equals("entitate juridica")) {
            return new EntitateJuridica(args.get(2), args.get(3));
        }

        if (args.get(1).equals("pensionar")) {
            return new Pensionar(args.get(2));
        }

        if (args.get(1).equals("persoana")) {
            return new Persoana(args.get(2));
        }

        return null;
    }
}
