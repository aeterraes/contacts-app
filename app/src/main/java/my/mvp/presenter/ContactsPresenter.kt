package my.mvp.presenter

import my.mvp.model.Contact
import my.mvp.model.ContactRepository
import my.mvp.view.ContactsView

class ContactsPresenter(
    private val view: ContactsView,
    private val repository: ContactRepository
) {
    fun loadContacts() {
        val groupedContacts = try {
            repository.getContactsGroupedByLetter()
        } catch (e: Exception) {
            repository.getAllContacts()
                .groupBy { it.name.first().uppercaseChar() }
        }
        view.showContactsGrouped(groupedContacts)
    }
    fun onContactClicked(contact: Contact) = view.navigateToCallScreen(contact)
}