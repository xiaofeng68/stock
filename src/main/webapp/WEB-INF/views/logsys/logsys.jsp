<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>
<script type="text/javascript">
stockyear = '${param.id}';
log_levels = eval('(${fields.log_levels==null?"{}":fields.log_levels})');//日志级别

yepnope("${ctx}/resources/js/logsys/logsys.js");
</script>