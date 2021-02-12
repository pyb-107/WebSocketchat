# SSM_websocketchat<br>
一个用SSM以及WebSocket写的一个聊天室，其中还有联机五子棋以及AI五子棋<br>
#################################<br>
介绍:<br>
  此项目利用`SpringMVC+mybatis+spring`编写的一个聊天室，聊天功能实现利用WebSocket。<br>
#################################<br>
所用技术：<br>
  开发工具:`eclipse`<br>
  项目管理：`Maven`<br>
  使用语言：`Java`<br>
  使用的框架：`SpringMVC+mybatis+spring`<br>
  安全认证：`shiro`<br>
  数据库：`mysql`<br>
  前端采用：`Amaze UI`<br>
  弹窗和分页:`Layer`<br>
 #################################<br>
 前台与后台交互<br>
 前台与后台交互的JSON格式为：<br>
 聊天信息的JSON：<br>
 "message" : {<br>
	"from" : "xxx",<br>
	"to" : "xxx",<br>
	"content" : "xxxxxx",<br>
	"time" : "xxxx.xx.xx"<br>
},<br>
"type" : {<br>
	"xxx"<br>
},<br>
"list" : {<br>
	["xx","xx"]<br>
}<br>
from:代表是谁发送的信息，to代表发送给谁，content:代表发送的内容，time:发送的时间。<br>
type:代表类型，代表是什么JSON，聊天还是其他，list:后台统计在线人数，然后前台解析展示。<br>
五子棋的JSON：<br>
{"zuobiao":{<br>
"x":"xx",<br>
"y":"yy",<br>
}<br>
type:"zuobiaoxy"<br>
}<br>
x:代表下子的x轴，y:代表下子的y轴<br>
type:zuobiaoxy 标记这个JSON格式为棋子JSON<br>
五子棋提示JSON:<br>
{<br>
"message" : "提示内容",<br>
"type" :"tishi",<br>
"list" : ["xx","xx"]<br>
}<br>
message:后台提示内容,type:提示JSON，list:下棋的人数，以及观战的人数。<br><br>
#########################<br>
实现的功能：<br>
其中简要的功能有使用shiro进行账号登陆、注册，与其他聊天，人工智聊天，资料查看以及查看别人资料，个人信息修改<br>
头像上传，修改，密码修改，对日志查看，系统修改包括，日志分页大小，个人信息是否允许别人查看，<br>
最后还有一个与别人对战的五子棋，可以让其他人进行观战，并且有可以自己与人机进行对战。<br>
########################<br>
使用方法：<br>
下载好项目后,导入文件夹，`webpapp`中有`file`中的`sql`文件，倒入此文件到数据库，修改`resource`文件的`jdbc.properties`,修改数据库账号密码，运行程序。<br>
########################<br>
登陆界面:
![](https://github.com/SinceNovember/websockchat/blob/master/src/main/webapp/file/photo/login.png)
聊天主界面:
![](https://github.com/SinceNovember/websockchat/blob/master/src/main/webapp/file/photo/index.png)
个人信息界面：
![](https://github.com/SinceNovember/websockchat/blob/master/src/main/webapp/file/photo/info.png)
系统日志界面：
![](https://github.com/SinceNovember/websockchat/blob/master/src/main/webapp/file/photo/log.png)
个人设置界面:
![](https://github.com/SinceNovember/websockchat/blob/master/src/main/webapp/file/photo/setting.png)
系统设置界面：
![](https://github.com/SinceNovember/websockchat/blob/master/src/main/webapp/file/photo/sysset.png)
联机五子棋界面:
![](https://github.com/SinceNovember/websockchat/blob/master/src/main/webapp/file/photo/five_chess.png)
