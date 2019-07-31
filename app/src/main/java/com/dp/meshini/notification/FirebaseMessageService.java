package com.dp.meshini.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.dp.meshini.R;
import com.dp.meshini.servise.model.pojo.ServiceProvider;
import com.dp.meshini.servise.model.pojo.TripDetail;
import com.dp.meshini.view.activity.ChatActivity;
import com.dp.meshini.view.activity.ContainerActivity;
import com.dp.meshini.view.activity.OffersActivity;
import com.dp.meshini.view.activity.SplashActivity;
import com.dp.meshini.view.fragment.MapsFragment;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static com.dp.meshini.utils.ConstantsFile.IntentConstants.OPEN_ACTIVE_TRIP;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.TRIP_DETAIL;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.TRIP_ID;


public class FirebaseMessageService extends FirebaseMessagingService {
    public static String TOKEN = null;
    Intent i;

    @Override
    public void onNewToken(String s) {
        System.out.println("device Token 2: " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        System.out.println("Notification Log Title:" + remoteMessage.getNotification().getTitle());
        System.out.println("Notification Log Body:" + remoteMessage.getNotification().getBody());
        System.out.println("Notification Log Data :" + remoteMessage.getData().get("title"));
        if (remoteMessage.getData() != null) {
            if (remoteMessage.getData().get("title") != null) {
                System.out.println("title is : " + remoteMessage.getData().get("title"));
                i = new Intent();
                if (remoteMessage.getData().get("title").equals("offer-received")) {
                    int requestId = Integer.parseInt(remoteMessage.getData().get("request_id"));
//                    ClientData response = sharedPreferenceHelpersLazy.getValue().getSaveUserObject();
//                    response.setActivated(true);
                    //sharedPreferenceHelpersLazy.getValue().saveDataToPrefs(response);
                    i = new Intent(getApplicationContext(), OffersActivity.class);
                    i.putExtra(TRIP_ID, requestId);
                    Notify(i, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                    // Notify(i, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                } else if (remoteMessage.getData().get("title").equals("trip_finished")) {
                    i = new Intent(getApplicationContext(), SplashActivity.class);
                    Notify(i, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                    //Notify(i, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                } else if (remoteMessage.getData().get("title").equals("trip-started") ||
                        remoteMessage.getData().get("title").equals("destination-finished") ||
                        remoteMessage.getData().get("title").equals("destination-started")) {
                    if (!MapsFragment.active) {
                        i = new Intent(getApplicationContext(), ContainerActivity.class);
                        i.putExtra(OPEN_ACTIVE_TRIP, true);
                        Notify(i, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                    }
                } else if (remoteMessage.getData().get("title").equals("new-message")) {
                    if (!ChatActivity.active) {
                        makeChatIntent(remoteMessage.getData());
                        Notify(i, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                    }
                }
            }
        }
    }

    public void Notify(Intent intent, String messageTitle, String nb) {
        System.out.println("onNotify method : " + messageTitle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        long[] pattern = {500, 500, 500, 500, 500};

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "androidChannel")
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle(messageTitle)
                .setContentText(nb)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLights(Color.BLUE, 1, 1)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void makeChatIntent(Map<String, String> data) {
        TripDetail tripDetail = new TripDetail();
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName(data.get("name"));
        serviceProvider.setProfileImageUrl(data.get("profile_picture"));
        serviceProvider.setId(Integer.parseInt(data.get("id")));
        tripDetail.setServiceProvider(serviceProvider);
        tripDetail.setId(Integer.parseInt(data.get("request_id")));
        i = new Intent(getApplicationContext(), ChatActivity.class);
        i.putExtra(TRIP_DETAIL, tripDetail);
    }

}


