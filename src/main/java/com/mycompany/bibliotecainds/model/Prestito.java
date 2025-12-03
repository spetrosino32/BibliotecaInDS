/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bibliotecainds.model;
import java.time.LocalDate;
/**
 *
 * @author Giuseppe
 */
public class Prestito {
    private Libro libro;
    private LocalDate dataPrestito;

    public Prestito(Libro libro, LocalDate dataPrestito) {
        this.libro = libro;
        this.dataPrestito = dataPrestito;
    }

    // getter e setter
}
