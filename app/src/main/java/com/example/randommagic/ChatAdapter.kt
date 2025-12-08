package com.example.randommagic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.randommagic.databinding.ItemMessageMeBinding
import com.example.randommagic.databinding.ItemMessageOtherBinding
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val currentUserEmail = auth.currentUser?.email

    private companion object {
        const val VIEW_TYPE_SENT = 1
        const val VIEW_TYPE_RECEIVED = 2
    }

    inner class SentMessageViewHolder(val binding: ItemMessageMeBinding) : RecyclerView.ViewHolder(binding.root)
    inner class ReceivedMessageViewHolder(val binding: ItemMessageOtherBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.userEmail == currentUserEmail) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            val binding = ItemMessageMeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SentMessageViewHolder(binding)
        } else {
            val binding = ItemMessageOtherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ReceivedMessageViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        val formattedTime = formatTimestamp(message.timestamp)

        when (holder) {
            is SentMessageViewHolder -> {
                holder.binding.tvEmail.text = message.userEmail
                holder.binding.tvMessageSent.text = message.text
                holder.binding.tvTimestampSent.text = formattedTime
            }
            is ReceivedMessageViewHolder -> {
                holder.binding.tvMessageText.text = message.text
                holder.binding.tvSender.text = message.userEmail
                holder.binding.tvTimestamp.text = formattedTime
            }
        }
    }
    private fun formatTimestamp(timestamp: com.google.firebase.Timestamp): String {
        return try {
            val sdf = java.text.SimpleDateFormat("h:mm a", java.util.Locale.getDefault())
            sdf.format(timestamp.toDate())
        } catch (e: Exception) {
            ""
        }
    }
}
