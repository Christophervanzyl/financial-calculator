import TwoPartRetirementInfoUI.showTwoPartRetirementInfo
import calculators.DrawdownCalculator.{calculateCompoundInterest, calculateTwoPartRetirement}
import models.CompoundingPeriod

import scala.swing.event.ButtonClicked
import scala.swing._
import scala.util.Try

object TwoTierRetirementCalculatorUI extends SimpleSwingApplication {

  val principalTextField = new TextField { columns = 10 }
  val ageTextField = new TextField { columns = 10 }
  val calculateButton = new Button("Calculate")
  val resultTextArea = new TextArea {
    editable = false
    lineWrap = true
    wordWrap = true
  }

  def top: MainFrame = new MainFrame {
    title = "Two part Retirement visualiser"
    contents = new BoxPanel(Orientation.Vertical) {
      border = Swing.EmptyBorder(20)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Principal: ")
        contents += principalTextField
      }
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Age: ")
        contents += ageTextField
      }
      contents += Swing.VStrut(10)
      contents += calculateButton
      contents += Swing.VStrut(10)
      contents += new ScrollPane(resultTextArea)
      border = Swing.EmptyBorder(20)
    }

    // Define calculation logic
    listenTo(calculateButton)
    reactions += {
      case ButtonClicked(`calculateButton`) =>
      // Parsing input and validation

      val principalOpt = parseDouble(principalTextField.text)
      val ageOpt = parseInt(ageTextField.text)

        (principalOpt, ageOpt) match {
          case (Some(principal), Some(age)) => showTwoPartRetirementInfo(principal, age)

          case _ => resultTextArea.text = "Error: Invalid input. Please check your inputs and try again."
        }
    }
  }

  // Helper functions to parse inputs
  private def parseDouble(s: String): Option[Double] = Try(s.toDouble).toOption
  private def parseInt(s: String): Option[Int] = Try(s.toInt).toOption

}

object TwoPartRetirementInfoUI  extends Frame {
  title = "Two Part Retirement Info" // set the title
  location = new Point(50, 200) // set the initial position
  val resultTextArea = new TextArea {
    editable = false
    lineWrap = true
    wordWrap = true
    preferredSize = new Dimension(400, 400)
  }
  contents = new ScrollPane(resultTextArea)

  def showTwoPartRetirementInfo(principal: Double, age: Int): Unit = {
    // Assumptions:
    // Retirement savings at 25.
    // Retirement at 55.
    // Interest rate at 10%
    // Compounding Period annually

    val withoutWithdraw = calculateCompoundInterest(principal, 15, 55 - age, 0, CompoundingPeriod.Yearly)
    val withdraw = calculateTwoPartRetirement(principal, age)
    val difference = withoutWithdraw - withdraw

    resultTextArea.text = f"""
                             |Without withdrawing: $withoutWithdraw%.2f
                             |With withdrawing: $withdraw%.2f
                             |Loss: $difference%.2f
                             |""".stripMargin
    this.visible = true
  }
}

