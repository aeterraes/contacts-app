package my.mvp.view

import my.mvp.model.Contact

interface ContactsView {
    fun showContacts(contacts: List<Contact>)
    fun showContactsGrouped(sections: Map<Char, List<Contact>>)
    fun navigateToCallScreen(contact: Contact)
}