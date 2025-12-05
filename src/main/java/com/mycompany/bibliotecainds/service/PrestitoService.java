/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
