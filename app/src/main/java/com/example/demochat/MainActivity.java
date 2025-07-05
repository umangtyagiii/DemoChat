package com.example.demochat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.ComponentActivity;
import com.cometchat.chat.core.CometChat;
import com.cometchat.chat.exceptions.CometChatException;
import com.cometchat.chat.models.User;
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit;
import com.cometchat.chatuikit.shared.cometchatuikit.UIKitSettings;

public class MainActivity extends ComponentActivity {

    private static final String TAG = "MainActivity";

    private final String appID = "2783770efde0f9d7"; // Replace with your App ID
    private final String region = "IN"; // Replace with your App Region
    private final String authKey = "7f817829782ba940823a1b2e9f4f4f1f65851ce4"; // Replace with your Auth Key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setDecorFitsSystemWindows(false); // Equivalent to enableEdgeToEdge()

        UIKitSettings uiKitSettings = new UIKitSettings.UIKitSettingsBuilder()
                .setRegion(region)
                .setAppId(appID)
                .setAuthKey(authKey)
                .subscribePresenceForAllUsers()
                .build();

        CometChatUIKit.init(this, uiKitSettings, new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String success) {
                Log.d(TAG, "Initialization completed successfully");
                loginUser();
            }

            @Override
            public void onError(CometChatException e) {
                Log.e(TAG, "Initialization failed: " + (e != null ? e.getMessage() : "Unknown error"));
            }
        });
    }

    private void loginUser() {
        CometChatUIKit.login("cometchat-uid-1", new CometChat.CallbackListener<User>() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "Login successful for user: " + user.getUid());

                // Launch Tab-Based Chat Experience (Chats, Calls, Users, Groups)
                startActivity(new Intent(MainActivity.this, TabbedActivity.class));
            }

            @Override
            public void onError(CometChatException e) {
                Log.e("Login", "Login failed: " + (e != null ? e.getMessage() : "Unknown error"));
            }
        });
    }
}