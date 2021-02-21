package com.pan.controller;

import com.pan.pojo.Room;
import com.pan.pojo.UserRoomRelation;
import com.pan.service.impl.RoomServiceImpl;
import com.pan.service.impl.UserLogServiceImpl;
import com.pan.service.impl.UserServiceImpl;
import com.pan.utils.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class RoomChatController {
	@Resource
	private UserServiceImpl userServiceImpl;
	@Resource
	private UserLogServiceImpl userLogServiceImpl;
	@Resource
	private RoomServiceImpl roomServiceImpl;
	DateUtil dateUtil=new DateUtil();
//	��������

	@ResponseBody
	@RequestMapping("/chatroom/insertroom")
	public boolean insertrecord(@RequestBody Room room)
	{
		boolean state = true;

		try {
			if (room.getPassword().equals("")){
				room.setPassword("no");
			}

			state = roomServiceImpl.insertRoom(room);
		} catch (Exception e) {
			e.printStackTrace();
			state = false;
		}finally {
			return state;
		}
	}


	//	���뷿��

	@ResponseBody
	@RequestMapping("/chatroom/joinroom")
	public int joinroom(@RequestBody Room room)
	{
		int state = 0;

		try {
			state = roomServiceImpl.enterRoom(room);
		} catch (Exception e) {
			e.printStackTrace();
			state = 0;
		}finally {
			return state;
		}
	}

//	��ת��������
	@RequestMapping("/chatroom/jumptoroom/{userid}")
	public String jumptoroom(@PathVariable("userid")String userid,String roomid, RedirectAttributes redirectAttributes, HttpSession httpSession){
		httpSession.setAttribute("roomid",roomid);
//		������û��˳���������
		roomServiceImpl.exitRoom(userid);
		System.out.println(userid);
//				�����µķ���
		UserRoomRelation roomRelation = new UserRoomRelation();
		roomRelation.setRoomId(roomid);
		roomRelation.setUserId(userid);
		roomServiceImpl.joinRoom(roomRelation);
//		redirectAttributes.addFlashAttribute("roomid",roomid);
		System.out.println("jumpsuccess");
		return "redirect:/roomchat.jsp";
	}




}
