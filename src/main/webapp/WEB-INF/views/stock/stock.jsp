<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<script type="text/javascript">
	yepnope("${ctx}/resources/css/stock.css");
	stock_bustype = eval('(${fields.bus_type==null?"{}":fields.bus_type})');//行业分类
	stock_formtype = eval('(${fields.form_type==null?"{}":fields.form_type})');//个股形态
	stock_sosmetype = eval('(${fields.sosme_type==null?"{}":fields.sosme_type})');//价值投资
	stock_newtype = eval('(${fields.new_type==null?"{}":fields.new_type})');//股票新分类
	stock_lcstocktype = eval('(${fields.lcstock_type==null?"{}":fields.lcstock_type})');//龙川44行业
	stocks_type =  eval('(${stocks==null?"{}":stocks})');
	stock_gridstyle = eval('(${fields.stock_grid==null?"{}":fields.stock_grid})');//个股样式
</script>