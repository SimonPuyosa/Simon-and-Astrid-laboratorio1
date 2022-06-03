package ve.usb.grafoLib

/*
 Esta clase al inicializarse determina si un digrafo fuertemente conectado, 
 tiene o no un ciclo euleriano. Si el digrafo de entrada no es fuertemente conectado,
 entonces se lanza un RuntineException.
 */
public class CicloEuleriano(val g: GrafoDirigido) {

    // Retorna un objeto iterable que contiene los lados del ciclo euleriano.
    // Si el digrafo no tiene ciclo euleriano, entonces se lanza un RuntineException. 
    fun obtenerCicloEuleriano() : Iterable<Arco> {
	
    }
    
    // Retorna true si el digrafo tiene un ciclo euleriano, y false en caso contrario.
    fun tieneCicloEuleriano() : Boolean {

    }
}
