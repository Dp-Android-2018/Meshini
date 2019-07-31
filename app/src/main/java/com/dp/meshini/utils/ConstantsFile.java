package com.dp.meshini.utils;

public class ConstantsFile {

    public static class Urls {

        public static final String BASE_URL = "http://151.106.52.109:2018/api/";
        public static final String REGISTER_CLIENT_URL = "client/register";
        public static final String LOGIN_URL="client/login";
        public static final String SEND_MAIL_ACTIVATION_URL="client/activate/email/send";
        public static final String ACTIVE_PHONE_URL="client/activate/phone";
        public static final String SEND_PHONE_ACTIVATION_CODE_URL="client/phone/send";
        public static final String FORGET_PASSWORD_URL="client/forget";
        public static final String RESET_PASSWORD_URL="client/forget/reset/{token}";
        public static final String CHANGE_PASSWORD_URL ="client/change-password";
        public static final String COUNTRY_URL="utilities/world-countries";
        public static final String TCOUNTRY_URL="utilities/countries";
        public static final String LANGUAGES_URL="utilities/languages";
        public static final String CITY_URL="utilities/world-cities";
        public static final String EDIT_PROFILE_URL="client/profile";
        public static final String CREATE_TRIP_URL="client/request";
        public static final String PLACES_URL="utilities/places";
        public static final String OFFERS_URL="client/request/{id}/offers";
        public static final String PENDING_REQUESTS_URL="client/request/pending";
        public static final String ACCEPT_OFFER_URL="client/request/accept-offer";
        public static final String REQUEST_DETAIL_URL="client/request/{request_id}";
        public static final String DELETE_REQUEST_URL="client/request/{request_id}";
        public static final String PAST_REQUESTS_URL="client/request/past";
        public static final String UPCOMING_REQUESTS_URL="client/request/upcoming";
        public static final String GET_ACTIVE_TRIP_URL="client/request/active-request";
        public static final String ACTIVE_TRIP_DETAIL="client/request/active-request/details";
        public static final String CREATE_COMMENT_URL="client/request/{request}/review";
        public static final String CHANGE_LANGUAGE_URL="client/change-language";
        public static final String GET_ALL_PACKAGES_URL="client/packages";
        public static final String PACKAGE_DETAIL_URL="client/packages/{package}";
    }

    public static class Constants {
        public static final String API_KEY = "27180383-4918-4b94-9e24-27e37ec19c94";
        public static final String CONTENT_TYPE = "application/json";
        public static final String FORGET_PASSWORD_ACTIVITY="fpa";
        public static final String MAIL_ACTIVATION_ACTIVITY="maa";
        public static final String PAST_TRIP="past";
        public static final String UPCOMING_TRIP="upcoming";
        public static final String PENDING_TRIP="pending";
        public static final String OFFER="offer";
        public static final String CAR="car";
        public static final String MOTORCYCLE="motorcycle_white";
        public static final String ONFOOT="onfoot";
        public static final String CASH="COD";
        public static final String VISA="VISA";
        public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
        public static final int SUCCESS_CODE = 200;
        public static final int INVALID_DATA_CODE=422;
        public static final int UNAUTHENTICATED_CODE=401;
        public static final int TRY_LATER=429;
        public static final int UNAUTH0RIZED_CODE=403;
        public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002;
        public static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9003;
        public static final int ERROR_DIALOG_REQUEST = 9001;
        public static final int START_PLACE_PICKER=9004;
        public static final int VIEW_TYPE_MESSAGE_SENT=1;
        public static final int VIEW_TYPE_MESSAGE_RECEIVED=2;

        public static final String CAR_TYPE = "car";
        public static final String ON_FOOT_TYPE = "onfoot";
        public static final String MOTORBIKE_TYPE = "motorcycle";
        public static final String VAN_TYPE = "van";
        public static final String TUKTUK_TYPE= "tuk_tuk";
        public static final String STEAGECOACH_TYPE = "stage_coach";
        public static final String YACHAT_TYPE = "yacht";
        public static final String GOLFCAR_TYPE = "golf_car";
        public static final String JETSKI_TYPE = "jet_ski";

        public static final int CAR_ID = 1111;
        public static final int BIKE_ID = 2222;
        public static final int VAN_ID = 3333;
        public static final int TUKTUK_ID = 4444;
        public static final int STEAGECOACH_ID = 5555;
        public static final int ONFOOT_ID = 6666;
        public static final int YACHAT_ID = 7777;
        public static final int JETSKI_ID = 8888;
        public static final int GOLFCAR_ID = 9999;



    }

    public static class SharedPref {
        public static final String SHARED_PREF_NAME="MESHINI_SHARED_PREF_NAME";
        public static final String APP_LANGUAGE = "APP_LANGUAGE";
        public static final String REQUEST_ID="REQUEST_ID";
        public static final String GUIDE_IMAGE_URL="GUIDE_IMAGE";


    }

    public static class IntentConstants {
        public static final String CLIENT_PHONE="clientphone";
        public static final String SOURCE_ACTIVITY="source_activity";
        public static final String CREATE_TRIP_REQUEST="trip_request";
        public static final String SHARED_TRIP="shared_trip";
        public static final String SHARED_TRIP_ID="shared_trip_id";
        public static final String TOKEN="token";
        public static final int AUTO_COMP_REQ_CODE=111;
        public static final String OPEN_ACTIVE_TRIP="open_active_trip";
        public static final String TRIP_ID="request_id";
        public static final String OFFER_ID="offerid";
        public static final String TRIP_TYPE="triptype";
        public static final String SELECTED_ADDRESS="address";
        public static final String SELECTED_LAT="lat";
        public static final String SELECTED_LANG="lang";
        public static final String TRIP_DETAIL="trip_detail";
        public static final String ACTIVE_TRIP_FIREBASE="active_trip_firebase";


    }

}
