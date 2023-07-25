package calculators

import models.FundValue
import models.InvestmentStrategy.InvestmentStrategy

import scala.annotation.tailrec

// TODO: Add recalculate value so you can add different values at different years
object FundValueCalculator {
  /***
   * Method to calculate the accrued interest
   * @param principal The starting amount
   * @param interestRate The interest rate
   * @param months The period for which to base the calculation on
   * @return
   */
  def calculateAccruedInterest(principal: Double, interestRate: Double, months: Int): Double = {
    val monthlyInterestRate = interestRate / 100.0

    @tailrec
    def loop(monthsRemaining: Int, currentPrincipal: Double, totalAccruedInterest: Double): Double = {
      if (monthsRemaining == 0) totalAccruedInterest
      else {
        val interest = currentPrincipal * monthlyInterestRate
        loop(monthsRemaining - 1, currentPrincipal + interest, totalAccruedInterest + interest)
      }
    }

    loop(months, principal, 0.0)
  }


  /***
   * Method to determine the funds value
   * @param term The term of the fund
   * @param initialMonthlyPremium Monthly premium
   * @param annualInterestRate Interest rate
   * @param boosterRate Product specific value that adds to the fund after 5 years of initial investment
   * @param contributionYears Year contributed
   * @param investmentStrategy Percent growth desired
   * @return
   */
  def calculateValues(term: Int, initialMonthlyPremium: Double, annualInterestRate: Double, boosterRate: Double, contributionYears: Int, investmentStrategy: InvestmentStrategy): List[FundValue] = {
    // Not tail recursive but wont cause stack overflow because plausible values would  not be that high
    def go(year: Int, monthlyPremium: Double, premiumPaid: Double): List[FundValue] = {
      if (year > term) Nil
      else {
        val yearlyPremium = monthlyPremium * 12
        val newPremiumPaid = premiumPaid + yearlyPremium
        val accruedInterest = calculateAccruedInterest(monthlyPremium, investmentStrategy.id.toDouble, 12)
        val newFundValueWithoutBooster = accruedInterest + newPremiumPaid
        val newBoosterValue = if (year < 5) 0 else newFundValueWithoutBooster * 0.025
        val totalFundValue = newFundValueWithoutBooster + newBoosterValue

        FundValue(year, newPremiumPaid, newFundValueWithoutBooster, newBoosterValue, totalFundValue) :: go(year + 1, monthlyPremium * 1.05, newPremiumPaid)
      }
    }

    go(1, initialMonthlyPremium, 0.0)
  }
}

