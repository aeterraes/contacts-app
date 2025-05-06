package my.mvp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import my.mvp.model.Contact
import my.mvp.model.ContactRepository
import my.mvp.model.InMemContactRepository

class CallActivity : AppCompatActivity() {
    private lateinit var repository: ContactRepository
    private lateinit var textViewContactName: TextView
    private lateinit var textViewPhoneNumber: TextView
    private lateinit var buttonEndCall: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        textViewContactName = findViewById(R.id.textViewContactName)
        textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber)
        buttonEndCall = findViewById(R.id.buttonEndCall)

        repository = InMemContactRepository()

        val contactId = intent.getStringExtra("contact_id") ?: run {
            finish()
            return
        }

        val contact = repository.getContactById(contactId) ?: run {
            finish()
            return
        }

        displayContactInfo(contact)
        setupCall(contact.phoneNumber)
    }

    private fun displayContactInfo(contact: Contact) {
        "${contact.name} ${contact.surname}".also { textViewContactName.text = it }
        textViewPhoneNumber.text = contact.phoneNumber
    }

    private fun setupCall(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CALL_PHONE), 1)
        } else {
            startActivity(callIntent)
        }

        buttonEndCall.setOnClickListener {
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val contactId = intent.getStringExtra("contact_id") ?: return
                val contact = repository.getContactById(contactId) ?: return
                val callIntent = Intent(Intent.ACTION_CALL).apply {
                    data = Uri.parse("tel:${contact.phoneNumber}")
                }
                startActivity(callIntent)
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}