package com.erwin.historygo.api;

import java.util.Comparator;

public class UserComparator implements Comparator<UserModel> {

    @Override
    public int compare(UserModel o1, UserModel o2) {
        return o1.getPoints() > o2.getPoints() ? -1 : o1.getPoints() == o2.getPoints() ? 0 : 1;
    }
}
