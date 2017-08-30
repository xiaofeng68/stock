<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/stock/stock.jsp"%>
<div id="${param.id}"></div>
<script type="text/javascript">
stockyear = '${param.id}';
stock_track = eval('(${fields.stock_track==null?"{}":fields.stock_track})');//轨道
stock_track_des = eval('(${fields.stock_track_des==null?"{}":fields.stock_track_des})');//轨道突破
yepnope("${ctx}/resources/js/stock/track.js");
</script>