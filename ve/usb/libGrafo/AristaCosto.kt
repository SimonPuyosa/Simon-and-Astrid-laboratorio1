package ve.usb.libGrafo

public class AristaCosto(val x: Int,
			 val y: Int,
			 val costo: Double) : Comparable<AristaCosto>, Arista(x, y) {

    // Retorna el costo del arco
    fun costo() : Double {
        return costo //?
    }

    // Representación en string de la arista
    override fun toString() : String {
        return " ($x, $y, $costo) "
    }

     //Se compara dos arista con respecto a su costo.
     override fun compareTo(other: AristaCosto): Int {
        return when {
            this.costo > other.costo -> {
                1
            }
            this.costo < other.costo -> {
                -1
            }
            else -> {
                0
            }
        }
     }
} 
