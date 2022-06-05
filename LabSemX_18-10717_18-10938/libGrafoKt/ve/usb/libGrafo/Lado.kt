package libGrafoKt.ve.usb.libGrafo

abstract class Lado(val a: Int, val b: Int) {

    /** Metodo en el que retorna un entero que representa cualquier vertice del lado
     */
    fun cualquieraDeLosVertices() : Int {
        /** Salida: un entero del valor de cualquier vertice del lado
         *  Precondicion: a.isInt() == true
         *  Tiempo: O(1)
         */
        return this.a
    }

    /** Metodo en el que dado un entero que representa un vertice del lado retorna un entero que
     *  representa el otro vertice del lado, si el valor no se encuentra en el lado se lanza un RuntimeException
     */
    fun elOtroVertice(w: Int) : Int {
        /** Entrada: un entero del valor de un vertice del lado
         *  Salida: un entero del valor del otro vertice del lado
         *  Precondicion: a.isInt() == true && b.isInt() == true
         *  Postcondicion: result != w
         *  Tiempo: O(1)
         */
        return when (w) {
            a -> {
                b
            }
            b -> {
                a
            }
            else -> {
                throw RuntimeException("el entero debe ser uno de los dos vertices")
            }
        }
    }
}
