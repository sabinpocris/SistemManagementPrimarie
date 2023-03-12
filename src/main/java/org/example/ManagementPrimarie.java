package org.example;

import java.io.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Reprezinta functia de management a unei primarii.
 *
 * @author Pocris Sabin
 */
public class ManagementPrimarie {

    private Birou<Angajat> birouDeAngajati;
    private Birou<Elev> birouDeElevi;
    private Birou<Persoana> birouDePersoane;
    private Birou<Pensionar> birouDePensionari;
    private Birou<EntitateJuridica> birouJuridic;
    private ArrayList<Utilizator> utilizatori;
    private BufferedReader input;
    private BufferedWriter output;

    public enum Actiune {
        ADAUGA_UTILIZATOR, CERERE_NOUA, AFISEAZA_CERERI, UNDEFINED,
        RETRAGE_CERERE, AFISEAZA_CERERI_BIROU, ADAUGA_FUNCTIONAR, REZOLVA_CERERE,
        AFISEAZA_CERERI_FINALIZATE
    }

    /**
     * @param inputFilePath Numele fisierului ce contine inputul.
     */
    ManagementPrimarie(String inputFilePath) {
        utilizatori = new ArrayList<>();
        birouDeAngajati = new Birou<>();
        birouDeElevi = new Birou<>();
        birouDePersoane = new Birou<>();
        birouDePensionari = new Birou<>();
        birouJuridic = new Birou<>();

        try {
            input = new BufferedReader(new FileReader("src/main/resources/input/" + inputFilePath));
            output = new BufferedWriter(new FileWriter("src/main/resources/output/" + inputFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inchide fisierele deschise.
     */
    public void close() {
        try {
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Citeste din fisier si prelucreaza inputul pentru a fi mai usor de lucrat pe el.
     * @return Un vector de vectori ce contin cuvintele de pe fiecare linie.
     */
    public ArrayList<ArrayList<String>> citesteInput() {
        ArrayList<ArrayList<String>> inputArgs = new ArrayList<>();
        String inputLine = "";


        try {
            while ((inputLine = input.readLine()) != null) {
                inputArgs.add(new ArrayList<>(Arrays.asList(inputLine.split("; "))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputArgs;
    }

    /** Adauga un utilizator nou.
     * @param args Cuvintele de pe linia in care se adauga un utilizator
     */
    public void adaugaUtilizator(ArrayList<String> args) {
        Utilizator utilizatorNou = Factory.creareUtilizator(args);

        if (utilizatorNou == null) {
            return;
        }

        utilizatori.add(utilizatorNou);
    }

    /** Creeaza o cerere noua.
     * @param args Cuvintele de pe linia in care se adauga o cerere
     */
    public void cerereNoua(ArrayList<String> args) {
        Utilizator utilizator = cautaUtilizator(args.get(1));

        if (utilizator == null) {
            return;
        }

        try {
            Cerere cerere = utilizator.creeazaCerere(Parser.parseazaTipCerere(args.get(2)), Integer.parseInt(args.get(4)),
                    Parser.parseazaLocalDateTime(args.get(3)));
            adaugaCerereBirou(cerere, utilizator, utilizator.obtineTip());
        } catch (TipCerereEronat eronat) {
            try {
                output.write("Utilizatorul de tip " + utilizator.obtineTip() + " nu poate inainta o cerere de tip " + args.get(2) + "\n");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /** Adauga cererea in biroul potrivit.
     * @param cerere Cererea ce urmeaza a fi adaugata.
     * @param user Utilizatorul care e creat cererea.
     * @param tipUtilizator Tipul utilizatorului, ex: "angajat", "elev", etc.
     */
    public void adaugaCerereBirou(Cerere cerere, Utilizator user, String tipUtilizator) {
        switch (tipUtilizator) {
            case "angajat":
                birouDeAngajati.adaugaCerere(cerere, user);
                break;
            case "elev":
                birouDeElevi.adaugaCerere(cerere, user);
                break;
            case "persoana":
                birouDePersoane.adaugaCerere(cerere, user);
                break;
            case "pensionar":
                birouDePensionari.adaugaCerere(cerere, user);
                break;
            case "entitate juridica":
                birouJuridic.adaugaCerere(cerere, user);
                break;
            default:
                break;
        }
    }

    /** Afiseaza cererile in asteptare ale unui utilizator.
     * @param args Cuvintele de pe linia in care se afiseaza cererile.
     */
    public void afiseazaCereri(ArrayList<String> args) {
        Utilizator utilizator = cautaUtilizator(args.get(1));

        try {
            output.write(utilizator.afiseazaCereriInAsteptare());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Afiseaza cererile finalizate ale unui utilizator.
     * @param args Cuvintele de pe linia in care se afiseaza cererile.
     */
    public void afiseazaCereriFinalizate(ArrayList<String> args) {
        Utilizator utilizator = cautaUtilizator(args.get(1));

        try {
            output.write(utilizator.afiseazaCereriFinalizate());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Afiseaza cererile dintr-un birou dupa tipul lui.
     * @param args Cuvintele de pe linia in care se afiseaza cererile din birou.
     */
    public void afiseazaCereriBirou(ArrayList<String> args) {
        StringBuilder resultat = new StringBuilder(args.get(1));
        resultat.append(" - cereri in birou:\n");

        switch (args.get(1)) {
            case "angajat":
                resultat.append(birouDeAngajati.afisareCereriInAsteptare());
                break;
            case "elev":
                resultat.append(birouDeElevi.afisareCereriInAsteptare());
                break;
            case "persoana":
                resultat.append(birouDePersoane.afisareCereriInAsteptare());
                break;
            case "pensionar":
                resultat.append(birouDePensionari.afisareCereriInAsteptare());
                break;
            case "entitate juridica":
                resultat.append(birouJuridic.afisareCereriInAsteptare());
                break;
            default:
                return;
        }

        try {
            output.write(resultat.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** Cauta utilizatorul dupa nume si intoarce obiectul daca exista.
     * @param nume Numele utilizatorului
     * @return obiectul de tip Utilizator sau null
     */
    public Utilizator cautaUtilizator(String nume) {
        for (var user : utilizatori) {
            if (user.obtineNume().equals(nume)) {
                return user;
            }
        }

        return null;
    }

    /** Retrage o cerere.
     * @param args Cuvintele de pe linia in care se retrage o cerere.
     */
    public void retrageCerere(ArrayList<String> args) {
        Utilizator utilizator = cautaUtilizator(args.get(1));

        utilizator.stergeCerere(Parser.parseazaLocalDateTime(args.get(2)));

        // sterge cererea si din birou
        retrageCerereDinBirou(Parser.parseazaLocalDateTime(args.get(2)), utilizator.obtineTip());
    }

    /** Retrage o cerere dupa data si ora, din biroul de tip precizat.
     * @param dateTime Data si ora la care a fost facuta cererea.
     * @param tipBirou Tipul biroului
     */
    public void retrageCerereDinBirou(LocalDateTime dateTime, String tipBirou) {
        switch (tipBirou) {
            case "elev":
                birouDeElevi.stergeCerere(dateTime);
                break;
            case "persoana":
                birouDePersoane.stergeCerere(dateTime);
                break;
            case "angajat":
                birouDeAngajati.stergeCerere(dateTime);
                break;
            case "pensionar":
                birouDePensionari.stergeCerere(dateTime);
                break;
            case "entitate juridica":
                birouJuridic.stergeCerere(dateTime);
                break;
            default:
        }
    }

    /** Adauga un functionar.
     * @param args Cuvintele de pe linia in care se adauga un functionar..
     */
    public void adaugaFunctionar(ArrayList<String> args) {
        FunctionarPublic functionar = new FunctionarPublic(args.get(2), args.get(1));

        switch (args.get(1)) {
            case "angajat":
                birouDeAngajati.adaugaFunctionar(functionar);
                break;
            case "elev":
                birouDeElevi.adaugaFunctionar(functionar);
                break;
            case "persoana":
                birouDePersoane.adaugaFunctionar(functionar);
                break;
            case "pensionar":
                birouDePensionari.adaugaFunctionar(functionar);
                break;
            case "entitate juridica":
                birouJuridic.adaugaFunctionar(functionar);
                break;
            default:
        }
    }

    /** Rezolva o cerere.
     * @param args Cuvintele de pe linia in care se rezolva o cerere..
     */
    public void rezolvaCerere(ArrayList<String> args) {
        switch (args.get(1)) {
            case "angajat":
                birouDeAngajati.rezolvaDosar(args.get(2));
                break;
            case "elev":
                birouDeElevi.rezolvaDosar(args.get(2));
                break;
            case "persoana":
                birouDePersoane.rezolvaDosar(args.get(2));
                break;
            case "pensionar":
                birouDePensionari.rezolvaDosar(args.get(2));
                break;
            case "entitate juridica":
                birouJuridic.rezolvaDosar(args.get(2));
                break;
            default:
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        ManagementPrimarie managementPrimarie =
                new ManagementPrimarie(args[0]);

        ArrayList<ArrayList<String>> inputArgs = managementPrimarie.citesteInput();

        for (var lineArgs : inputArgs) {
            switch (Parser.parseazaManagementPrimarieAction(lineArgs.get(0))) {
                case ADAUGA_UTILIZATOR:
                    managementPrimarie.adaugaUtilizator(lineArgs);
                    break;
                case AFISEAZA_CERERI:
                    managementPrimarie.afiseazaCereri(lineArgs);
                    break;
                case CERERE_NOUA:
                    managementPrimarie.cerereNoua(lineArgs);
                    break;
                case RETRAGE_CERERE:
                    managementPrimarie.retrageCerere(lineArgs);
                    break;
                case AFISEAZA_CERERI_BIROU:
                    managementPrimarie.afiseazaCereriBirou(lineArgs);
                    break;
                case ADAUGA_FUNCTIONAR:
                    managementPrimarie.adaugaFunctionar(lineArgs);
                    break;
                case REZOLVA_CERERE:
                    managementPrimarie.rezolvaCerere(lineArgs);
                    break;
                case AFISEAZA_CERERI_FINALIZATE:
                    managementPrimarie.afiseazaCereriFinalizate(lineArgs);
                    break;
                default:
                    break;
            }
        }

        managementPrimarie.close();
    }
}