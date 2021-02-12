package com.liu.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.liu.pojo.FriendRelation;
import com.liu.pojo.GameRecord;
import org.apache.ibatis.annotations.Param;

import com.liu.pojo.User;

public interface UserDao {
	List<User> selectAll(@Param("offset") int offset,@Param("limit") int limit);
	User selectUserByUserId(String userid);
	int selectCount();
	boolean insertUser(User user);
	boolean updateUser(User user);
	boolean deleteUser(String userid);

    boolean insertGameRecord(String winperson, String loseperson);

	boolean insertGameRecord(GameRecord record);

	ArrayList<GameRecord> getRecord(String userid);

	boolean insertFriend(FriendRelation friendRelation);

    ArrayList<FriendRelation> selectFriendRelationList(String userid);

    void deleteFriend(String friendname);
}
