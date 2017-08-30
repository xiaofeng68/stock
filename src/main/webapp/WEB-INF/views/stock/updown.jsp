<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/stock/stock.jsp"%>
<div id="${param.id}"></div>
<script type="text/javascript">
stockyear = '${param.id}';
stock_updown = eval('(${fields.stock_updown==null?"{}":fields.stock_updown})');//涨幅
yepnope("${ctx}/resources/js/stock/updown.js");
</script>