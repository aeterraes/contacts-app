package my.mvp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import my.mvp.model.Contact
import my.mvp.model.InMemContactRepository
import my.mvp.presenter.ContactsPresenter
import my.mvp.view.ContactsAdapter
import my.mvp.view.ContactsView

class MainActivity : AppCompatActivity(), ContactsView {
    private lateinit var presenter: ContactsPresenter
    private lateinit var adapter: ContactsAdapter
    private lateinit var recyclerViewContacts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewContacts = findViewById(R.id.recyclerViewContacts)

        val repository = InMemContactRepository()
        presenter = ContactsPresenter(this, repository)

        adapter = ContactsAdapter(emptyMap()) { contact ->
            presenter.onContactClicked(contact)
        }

        recyclerViewContacts.layoutManager = LinearLayoutManager(this)
        recyclerViewContacts.adapter = adapter

        presenter.loadContacts()
    }

    override fun showContacts(contacts: List<Contact>) {}

    override fun showContactsGrouped(sections: Map<Char, List<Contact>>) {
        adapter.updateData(sections)
    }

    override fun navigateToCallScreen(contact: Contact) {
        val intent = Intent(this, CallActivity::class.java).apply {
            putExtra("contact_id", contact.id)
        }
        startActivity(intent)
    }
}