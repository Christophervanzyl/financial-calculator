import scala.io.StdIn

object Main {
  def main(args: Array[String]): Unit = {
    println("Select your calculator:")
    println("1. Three in one calculator")
    println("2. Two part retirement calculator")

    val input = StdIn.readInt()
    input match {
      case 1 => EncompassingCalculatorUI.main(args)
      case 2 => TwoTierRetirementCalculatorUI.main(args)
      case _ => println("Invalid input. Please select 1 or 2.")
    }
  }
}
