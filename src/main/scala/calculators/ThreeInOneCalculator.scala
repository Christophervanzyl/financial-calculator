package calculators

import calculators.BasicIncomeTaxCalculator.calculateIncomeTax
import calculators.DrawdownCalculator.calculateCompoundInterestWithWithdrawal
import models.CompoundingPeriod.CompoundingPeriod
import models.{CalculationResult, FundValue}
import models.InvestmentStrategy.InvestmentStrategy

object ThreeInOneCalculator {
  def toCalculationResult(income: Double, ra: Double, interestRateDrawDown: Double, withDrawRate: Double, years: Int, months: Int, compoundingPeriod: CompoundingPeriod, investmentStrategy: InvestmentStrategy): CalculationResult = {
    val values = FundValueCalculator.calculateValues(25, 3000, 3.65, 2.5, 5, investmentStrategy)
    val tax = calculateIncomeTax(income, ra)
    val netIncome = (income*12) - tax
    val principal = values.last.totalFundValue
    val drawDown = calculateCompoundInterestWithWithdrawal(principal, interestRateDrawDown, withDrawRate, years)
    val yearlyIncome = income*12
    val benefit = income * 0.275
    val maxRetirementAnnuityBenefit = if (benefit < 350000) benefit else 0.0
    val monthlyIncome = income
    val monthlyTax = tax/12
    val netMonthlyIncome = netIncome/12
    val futureInvestmentValue = principal + drawDown
    val totalInterest = drawDown
    val initialInvestmentValue = principal
    val interestRate = interestRateDrawDown

    CalculationResult(yearlyIncome, maxRetirementAnnuityBenefit, monthlyIncome, monthlyTax, netMonthlyIncome, values, futureInvestmentValue, totalInterest, initialInvestmentValue, interestRate)
  }
}
