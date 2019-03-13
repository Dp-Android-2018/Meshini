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
        public static final String CHANGE_PASSWORD_URL ="change-password";
        public static final String COUNTRY_URL="utilities/world-countries";
        public static final String CITY_URL="utilities/world-cities";
        public static final String EDIT_PROFILE_URL="client/profile";
    }

    public static class Constants {
        public static final String API_KEY = "27180383-4918-4b94-9e24-27e37ec19c94";
        public static final String CONTENT_TYPE = "application/json";
        public static final String FORGET_PASSWORD_ACTIVITY="fpa";
        public static final String MAIL_ACTIVATION_ACTIVITY="maa";
        public static final int SUCCESS_CODE = 200;
        public static final int INVALID_DATA_CODE=422;
        public static final int UNAUTHENTICATED_CODE=401;
        public static final int TRY_LATER=429;
        public static final int UNAUTH0RIZED_CODE=403;

    }

    public static class SharedPref {
        public static final String SHARED_PREF_NAME="MESHINI_SHARED_PREF_NAME";
        public static final String APP_LANGUAGE = "APP_LANGUAGE";


    }

    public static class IntentConstants {
        public static final String CLIENT_PHONE="clientphone";
        public static final String SOURCE_ACTIVITY="source_activity";
        public static final String TOKEN="token";
    }

}
