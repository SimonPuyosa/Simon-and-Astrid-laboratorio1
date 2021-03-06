package ve.usb.libGrafo

public open class Arco(val inicio: Int, val fin: Int) : Lado(inicio, fin) {

    /** Metodo en el que retorna un entero que representa el primer vertice del arco
     */
    fun fuente() : Int {
        /** Salida: un entero del valor del primer vertice del arco
         *  Precondicion: inicio.isInt() == true
         *  Tiempo: O(1)
         */
        return inicio
    }

    /** Metodo en el que retorna un entero que representa el segundo vertice del arco
     */
    fun sumidero() : Int {
        /** Salida: un entero del valor del segundo vertice del arco
         *  Precondicion: fin.isInt() == true
         *  Tiempo: O(1)
         */
        return fin
    }

    /** Metodo en el que retorna una string de los valores del arco
     */
    override fun toString() : String {
        /** Salida: una string de los valores del arco
         *  Precondicion: inicio.isInt() == true && fin.isInt() == true
         *  Tiempo: O(1)
         */
        return "($inicio, $fin) "
     }
} 
