package ve.usb.libGrafo

abstract class Lado(val a: Int, val b: Int) {

    /** Metodo en el que retorna una integral que representa cualquier vertice del lado
     */
    fun cualquieraDeLosVertices() : Int {
        /** Salida: una integral del valor de cualquier vertice del lado
         *  Precondicion: a.isInt() == true
         *  Tiempo: O(1)
         */
        return a
    }

    /** Metodo en el que dado una integral que representa un vertice del lado retorna una integral que
     *  representa el otro vertice del lado, si el valor no se encuentra en el lado se lanza un RuntimeException
     */
    fun elOtroVertice(w: Int) : Int {
        /** Entrada: una integral del valor de un vertice del lado
         *  Salida: una integral del valor del otro vertice del lado
         *  Precondicion: a.isInt() == true && b.isInt() == true
         *  Postcondicion: result != w
         *  Tiempo: O(1)
         */
        when (w) {
            a -> {
                return b
            }
            b -> {
                return a
            }
            else -> {
                RuntimeException("la integral debe ser uno de los dos vertices")
                return 0
            }
        }
    }
}
