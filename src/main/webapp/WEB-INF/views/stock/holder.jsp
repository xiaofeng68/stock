<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/stock/stock.jsp"%>
<div id="${param.id}"></div>
<script type="text/javascript">
stockyear = '${param.id}';
stock_holder_state = eval('(${fields.stock_holder_state==null?"{}":fields.stock_holder_state})');//轨道
yepnope("${ctx}/resources/js/stock/holder.js");
</script>