package ru.netology

var thisDayTransferIn = 0
var thisDayTransferOut = 0
var thisMonthTransfersIn = 0
var thisMonthTransfersOut = 0
val maestroFreeTransferAmount = 75_000_00

fun main() {
    val cardType = "Maestro"
    val outgoingTransfer = true
    val transferAmount = 150_000_00

    if (isTransferAvailable(cardType, outgoingTransfer, transferAmount)) {
        addToTotal(transferAmount, outgoingTransfer)
        println("Комиссия составит: ${commissionAmount(cardType, transferAmount = transferAmount)} копеек")
    }
    else println("Превышен лимит на переводы")
}


fun isTransferAvailable(cardType: String, outgoingTransfer: Boolean, transferAmount: Int): Boolean {
    val dayLimit = 150_000_00
    val monthLimit = 600_000_00
    val vkOneTimeTransferLimit = 15_000_00
    val vkMonthTransferLimit = 40_000_00

    if (outgoingTransfer) {
        when (cardType) {
            "Mastercard", "Maestro", "Visa", "МИР" -> return (transferAmount <= (monthLimit - thisMonthTransfersOut) &&
                    transferAmount <= (dayLimit - thisDayTransferOut))
            "VK" -> return (transferAmount <= (vkMonthTransferLimit - thisMonthTransfersOut) &&
                    (transferAmount <= vkOneTimeTransferLimit))
        }
    } else
        when (cardType) {
            "Mastercard", "Maestro", "Visa", "МИР" -> return (transferAmount <= (monthLimit - thisMonthTransfersIn) &&
                    transferAmount <= (dayLimit - thisDayTransferIn))
            "VK" -> return (transferAmount <= (vkMonthTransferLimit - thisMonthTransfersIn) &&
                    (transferAmount <= vkOneTimeTransferLimit))
        }
    return false
}

fun commissionAmount(cardType: String = "VK", transferAmount: Int = 0): Int {
    val maestroCommission = 60
    val maestroFixCommission = 20_00
    val visaCommission = 75
    val visaMinCommission = 35_00
    val visaCommissionResult = (calcCommission(cardType, transferAmount, visaCommission))

    return when (cardType) {
        "Mastercard", "Maestro" -> {
            if (thisMonthTransfersOut > maestroFreeTransferAmount)
                (calcCommission(cardType, transferAmount, maestroCommission) + maestroFixCommission)
            else 0
        }
        "Visa", "МИР" -> {
            if (visaCommissionResult > visaMinCommission) visaCommissionResult
            else visaMinCommission
        }
        else -> 0
    }
}

fun calcCommission(cardType: String, transferAmount: Int, commissionPercentage: Int = 0): Int {
    val percentage = 100_00
    return when (cardType) {
        "Mastercard", "Maestro" -> (transferAmount - maestroFreeTransferAmount) * commissionPercentage / percentage
        "VK" -> 0
        else -> transferAmount * commissionPercentage / percentage
    }
}

fun addToTotal(transferAmount: Int, outgoingTransfer: Boolean = true) {
   if (outgoingTransfer) {
       thisMonthTransfersOut += transferAmount
       thisDayTransferOut += transferAmount
   }
    else {
       thisMonthTransfersIn += transferAmount
       thisDayTransferIn += transferAmount
   }
}

