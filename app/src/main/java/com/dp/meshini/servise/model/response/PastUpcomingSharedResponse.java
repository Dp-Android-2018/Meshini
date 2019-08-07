package com.dp.meshini.servise.model.response;

import com.dp.meshini.servise.model.pojo.Links;
import com.dp.meshini.servise.model.pojo.PastUpcommingSharedListItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PastUpcomingSharedResponse {
    @SerializedName("data")
    private List<PastUpcommingSharedListItem>listItems;

    @SerializedName("links")
    private Links links;


    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public List<PastUpcommingSharedListItem> getListItems() {
        return listItems;
    }


    public void setListItems(List<PastUpcommingSharedListItem> listItems) {
        this.listItems = listItems;
    }
}
