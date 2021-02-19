<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>WebChat | 更新日志</title>
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
    <div class="admin-content">
        <div class="am-cf am-padding">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">更新日志</strong> / <small>updatelog</small></div>
        </div>
        <div class="am-tabs am-margin" data-am-tabs>
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">时间线</a></li>
            </ul>
            <div class="am-tabs-bd">
                <div class="am-tab-panel am-fade am-in am-active" id="tab1">
                    <ul class="layui-timeline">

                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <h3 class="layui-timeline-title">2月15日</h3>
                                <p>
                                    完成了头像上传功能，在用户表中存储了头像文件名称，
                                    <br>完成了聊天框中的头像显示功能
                                    <br>通过id请求头像的流数据进行显示
                                </p>
                            </div>
                        </li>

                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <h3 class="layui-timeline-title">2月14日</h3>
                                <p>
                                    完成了好友列表中最后一个功能：好友重复添加失败的提示
                                    <br>彻底完成了好友功能
                                </p>
                            </div>
                        </li>
                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <h3 class="layui-timeline-title">2月12日</h3>
                                <p>
                                    完成了展示好友列表功能
                                </p>
                            </div>
                        </li>
                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <h3 class="layui-timeline-title">2月10日</h3>
                                <p>
                                    完成好友的数据库结构设计
                                    <br>完成了好友插入功能
                                </p>
                            </div>
                        </li>
                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <div class="layui-timeline-title">过去</div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- content end -->
    </div>
    <a href="#" class="am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}">
        <span class="am-icon-btn am-icon-th-list"></span>
    </a>
    <jsp:include page="view/include/footer.jsp"/>
    <script>
//        $(function(){
//            $(window).resize(function(){
//                var height = $(window).height();
//                console.log(height);
//                $('html body').css('height',height);
//            });
//        })
    </script>
</body>
</html>
