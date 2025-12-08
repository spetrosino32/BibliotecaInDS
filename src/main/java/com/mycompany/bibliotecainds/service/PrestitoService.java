/**
 * @file PrestitoService,java
 * @brief Interfaccia per la gestione dei prestiti dei libri 
 * 
 * Questa interfaccia definisce le operazion 
 */
package com.mycompany.bibliotecainds.service;

import com.mycompany.bibliotecainds.model.*;
/**
 *
 * @author Giuseppe
 */
public interface PrestitoService {
    public boolean registraPrestito(Utente utente, Libro libro) throws Exception;
    public void restituisciPrestito(Prestito prestito);
}
