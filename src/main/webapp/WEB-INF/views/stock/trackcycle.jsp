<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/stock/stock.jsp"%>
<div id="${param.id}"></div>
<script type="text/javascript">
stockyear = '${param.id}';
stock_track = eval('(${fields.stock_track==null?"{}":fields.stock_track})');//轨道
yepnope("${ctx}/resources/swfupload/css/icons.css");
yepnope("${ctx}/resources/swfupload/swfupload.js");
yepnope("${ctx}/resources/swfupload/uploaderPanel.js");
yepnope("${ctx}/resources/js/stock/trackcycle.js");
</script>