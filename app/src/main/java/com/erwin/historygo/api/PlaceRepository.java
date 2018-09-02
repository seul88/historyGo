package com.erwin.historygo.api;

import java.util.ArrayList;
import java.util.List;

public class PlaceRepository {


    private List<PlaceModel> placeList;

    public List<PlaceModel> getPlaces(){
        return this.placeList;
    }

    public void addPlace(PlaceModel place){
        this.placeList.add(place);
    }

    public void clearList(){this.placeList.clear();}


    public PlaceRepository(){
        this.placeList = new ArrayList<>();
    }
}
