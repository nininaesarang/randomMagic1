package com.example.randommagic
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.randommagic.ChatMessage
import com.example.randommagic.R
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Locale

class ChatAdapter(private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    private val currentEmail = FirebaseAuth.getInstance().currentUser?.email

    companion object {
        private const val VIEW_TYPE_ME = 1
        private const val VIEW_TYPE_OTHER = 2
    }

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessageText: TextView = view.findViewById(R.id.tvMessageText)
        val tvSender: TextView = view.findViewById(R.id.tvSender)
        val tvTimestamp: TextView = view.findViewById(R.id.tvTimestamp)

        private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        fun bind(message: ChatMessage) {
            tvMessageText.text = message.text
            val senderDisplay = message.userEmail.split("@").first()
            tvSender.text = senderDisplay
            tvTimestamp.text = timeFormat.format(message.timestamp.toDate())
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].userEmail == currentEmail) {
            VIEW_TYPE_ME
        } else {
            VIEW_TYPE_OTHER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutId = when (viewType) {
            VIEW_TYPE_ME -> R.layout.item_message_me
            VIEW_TYPE_OTHER -> R.layout.item_message_other
            else -> throw IllegalArgumentException("Tipo de vista desconocido")
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size
}