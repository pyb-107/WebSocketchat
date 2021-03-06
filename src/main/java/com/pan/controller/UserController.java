package com.pan.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pan.pojo.GameRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pan.pojo.User;
import com.pan.pojo.UserLog;
import com.pan.service.impl.UserLogServiceImpl;
import com.pan.service.impl.UserServiceImpl;
import com.pan.utils.DateUtil;

@Controller
public class UserController {
	@Resource
	private UserServiceImpl userServiceImpl;
	@Resource
	private UserLogServiceImpl userLogServiceImpl;
	DateUtil dateUtil=new DateUtil();
//	展示用户信息
	@RequestMapping("information/{userid}")
	public ModelAndView information(@PathVariable("userid")String userid)
	{
		ModelAndView modelAndView=new ModelAndView();
		User user=userServiceImpl.selectUserByUserId(userid);
		modelAndView.addObject("user", user);
		modelAndView.setViewName("information");
		return modelAndView;
	}
//展示更新日志
	@RequestMapping("/updatelog")
	public String updatelog()
		{
			return "updatelog";
		}

//	信息设置
	@RequestMapping("infosetting/{userid}")
	public ModelAndView infosetting(@PathVariable("userid")String userid,HttpServletRequest request){
		ModelAndView modelAndView=new ModelAndView();
		User user=userServiceImpl.selectUserByUserId(userid);
		modelAndView.addObject("user", user);
		modelAndView.setViewName("info-setting");
		return modelAndView;
	}
//	修改用户信息
	@RequestMapping("/updateUser/{userid}")
	public String updateUser(User user,@PathVariable("userid")String userid,RedirectAttributes redirectAttributes,HttpServletRequest request){
		user.setUserid(userid);
		boolean flag=userServiceImpl.updateUser(user);
		if(flag)
		{
			UserLog userLog=new UserLog();
			String ip=request.getRemoteAddr();
			userLog.setUserid(user.getUserid());
			userLog.setTime(dateUtil.getDateformat());
			userLog.setType("修改");
			userLog.setDetail("修改资料");
			userLog.setIp(ip);
			userLogServiceImpl.insertLog(userLog);
			redirectAttributes.addFlashAttribute("message", "["+userid+"]资料修改成功!");
			return "redirect:/information/"+userid;
		}
		else
		{
			redirectAttributes.addFlashAttribute("error", "["+userid+"]资料修改失败!");
			return "redirect:/information/"+userid;
		}
		
	}
//	上传头像
	@RequestMapping("/upload/{userid}")
	public String upload(@PathVariable("userid")String userid,MultipartFile file,HttpServletRequest request,RedirectAttributes redirectAttributes) throws IllegalStateException, IOException{
		String real=request.getServletContext().getRealPath("/");
		String imagename=file.getOriginalFilename();
		System.out.println("imagename:"+imagename);
//		String imageurlnotag="/information/upload/"+userid+"/"+imagename;
		String imageurlnotag=imagename;
//		File file1=new File(real+"\\information\\upload"+"\\"+userid);
		File file1=new File(real+"/file/photo");
		if(!file1.exists())
		{
//			如果目录不存在就创建目录
			file1.mkdirs();
		}

		File imageurl=new File(file1+"/"+imagename);
		file.transferTo(imageurl);//将源图片复制到制定位置
		User user=new User();
		user.setUserid(userid);
		user.setProfilehead(imageurlnotag);
		boolean flag=userServiceImpl.updateUser(user);
		if(flag)
		{
			UserLog userLog=new UserLog();
			String ip=request.getRemoteAddr();
			userLog.setUserid(user.getUserid());
			userLog.setTime(dateUtil.getDateformat());
			userLog.setType("修改");
			userLog.setDetail("修改头像");
			userLog.setIp(ip);
			userLogServiceImpl.insertLog(userLog);
			redirectAttributes.addFlashAttribute("message", "["+userid+"]头像上传成功!");
			return "redirect:/information/"+userid;
		}
		else
		{
			redirectAttributes.addFlashAttribute("error", "["+userid+"]头像上传失败!");
			return "redirect:/information/"+userid;
		}
	}
//	修改密码
	@RequestMapping("/modifypassword/{userid}")
	public String modifyPassowrd(@PathVariable("userid")String userid,String oldpass,String newpass,RedirectAttributes redirectAttributes,HttpServletRequest request){
		User user=userServiceImpl.selectUserByUserId(userid);
		String password=user.getPassword();
		if(password.equals(oldpass))
		{
			User user1=new User();
			user1.setPassword(newpass);
			user1.setUserid(userid);
			boolean flag=userServiceImpl.updateUser(user1);
			if(flag)
			{
				UserLog userLog=new UserLog();
				String ip=request.getRemoteAddr();
				userLog.setUserid(user.getUserid());
				userLog.setTime(dateUtil.getDateformat());
				userLog.setType("修改");
				userLog.setDetail("修改密码");
				userLog.setIp(ip);
				userLogServiceImpl.insertLog(userLog);
			redirectAttributes.addFlashAttribute("message", "["+userid+"]密码修改成功!");
			return "redirect:/information/"+userid;
			}
			else {
				redirectAttributes.addFlashAttribute("error", "["+userid+"]密码修改失败!");
				return "redirect:/infosetting/"+userid;
			}
		}
		else{
			System.out.println("修改失败!");
			redirectAttributes.addFlashAttribute("error", "["+userid+"]密码修改失败!");
		}
		return "redirect:/infosetting/"+userid;
	}
//	查看日志
	@RequestMapping("/log/{userid}")
	public String log(@PathVariable("userid")String userid,@RequestParam("page")int page,HttpServletRequest request)
	{
		if( request.getSession().getAttribute("pageSize") == null)
		{
			request.getSession().setAttribute("pageSize", 5);
		}
		int pageSize=(Integer) request.getSession().getAttribute("pageSize");
		int count;
		List<UserLog> loglist=new ArrayList<UserLog>();
		loglist=userLogServiceImpl.selectLogByUserid(userid, page, pageSize);
		count=userLogServiceImpl.selectLogCountByUserid(userid, pageSize);
		request.getSession().setAttribute("loglist", loglist);
		request.getSession().setAttribute("count", count);
		return "log";
	}
//	用于获取发送信息时的头像，使用流
	@RequestMapping("/head/{userid}")
	public void gethead(@PathVariable("userid")String userid,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String realurl=request.getServletContext().getRealPath("/");
		String imageurl=userServiceImpl.selectUserByUserId(userid).getProfilehead();
		String url=realurl+"file/photo/"+imageurl;
		InputStream inputStream=new FileInputStream(url);
		ServletOutputStream outputStream=response.getOutputStream();
		response.setContentType("image/jpeg; charset=UTF-8");
		byte[] buffer=new byte[1024];
		int i=-1;
		while((i=inputStream.read(buffer))!=-1)
		{
			outputStream.write(buffer, 0, i);
		}
		outputStream.flush();
		outputStream.close();
		inputStream.close();
	}
//	帮助
	@RequestMapping("/help")
	public String help()
	{
		return "help";
		
	}
//	关于
	@RequestMapping("/about")
	public String about()
	{
		return "about";
	}
//	系统设置
	@RequestMapping("system-setting/{userid}")
	public String systemsetting(@PathVariable("userid")String userid,HttpServletRequest request)
	{
		User user=userServiceImpl.selectUserByUserId(userid);
		request.setAttribute("user", user);
		return "system-setting";
	}
//	修改系统设置
	@RequestMapping("changesystem/{userid}")
	public String changesystem(@PathVariable("userid")String userid,int options,int secrecy,HttpServletRequest request,RedirectAttributes redirectAttributes)
	{
		request.getSession().setAttribute("pageSize", options);
		User user=new User();
		user.setUserid(userid);
		user.setStatus(secrecy);
		boolean flag=userServiceImpl.updateUser(user);
		if(flag)
		{
			redirectAttributes.addFlashAttribute("message", "系统设置修改成功!");
		}
		else
		{
			redirectAttributes.addFlashAttribute("error", "系统修改失败!");
		}
		return "redirect:/system-set";
	}
//	其他人信息
	@RequestMapping("otherinfo/{userid}")
	public String otherinformation(@PathVariable("userid")String userid,HttpServletRequest request,RedirectAttributes redirectAttributes)
	{
		int status=userServiceImpl.selectUserByUserId(userid).getStatus();
		if(status==-1)
		{
			redirectAttributes.addFlashAttribute("error", userid+"的信息未公开!");
			redirectAttributes.addFlashAttribute("userid", userid);
			return "redirect:/errorinfo";
		}
		else
		{
			return "redirect:/information/"+userid;
		}
	}
//	在比赛结束之后插入数据
	@ResponseBody
	@RequestMapping("/game/insertrecord")
	public boolean insertrecord(String winperson,String loseperson,HttpServletRequest request,RedirectAttributes redirectAttributes)
	{
		boolean state = true;

		try {
			System.out.println(winperson);
			System.out.println(loseperson);
			userServiceImpl.insertGameRecord(winperson,loseperson);
		} catch (Exception e) {
			e.printStackTrace();
			state = false;
		}finally {
			return state;
		}
	}
//添加好友
	@ResponseBody
	@RequestMapping("/user/getfriend")
	public Integer getfriend(String p1,String p2,HttpServletRequest request,RedirectAttributes redirectAttributes)
	{
		Integer state = 0;

		try {
			System.out.println(p1);
			System.out.println(p2);
			state = userServiceImpl.insertFriend(p1,p2);
		} catch (Exception e) {
			e.printStackTrace();
			state = 3;
		}finally {
			return state;
		}
	}

	//删除好友
	@ResponseBody
	@RequestMapping("/user/deletefriend")
	public boolean deletefriend(String friendname,HttpServletRequest request,RedirectAttributes redirectAttributes)
	{
		boolean state = true;
		try {
			userServiceImpl.deletefriend(friendname);
		} catch (Exception e) {
			e.printStackTrace();
			state = false;
		}finally {
			return state;
		}
	}

//查询好友列表

	@ResponseBody
	@RequestMapping("/user/getfriendlist/{userid}")
	public Map getfriendlist(@PathVariable("userid")String userid, HttpServletRequest request, RedirectAttributes redirectAttributes)
	{
		HashMap<Object, Object> map = new HashMap<>();
		ArrayList<String> friendList = new ArrayList<>();

		try {
			friendList = userServiceImpl.getfriendlist(userid);
			ArrayList<Map> resultList = new ArrayList<>();
			for (String name:friendList){
				HashMap<String, String> resultMap = new HashMap<>();
				resultMap.put("name",name);
				resultList.add(resultMap);
			}
			map.put("code",0);
			map.put("msg","");
			map.put("count",friendList.size());
			map.put("data",resultList);

		} catch (Exception e) {
			e.printStackTrace();
			map = null;
		}finally {
			return map;
		}
	}

//	查询比赛记录
	@ResponseBody
	@RequestMapping("/game/getrecord/{userid}")
	public Map getrecord(@PathVariable("userid")String userid, HttpServletRequest request, RedirectAttributes redirectAttributes)
	{
		HashMap<Object, Object> map = new HashMap<>();
		ArrayList<GameRecord> gameRecords = new ArrayList<>();

		try {
			gameRecords = userServiceImpl.getrecord(userid);
			for (GameRecord record:gameRecords){
				map.put("code",0);
				map.put("msg","");
				map.put("count",gameRecords.size());
				map.put("data",gameRecords);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map = null;
		}finally {
			return map;
		}
	}
	@RequestMapping("errorinfo")
	public String error()
	{
		return "errorinfo";
	}
	@RequestMapping("/system-set")
	public String systemsett()
	{
		return "system-setting";
	}
}
