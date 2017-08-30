<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/stock/stock.jsp"%>
<div id="${param.id}"></div>
<script type="text/javascript">
stockyear = '${param.id}';
yepnope({load:["${ctx}/resources/js/stock/warn.js","${ctx}/resources/js/stock/statisticswarn.js"]});
</script>