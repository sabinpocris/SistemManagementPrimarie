package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Implementeaza functionalitatea unui functionar public.
 */
public class FunctionarPublic {
    String nume;
    String specializare;
    BufferedWriter writer;

    FunctionarPublic(String nume, String specializare) {
        this.nume = nume;
        this.specializare = specializare;

        try {
            writer = new BufferedWriter(new FileWriter("src/main/resources/output/functionar_"
                    + this.obtineNume() + ".txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeOutput() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String printeazaNume() {
        return String.join("_", this.nume.split(" "));
    }

    public BufferedWriter obtineWriter() {
        return writer;
    }

    public String obtineNume() {
        return nume;
    }

    public void seteazaNume(String nume) {
        this.nume = nume;
    }
}
