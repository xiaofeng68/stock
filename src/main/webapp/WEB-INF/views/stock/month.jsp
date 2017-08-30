<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/stock/stock.jsp"%>
<div id="${param.id}"></div>
<script type="text/javascript">
stockmonth = '${param.id}';
yepnope("${ctx}/resources/js/stock/month.js");
</script>