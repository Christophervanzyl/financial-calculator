import calculators.DrawdownCalculator
import models.CompoundingPeriod
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class DrawdownCalculatorTest extends AnyFunSuite with Matchers {

  test("calculateCompoundInterest should correctly calculate compound interest") {
    val principal = 1000.0
    val interestRate = 5.0
    val years = 5
    val months = 0
    val compoundingPeriod = CompoundingPeriod.Yearly
    val expectedInterest = 276.28

    val actualInterest = DrawdownCalculator.calculateCompoundInterest(principal, interestRate, years, months, compoundingPeriod)

    actualInterest should be (expectedInterest +- 0.1)
  }

  test("calculateCompoundInterestWithWithdrawal should correctly calculate compound interest with withdrawal") {
    val principal = 1000000
    val interestRate = 8.0
    val years = 30
    val withdrawalRate = 5
    val expectedInterest = 153846.13

    val actualInterest = DrawdownCalculator.calculateCompoundInterestWithWithdrawal(principal, interestRate,withdrawalRate, years)

    actualInterest should be (expectedInterest +- 0.1)
  }
  // TODO: Add more tests
}

