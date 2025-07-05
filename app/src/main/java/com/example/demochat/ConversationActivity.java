package com.example.demochat;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.cometchat.chat.constants.CometChatConstants;
import com.cometchat.chat.models.Conversation;
import com.cometchat.chat.models.Group;
import com.cometchat.chat.models.User;
import com.cometchat.chatuikit.conversations.CometChatConversations;

public class ConversationActivity extends AppCompatActivity {

    private CometChatConversations conversationsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conversation);

        initView();
        setListeners();
    }

    private void initView() {
        conversationsView = findViewById(R.id.conversation_view);
    }

    private void setListeners() {
        conversationsView.setOnItemClick((view, position, conversation) -> {
            startMessageActivity(conversation);
        });
    }

    private void startMessageActivity(Conversation conversation) {
        Intent intent = new Intent(this, MessageActivity.class);

        if (CometChatConstants.CONVERSATION_TYPE_GROUP.equals(conversation.getConversationType())) {
            Group group = (Group) conversation.getConversationWith();
            intent.putExtra("guid", group.getGuid());
        } else {
            User user = (User) conversation.getConversationWith();
            intent.putExtra("uid", user.getUid());
        }

        startActivity(intent);
    }
}
