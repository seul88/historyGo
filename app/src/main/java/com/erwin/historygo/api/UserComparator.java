package com.erwin.historygo.api;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        return o1.getPoints() > o2.getPoints() ? -1 : o1.getPoints() == o2.getPoints() ? 0 : 1;
    }
}
