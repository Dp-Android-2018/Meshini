package com.dp.meshini.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityChatBinding;
import com.dp.meshini.servise.model.pojo.Message;
import com.dp.meshini.servise.model.pojo.TripDetail;
import com.dp.meshini.servise.model.request.NotificationRequest;
import com.dp.meshini.servise.model.response.ErrorResponse;
import com.dp.meshini.utils.SharedPreferenceHelpers;
import com.dp.meshini.view.adapter.ChatRecyclerViewAdapter;
import com.dp.meshini.viewmodel.ChatViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import kotlin.Lazy;
import okhttp3.ResponseBody;

import static com.dp.meshini.utils.ConstantsFile.IntentConstants.TRIP_DETAIL;
import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ChatActivity extends BaseActivity {

    ActivityChatBinding binding;
    private String deviceToken;
    public static boolean active = false;
    private DatabaseReference reference;
    public ObservableList<Message> messages;
    private List<Message> allMessages;
    public ObservableField<String> message;
    private int userId;
    private int tripId;
    private int serviceProviderId;
    TripDetail tripDetail;
    private ChatRecyclerViewAdapter chatRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    Lazy<SharedPreferenceHelpers> sharedPreferenceHelpersLazy = inject(SharedPreferenceHelpers.class);
    Lazy<ChatViewModel> chatViewModelLazy = inject(ChatViewModel.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        tripDetail = (TripDetail) getIntent().getSerializableExtra(TRIP_DETAIL);
        userId = sharedPreferenceHelpersLazy.getValue().getSaveUserObject().getUserId();
        tripId = tripDetail.getId();
        serviceProviderId = tripDetail.getServiceProvider().getId();
        messages = new ObservableArrayList<>();
        allMessages = new ArrayList<>();
        message = new ObservableField<String>("");
        initializeUiData();
        getUserConversation();
        initializeChatRecyclerView();
        getLastMessage();
        // FirebaseToken.getInstance().getFirebaseToken().observe(this, s -> deviceToken = s);
        setupToolbar();
        makeActionOnClickOnBtnSend();
    }

    private void initializeUiData() {
        binding.tvUserName.setText(tripDetail.getServiceProvider().getName());
        ImageView userImageView = binding.ivUser;
        Picasso.get().load(tripDetail.getServiceProvider().getProfileImageUrl()).into(userImageView);
    }

    private void makeActionOnClickOnBtnSend() {
        binding.ivSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.etMessage.getText().toString().equals("")) {
                    addMessage(binding.etMessage.getText().toString(), "text");
                    binding.etMessage.setText("");
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    private void setupToolbar() {
        binding.chatToolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        binding.chatToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void addMessage(String content, String type) {
        reference = FirebaseDatabase.getInstance().getReference("conversations/Meshini"
                + serviceProviderId + "-" + userId + "-" + tripId);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final Message newMessage = new Message(content, "user-" + userId, "serviceProvider-" + serviceProviderId, false, type, (timestamp.getTime()) / 1000);
        reference.push().setValue(newMessage).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Snackbar.make(binding.getRoot(), "Error Sending message", Snackbar.LENGTH_SHORT).show();
            } else {
                sendNotification(content);
            }
        });

        reference.child("last_message").setValue(newMessage);
    }

    private void sendNotification(String content) {
        chatViewModelLazy.getValue().sendNotification(getNotificationRequest(content)).observe(this, voidResponse -> {
            if (voidResponse.isSuccessful()) {

                //TODO if sending notification is success

            } else {
                if (voidResponse.errorBody() != null) {
                    showMainErrorMessage(voidResponse.errorBody());
                }
            }
        });
    }

    private NotificationRequest getNotificationRequest(String content) {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setRequestId(tripId);
        notificationRequest.setMessage(content);
        return notificationRequest;
    }

    private void showMainErrorMessage(ResponseBody errorResponseBody) {
        Gson gson = new GsonBuilder().create();
        ErrorResponse errorResponse = new ErrorResponse();

        try {
            errorResponse = gson.fromJson(errorResponseBody.string(), ErrorResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String error = "";
        for (String string : errorResponse.getErrors()) {
            error += string;
            error += "\n";
        }
        showSnackbarHere(error);
    }

    private void showSnackbarHere(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();

    }

    public void getUserConversation() {
        reference = FirebaseDatabase.getInstance().getReference("conversations");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getRef().getKey();
//                hasData.set(View.VISIBLE);
//                noData.set(View.GONE);
                if (key.equals("Meshini"
                        + serviceProviderId + "-" + userId + "-" + tripId)) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        Message message = (Message) dataSnapshot1.getValue(Message.class);
                        allMessages.add(message);
                    }
                    setAdapterMessages(allMessages);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        reference.addChildEventListener(childEventListener);
    }

    private void initializeChatRecyclerView() {
        chatRecyclerViewAdapter = new ChatRecyclerViewAdapter(serviceProviderId, userId);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rvMessages.setLayoutManager(linearLayoutManager);
        binding.rvMessages.setAdapter(chatRecyclerViewAdapter);
        linearLayoutManager.scrollToPositionWithOffset(chatRecyclerViewAdapter.getLastPosition(), 20);
        binding.rvMessages.setItemAnimator(new DefaultItemAnimator());
    }

    private void setAdapterMessages(List<Message> allMessages) {
        if (allMessages.size() != 1) {
            allMessages.remove(allMessages.get(allMessages.size() - 1));
            chatRecyclerViewAdapter.setData(allMessages);
        }
    }

    private void getLastMessage() {
        reference = FirebaseDatabase.getInstance().getReference("conversations/Meshini"
                + serviceProviderId + "-" + userId + "-" + tripId);
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getRef().getKey();
                System.out.println("Conversation kay :" + key);
//                hasData.set(View.VISIBLE);
//                noData.set(View.GONE);
                if (key.equals("last_message")) {

                    Message message1 = (Message) dataSnapshot.getValue(Message.class);
                    if (message1 != null) {
                        System.out.println("Conversation new message :" + message1.getContent());
                        chatRecyclerViewAdapter.addItem(message1);
                        linearLayoutManager.scrollToPositionWithOffset(chatRecyclerViewAdapter.getLastPosition(), 20);
                    }
//                    System.out.println("Conversation List :" + allMessages.get(allMessages.size() - 1).getContent());
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        reference.addChildEventListener(childEventListener);

    }
}
