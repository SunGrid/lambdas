import java.io.File

class LogReader {                            //lambda parameter of String
    // = {} default argument implementation allows process file method to have this second parameter of a lambda
    fun processFile(file: File, processLine: (String) -> Unit = {}) {

            file.forEachLine {
                println("Number of Chars: ${it.length}")
                if (it.isNotEmpty()) {
                    processLine(it)
                }
                println("Line Done Processing")
            }

    }

    fun processFileWithHandlers(file: File, logHandler: LogHandler) {
        file.forEachLine {
            println("p`FWH - Start of Processing")
            if (it.isNotEmpty()) {
                logHandler.handleLine().forEach { handler -> handler(it) }
            }
            println("Line Done Processing")
        }
    }
}

interface LogHandler {
    fun handleLine(): List<(String) -> Unit>
}

val reader = LogReader()
val textFile = File("/home/michael/test.txt")

//Process with single lambda
fun main(){

  //  reader.processFile(textFile, {
  //      println( "First 10 Chars: ${it.substring(0..9)} ") })
    reader.processFile(textFile) {
        println( "First 10 Chars: ${it.substring(0..9)} ") }

    val logHandler = object : LogHandler {
        override fun handleLine(): List<(String) -> Unit> { return listOf<(String) -> Unit> (
            { line -> println("${line.substring(0,1)}") },
            { line -> println("${line.substring(2,4)}") },
            { line -> println("${line.substring(5,10)}") }
        )
        }
    }

    //Process with multiple handlers via the logHandler
    reader.processFileWithHandlers(textFile, logHandler)
}