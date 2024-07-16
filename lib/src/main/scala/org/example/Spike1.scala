package org.example

import ExampleUtils.itemsAsDataframe
import com.amazon.deequ.VerificationSuite
import com.amazon.deequ.analyzers.KLLParameters
import com.amazon.deequ.checks.{Check, CheckLevel, CheckStatus}
import com.amazon.deequ.constraints.ConstraintStatus
import org.apache.spark.sql.types.DoubleType

class Spike1 {

  // function to print hello
  def go(): Unit = {

    // taken from https://github.com/awslabs/deequ/blob/master/src/main/scala/com/amazon/deequ/examples/KLLCheckExample.scala
    ExampleUtils.withSpark { session =>
      println(s"Spark version: ${session.version}")

      val data = itemsAsDataframe(session,
        Item(1, "Thingy A", "awesome thing.", "high", 0),
        Item(2, "Thingy B", "available at http://thingb.com", null, 0),
        Item(3, null, null, "low", 5),
        Item(4, "Thingy D", "checkout https://thingd.ca", "low", 10),
        Item(5, "Thingy E", null, "high", 10))

      data.show()

      val newData = data.select(data("numViews").cast(DoubleType).as("numViews"))

      val verificationResult = VerificationSuite()
        .onData(newData)
        .addCheck(
          Check(CheckLevel.Error, "integrity checks")
            // we expect 5 records
            .hasSize(_ == 5)
            // we expect the maximum of tips to be not more than 10
            .hasMax("numViews", _ <= 10)
        ).run()

//      The data passed the test, everything is fine!

      if (verificationResult.status == CheckStatus.Success) {
        println("The data passed the test, everything is fine!")
      } else {
        println("We found errors in the data, the following constraints were not satisfied:\n")

        val resultsForAllConstraints = verificationResult.checkResults
          .flatMap { case (_, checkResult) => checkResult.constraintResults }

        resultsForAllConstraints
          .filter {
            _.status != ConstraintStatus.Success
          }
          .foreach { result =>
            println(s"${result.constraint} failed: ${result.message.get}")
          }
      }

    }
  }
}
