package com.liu.webSocket;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.enterprise.inject.New;
import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat.Value;

@ServerEndpoint(value="/chessServer",configurator=HttpSessionConfigurator.class)
public class five_chessServer {
	private  static int onlineCount=0;
	private static CopyOnWriteArraySet<five_chessServer> webSocket=new CopyOnWriteArraySet<five_chessServer>();
	private Session session;
	private HttpSession httpsession;
	private String userid;
	private static boolean whitechess=false;//�жϰ����Ƿ������
	private static boolean blackchess=true;//�жϺ����Ƿ������
	private static List<String> list=new ArrayList<String>();//������н�����û�������
	private  static List<String> chesss=new ArrayList<String>();//��ŶԾ����Ծ��µ����ӵ�λ�����ڹ�ս
	private static Map<String, Session>routab=new HashMap<String, Session>();
	
	@OnOpen
	public void onOpen(Session session,EndpointConfig config) throws IOException
	{
		this.session=session;
		onlineCount++;
		webSocket.add(this);
		this.httpsession=(HttpSession)config.getUserProperties().get(HttpSession.class.getName());
		this.userid=(String) httpsession.getAttribute("userid");//��ȡ���ʵ�session���û�Id
		list.add(userid);
		routab.put(userid,session);
		//����ǹ�ս���û�����δ��֮ǰ���µ�����չʾ
		if(list.size()>2)
			{
			if(userid!=list.get(0)&&userid!=list.get(1))
			{
				for(int i=0;i<chesss.size();i++)
				{
					sendxy(chesss.get(i), routab.get(userid));
				}
			}
			}
	}
	@OnClose
	public void onClose()
	{
		String closemessage;
		//������������뿪������˳��ָ�
		if(list.size()>1)
		{
			if(userid.equals(list.get(1))||userid.equals(list.get(0)))
			{
				whitechess=false;
				blackchess=true;
				chesss.clear();
			}
		}
		webSocket.remove(this);
		onlineCount--;
		list.remove(userid);
		routab.remove(userid);
		closemessage=getMessage("[" + userid +"]�˳�����ǰ��������Ϊ"+onlineCount+"λ", "tishi",list);
//		������ʾ��Ϣ
		broadcast(closemessage);
	}
	@OnMessage
	public void onMessage(String _message) throws IOException
	{

		String message1;
		JSONObject message=JSONObject.parseObject(_message);//����ǰ̨������json
		if(message.get("type").toString().equals("tishi"))//���typeΪ��ʾ����㲥��Ϣ
		{
			if(onlineCount==1)
			{
			 message1=getMessage("[" + userid +"]���������壬��ǰ��������Ϊ"+onlineCount+"λ,��ȴ���һλ����", "tishi",list);
			}
			else if(onlineCount==2)
			{
			message1=getMessage("[" + userid +"]���������壬��ǰ��������Ϊ"+onlineCount+"λ,��Ϸ��ʼ", "tishi",list);
			}
			else {
				
			message1=getMessage("[" + userid +"]�����������ս����ǰ��������Ϊ"+onlineCount+"λ", "tishi",list);
			}
			broadcast(message1);
		}
//		���������typeΪ����
		if(message.get("type").toString().equals("zuobiaoxy"))
		{
		String zuobiao=message.get("zuobiao").toString();//��ȡ
//		�����ǰ���µ��ǰ��壬�����ǵ�һ���û��µ������������
		if(whitechess&&userid.equals(list.get(0)))
		{
			for(String user:list)
			{
					sendxy(_message,routab.get(user));
			}
			chesss.add(_message);
			whitechess=false;//����˳������л�
			blackchess=true;
		}
		if(list.size()>1)
		{
//			�����෴
		if(blackchess&&userid.equals(list.get(1)))
		{
			for(String user:list)
			{

					sendxy(_message,routab.get(user));
					
			}
			chesss.add(_message);
			whitechess=true;
			blackchess=false;
		}
		}
		}
	
	}
	@OnError
	public void onError(Throwable error){
		error.printStackTrace();
	}
	public void sendxy(String xymessage,Session session) throws IOException
	{
		session.getBasicRemote().sendText(xymessage);
	}
	public String getMessage(String message,String type,List list)
	{
		JSONObject member=new JSONObject();
		member.put("message", message);
		member.put("type", type);
		member.put("list", list);
		return member.toString();
	}
	public void broadcast(String message)//�㲥��Ϣ
	{
		for(five_chessServer chat:webSocket){
			try{
				  chat.session.getBasicRemote().sendText(message);//����session������Ϣ
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
