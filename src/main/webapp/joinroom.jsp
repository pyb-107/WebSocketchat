<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>WebChat | 加入聊天室</title>
    <jsp:include page="view/include/commonfile.jsp"/>
    <style>
        html, body{
            height:80%;;
        }
    </style>
</head>
<body>
<jsp:include page="view/include/header.jsp"/>
<div class="am-cf admin-main">
    <jsp:include page="view/include/sidebar.jsp"/>

    <!-- content start -->
    <div class="admin-content" style="overflow: scroll">
        <div class="am-cf am-padding">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">创建聊天室</strong> / <small>createroom</small></div>
        </div>

        <div class="align-center" style="width: 100%;height: 100%">
        <form class="layui-form"  lay-filter="example" action="${pageContext.request.contextPath}/chatroom/jumptoroom/${userid}" method="post">
            <div class="layui-form-item">
                <label class="layui-form-label">房间号</label>
                <div class="layui-input-block">
                    <input type="text" required name="roomid" lay-verify="required" autocomplete="off" placeholder="请输入房间号" class="layui-input">
                </div>
            </div>



            <div class="mycheckbox" >
                <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-block">
                        <input type="text"  name="password" lay-verify="title" autocomplete="off" placeholder="请设置密码" class="layui-input">
                    </div>
                </div>
            </div>


            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button type="submit" class="layui-btn" lay-submit="" lay-filter="createroom">创建</button>
                </div>
            </div>
        </form>

        </div>


        <!-- content end -->
    </div>
    <a href="#" class="am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}">
        <span class="am-icon-btn am-icon-th-list"></span>
    </a>
    <jsp:include page="view/include/footer.jsp"/>
    <script>
        $(".mycheckbox").slideUp();
        layui.use('form', function(){
            var form = layui.form;

            //监听提交
            form.on('submit(createroom)', function(data){
                var state = true;
                var room ={
                    "roomid":data.field.roomid,
                    "password":data.field.password,
                }
                $.ajax({
                    url:'${pageContext.request.contextPath}/chatroom/joinroom',
                    type:'post',
                    data:JSON.stringify(room),
                    async: false,
                    dataType:'JSON',
                    contentType:'application/json',
                    success:function(res){
                        if(res==1){
                        }
                        else if(res==2){
                            alert('该房间设置了密码');
                            $(".mycheckbox").slideDown();
                            state = false;
                        }else if(res==3){
                            alert('密码错误')
                            state = false;
                        }else if(res==4){
                            alert('房间号不存在')
                            state = false;
                        }else if(res==0){
                            alert('系统错误')
                            state = false;
                        }
                    },
                    error:function (data) {
                        alert('网络错误');
                        state = false;
                    }
                });
                if (state == false){
                    return false;
                }
            });


        });
    </script>
</body>
</html>
