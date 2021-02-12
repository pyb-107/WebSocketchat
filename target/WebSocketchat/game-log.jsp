<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>WebChat | 比赛记录</title>
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
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">比赛记录</strong> / <small>log</small></div>
        </div>
        <div>
            <table id="gamelog"></table>
        </div>
        <!-- content end -->
    </div>
    <a href="#" class="am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}">
        <span class="am-icon-btn am-icon-th-list"></span>
    </a>
    <jsp:include page="view/include/footer.jsp"/>
    <script>
        layui.use('table',function () {
            var table = layui.table;
            table.render({
                elem:'#gamelog'
                ,url:'${pageContext.request.contextPath}/game/getrecord/${userid}'
                ,cols:[[
                    {field: 'id', title: 'ID', width:150, sort: true, fixed: 'left'}
                    ,{field: 'winner', title: '胜者', width:160}
                    ,{field: 'loser', title: '败者', width:160}
                    ,{field: 'datetime', title: '比赛日期', width:250}

                ]]


            });

        });
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
