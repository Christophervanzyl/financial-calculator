package calculators

import models.CompoundingPeriod.CompoundingPeriod

import scala.annotation.tailrec

trait CompoundInterestCalculator {
  def calculateCompoundInterest(principal: Double, interestRate: Double, years: Int, months: Int, compoundingPeriod: CompoundingPeriod): Double
  def calculateCompoundInterestWithWithdrawal(principal: Double, annualInterestRate: Double, monthlyWithdrawalPercent: Double, years: Int): Double
}

// TODO: Add annual and monthly repayments for the drawdown
object DrawdownCalculator extends CompoundInterestCalculator {
  override def calculateCompoundInterest(principal: Double, interestRate: Double, years: Int, months: Int, compoundingPeriod: CompoundingPeriod): Double = {
    val totalPeriods = years + (months / 12.0)
    val n = compoundingPeriod.id
    val r = interestRate / (n * 100.0)
    val nt = n * totalPeriods
    val amount = principal * Math.pow(1 + r, nt)
    amount - principal
  }

  /***
   * Calculates the amount of compound interest given a monthly withdrawal of a certain percentage
   * @param principal The starting amount invested
   * @param annualInterestRate The annual interest rate
   * @param monthlyWithdrawalPercent The amount you wish to withdraw
   * @param years The period of which this will be invested
   * @return
   */
  override def calculateCompoundInterestWithWithdrawal(principal: Double, annualInterestRate: Double, monthlyWithdrawalPercent: Double, years: Int): Double = {
    // Convert interest rate to decimal and calculate monthly interest rate
    val monthlyInterestRate = (annualInterestRate / 100) / 12

    // Convert monthly withdrawal percent to decimal
    val monthlyWithdrawalPercentDecimal = monthlyWithdrawalPercent / 100

    // Calculate the number of months
    val months = years * 12

    @tailrec
    def compoundAmount(remainingMonths: Int, principal: Double, totalBalance: Double): Double = {
      if (remainingMonths <= 0) totalBalance
      else {
        val interest = principal * monthlyInterestRate
        val withdrawalAmount = principal * monthlyWithdrawalPercentDecimal
        val newPrincipal = principal - withdrawalAmount + interest
        val newTotalBalance = totalBalance + interest
        compoundAmount(remainingMonths - 1, newPrincipal, newTotalBalance)
      }
    }

    compoundAmount(months, principal, 0)
  }
}
