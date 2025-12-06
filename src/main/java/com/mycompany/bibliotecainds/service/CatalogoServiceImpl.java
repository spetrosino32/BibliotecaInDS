/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bibliotecainds.service;

import com.mycompany.bibliotecainds.data.Archivio;
import com.mycompany.bibliotecainds.model.Libro;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author Giuseppe
 */
public class CatalogoServiceImpl implements CatalogoService {
    @Override
    public void aggiungiLibro(Libro nuovoLibro) throws Exception {
        List<Libro> catalogo = Archivio.getInstance().getCatalogoLibri();
        
        for (Libro l : catalogo) {
            if (l.getIsbn().equalsIgnoreCase(nuovoLibro.getIsbn())) {
                throw new Exception("Esiste già un libro con questo ISBN: " + nuovoLibro.getIsbn());
            }
        }
        catalogo.add(nuovoLibro);
    }

    @Override
    public void rimuoviLibro(Libro libro) throws Exception {
        // Regola di Business: Non si può cancellare un libro se è attualmente in prestito?
        Archivio.getInstance().getCatalogoLibri().remove(libro);
    }

    @Override
    public List<Libro> cercaLibri(String query) {
        if (query == null || query.isEmpty()) {
            return getCatalogo();
        }
        
        String lowerQuery = query.toLowerCase();
        
        return Archivio.getInstance().getCatalogoLibri().stream()
                .filter(l -> l.getTitolo().toLowerCase().contains(lowerQuery) || 
                             l.getIsbn().toLowerCase().contains(lowerQuery) ||
                             l.getAutori().toString().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    @Override
    public List<Libro> getCatalogo() {
        return Archivio.getInstance().getCatalogoLibri();
    }
    
}
