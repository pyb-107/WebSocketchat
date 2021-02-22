package com.pan.dao;

import com.pan.pojo.Room;
import com.pan.pojo.UserRoomRelation;

import java.util.ArrayList;

public interface RoomDao {
    void insertRoom(Room room);

    ArrayList<Room> selectRoomById(String id);

    void exitRoom(String userid);

    void joinRoom(UserRoomRelation roomRelation);

    ArrayList<UserRoomRelation> selectAllRoomRelation();

    String selectRoomIdByUserId(String userid);
}
