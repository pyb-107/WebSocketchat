package com.liu.service.impl;

import com.liu.dao.RoomDao;
import com.liu.pojo.Room;
import com.liu.service.RoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service(value="RoomService")
public class RoomServiceImpl implements RoomService {
    @Resource
    private RoomDao roomDao;
    @Override
    public boolean insertRoom(Room room) {
        ArrayList<Room> rooms = selectRoomById(room.getRoomid());
        if(rooms.size()>0){
            return false;
        }else {
            roomDao.insertRoom(room);
            return true;
        }
    }

    public ArrayList<Room> selectRoomById(String id){
        return roomDao.selectRoomById(id);
    }
}
