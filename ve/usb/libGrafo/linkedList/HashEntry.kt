package ve.usb.libGrafo.linkedList

class HashEntry(val valor: Int?, var gradoInterno: Int = 0, var gradoExterno: Int = 0){
    var next: HashEntry? = null
    var prev: HashEntry? = null
}