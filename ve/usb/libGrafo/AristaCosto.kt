package ve.usb.libGrafo

public class AristaCosto(val x: Int,
			 val y: Int,
			 val costo: Double) : Comparable<AristaCosto>, Arista(x, y) {

    // Retorna el costo del arco
    fun costo() : Double {
    }

    // Representación en string de la arista
    override fun toString() : String {
    }

    /* 
     Se compara dos arista con respecto a su costo. 
     Si this.obtenerCosto > other.obtenerCosto entonces
     retorna 1. Si this.obtenerCosto < other.obtenerCosto 
     entonces retorna -1. Si this.obtenerCosto == other.obtenerCosto
     entonces retorna 0 
     */
     override fun compareTo(other: AristaCosto): Int {
     }
} 
