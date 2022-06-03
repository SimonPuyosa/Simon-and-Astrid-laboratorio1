package ve.usb.libGrafo

/** estructura que permite manejar valores, costo, grados y permite compararlo entre si:
 *      valor: un entero que representa el valor del vertice
 *      costo: un entero que representa el costo del vertice
 *      gradoExterior: entero que representa el gradoExterior del vertice o el lado total del vertice si es no dirigido
 *      numDeVertices: entero que representa el gradoInterior de vertice
 */
class Vertice(val valor: Int, var Costo: Double = 0.0, var gradoExterior: Int = 0, var gradoInterior: Int = 0) {

    /** Metodo que permite comparar dos Vertices y determinar si son iguales por su valor
     */
    override fun equals(other: Any?): Boolean {
        val b: Vertice = other as Vertice
        return this.valor == b.valor
    }

    override fun hashCode(): Int = this.valor
}

