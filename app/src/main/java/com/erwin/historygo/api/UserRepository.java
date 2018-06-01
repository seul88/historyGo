package com.erwin.historygo.api;

import java.util.List;

public class UserRepository {

    private List<UserModel> usersList;


    public List<UserModel> getUsers(){
        return usersList;
    }

    public void addUser(UserModel u){
        this.usersList.add(u);
    }

    public void clearList(){
        this.usersList.clear();
    }


}
