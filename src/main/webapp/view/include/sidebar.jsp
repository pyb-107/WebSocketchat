<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
    <div class="am-offcanvas-bar admin-offcanvas-bar">
        <ul class="am-list admin-sidebar-list">
            <li><a href="${ctx}/index.jsp"><span class="am-icon-commenting"></span> 大厅聊天</a></li>

            <li class="admin-parent">
                <a class="am-cf" data-am-collapse="{target: '#collapse-nav2'}"><span class="am-icon-users"></span> 聊天室 <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
                <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav2">
                    <li><a href="${ctx}/createroom.jsp"><span class="am-icon-plus-square"></span> 创建聊天室</a></li>
                    <li><a href="${ctx}/joinroom.jsp"><span class="am-icon-group"></span> 加入聊天室</a></li>
                    <li><a href="${ctx}/chatroom/jumprecentroom/${userid}"><span class="am-icon-group"></span> 最近加入聊天室</a></li>

                </ul>
            </li>

            <li><a href="${ctx}/information/${userid}" class="am-cf"><span class="am-icon-book"></span> 个人信息<span class="am-icon-star am-fr am-margin-right admin-icon-yellow"></span></a></li>
            <li class="admin-parent">
                <a class="am-cf" data-am-collapse="{target: '#collapse-nav'}"><span class="am-icon-cogs"></span> 设置 <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
                <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav">
                    <li><a href="${ctx}/infosetting/${userid}"><span class="am-icon-group"></span> 个人设置</a></li>
                    <li><a href="${ctx}/system-setting/${userid}"><span class="am-icon-cog"></span> 系统设置</a></li>
                </ul>
            </li>
            <li><a href="${ctx}/log/${userid}?page=1"><span class="am-icon-inbox"></span> 系统日志<span class="am-badge am-badge-secondary am-margin-right am-fr">${lognumber }</span></a></li>
             <li class="admin-parent">
                <a class="am-cf" data-am-collapse="{target: '#collapse-nav1'}"><span class="am-icon-comments"></span> 五子棋<span class="am-icon-angle-right am-fr am-margin-right"></span></a>
                <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav1">
                    <li><a href="${ctx}/AI_five-chess.jsp"><span class="am-icon-cog"></span> 人机对战</a></li>
                    <li><a href="${ctx}/five-chess.jsp"><span class="am-icon-group"></span> 人人对战</a></li>
                    <li><a href="${ctx}/game-log.jsp"><span class="am-icon-book"></span> 比赛记录</a></li>
                </ul>
            </li>
            <li><a href="${ctx}/help"><span class="am-icon-globe"></span> 帮助</a></li>
            <li><a href="${ctx}/about"><span class="am-icon-leaf"></span> 关于</a></li>
            <li><a href="${ctx}/updatelog"><span class="am-icon-reorder"></span> 更新日志</a></li>

            <li><a href="${ctx}/user/logout"><span class="am-icon-sign-out"></span> 注销</a></li>
        </ul>
        <div class="am-panel am-panel-default admin-sidebar-panel">
            <div class="am-panel-bd">
                <p><span class="am-icon-tag"></span> Welcome</p>
                <p>欢迎使用WebChat聊天室~</p>
            </div>
        </div>
    </div>
</div>
<!-- sidebar end -->