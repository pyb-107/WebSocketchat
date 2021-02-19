package com.liu.controller;

import com.liu.pojo.Room;
import com.liu.service.impl.RoomServiceImpl;
import com.liu.service.impl.UserLogServiceImpl;
import com.liu.service.impl.UserServiceImpl;
import com.liu.utils.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Controller
public class RoomChatController {
	@Resource
	private UserServiceImpl userServiceImpl;
	@Resource
	private UserLogServiceImpl userLogServiceImpl;
	@Resource
	private RoomServiceImpl roomServiceImpl;
	DateUtil dateUtil=new DateUtil();
//	创建房间

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
//	跳转到房间内
	@RequestMapping("/chatroom/jumptoroom")
	public String jumptoroom(String roomid,RedirectAttributes redirectAttributes){
		redirectAttributes.addFlashAttribute("roomid",roomid);
		System.out.println("jumpsuccess");
		return "redirect:/roomchat.jsp";
	}




}
