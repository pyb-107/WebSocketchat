package com.pan.service;

import com.pan.pojo.Room;
import com.pan.pojo.UserRoomRelation;

import java.util.ArrayList;

public interface RoomService {
    boolean insertRoom(Room room);

    void exitRoom(String userid);

    void joinRoom(UserRoomRelation roomRelation);

    ArrayList<UserRoomRelation> selectAllRoomRelation();

    int enterRoom(Room room);

    String selectRoomIdByUserId(String userid);
}
