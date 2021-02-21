package com.pan.webSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;

@ServerEndpoint(value="/chatServer",configurator=HttpSessionConfigurator.class)
public class ChatServer{
	private static int onlineCount=0;	//ͳ����������
	private static CopyOnWriteArraySet<ChatServer> webSocket=new CopyOnWriteArraySet<ChatServer>();//���ÿ��Server����Ϊ�̰߳�ȫ
	private Session session;
	private String userid;//�û�Id
	private HttpSession httpSession;
	private static List<Object> list=new ArrayList<Object>();//���ÿ���û�id
	private static Map<String,Session> routetab=new HashMap<String,Session>();//���session
	@OnOpen
	public void onOpen(Session session,EndpointConfig config)//����ʱ���ô˺���
	{
		this.session=session;
		webSocket.add(this);         //��Ӵ�Server
		System.out.println(this);
		addOnlineCount();	//����������1��
		this.httpSession=(HttpSession) config.getUserProperties().get(HttpSession.class.getName());//��ȡ��Server��httpSession
		this.userid=(String) httpSession.getAttribute("userid");//��ȡ�û�Id
		System.out.println("userid:"+userid);
		list.add(userid);//��ӵ�list
		routetab.put(userid, session);//��session���û�Id��ŵ�map��
		String message=getMessage("[" + userid + "]����������,��ǰ��������Ϊ"+getOnlineCount()+"λ", "notice",  list);//��ȡ�㲥������ʽ
		broadcast(message);//�㲥��ʾ���
	}
	@OnClose
	public void onClose(){//�ر�ʱ����
		webSocket.remove(this);//�Ƴ���Server
		subOnlineCount();     //������һ
		list.remove(userid);//�Ƴ����û�id
		routetab.remove(userid);//�Ƴ�MAP�еĴ��û�
		String message=getMessage("[" + userid +"]�뿪��������,��ǰ��������Ϊ"+getOnlineCount()+"λ", "notice", list);
		broadcast(message);//�㲥
	}
	@OnMessage
	public void onMessage(String _message)//���ܵ�����ʱ����
	{
		JSONObject chat=JSONObject.parseObject(_message);//������JSONΪ����
		JSONObject message=JSONObject.parseObject(chat.get("message").toString());//��ȡmessage�е�����
		if(message.get("to")==null||message.get("to").toString().equals("")){//������͵���Ϊ�գ�����й㲥
			broadcast(_message);
		}else
		{
			String [] userlist=message.get("to").toString().split(",");//��ȡҪ���͵�����
			singleSend(_message,(Session)routetab.get(message.get("from")));//���͸��Լ�
			for(String user:userlist)//���û����з���
			{
				if(!user.equals(message.get("from")))
				{
					singleSend(_message,(Session)routetab.get(user));//������Ϣ��ָ���û�
				}
			}
		}
	}
	@OnError
	public void onError(Throwable error){
		error.printStackTrace();
	}
	public void broadcast(String message)//�㲥��Ϣ
	{
		for(ChatServer chat:webSocket){
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
	        ChatServer.onlineCount++;
	    }

	    public  void subOnlineCount() {
	        ChatServer.onlineCount--;
	    }
}
