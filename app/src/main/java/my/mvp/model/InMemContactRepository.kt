package my.mvp.model

class InMemContactRepository : ContactRepository {
    private val contacts = listOf(
        Contact("1", "Test", "Testov", "+71111111111"),
        Contact("2", "Kapibara", "Kapibara", "+72222222222"),
        Contact("3", "Vasya", "Ivanov", "+71234567890"),
        Contact("4", "Vanya", "Vasin", "+70007654321"),
        Contact("5", "Aboba", "Bobov", "+75879846969"),
        Contact("6", "Boris", "Sidorov", "+71112223344"),
        Contact("7", "Name", "NotName", "+74445556677"),
        Contact("8", "Ivan", "Pavlov", "+76116277394"),
        Contact("9", "John", "Smith", "+70016244394"),
        Contact("10", "Mike", "Dow", "+700161234300"),
    )

    override fun getAllContacts() = contacts.sortedBy { it.name }

    override fun getContactsGroupedByLetter() = contacts
        .sortedBy { it.name }
        .groupBy { it.name.first().uppercaseChar() }

    override fun getContactById(id: String) = contacts.find { it.id == id }
}