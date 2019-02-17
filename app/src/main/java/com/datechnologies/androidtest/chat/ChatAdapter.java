package com.datechnologies.androidtest.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.api.ChatLogMessageModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A recycler view adapter used to display chat log messages in {@link ChatActivity}.

 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    Context context;
    private List<ChatLogMessageModel> chatLogMessageModelList;

    public ChatAdapter(List<ChatLogMessageModel> chatLogMessageModelList, Context context) {
        this.chatLogMessageModelList = chatLogMessageModelList;
        this.context=context;
    }

    public void setChatLogMessageModelList(List<ChatLogMessageModel> chatLogMessageModelList) {
        this.chatLogMessageModelList = chatLogMessageModelList;
        notifyDataSetChanged();
    }
    // this gets layout by connecting to the item chat xml
    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_chat, parent, false);

        return new ChatViewHolder(itemView);
    }
    // this sets the text or image view to its corresponding value in chat class
    @Override
    public void onBindViewHolder(ChatViewHolder viewHolder, int position) {
        ChatLogMessageModel chatLogMessageModel = chatLogMessageModelList.get(position);
        viewHolder.messageTextView.setText(chatLogMessageModel.message);
        viewHolder.name.setText(chatLogMessageModel.username);
        Picasso.get().load(chatLogMessageModel.avatarUrl).into(viewHolder.avatarImageView);
    }
    //returns size
    @Override
    public int getItemCount() {
        return chatLogMessageModelList.size();
    }
    // this sets the variables of text or image view in this class to the xml's image or text view variables
    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;
        TextView messageTextView;
        TextView name;

        public ChatViewHolder(View view) {
            super(view);
            avatarImageView = (ImageView)view.findViewById(R.id.avatarImageView);
            messageTextView = (TextView)view.findViewById(R.id.messageTextView);
            name = view.findViewById(R.id.name);
        }
    }

}
