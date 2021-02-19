package com.liu.dao;

import com.liu.pojo.Room;

import java.util.ArrayList;

public interface RoomDao {
    void insertRoom(Room room);

    ArrayList<Room> selectRoomById(String id);
}
