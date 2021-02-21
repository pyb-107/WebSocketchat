package com.pan.webSocket;

import com.alibaba.fastjson.JSONObject;
import com.pan.pojo.UserRoomRelation;
import com.pan.service.impl.RoomServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value="/RoomChatServer",configurator=HttpSessionConfigurator.class)
public class RoomChatServer {
	private static int onlineCount=0;	//ͳ����������
	private static CopyOnWriteArraySet<RoomChatServer> webSocket=new CopyOnWriteArraySet<RoomChatServer>();//���ÿ��Server����Ϊ�̰߳�ȫ
	private Session session;
	private String userid;//�û�Id
	private HttpSession httpSession;
	private static List<Object> list=new ArrayList<Object>();//���ÿ���û�id
	private static Map<String,Session> routetab=new HashMap<String,Session>();//���session
	private static ArrayList<UserRoomRelation> userRoomRelations= new ArrayList<UserRoomRelation>();
	private static ApplicationContext ac;
	private static RoomServiceImpl roomService;
	static {
		ac = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		roomService = (RoomServiceImpl)ac.getBean(RoomServiceImpl.class);
	}

	@OnOpen
	public void onOpen(Session session,EndpointConfig config)//����ʱ���ô˺���
	{
		System.out.println("test");
//		ÿ������������ʱˢ�·�����û���ϵ�б��е�����
		userRoomRelations = roomService.selectAllRoomRelation();
		this.session=session;
		webSocket.add(this);         //��Ӵ�Server
		System.out.println(this);
		addOnlineCount();	//����������1��
		this.httpSession=(HttpSession) config.getUserProperties().get(HttpSession.class.getName());//��ȡ��Server��httpSession
		this.userid=(String) httpSession.getAttribute("userid");//��ȡ�û�Id
		System.out.println("userid:"+userid);
		list.add(userid);//��ӵ�list
		routetab.put(userid, session);//��session���û�Id��ŵ�map��
		String message=getMessage("[" + userid + "]����������,��ǰ��������Ϊ"+getOnlineCount()+"λ", "notice",  userRoomRelations);//��ȡ�㲥������ʽ
		broadcast(message);//�㲥��ʾ���
	}
	@OnClose
	public void onClose(){//�ر�ʱ����
		webSocket.remove(this);//�Ƴ���Server
		subOnlineCount();     //������һ
		list.remove(userid);//�Ƴ����û�id
		routetab.remove(userid);//�Ƴ�MAP�еĴ��û�
		String message=getMessage("[" + userid +"]�뿪��������,��ǰ��������Ϊ"+getOnlineCount()+"λ", "notice", userRoomRelations);
		broadcast(message);//�㲥
	}
	@OnMessage
	public void onMessage(String _message)//���ܵ�����ʱ����
	{
		System.out.println(userRoomRelations);
		JSONObject chat=JSONObject.parseObject(_message);//������JSONΪ����
		JSONObject message=JSONObject.parseObject(chat.get("message").toString());//��ȡmessage�е�����
		String roomid = message.get("roomid").toString();

//		��ȡ�������б�
		ArrayList<String> userIdList = getUserIdList(roomid, userRoomRelations);

//		String [] userlist=message.get("to").toString().split(",");//��ȡҪ���͵�����
		singleSend(_message,(Session)routetab.get(message.get("from")));//���͸��Լ�
		for(String user:userIdList)//���û����з���
		{
			if(!user.equals(message.get("from")))
			{
				singleSend(_message,(Session)routetab.get(user));//������Ϣ��ָ���û�
			}
		}

	}
	@OnError
	public void onError(Throwable error){
		error.printStackTrace();
	}
	public void broadcast(String message)//�㲥��Ϣ
	{
		for(RoomChatServer chat:webSocket){
			try{
				  chat.session.getBasicRemote().sendText(message);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	  public void singleSend(String message, Session session){
	        try {
	            session.getBasicRemote().sendText(message);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	public String getMessage(String message,String type,List list)
	{
		JSONObject member=new JSONObject();
		member.put("message", message);
		member.put("type", type);
		member.put("list", list);
		return member.toString();
	}
	public  int getOnlineCount() {
	        return onlineCount;
	    }
	public  void addOnlineCount() {
	        RoomChatServer.onlineCount++;
	    }
	public  void subOnlineCount() {
	        RoomChatServer.onlineCount--;
	    }
	public ArrayList<String> getUserIdList(String roomId,ArrayList<UserRoomRelation> userRoomRelations){
		ArrayList<String> list = new ArrayList<>();
		for (UserRoomRelation userRoomRelation:userRoomRelations){
			if (userRoomRelation.getRoomId().equals(roomId)){
				list.add(userRoomRelation.getUserId());
			}
		}
		return list;

	}
}
