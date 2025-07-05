package com.example.demochat;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cometchat.chat.core.CometChat;
import com.cometchat.chat.exceptions.CometChatException;
import com.cometchat.chat.models.Group;
import com.cometchat.chat.models.User;
import com.cometchat.chatuikit.messagecomposer.CometChatMessageComposer;
import com.cometchat.chatuikit.messageheader.CometChatMessageHeader;
import com.cometchat.chatuikit.messagelist.CometChatMessageList;

public class MessageActivity extends AppCompatActivity {

    private static final String TAG = "MessageActivity";

    private CometChatMessageHeader messageHeader;
    private CometChatMessageList messageList;
    private CometChatMessageComposer messageComposer;

    private String uid;
    private String guid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setDecorFitsSystemWindows(false); // enableEdgeToEdge()
        setContentView(R.layout.activity_message);

        initializeViews();
        setupChat();
        setupHeaderBackButton();
    }

    private void initializeViews() {
        messageHeader = findViewById(R.id.message_header);
        messageList = findViewById(R.id.message_list);
        messageComposer = findViewById(R.id.message_composer);
    }

    private void setupChat() {
        uid = getIntent().getStringExtra("uid");
        guid = getIntent().getStringExtra("guid");

        if (uid != null) {
            setupUserChat(uid);
        } else if (guid != null) {
            setupGroupChat(guid);
        } else {
            Log.e(TAG, "No user ID or group ID provided");
            showError("Missing user ID or group ID");
            finish();
        }
    }

    private void setupUserChat(String userId) {
        CometChat.getUser(userId, new CometChat.CallbackListener<User>() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "Successfully loaded user: " + user.getUid());
                messageHeader.setUser(user);
                messageList.setUser(user);
                messageComposer.setUser(user);
            }

            @Override
            public void onError(CometChatException e) {
                Log.e(TAG, "Error loading user: " + (e != null ? e.getMessage() : "Unknown error"));
                showError("Could not find user: " + (e != null ? e.getMessage() : ""));
                finish();
            }
        });
    }

    private void setupGroupChat(String groupId) {
        CometChat.getGroup(groupId, new CometChat.CallbackListener<Group>() {
            @Override
            public void onSuccess(Group group) {
                Log.d(TAG, "Successfully loaded group: " + group.getGuid());
                messageHeader.setGroup(group);
                messageList.setGroup(group);
                messageComposer.setGroup(group);
            }

            @Override
            public void onError(CometChatException e) {
                Log.e(TAG, "Error loading group: " + (e != null ? e.getMessage() : "Unknown error"));
                showError("Could not find group: " + (e != null ? e.getMessage() : ""));
                finish();
            }
        });
    }

    private void setupHeaderBackButton() {
        messageHeader.setOnBackButtonPressed(() -> finish());
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
