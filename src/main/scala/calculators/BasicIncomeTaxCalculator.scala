package calculators

trait IncomeTaxCalculator {
  /***
   * Calculates the income tax depending on which tax bracket you fall in
   * @param income The monthly income
   * @param retirementAnnuity The amount you put into a Retirement annuity - for rebate purposes
   * @return The amount of tax to be paid
   */
  def calculateIncomeTax(income: Double, retirementAnnuity: Double): Double
}

object BasicIncomeTaxCalculator extends IncomeTaxCalculator {
  // TODO: Use config values for set amounts
  override def calculateIncomeTax(income: Double, retirementAnnuity: Double): Double = {
    val annualIncome = income * 12
    // (UIF / Unemployment Insurance Fund is levied at 1% of your gross income, at most R177.12)
    val uif = if (annualIncome * 0.01 > 177.12) 177.12 else annualIncome * 0.01

    // TODO: Add Retirement annuity
    // limited to 27.5% of salary, limited to R350k

    /***
     * Rates from https://www.oldmutual.co.za/personal/tools-and-calculators/income-tax-calculator/
     */
    // TODO: Use TaxBracket case class with configurable values instead
    val tax = annualIncome match {
      case amount if amount < 237100 => amount * 0.18
      case amount if amount < 370500 => ((amount - 237100) * 0.26) + 42678
      case amount if amount < 512800 => ((amount - 370500) * 0.31) + 77362
      case amount if amount < 673000 => ((amount - 512800) * 0.36) + 121475
      case amount if amount < 857900 => ((amount - 673000) * 0.39) + 179147
      case amount if amount < 1817000 => ((amount - 857900) * 0.41) + 251258
      case amount => ((amount - 1817000) * 0.45) + 644489
      case _ => 0.0 // Default case to cover any remaining possibilities
    }

    val totalTax = tax - uif
    totalTax
  }
}
