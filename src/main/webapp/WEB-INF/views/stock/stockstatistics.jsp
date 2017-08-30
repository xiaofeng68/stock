<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/stock/stock.jsp"%>
<div id="${param.id}"></div>
<script type="text/javascript">
stockyear = '${param.id}';
stock_czb = eval('(${fields.stock_czb==null?"{}":fields.stock_czb})');//财政营业部

yepnope({load:["${ctx}/resources/amcharts/style.css"]});
yepnope({load:["${ctx}/resources/amcharts/amcharts.js"]});
yepnope({load:["${ctx}/resources/amcharts/serial.js"]});
yepnope({load:["${ctx}/resources/amcharts/amstock.js"]});
yepnope({load:["${ctx}/resources/js/stock/stockstatistics.js"]});
</script>