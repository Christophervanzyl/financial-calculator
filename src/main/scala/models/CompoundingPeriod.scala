package models

object CompoundingPeriod extends Enumeration {
  type CompoundingPeriod = Value

  val Daily = Value(365)
  val Weekly = Value(52)
  val Monthly = Value(12)
  val Quarterly = Value(4)
  val Yearly = Value(1)
}
