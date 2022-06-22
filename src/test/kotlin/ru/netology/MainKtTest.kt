package ru.netology

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class MainKtTest {

    @Before
    fun setUp(){
        thisMonthTransfersIn = 0
        thisMonthTransfersOut = 0
        thisDayTransferIn = 0
        thisDayTransferOut = 0
    }

    @Test
    fun isTransferAvailable_shouldReturnTrueTransferOutVK() {
        val type = "VK"
        val outgoingTransfer = true
        val amount = 15_000_00
        val expectedResult = true

        val result = ru.netology.isTransferAvailable(cardType = type,
            outgoingTransfer = outgoingTransfer,
            transferAmount = amount)

        assertEquals(expectedResult, result)
    }

    @Test
    fun isTransferAvailable_shouldReturnFalseTransferOutVK() {
        val type = "VK"
        val outgoingTransfer = true
        val amount = 16_000_00
        val expectedResult = false

        val result = ru.netology.isTransferAvailable(cardType = type,
            outgoingTransfer = outgoingTransfer,
            transferAmount = amount)

        assertEquals(expectedResult, result)
    }

    @Test
    fun isTransferAvailable_shouldReturnTrueTransferInVK() {
        val type = "VK"
        val outgoingTransfer = false
        val amount = 15_001_00
        val expectedResult = true

        val result = ru.netology.isTransferAvailable(cardType = type,
            outgoingTransfer = outgoingTransfer,
            transferAmount = amount)

        assertEquals(expectedResult, result)
    }

    @Test
    fun isTransferAvailable_shouldReturnFalseTransferInVK() {
        val type = "VK"
        val outgoingTransfer = false
        val amount = 16_000_00
        val expectedResult = false

        val result = ru.netology.isTransferAvailable(cardType = type,
            outgoingTransfer = outgoingTransfer,
            transferAmount = amount)

        assertEquals(expectedResult, result)
    }

    @Test
    fun isTransferAvailable_shouldReturnTrueTransferOutAnotherPayments() {
        val type = "Maestro"
        val outgoingTransfer = true
        val amount = 150_000_00
        val expectedResult = true

        val result = ru.netology.isTransferAvailable(cardType = type,
            outgoingTransfer = outgoingTransfer,
            transferAmount = amount)

        assertEquals(expectedResult, result)
    }

    @Test
    fun isTransferAvailable_shouldReturnFalseTransferOutAnotherPayments() {
        val type = "Maestro"
        val outgoingTransfer = true
        val amount = 151_000_00
        val expectedResult = false

        val result = ru.netology.isTransferAvailable(cardType = type,
            outgoingTransfer = outgoingTransfer,
            transferAmount = amount)

        assertEquals(expectedResult, result)
    }

    @Test
    fun isTransferAvailable_shouldReturnTrueTransferInAnotherPayments() {
        val type = "Maestro"
        val outgoingTransfer = false
        val amount = 150_000_00
        val expectedResult = true

        val result = ru.netology.isTransferAvailable(cardType = type,
            outgoingTransfer = outgoingTransfer,
            transferAmount = amount)

        assertEquals(expectedResult, result)
    }

    @Test
    fun isTransferAvailable_shouldReturnFalseTransferInAnotherPayments() {
        val type = "Maestro"
        val outgoingTransfer = false
        val amount = 151_000_00
        val expectedResult = false

        val result = ru.netology.isTransferAvailable(cardType = type,
            outgoingTransfer = outgoingTransfer,
            transferAmount = amount)

        assertEquals(expectedResult, result)
    }

    @Test
    fun isTransferAvailable_shouldReturnFalseIncorrectCard() {
        val type = "Tinkoff"
        val outgoingTransfer = false
        val amount = 151_000_00
        val expectedResult = false

        val result = ru.netology.isTransferAvailable(cardType = type,
            outgoingTransfer = outgoingTransfer,
            transferAmount = amount)

        assertEquals(expectedResult, result)
    }

    @Test
    fun commissionAmount_shouldCorrectMaestro() {
        val card = "Maestro"
        val amount = 100_000_00
        val commission = 60
        val fixCommission = 20_00

        addToTotal(transferAmount = amount)

        val expectedResult = calcCommission(cardType = card, transferAmount = amount, commissionPercentage = commission) +
                fixCommission
        val result = commissionAmount(cardType = card, transferAmount = amount)

        assertEquals(expectedResult, result)
    }

    @Test
    fun commissionAmount_shouldCorrectMaestroWithoutCommission() {
        val card = "Maestro"
        val amount = 50_000_00
        val withoutCommission = 0

        addToTotal(transferAmount = amount)

        val expectedResult = withoutCommission
        val result = commissionAmount(cardType = card, transferAmount = amount)

        assertEquals(expectedResult, result)
    }

    @Test
    fun commissionAmount_shouldCorrectVisa() {
        val card = "Visa"
        val outgoingPay = false
        val amount = 100_000_00
        val commission = 75

        addToTotal(transferAmount = amount, outgoingTransfer = outgoingPay)

        val expectedResult = calcCommission(cardType = card, transferAmount = amount, commissionPercentage = commission)
        val result = commissionAmount(cardType = card, transferAmount = amount)

        assertEquals(expectedResult, result)
    }

    @Test
    fun commissionAmount_shouldCorrectVisaMinCommission() {
        val card = "Visa"
        val amount = 1_000_00
        val minCommission = 35_00

        addToTotal(transferAmount = amount)

        val expectedResult = minCommission
        val result = commissionAmount(cardType = card, transferAmount = amount)

        assertEquals(expectedResult, result)
    }

    @Test
    fun commissionAmount_shouldCorrectVK() {
        val card = "VK"
        val amount = 100_000_00

        addToTotal(transferAmount = amount)

        val expectedResult = calcCommission(cardType = card, transferAmount = amount)
        val result = commissionAmount(cardType = card, transferAmount = amount)

        assertEquals(expectedResult, result)
    }

    @Test
    fun calcCommission_shouldMaestroCorrect() {
        val card = "Maestro"
        val amount = 100_000_00
        val commission = 60
        val expectedResult = 150_00

        val result = ru.netology.calcCommission(cardType = card, transferAmount = amount,
            commissionPercentage = commission)

        assertEquals(expectedResult, result)
    }

    @Test
    fun calcCommission_shouldVkCorrect() {
        val card = "VK"
        val amount = 100_000_00
        val commission = 0
        val expectedResult = 0

        val result = ru.netology.calcCommission(cardType = card, transferAmount = amount,
            commissionPercentage = commission)

        assertEquals(expectedResult, result)
    }


}