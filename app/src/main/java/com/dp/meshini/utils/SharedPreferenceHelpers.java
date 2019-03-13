package com.dp.meshini.utils;

import com.dp.meshini.servise.model.pojo.ClientData;

import kotlin.Lazy;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class SharedPreferenceHelpers {

    Lazy<SharedPrefrence>sharedPrefrenceLazy=inject(SharedPrefrence.class);


    public void saveAppLanguage(String lang){
        sharedPrefrenceLazy.getValue().addStringToSharedPrederances(ConstantsFile.SharedPref.APP_LANGUAGE,lang);
    }

    public String getAppLanguage(){
        String lang=sharedPrefrenceLazy.getValue().getStringFromSharedPrederances(ConstantsFile.SharedPref.APP_LANGUAGE);
        if (lang == null)
            return "en";
        return lang;
    }

    public void clearSharedPref() {
        String appLang=getAppLanguage();
        sharedPrefrenceLazy.getValue().clearToken();
        saveAppLanguage(appLang);
    }

    public ClientData getSaveUserObject() {
        ClientData userData = (ClientData) sharedPrefrenceLazy.getValue().getSavedObject(ConstantsFile.SharedPref.SHARED_PREF_NAME, ClientData.class);
        return userData;
    }

    public void saveDataToPrefs(ClientData data) {
        sharedPrefrenceLazy.getValue().saveObjectToSharedPreferences(ConstantsFile.SharedPref.SHARED_PREF_NAME, data);
    }





}
