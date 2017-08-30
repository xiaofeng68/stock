<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/stock/stock.jsp"%>
<div id="${param.id}"></div>
<script type="text/javascript">
stockyear = '${param.id}';
stock_czb = eval('(${fields.stock_czb==null?"{}":fields.stock_czb})');//财政营业部
yepnope("${ctx}/resources/js/stock/czb.js");
</script>