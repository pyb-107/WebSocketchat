package com.liu.pojo;

public class UserLog {
		private int id;      //��־���
	    private String userid;  //�û���
	    private String time;    //ʱ��
	    private String type;    //����
	    private String detail;  //����
	    private String ip;      //ip��ַ
		public int getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getUserid() {
			return userid;
		}
		public void setUserid(String userid) {
			this.userid = userid;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getDetail() {
			return detail;
		}
		public void setDetail(String detail) {
			this.detail = detail;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
	    
}
