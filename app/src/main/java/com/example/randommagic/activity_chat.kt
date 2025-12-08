package com.example.randommagic

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randommagic.ChatAdapter
import com.example.randommagic.databinding.ActivityChatBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val chatCollection = db.collection("chat_messages")

    private val messageList = mutableListOf<ChatMessage>()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Chat Grupal"
            setDisplayHomeAsUpEnabled(true)
        }

        if (auth.currentUser == null) {
            Toast.makeText(this, "Debe iniciar sesión para usar el chat.", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        setupRecyclerView()
        binding.btnSend.setOnClickListener {
            sendMessage()
        }
        listenForMessages()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        binding.recyclerViewChat.layoutManager = layoutManager
        chatAdapter = ChatAdapter(messageList)
        binding.recyclerViewChat.adapter = chatAdapter
    }

    private fun sendMessage() {
        val messageText = binding.etMessage.text.toString().trim()
        val userEmail = auth.currentUser?.email
        if (messageText.isEmpty() || userEmail == null) {
            Toast.makeText(this, "No puedes enviar un mensaje vacío.", Toast.LENGTH_SHORT).show()
            return
        }
        val chatMessage = ChatMessage(
            text = messageText,
            userEmail = userEmail,
            timestamp = Timestamp.now()
        )
        chatCollection.add(chatMessage)
            .addOnSuccessListener {
                binding.etMessage.setText("")
                // Desplazarse al final
                binding.recyclerViewChat.scrollToPosition(messageList.size - 1)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al enviar: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun listenForMessages() {
        chatCollection.orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, e ->

                if (e != null) {
                    Toast.makeText(this, "Error al cargar mensajes: ${e.message}", Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                if (snapshots != null) {
                    messageList.clear()
                    for (doc in snapshots.documents) {
                        val message = doc.toObject(ChatMessage::class.java)
                        if (message != null) {
                            messageList.add(message)
                        }
                    }
                    chatAdapter.notifyDataSetChanged()
                    if (messageList.isNotEmpty()) {
                        binding.recyclerViewChat.scrollToPosition(messageList.size - 1)
                    }
                }
            }
    }
}