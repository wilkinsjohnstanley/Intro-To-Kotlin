fun main(){
    val a = 10 //Int is the default integer number representation when a whole number (such as 20) is specified.
    val b = 1234567890L //Long is an integer representation supporting larger numbers. YOu create it by using an L suffix.
    val c = 10.0 //Double is the default decimal number representation. It holds values up to 15 or 16 decimal places.
    val d = 10F //Float is an integer representation supporting smaller decimal numbers. It holds values up to 7 or 8 decimal places. You create it using the F or f suffix.

    println("val b is $b and c is $c and d is $d")

/*
* All numbers can be negative,
* and you can use underscores('_') inside longer numbers to improve their readability.
*/
fun main() {
    val smallDebt = -0.72
    println(smallDebt) // -0.72
    val million = 1_000_000
    println(million) // 1000000
}
}
