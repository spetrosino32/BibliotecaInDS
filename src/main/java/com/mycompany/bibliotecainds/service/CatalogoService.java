/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bibliotecainds.service;

import com.mycompany.bibliotecainds.model.Libro;
import java.util.List;
/**
 *
 * @author Giuseppe
 */
public interface CatalogoService {
    public void aggiungiLibro(Libro libro) throws Exception;
    public void rimuoviLibro(Libro libro) throws Exception;
    public List<Libro> cercaLibri(String l);
    public List<Libro> getCatalogo();
    
}
