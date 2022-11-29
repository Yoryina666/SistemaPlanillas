/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Model;

import java.util.LinkedList;
import javax.faces.model.SelectItem;

/**
 *
 * @author Estudiante
 */
public enum TipoJornada {
        SEMANAL {
        @Override
        public String toString() {
            return "Semanal";
        }
    }, QUINCENAL {
        @Override
        public String toString() {
            return "Quincenal";
        }
    }   
}
