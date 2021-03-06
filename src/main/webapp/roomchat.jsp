<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>WebChat | 聊天 ${message }</title>
    <jsp:include page="view/include/commonfile.jsp"/>
<%--    <script src="${ctx}/plugins/sockjs/sockjs.js"></script>--%>
</head>
<body>
<%--顶部--%>
<jsp:include page="view/include/header.jsp"/>
<div class="am-cf admin-main">
<%--    左部--%>
    <jsp:include page="view/include/sidebar.jsp"/>
    <div class="admin-content" style="overflow: scroll">
        <div class="" style="width: 80%;float:left;">
            <!-- 聊天区 -->
            <div class="am-scrollable-vertical" id="chat-view" style="height: 510px;">
                <ul class="am-comments-list am-comments-list-flip" id="chat">
                </ul>
                
            </div>
             <!-- 接收者 -->
             <div class="" style="float: right">
                 <p class="am-kai" style="float: right">发送给 : <span id="sendto">全体成员</span></p>
                 <button class="layui-btn layui-btn-radius layui-btn-normal layui-btn-xs" onclick="$('#sendto').text('全体成员')" style="float: right">切换</button>
             </div>
            <!-- 输入区 -->
            <div class="am-form-group am-form" style="float: right;width: 95%">
                <textarea class="" id="message" name="message" rows="5"  placeholder="这里输入你想发送的信息..."></textarea>
            </div>

            <!-- 按钮区 -->
            <div class="am-btn-group am-btn-group-xs" style="float:right;">
                <button class="layui-btn" type="button" onclick="getConnection()"><span class="am-icon-plug"></span> 连接</button>
                <button class="layui-btn layui-btn-normal" type="button" onclick="closeConnection()"><span class="am-icon-remove"></span> 断开</button>
                <button class="layui-btn layui-btn-warm" type="button" onclick="checkConnection()"><span class="am-icon-bug"></span> 检查</button>
                <button class="layui-btn layui-btn-danger" type="button" onclick="clearConsole()"><i class="layui-icon"></i>清空记录</button>
                <button class="layui-btn layui-btn-lg" type="button" onclick="sendMessage()"> 发送</button>
            </div>
        </div>
         <!-- 列表区 -->
        <div class="am-panel am-pa el-default" style="float:right;width: 20%;">
            <div style="height: 50%">
                <div class="am-panel-hd" >
                    <h3 class="am-panel-title">在线列表 [<span id="onlinenum"></span>]</h3>
                </div>
                <ul class="am-list am-list-static am-list-striped" >
                    <li>图灵机器人 <button class="am-btn am-btn-xs am-btn-danger" id="tuling" data-am-button>未上线</button></li>
                </ul>
                <ul class="am-list am-list-static am-list-striped" id="list">
                </ul>
            </div>
            <div style="height: 50%">
                <div class="am-panel-hd" >
                    <h3 class="am-panel-title">好友列表 </h3>
                    <div>
                        <table id="friendlist" lay-filter="friendlist"></table>
                    </div>
                </div>
            </div>


        </div>
    </div>
</div>
  	<a href="#" class="am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}">
    <span class="am-icon-btn am-icon-th-list"></span></a>
    <jsp:include page="view/include/footer.jsp"/>
    <script>
        roomid = ${roomid};
        console.log("roomid="+roomid);
        layui.use(['layer','table'], function(){
            var layer = layui.layer;
            var table = layui.table;
            table.render({
                elem:'#friendlist'
                ,url:'${pageContext.request.contextPath}/user/getfriendlist/${userid}'
                ,cols:[[
                    {field: 'name', title: '名称',  sort: true, fixed: 'left'}
                ]]


            });
            table.on('row(friendlist)', function(obj){
                console.log(obj.tr); //得到当前行元素对象
                console.log(obj.data); //得到当前行数据
                checkfriend(obj.data.name);

            });
        });

        if("${message}"){
        layer.msg('${message}', {
            offset: 0
        });
    }
    if("${error}"){
        layer.msg('${error}', {
            offset: 0,
            shift: 6
        });
    }
    	$("#tuling").click(function(){
    		var onlinenum=$("#onlinenum").text();
    		if($(this).text()=="未上线"){
    		    $(this).text("已上线").removeClass("am-btn-danger").addClass("am-btn-success");
    		    showNotice("图灵机器人加入，你可以与其交流");
    		    $('#onlinenum').text(parseInt(onlinenum)+1);
    		}
    		else
    			{
    			$(this).text("未上线").removeClass("am-btn-success").addClass("am-btn-danger");
    			showNotice("图灵机器人加入，你可以与其交流");
    		    $('#onlinenum').text(parseInt(onlinenum)-1);
    			}
    	});
    
        var wsServer = null;
        var ws = null;
        wsServer = "ws://" + location.host+"${pageContext.request.contextPath}" + "/RoomChatServer";	//WebServer的路径
        ws = new WebSocket(wsServer); //创建WebSocket对象
        ws.onopen = function (evt) {  //打开时候进行额回掉函数
            layer.msg("已经建立连接", { offset: 0}); //提示已经建立
        };
        ws.onmessage = function (evt) { //用于显示后台传递的数据
            analysisMessage(evt.data);  //解析后台传回的消息,并予以展示
        };
        ws.onerror = function (evt) {  //错误提示
            layer.msg("产生异常", { offset: 0});
        };
        ws.onclose = function (evt) {//关闭提示
            layer.msg("已经关闭连接", { offset: 0});
        };
    	 /**
         * 连接按钮，进行连接
         */
         function getConnection(){
        	 if(ws == null){
                 ws = new WebSocket(wsServer); //创建WebSocket对象
                 ws.onopen = function (evt) {
                     layer.msg("成功建立连接!", { offset: 0});
                 };
                 ws.onmessage = function (evt) {
                     analysisMessage(evt.data);  //解析后台传回的消息,并予以展示
                 };
                 ws.onerror = function (evt) {
                     layer.msg("产生异常", { offset: 0});
                 };
                 ws.onclose = function (evt) {
                     layer.msg("已经关闭连接", { offset: 0});
                 };
             }else{
                 layer.msg("连接已存在!", { offset: 0, shift: 6 });
             }
    	 }
         /**
          * 关闭连接
          */
         function closeConnection(){
             if(ws != null){
                 ws.close();
                 ws = null;
                 $("#list").html("");    //清空在线列表
                 layer.msg("已经关闭连接", { offset: 0});
             }else{
                 layer.msg("未开启连接", { offset: 0, shift: 6 });
             }
         }
          /**
           * 检查连接
           */
           function checkConnection(){
        	  if(ws!=null){
        		  layer.msg(ws.readyState==0?"连接异常":"连接正常",{offset:0});
        	  }else{
        		  layer.msg("连接未开启!",{offset:0,shift:6});
        	  }
          }

           /**
            * 发送信息给后台
            */
   			function sendMessage(){
        	   if(ws==null){
					layer.msg("连接未开启!",{offset:0,shift:6});   
					return null;
        	   }
        	   var message=$("#message").val();//获取发送的信息
        	   var to=$("#sendto").text()=="全体成员"?"":$("#sendto").text();//获取要发送给的人，如果为全体成员则to设置为空
        	   if(message==null|message==""){
        		   layer.msg("请输入内容！",{offset:0,shift:6});
        	   }
        	   $("#tuling").text()=="已上线"?tuling(message):console.log("图灵机器人未开启");//图灵机器人
        	   ws.send(JSON.stringify(
        	       {  //发送信息给后台，发送的格式为JSON:{ message:{cotent:message,from:"userid",to:to,time:Daee()},type:"message" }
                       message:{
                           content:message,
                           from:'${userid}',
                           to:to,
                           time:getDateFull(),
                           roomid:roomid
                       },
                       type:"message"
                   }
        	   ));
        	   }
   		  /**
   		     * 解析后台传来的消息
   		     * "massage" : {
   		     *              "from" : "xxx",
   		     *              "to" : "xxx",
   		     *              "content" : "xxx",
   		     *              "time" : "xxxx.xx.xx"
   		     *          },
   		     * "type" : {notice|message},
   		     * "list" : {[xx],[xx],[xx]}
   		     */
   		  // 每次后台传输数据来时调用这个
   		     function analysisMessage(message){
   		    	 message=JSON.parse(message);//将后台发来的数据解析出JSON数据
   		    	 console.log(message);
   		    	 if(message.type=="message"){	//如果格式为message则展示数据到
   		    		 showChat(message.message);
   		    	 }
   		    	 if(message.type=="notice"){	//如果为notice则广播
   		    		 showNotice(message.message);
   		    	 }
   		    	 if(message.list != null && message.list != undefined){     //如果列表部位空，显示好友在线列表
   		            showOnline(message.list);
   		        }
   		     }
   		   function showChat(message){
   	        var to = message.to == null || message.to == ""? "全体成员" : message.to;   //获取接收人
   	        var from;
   	        var fromtitle;
   	        var isSef = '${userid}' == message.from ? "am-comment-flip" : "";   //如果是自己则显示在右边,他人信息显示在左边
   	        var isSefhead='${userid}'==message.from? "information":"otherinfo";
   	        if(message.to=='${userid}'){
   	        	to="你";
   	        }
   	        if(message.from=='${userid}')
   	        	{
   	        	messagetitle="你";
   	        	}
   	        else
   	        	{
   	        	messagetitle=message.from;
   	        	}
   	        
   	        
   	        var html = "<li class=\"am-comment "+isSef+" am-comment-primary\"><a href=\"${ctx}/"+isSefhead+"/"+message.from+"\"><img width=\"48\" height=\"48\" class=\"am-comment-avatar\" alt=\"\" src=\"${ctx}/head/"+message.from+"\"></a><div class=\"am-comment-main\">\n" +
   	                "<header class=\"am-comment-hd\"><div class=\"am-comment-meta\">   <a class=\"am-comment-author\" href=\"#link-to-user\">"+messagetitle+"</a> 在<time> "+message.time+"</time> 发送给 "+to+" </div></header><div class=\"am-comment-bd\"> <p>"+message.content+"</p></div></div></li>";
   	        $("#chat").append(html);
   	        $("#message").val("");  //清空输入区
   	        var chat = $("#chat-view");
   	        chat.scrollTop(chat[0].scrollHeight);   //让聊天区始终滚动到最下面
   	    }
   	    // 登入信息
   		  function showNotice(notice){
   	        $("#chat").append("<div><p class=\"am-text-success\" style=\"text-align:center\"><span class=\"am-icon-bell\"></span> "+notice+"</p></div>");
   	        var chat = $("#chat-view");
   	        chat.scrollTop(chat[0].scrollHeight);   //让聊天区始终滚动到最下面
   	    }
   	        // 生成在线列表
   		   function showOnline(list){
   		         // 遍历list来取出房间在线列表
            var userIdlist = getOnlineUserId(list);
   	        $("#list").html("");    //清空在线列表
   	        console.log(userIdlist.length);
   	        $.each(userIdlist, function(index, item){     //添加私聊按钮
   	            var li = "<li>"+item+"[自己]</li>";
   	            if('${userid}' != item){    //排除自己
   	                li = "<li>"+item+
                        // "<button type=\"button\" class=\"am-btn am-btn-xs am-btn-primary am-round\" onclick=\"addChat('"+item+"');\"><span class=\"am-icon-phone\"><span> 私聊</button>" +
                        // "<button type=\"button\" class=\"am-btn am-btn-xs am-btn-primary am-round\" onclick=\"checkinfo('"+item+"');\"><span class=\"am-icon-phone\"><span> 查看资料</button>" +
                        "<button type=\"button\" class=\"am-btn am-btn-xs am-btn-primary am-round\" onclick=\"checkuser('"+item+"');\"> 操作</button>" +
                        "</li>";
   	            }
   	            $("#list").append(li);
   	        });
   	        $("#onlinenum").text($("#list li").length);     //获取在线人数
   	    }

   	    function getOnlineUserId(list){
            var userIdList = [];
            for(var i=0;i<list.length;i++){
                if(list[i].roomId==roomid){
                    userIdList.push(list[i].userId);
                }
            }
            return userIdList;
        }
         function addChat(user){

             var sendto=$("#sendto");
             var receive=sendto.text()=="全体成员"?"":sendto.text()+",";
             if(receive.indexOf(user)==-1){
                 sendto.text(receive+user);
             }
             layer.closeAll();
         }
        function tuling(message){
            var html;
            $.getJSON("http://www.tuling123.com/openapi/api?key=6ad8b4d96861f17d68270216c880d5e3&info=" + message,function(data){
                if(data.code == 100000){
                    html = "<li class=\"am-comment am-comment-primary\"><a href=\"#link-to-user-home\"><img width=\"48\" height=\"48\" class=\"am-comment-avatar\" alt=\"\" src=\"${ctx}/static/img/robot.jpg\"></a><div class=\"am-comment-main\">\n" +
                            "<header class=\"am-comment-hd\"><div class=\"am-comment-meta\">   <a class=\"am-comment-author\" href=\"#link-to-user\">Robot</a> 发表于<time> "+getDateFull()+"</time> 发送给: ${userid}</div></header><div class=\"am-comment-bd\"> <p>"+data.text+"</p></div></div></li>";
                }
                if(data.code == 200000){
                    html = "<li class=\"am-comment am-comment-primary\"><a href=\"#link-to-user-home\"><img width=\"48\" height=\"48\" class=\"am-comment-avatar\" alt=\"\" src=\"${ctx}/static/img/robot.jpg\"></a><div class=\"am-comment-main\">\n" +
                            "<header class=\"am-comment-hd\"><div class=\"am-comment-meta\">   <a class=\"am-comment-author\" href=\"#link-to-user\">Robot</a> 发表于<time> "+getDateFull()+"</time> 发送给: ${userid}</div></header><div class=\"am-comment-bd\"> <p>"+data.text+"</p><a href=\""+data.url+"\" target=\"_blank\">"+data.url+"</a></div></div></li>";
                }
                $("#chat").append(html);
                var chat = $("#chat-view");
                chat.scrollTop(chat[0].scrollHeight);
                $("#message").val("");  //清空输入区
            });
        }
         function checkinfo(item)
         {
             window.location.href="${ctx}/otherinfo/"+item;
         }
         function test() {
             alert("test");

         }
        function addfriend(item)
        {
            var p1 = item;
            var p2 = "${userid}";
            console.log(p2)
            $.post("${pageContext.request.contextPath}/user/getfriend",{p1:p1,p2:p2},
                function (res) {
                    if (res == 1){
                        layer.msg("添加好友成功")
                    }else if(res == 2){
                        layer.msg("好友已存在")
                    }else {
                        layer.msg("添加好友失败")
                    }

                });
            layer.closeAll();

        }
        function test() {
            alert("test");

        }
        function checkuser(item)
        {
            layer.open({
                type: 1,
                offset: 'rt',
                content:"<div><button type=\"button\" class=\"am-btn am-btn-xs am-btn-primary am-round\" onclick=\"addChat('"+item+"');\"><span class=\"am-icon-phone\"><span> 私聊</button></div>"+
                    "<div><button type=\"button\" class=\"am-btn am-btn-xs am-btn-primary am-round\" onclick=\"checkinfo('"+item+"');\"><span class=\"am-icon-phone\"><span> 查看资料</button></div>"+
                    "<div><button type=\"button\" class=\"am-btn am-btn-xs am-btn-primary am-round\" onclick=\"addfriend('"+item+"');\"><span class=\"am-icon-phone\"><span> 添加好友</button></div>"
                ,
            })
        }

        function checkfriend(item)
        {
            layer.open({
                type: 1,
                offset: 'r',
                content:"<div><button type=\"button\" class=\"am-btn am-btn-xs am-btn-primary am-round\" onclick=\"deletefriend('"+item+"');\"><span class=\"am-icon-phone\"><span> 删除好友</button></div>"
                ,
            })
        }

        function deletefriend(item) {
   		         var friendname = item;

            $.post("${pageContext.request.contextPath}/user/deletefriend",{friendname:friendname},
                function (res) {
                    if (res ==true){
                        layer.msg("删除好友成功")
                    }else {
                        layer.msg("删除好友失败")
                    }

                });

                layer.closeAll();
                location.reload();
        }


    function clearConsole(){
   		    	 $("#chat").html("");
   		     }
   		  function appendZero(s){return ("00"+ s).substr((s+"").length);} 
   		  function getDateFull(){
   			  var date=new Date();
   			var currentdate = date.getFullYear() + "-" + appendZero(date.getMonth() + 1) + "-" + appendZero(date.getDate()) + " " + appendZero(date.getHours()) + ":" + appendZero(date.getMinutes()) + ":" + appendZero(date.getSeconds());
   			return currentdate;
   		  }
    </script>
</body>
</html>