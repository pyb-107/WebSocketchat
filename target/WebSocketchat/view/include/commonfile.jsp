<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${ctx}/static/plugins/amaze/css/amazeui.min.css">
<link rel="stylesheet" href="${ctx}/static/plugins/amaze/css/admin.css">
<link rel="stylesheet" href="${ctx}/static/plugins/contextjs/css/context.standalone.css">
<link rel="stylesheet" href="${ctx}/static/plugins/layui/css/layui.css">

<script src="${ctx}/static/plugins/jquery/jquery-2.1.4.min.js"></script>
<script src="${ctx}/static/plugins/amaze/js/amazeui.min.js"></script>
<script src="${ctx}/static/plugins/amaze/js/app.js"></script>
<script src="${ctx}/static/plugins/layer/layer.js"></script>
<script src="${ctx}/static/plugins/laypage/laypage.js"></script>
<script src="${ctx}/static/plugins/contextjs/js/context.js"></script>
<script src="${ctx}/static/plugins/layui/layui.js"></script>
<script src="https://s3.pstatp.com/cdn/expire-1-M/jquery/3.3.1/jquery.min.js"></script>

<script>
    var path = '${ctx}';
    var userid = '${userid}'
</script>