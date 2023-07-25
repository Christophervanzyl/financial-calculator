import calculators.ThreeInOneCalculator.toCalculationResult
import models.{CompoundingPeriod, InvestmentStrategy}

import scala.swing._
import scala.swing.event.ButtonClicked
import scala.util.Try

/***
 * Simple GUI for viewing all the calculators
 */
object EncompassingCalculatorUI extends SimpleSwingApplication {

  // Create components
  val principalTextField = new TextField { columns = 10 }
  val interestRateTextField = new TextField { columns = 10 }
  val yearsTextField = new TextField { columns = 10 }
  val monthsTextField = new TextField { columns = 10 }
  val incomeTextField = new TextField { columns = 10 }
  val retirementTextField = new TextField { columns = 10 }
  val withDrawRateTextField = new TextField { columns = 10 }
  val compoundingPeriodComboBox = new ComboBox(CompoundingPeriod.values.toList)
  val investementStrategyComboBox = new ComboBox(InvestmentStrategy.values.toList)
  val calculateButton = new Button("Calculate")
  val resultTextArea = new TextArea {
    editable = false
    lineWrap = true
    wordWrap = true
  }

  def top: MainFrame = new MainFrame {
    title = "All-you-need Calculator"
    contents = new BoxPanel(Orientation.Vertical) {
      border = Swing.EmptyBorder(20)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Monthly Income: ")
        contents += incomeTextField
      }
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Monthly RA Contributions: ")
        contents += retirementTextField
      }
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Interest Rate for the drawdown: ")
        contents += interestRateTextField
      }
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Withdraw Rate for the drawdown: ")
        contents += withDrawRateTextField
      }
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Years: ")
        contents += yearsTextField
      }
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Months: ")
        contents += monthsTextField
      }
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Compounding Period: ")
        contents += compoundingPeriodComboBox
      }
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("Investment Strategy %: ")
        contents += investementStrategyComboBox
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
        val incomeOpt = parseDouble(incomeTextField.text)
        val raOpt = parseDouble(retirementTextField.text)
        val interestRateOpt = parseDouble(interestRateTextField.text)
        val withDrawRateOpt = parseDouble(withDrawRateTextField.text)
        val yearsOpt = parseInt(yearsTextField.text)
        val monthsOpt = parseInt(monthsTextField.text)
        val compoundingPeriodOpt = Try(compoundingPeriodComboBox.selection.item).toOption
        val investmentStrategyOpt = Try(investementStrategyComboBox.selection.item).toOption

        // Check if all the options have values, if so, calculate the result and update the resultTextArea
        (incomeOpt, raOpt, interestRateOpt, withDrawRateOpt, yearsOpt, monthsOpt, compoundingPeriodOpt, investmentStrategyOpt) match {
          case (Some(income), Some(ra), Some(interestRate), Some(withDrawRate), Some(years), Some(months), Some(compoundingPeriod), Some(investmentStrategy)) =>
            val result = toCalculationResult(income, ra, interestRate, withDrawRate, years, months, compoundingPeriod, investmentStrategy)

            // Update resultTextArea with values from result
            resultTextArea.text =
              f"""
                 |Yearly Income: ${result.yearlyIncome}%.2f
                 |Max Retirement Annuity Benefit: ${result.maxRetirementAnnuityBenefit}%.2f
                 |Monthly Income: ${result.monthlyIncome}%.2f
                 |Monthly Tax: ${result.monthlyTax}%.2f
                 |Net Monthly Income: ${result.netMonthlyIncome}%.2f
                 |Future Investment Value: ${result.futureInvestmentValue}%.2f
                 |Total Interest: ${result.totalInterest}%.2f
                 |Initial Investment Value: ${result.initialInvestmentValue}%.2f
                 |Interest Rate: ${result.interestRate}%.2f
          """.stripMargin
            result.values.foreach { fundValue =>
              resultTextArea.append(
                f"""
                   |Year: ${fundValue.year}%.2f, Premium Paid: ${fundValue.premiumPaid}%.2f, Fund Value without Booster: ${fundValue.fundValueWithoutBooster}%.2f, Booster Value: ${fundValue.boosterValue}%.2f, Total Fund Value: ${fundValue.totalFundValue}%.2f
            """.stripMargin
              )
            }
          case _ => resultTextArea.text = "Error: Invalid input. Please check your inputs and try again."
        }
    }
  }
  // Helper functions to parse inputs
  private def parseDouble(s: String): Option[Double] = Try(s.toDouble).toOption

  private def parseInt(s: String): Option[Int] = Try(s.toInt).toOption
}

object CompoundInterestCalculatorMain {
  def main(args: Array[String]): Unit = {
    EncompassingCalculatorUI.main(args)
  }
}