<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/stock/stock.jsp"%>
<div id="${param.id}"></div>
<script type="text/javascript">
stockyear = '${param.id}';
yepnope("${ctx}/resources/css/data-view.css");
yepnope({load:["${ctx}/resources/js/stock/urls.js"]});
</script>