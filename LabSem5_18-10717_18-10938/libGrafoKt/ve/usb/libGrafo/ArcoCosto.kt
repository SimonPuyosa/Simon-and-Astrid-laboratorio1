package ve.usb.libGrafo

public class ArcoCosto(val x: Int, val y: Int, val costo: Double) : Arco(x, y) {

    /** Metodo en el que retorna un Double que representa costo del arco
     */
    fun costo() : Double {
        /** Salida: un entero del valor del costo del arco
         *  Precondicion: costo.isDouble() == true
         *  Tiempo: O(1)
         */
        return costo
    }

    /** Metodo en el que retorna una string de los valores y el costo del arco
     */
    override fun toString() : String {
        /** Salida: una string de los valores y el costo del arco
         *  Precondicion: inicio.isInt() == true && fin.isInt() == true && costo.isDouble() == true
         *  Tiempo: O(1)
         */
        return " ($x, $y, $costo) "
    }
} 
