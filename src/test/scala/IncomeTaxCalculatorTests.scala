import calculators.BasicIncomeTaxCalculator
import org.scalatest.funsuite.AnyFunSuite

class IncomeTaxCalculatorTests extends AnyFunSuite {
  test("IncomeTaxCalculator calculates tax for income less than 237100") {
    val income = 15000
    val retirementAnnuity = 2000
    val expectedTax = (income * 12 * 0.18) - 177.12
    val actualTax = BasicIncomeTaxCalculator.calculateIncomeTax(income, retirementAnnuity)
    assert(math.abs(expectedTax - actualTax) < 0.01)
  }

  test("IncomeTaxCalculator calculates tax for income between 237100 and 370500") {
    val income = 25000
    val retirementAnnuity = 2000
    val annualIncome = income * 12
    val expectedTax = ((annualIncome - 237100) * 0.26) + 42678 - 177.12
    val actualTax = BasicIncomeTaxCalculator.calculateIncomeTax(income, retirementAnnuity)
    assert(math.abs(expectedTax - actualTax) < 0.01)
  }
  // TODO: Add more tests
}


