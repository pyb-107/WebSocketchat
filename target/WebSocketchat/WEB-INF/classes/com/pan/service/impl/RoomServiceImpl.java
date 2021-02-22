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
    //�����µķ�����Ϣ
    public boolean insertRoom(Room room) {
        ArrayList<Room> rooms = selectRoomById(room.getRoomid());
        if(rooms.size()>0){
            return false;
        }else {
            roomDao.insertRoom(room);
            return true;
        }
    }
//    �����û�id�˳�����
    @Override
    public void exitRoom(String userid) {
        roomDao.exitRoom(userid);
    }
//   �û����뷿��
    @Override
    public void joinRoom(UserRoomRelation roomRelation) {
        roomDao.joinRoom(roomRelation);
    }
//  ��ѯ�����û����뷿��ļ�¼
    @Override
    public ArrayList<UserRoomRelation> selectAllRoomRelation() {
        ArrayList<UserRoomRelation> userRoomRelations = roomDao.selectAllRoomRelation();
        System.out.println(userRoomRelations);
        return userRoomRelations;

    }
//  ���뷿��
    @Override
    public int enterRoom(Room room) {
//        ����roomid��ѯ������Ϣ
        ArrayList<Room> rooms = selectRoomById(room.getRoomid());
//        δ��ѯ������
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

    @Override
    public String selectRoomIdByUserId(String userid) {

        return roomDao.selectRoomIdByUserId(userid);
    }

    //    ���ݷ���id��ѯ������Ϣ
    public ArrayList<Room> selectRoomById(String id){
        return roomDao.selectRoomById(id);
    }
}
