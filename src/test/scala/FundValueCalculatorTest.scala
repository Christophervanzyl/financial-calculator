import calculators.FundValueCalculator
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class FundValueCalculatorTest extends AnyFunSuite with Matchers {

  test("FundValueCalculator should correctly calculate accrued interest") {
    val principal = 1000
    val interestRate = 10
    val months = 1
    val expectedAccruedInterest = 8.3
    val actualAccruedInterest = FundValueCalculator.calculateAccruedInterest(principal, interestRate, months)

    actualAccruedInterest should be (expectedAccruedInterest +- 0.1)
  }

}
