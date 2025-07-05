package com.example.demochat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cometchat.chat.constants.CometChatConstants;
import com.cometchat.chat.models.Conversation;
import com.cometchat.chat.models.Group;
import com.cometchat.chat.models.User;
import com.cometchat.chatuikit.conversations.CometChatConversations;

public class ChatsFragment extends Fragment {

    private CometChatConversations cometChatConversations;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        cometChatConversations = view.findViewById(R.id.cometChatConversations);

        cometChatConversations.setOnItemClick((clickedView, position, conversation) -> {
            if (conversation != null) {
                Intent intent = new Intent(getContext(), MessageActivity.class);

                if (CometChatConstants.CONVERSATION_TYPE_GROUP.equals(conversation.getConversationType())) {
                    Group group = (Group) conversation.getConversationWith();
                    intent.putExtra("guid", group.getGuid());
                } else {
                    User user = (User) conversation.getConversationWith();
                    intent.putExtra("uid", user.getUid());
                }

                startActivity(intent);
            }
        });

        return view;
    }
}

