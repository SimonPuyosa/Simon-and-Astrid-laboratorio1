package linkedList

class Vertice(val valor: Int?, var gradoInterior: Int = 0, var gradoExterior: Int = 0){
    var next: Vertice? = null
    var prev: Vertice? = null
}
