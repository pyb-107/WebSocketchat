package com.liu.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.liu.pojo.FriendRelation;
import com.liu.pojo.GameRecord;
import org.springframework.stereotype.Service;

import com.liu.dao.UserDao;
import com.liu.pojo.User;
import com.liu.service.UserService;
@Service(value="userService")
public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDao;
	public List<User> selectAll(int page, int pageSize) {
		// TODO Auto-generated method stub
		return userDao.selectAll(page, pageSize);
	}

	public User selectUserByUserId(String userid) {
		// TODO Auto-generated method stub
		return userDao.selectUserByUserId(userid);
	}

	public Integer selectCount() {
		// TODO Auto-generated method stub
		return userDao.selectCount();
	}

	public boolean insertUser(User user) {
		// TODO Auto-generated method stub
		return userDao.insertUser(user);
	}

	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return userDao.updateUser(user);
	}

	public boolean deleteUser(String userid) {
		// TODO Auto-generated method stub
		return userDao.deleteUser(userid);
	}

	@Override
	public boolean insertGameRecord(String winperson, String loseperson) {
		GameRecord record = new GameRecord();
		record.setLoser(loseperson);
		record.setWinner(winperson);
		Long timeMillis = System.currentTimeMillis();
		BigInteger id= new BigInteger(timeMillis.toString());
		record.setId(id);
		return userDao.insertGameRecord(record);
	}

	@Override
	public ArrayList<GameRecord> getrecord(String userid) {
		ArrayList<GameRecord> list= userDao.getRecord(userid);
		ArrayList<GameRecord> gameRecords = new ArrayList<GameRecord>();
		for(GameRecord record:list){
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
			long value = record.getId().longValue();
			String dateStr = formatter.format(new Date(value));
			record.setDatetime(dateStr);
			gameRecords.add(record);
		}
		return gameRecords;
	}

	@Override
	public Integer insertFriend(String p1, String p2) {
		FriendRelation friendRelation = new FriendRelation();
//		规定p1大于p2
		if(p1.compareTo(p2)>0){
			friendRelation.setPerson1(p1);
			friendRelation.setPerson2(p2);
		}else {
			friendRelation.setPerson2(p1);
			friendRelation.setPerson1(p2);
		}
		userDao.insertFriend(friendRelation);
		return 1;
	}

	@Override
	public ArrayList<String> getfriendlist(String userid) {
		ArrayList<String> friendList = new ArrayList<>();
		ArrayList<FriendRelation> friendRelationList =	userDao.selectFriendRelationList(userid);
		for (FriendRelation friendRelation:friendRelationList){
			if (friendRelation.getPerson1().equals(userid)){
				friendList.add(friendRelation.getPerson2());
			}else {
				friendList.add(friendRelation.getPerson1());
			}
		}
		return friendList;
	}

	@Override
	public void deletefriend(String friendname) {
		userDao.deleteFriend(friendname);
	}

}
