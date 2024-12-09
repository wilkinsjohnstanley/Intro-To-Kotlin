import kotlin.math.abs
import kotlin.random.Random

class DebtTracker {
    private val debts = mutableListOf<Debt>()

    data class Debt(
        var amount: Double,
        var description: String,
        var category: DebtCategory,
        val id: Int = nextId++,
        val timestamp: Long = System.currentTimeMillis()
    ) {
        companion object {
            private var nextId = 1
        }
    }

    enum class DebtCategory {
        PERSONAL, CREDIT_CARD, STUDENT_LOAN, MEDICAL, MISCELLANEOUS
    }

    // Extension function to format currency
    private fun Double.toCurrency(): String {
        return "$ %.2f".format(this)
    }

    // Create: Add a new debt
    fun addDebt(amount: Double, description: String, category: DebtCategory) {
        val debt = Debt(amount, description, category)
        debts.add(debt)
        println("Debt added successfully. Debt ID: ${debt.id}")
    }

    // Read: List all debts
    fun listDebts() {
        if (debts.isEmpty()) {
            println("No debts found.")
            return
        }

        println("=== Current Debts ===")
        debts.forEach { debt ->
            println("ID: ${debt.id} | Amount: ${debt.amount.toCurrency()} | " +
                    "Description: ${debt.description} | Category: ${debt.category}")
        }
    }

    // Read: Find debt by ID
    fun findDebtById(id: Int): Debt? {
        return debts.find { it.id == id }
    }

    // Update: Modify an existing debt
    fun updateDebt(id: Int, amount: Double? = null, description: String? = null, category: DebtCategory? = null) {
        val debtToUpdate = findDebtById(id)

        if (debtToUpdate == null) {
            println("Debt with ID $id not found.")
            return
        }

        amount?.let { debtToUpdate.amount = it }
        description?.let { debtToUpdate.description = it }
        category?.let { debtToUpdate.category = it }

        println("Debt updated successfully.")
    }

    // Delete: Remove a debt by ID
    fun deleteDebt(id: Int) {
        val removed = debts.removeIf { it.id == id }

        if (removed) {
            println("Debt with ID $id deleted successfully.")
        } else {
            println("Debt with ID $id not found.")
        }
    }

    // Calculate total debt
    fun getTotalDebt(): Double {
        return debts.sumOf { it.amount }
    }

    // Generate summary report
    fun generateReport() {
        println("\n=== Debt Tracker Report ===")
        println("Total Debts: ${debts.size}")
        println("Total Debt Amount: ${getTotalDebt().toCurrency()}")

        // Debt breakdown by category
        val categoryBreakdown = debts.groupBy { it.category }
            .mapValues { it.value.sumOf { debt -> debt.amount } }

        println("\nDebt Breakdown by Category:")
        categoryBreakdown.forEach { (category, total) ->
            println("$category: ${total.toCurrency()}")
        }
    }
}

// Console UI for interaction
class DebtTrackerConsole {
    private val tracker = DebtTracker()

    fun start() {
        while (true) {
            displayMenu()
            when (readln().trim()) {
                "1" -> addDebtPrompt()
                "2" -> tracker.listDebts()
                "3" -> updateDebtPrompt()
                "4" -> deleteDebtPrompt()
                "5" -> tracker.generateReport()
                "6" -> {
                    println("Exiting Debt Tracker. Goodbye!")
                    return
                }
                else -> println("Invalid option. Please try again.")
            }
        }
    }

    private fun displayMenu() {
        println("\n=== Debt Tracker Menu ===")
        println("1. Add New Debt")
        println("2. List All Debts")
        println("3. Update Debt")
        println("4. Delete Debt")
        println("5. Generate Report")
        println("6. Exit")
        print("Select an option (1-6): ")
    }

    private fun addDebtPrompt() {
        try {
            print("Enter debt amount (negative for debt): ")
            val amount = readln().toDouble()

            print("Enter debt description: ")
            val description = readln()

            println("Select debt category:")
            DebtTracker.DebtCategory.values().forEachIndexed { index, category ->
                println("${index + 1}. $category")
            }
            print("Enter category number: ")
            val categoryIndex = readln().toInt() - 1

            val category = DebtTracker.DebtCategory.values()[categoryIndex]

            tracker.addDebt(amount, description, category)
        } catch (e: Exception) {
            println("Error adding debt. Please check your input.")
        }
    }

    private fun updateDebtPrompt() {
        tracker.listDebts()

        print("Enter the ID of the debt to update: ")
        val id = readln().toInt()

        val existingDebt = tracker.findDebtById(id)
        if (existingDebt == null) {
            println("Debt not found.")
            return
        }

        println("Current Debt Details:")
        println("Amount: ${existingDebt.amount}")
        println("Description: ${existingDebt.description}")
        println("Category: ${existingDebt.category}")

        print("Enter new amount (press Enter to skip): ")
        val amountInput = readln()
        val amount = if (amountInput.isNotBlank()) amountInput.toDouble() else null

        print("Enter new description (press Enter to skip): ")
        val description = readln().takeIf { it.isNotBlank() }

        println("Select new category (press Enter to skip):")
        DebtTracker.DebtCategory.values().forEachIndexed { index, category ->
            println("${index + 1}. $category")
        }
        print("Enter new category number: ")
        val categoryInput = readln()
        val category = if (categoryInput.isNotBlank()) {
            DebtTracker.DebtCategory.values()[categoryInput.toInt() - 1]
        } else null

        tracker.updateDebt(id, amount, description, category)
    }

    private fun deleteDebtPrompt() {
        tracker.listDebts()

        print("Enter the ID of the debt to delete: ")
        val id = readln().toInt()

        tracker.deleteDebt(id)
    }
}

fun main() {
    val console = DebtTrackerConsole()
    console.start()
}