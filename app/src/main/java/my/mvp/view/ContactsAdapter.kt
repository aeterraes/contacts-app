package my.mvp.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.mvp.R
import my.mvp.model.Contact

class ContactsAdapter(
    private var sections: Map<Char, List<Contact>> = emptyMap(),
    private val onItemClick: (Contact) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Any>()
    private val colors = mutableMapOf<String, Int>()

    init {
        prepareItems()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        SECTION -> SectionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_section_header, parent, false)
        )
        else -> ContactViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_contact, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SectionViewHolder -> holder.bind(items[position] as Char)
            is ContactViewHolder -> holder.bind(items[position] as Contact)
        }
    }

    override fun getItemCount() = items.size
    override fun getItemViewType(position: Int) = when (items[position]) {
        is Char -> SECTION
        else -> CONTACT
    }

    fun updateData(newSections: Map<Char, List<Contact>>) {
        sections = newSections
        prepareItems()
    }

    private fun prepareItems() {
        items.clear()
        sections.keys.sorted().forEach { letter ->
            items.add(letter)
            sections[letter]?.let { contacts ->
                items.addAll(contacts.sortedBy { it.name })
            }
        }
    }

    inner class SectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val sectionTitle: TextView = view.findViewById(R.id.sectionTitle)
        fun bind(letter: Char) { sectionTitle.text = letter.toString() }
    }

    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewName: TextView = view.findViewById(R.id.textViewName)
        private val textViewPhone: TextView = view.findViewById(R.id.textViewPhone)
        private val iconLetter: TextView = view.findViewById(R.id.iconLetter)
        private val iconContainer: View = view.findViewById(R.id.iconContainer)
        private val itemContainer: View = view.findViewById(R.id.itemContainer)

        fun bind(contact: Contact) {
            "${contact.name} ${contact.surname}".also { textViewName.text = it }
            textViewPhone.text = contact.phoneNumber
            iconLetter.text = contact.name.first().toString()
            iconContainer.background.setTint(getColorForContact(contact))
            itemContainer.setOnClickListener { onItemClick(contact) }
        }
    }

    private fun getColorForContact(contact: Contact): Int {
        return colors.getOrPut(contact.id) {
            val r = (130..160).random()
            val g = (180..220).random()
            val b = (230..255).random()
            Color.rgb(r, g, b)
        }
    }

    companion object {
        private const val SECTION = 0
        private const val CONTACT = 1
    }
}