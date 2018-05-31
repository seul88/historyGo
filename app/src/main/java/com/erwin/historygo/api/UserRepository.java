package com.erwin.historygo.api;

import java.util.List;

public class UserRepository {

    private List<User> usersList;


    public List<User> getUsers(){
        return usersList;
    }

    public void addUser(User u){
        this.usersList.add(u);
    }

    public void clearList(){
        this.usersList.clear();
    }


}
