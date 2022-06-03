package libGrafoKt.ve.usb.libGrafo

public open class Arista(val v: Int, val u: Int) : Lado(v, u) {

    /** Metodo en el que retorna una string de los valores del lado
     */
    override fun toString() : String {
        /** Salida: una string de los valores del lado
         *  Precondicion: v.isInt() == true && u.isInt() == true
         *  Tiempo: O(1)
         */
        return " ($v, $u) "
    }
} 
