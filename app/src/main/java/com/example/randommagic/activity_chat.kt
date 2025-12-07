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
            title = "Chat Grupal" // Opcional: Pone un título a la pantalla.
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
        // Simula la acción del botón "atrás" del sistema, lo que te
        // llevará a la actividad anterior (MenuActivity).
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)

        // Hace que la lista se desplace al final automáticamente (stackFromEnd = true)
        layoutManager.stackFromEnd = true

        binding.recyclerViewChat.layoutManager = layoutManager

        chatAdapter = ChatAdapter(messageList)
        binding.recyclerViewChat.adapter = chatAdapter
    }

    private fun sendMessage() {
        val messageText = binding.etMessage.text.toString().trim()
        val userEmail = auth.currentUser?.email // Email del remitente

        // Validaciones
        if (messageText.isEmpty() || userEmail == null) {
            Toast.makeText(this, "No puedes enviar un mensaje vacío.", Toast.LENGTH_SHORT).show()
            return
        }

        // 1. Crear el objeto ChatMessage
        val chatMessage = ChatMessage(
            text = messageText,
            userEmail = userEmail,
            timestamp = Timestamp.now() // Hora del servidor para la marca de tiempo
        )

        // 2. Subir el mensaje a la colección "chat_messages"
        chatCollection.add(chatMessage)
            .addOnSuccessListener {
                // Limpiar campo de texto
                binding.etMessage.setText("")
                // Desplazarse al final
                binding.recyclerViewChat.scrollToPosition(messageList.size - 1)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al enviar: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun listenForMessages() {
        // Ordena los mensajes por la marca de tiempo (timestamp) de forma ascendente
        chatCollection.orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, e ->

                if (e != null) {
                    Toast.makeText(this, "Error al cargar mensajes: ${e.message}", Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    // Limpia la lista actual para recargar los datos
                    messageList.clear()

                    // Llena la lista con los documentos recibidos
                    for (doc in snapshots.documents) {
                        // Convierte el documento a la clase ChatMessage
                        val message = doc.toObject(ChatMessage::class.java)
                        if (message != null) {
                            messageList.add(message)
                        }
                    }

                    // Notifica al adaptador que los datos han cambiado
                    chatAdapter.notifyDataSetChanged()

                    // Desplazarse al último mensaje
                    if (messageList.isNotEmpty()) {
                        binding.recyclerViewChat.scrollToPosition(messageList.size - 1)
                    }
                }
            }
    }
}