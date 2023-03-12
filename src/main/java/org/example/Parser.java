package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility-class; contine metodele necesare pentru parsarea inputului
 */
public class Parser {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");

    /** Alege actiunea in functie de input.
     * @param arg Un cuvant din input ce reprezinta o "actiune"
     * @return campul specific actiunii din enumerarea ManagementPrimarie.Actiune
     */
    public static ManagementPrimarie.Actiune parseazaManagementPrimarieAction(String arg) {
        if (arg.equals("adauga_utilizator")) {
            return ManagementPrimarie.Actiune.ADAUGA_UTILIZATOR;
        } else if (arg.equals("cerere_noua")) {
            return ManagementPrimarie.Actiune.CERERE_NOUA;
        } else if (arg.equals("afiseaza_cereri_in_asteptare")) {
            return ManagementPrimarie.Actiune.AFISEAZA_CERERI;
        } else if (arg.equals("afiseaza_cereri")) {
            return ManagementPrimarie.Actiune.AFISEAZA_CERERI_BIROU;
        } else if (arg.equals("retrage_cerere")) {
            return ManagementPrimarie.Actiune.RETRAGE_CERERE;
        } else if (arg.equals("adauga_functionar")) {
            return ManagementPrimarie.Actiune.ADAUGA_FUNCTIONAR;
        } else if (arg.equals("rezolva_cerere")) {
            return ManagementPrimarie.Actiune.REZOLVA_CERERE;
        } else if (arg.equals("afiseaza_cereri_finalizate")) {
            return ManagementPrimarie.Actiune.AFISEAZA_CERERI_FINALIZATE;
        }

        return ManagementPrimarie.Actiune.UNDEFINED;
    }

    /** Converteste tipul unei cereri intr-o forma printabila.
     * @param tip tipul cererii
     * @return un string ce reprezinta tipul cererii
     */
    public static String tipCerereToString(Cerere.CerereTip tip) {
        switch (tip) {
            case INLOCUIRE_BULETIN:
                return "inlocuire buletin";
            case INLOCUIRE_PERMIS:
                return "inlocuire carnet de sofer";
            case INREGISTRARE_VENIT_SALARIAL:
                return "inregistrare venit salarial";
            case INLOCUIRE_CARNET:
                return "inlocuire carnet de elev";
            case CREARE_ACT_CONSTITUTIV:
                return "creare act constitutiv";
            case REINNOIRE_AUTORIZATIE:
                return "reinnoire autorizatie";
            case INREGISTRARE_CUPOANE_PENSIE:
                return "inregistrare cupoane de pensie";

            default:
                return "";
        }
    }

    /** Alege tipul de cerere in functie de input.
     * @param input Un cuvant din input ce reprezinta un "tip de cerere"
     * @return
     */
    public static Cerere.CerereTip parseazaTipCerere(String input) {
        switch (input) {
            case "inlocuire buletin":
                return Cerere.CerereTip.INLOCUIRE_BULETIN;
            case "inlocuire carnet de sofer":
                return Cerere.CerereTip.INLOCUIRE_PERMIS;
            case "reinnoire autorizatie":
                return Cerere.CerereTip.REINNOIRE_AUTORIZATIE;
            case "inlocuire carnet de elev":
                return Cerere.CerereTip.INLOCUIRE_CARNET;
            case "inregistrare venit salarial":
                return Cerere.CerereTip.INREGISTRARE_VENIT_SALARIAL;
            case "creare act constitutiv":
                return Cerere.CerereTip.CREARE_ACT_CONSTITUTIV;
            case "inregistrare cupoane de pensie":
                return Cerere.CerereTip.INREGISTRARE_CUPOANE_PENSIE;
            default:
                return Cerere.CerereTip.OK;
        }
    }

    public static LocalDateTime parseazaLocalDateTime(String time) {
        return LocalDateTime.parse(time, formatter);
    }

    public static String localDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }
}
