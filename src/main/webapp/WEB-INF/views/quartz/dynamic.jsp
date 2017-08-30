<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
stockyear = '${param.id}';
yepnope("${ctx}/resources/js/quartz/dynamic.js");
</script>
