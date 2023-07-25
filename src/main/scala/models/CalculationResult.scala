package models

case class CalculationResult(yearlyIncome: Double, maxRetirementAnnuityBenefit: Double, monthlyIncome: Double, monthlyTax: Double,
                             netMonthlyIncome: Double, values: List[FundValue], futureInvestmentValue: Double, totalInterest: Double, initialInvestmentValue: Double, interestRate: Double)

