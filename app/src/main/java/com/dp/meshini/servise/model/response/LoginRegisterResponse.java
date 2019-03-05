package com.dp.meshini.servise.model.response;

import com.dp.meshini.servise.model.pojo.ClientData;
import com.google.gson.annotations.SerializedName;

public class LoginRegisterResponse {
    @SerializedName("data")
    private ClientData clientData;

    public ClientData getClientData() {
        return clientData;
    }

    public void setClientData(ClientData clientData) {
        this.clientData = clientData;
    }
}
