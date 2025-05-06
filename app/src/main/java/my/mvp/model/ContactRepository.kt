package my.mvp.model

interface ContactRepository {
    fun getAllContacts(): List<Contact>
    fun getContactsGroupedByLetter(): Map<Char, List<Contact>>
    fun getContactById(id: String): Contact?
}