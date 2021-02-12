<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
  <title>WebChat | 登陆</title>
  <link href="${pageContext.request.contextPath }/static/source/css/login.css" rel='stylesheet' type='text/css' />
  <link href="${pageContext.request.contextPath }/static/plugins/layui/css/layui.css" rel='stylesheet' type='text/css' />
  <script src="${pageContext.request.contextPath }/static/plugins/jquery/jquery-2.1.4.min.js"></script>
  <script src="${pageContext.request.contextPath }/static/plugins/layer/layer.js"></script>
  <script src="${pageContext.request.contextPath }/static/plugins/layui/layui.js"></script>



</head>

<body>

<%--<h1>憨憨五子棋</h1>--%>
<div class="login-form">
<%--  取消关闭按钮--%>
<%--  <div class="close"> </div>--%>
  <div class="head-info">
<%--    <label class="lbl-1"></label>--%>
<%--    <label class="lbl-2"></label>--%>
<%--    <label class="lbl-3"></label>--%>
    <p style="font-size:30px">登入系统</p>
<%--    <a href="<%=path%>/user/toregister" ><p style="font-size:30px"; font-align="left">注册</p></a> --%>
  </div>
  <div class="clear"> </div>
  <div class="avtar"><img src="${pageContext.request.contextPath }/static/source/img/touxiang.png" /></div>
  <form id="login-form" action="<%=path%>/user/login" method="post">
    <div class="key">
      <input type="text" id="username" name="userid" placeholder="请输入账号" >
    </div>

    <div class="key">
      <input type="password" id="password" name="password" placeholder="请输入密码">
    </div>


    <div class="signin"><
      <button type="button" class="layui-btn layui-btn-radius layui-btn-lg" onclick="checkLoginForm()">登入</button>
      <a href="<%=path%>/user/toregister" ><button type="button" class="layui-btn layui-btn-normal layui-btn-radius layui-btn-lg">注册</button></a>
    </div>

  </form>
</div>

<script>
  $(function(){
    <c:if test="${not empty param.timeout}">
      layer.msg('连接超时,请重新登陆!', {
        offset: 0,
        shift: 6
      });
    </c:if>

    if("${error}"){
      $('#submit').attr('value',"${error}").css('background','red');
    }
    if("${registersuccess}"){
    	 layer.msg('注册成功', {
    	        offset: 0,
    	      });
      }
    if("${message}"){
      layer.msg('${message}', {
        offset: 0,
      });
    }

    $('.close').on('click', function(c){
      $('.login-form').fadeOut('slow', function(c){
        $('.login-form').remove();
      });
    });

    $('#username,#password').change(function(){
      $('#submit').attr('value','Login').css('background','#3ea751');
    });
  });

  /**
   * check the login form before user login.
   * @returns {boolean}
   */
  function checkLoginForm(){
    var username = $('#username').val();
    var password = $('#password').val();
    if(isNull(username) && isNull(password)){
      $('#submit').attr('value','请输入账号和密码!!!').css('background','red');
      return false;
    }
    if(isNull(username)){
      $('#submit').attr('value','请输入账号!!!').css('background','red');
      return false;
    }
    if(isNull(password)){
      $('#submit').attr('value','请输入密码!!!').css('background','red');
      return false;
    }
    //if(username != 'Amaya' || password != '123456'){
    //	$('#submit').attr('value','账号或密码错误!!!').css('background','red');
    //	return false;
    //}
    document.getElementById("login-form").submit();
  }

  /**
   * check the param if it's null or '' or undefined
   * @param input
   * @returns {boolean}
   */
  function isNull(input){
    if(input == null || input == '' || input == undefined){
      return true;
    }
    else{
      return false;
    }
  }
</script>
</body>
</html>