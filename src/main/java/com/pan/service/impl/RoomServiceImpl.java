package com.pan.service.impl;

import com.pan.dao.RoomDao;
import com.pan.pojo.Room;
import com.pan.pojo.UserRoomRelation;
import com.pan.service.RoomService;
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

    @Override
    public void exitRoom(String userid) {
        roomDao.exitRoom(userid);
    }

    @Override
    public void joinRoom(UserRoomRelation roomRelation) {
        roomDao.joinRoom(roomRelation);
    }

    @Override
    public ArrayList<UserRoomRelation> selectAllRoomRelation() {
        ArrayList<UserRoomRelation> userRoomRelations = roomDao.selectAllRoomRelation();
        System.out.println(userRoomRelations);
        return userRoomRelations;

    }

    @Override
    public int enterRoom(Room room) {
//        根据roomid查询房间信息
        ArrayList<Room> rooms = selectRoomById(room.getRoomid());
//        未查询到房间
        if (rooms.size()==0){
            return 4;
        }
        Room room1 = rooms.get(0);
        if(room.getPassword().equals(room1.getPassword())){
            return 1;
        }else if (room.getPassword().isEmpty() && room1.getPassword().equals("no")){
            return 1;
        }else if (room.getPassword().isEmpty()){
            return 2;
        }else{
            return 3;
        }
    }

    public ArrayList<Room> selectRoomById(String id){
        return roomDao.selectRoomById(id);
    }
}
