<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>您要访问的页面不存在</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="text/css">
.STYLE1 {
	color: #0000FF;
	font-weight: bold;
	font-size: 25px font-weight:  bold;
}
</style>
<script type="text/javascript">
	function getRootWin() {
		var win = window;
		while (win != win.parent) {
			win = win.parent;
		}
		return win;
	}
</script>
</head>
<body style="margin-top: 100px;">
	<div style="margin: 0 auto; width: 650px;">
		<p>
			<h3 class="STYLE1">${errorStr }</h3>
		</p>
	</div>
</body>
</html>
