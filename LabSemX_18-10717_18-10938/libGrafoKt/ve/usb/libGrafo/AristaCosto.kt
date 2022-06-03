package libGrafoKt.ve.usb.libGrafo

public class AristaCosto(val x: Int, val y: Int, val costo: Double) : Comparable<AristaCosto>, Arista(x, y) {

    /** Metodo en el que retorna un Double que representa costo del lado
     */
    fun costo() : Double {
        /** Salida: una integral del valor del costo del lado
         *  Precondicion: costo.isDouble() == true
         *  Tiempo: O(1)
         */
        return costo
    }

    /** Metodo en el que retorna una string de los valores y el costo del lado
     */
    override fun toString() : String {
        /** Salida: una string de los valores y el costo del lado
         *  Precondicion: x.isInt() == true && y.isInt() == true && costo.isDouble() == true
         *  Tiempo: O(1)
         */
        return " ($x, $y, $costo) "
    }

    /** Metodo en el que recibe otro AristaCosto y lo compara con el AristaCosto actual por su costo
     */
     override fun compareTo(other: AristaCosto): Int {
        /** Entrada: un AristaCosto que va a ser comprado con el AristaCosto actual
         *  Salida: una integral que representa el resultado de la comparacion entre ambos costo
         *  Precondicion: this == AristaCosto() && other == AristaCosto()
         *  Postcondicion: result == -1 || result == 1 || result == 0
         *  Tiempo: O(1)
         */
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
