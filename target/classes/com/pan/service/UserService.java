package com.pan.service;

import java.util.ArrayList;
import java.util.List;

import com.pan.pojo.GameRecord;

import com.pan.pojo.User;

public interface UserService {
	List<User> selectAll(int page, int pageSize);
	User selectUserByUserId(String userid);
	Integer selectCount();
	boolean insertUser(User user);
	boolean updateUser(User user);
	boolean deleteUser(String userid);

    boolean insertGameRecord(String winperson, String loseperson);

	ArrayList<GameRecord> getrecord(String userid);

    Integer insertFriend(String p1, String p2);

    ArrayList<String> getfriendlist(String userid);

    void deletefriend(String friendname);
}
