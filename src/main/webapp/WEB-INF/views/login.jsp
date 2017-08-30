<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>好运来数据中心平台</title>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<script type="text/javascript"
	src="${ctx}/resources/loader/yepnope.min.js"></script>
<%@ include file="/WEB-INF/views/commons/yepnope.jsp"%>
<script type="text/javascript">
	yepnope("${ctx}/resources/js/login.js");
</script>
<style type="text/css">
#titel_img {
	width: 692px;
	height: 494px;
}

#log_image {
	z-index: 0;
	position: absolute;
	left: 50%;
	top: 50%;
	margin-left: -346px;
	margin-top: -247px;
}

#text_box {
	position: absolute;
	top: 65px;
	left: 40px;
	z-index: 1;
}

#titel_text {
	position: absolute;
}

.input01 {
	width: 200px;
	height: 26px;
	background-color: #FFFFFF;
	border: solid 1px #BEBEBE;
	line-height: 26px
}

.login-btn INPUT {
	BORDER-BOTTOM: 1px solid;
	BORDER-LEFT: 1px solid;
	PADDING-BOTTOM: 4px;
	LINE-HEIGHT: 20px;
	BACKGROUND-COLOR: #f56c06;
	MARGIN: 0px auto;
	PADDING-LEFT: 12px;
	WIDTH: 80px;
	PADDING-RIGHT: 12px;
	DISPLAY: block;
	BACKGROUND-REPEAT: repeat-x;
	COLOR: #ffffff;
	FONT-SIZE: 13px;
	BORDER-TOP: 1px solid;
	CURSOR: pointer;
	BORDER-RIGHT: 1px solid;
	PADDING-TOP: 4px;
	border-radius: 4px 4px 4px 4px;
	box-shadow: 0 1px 0 rgba(255, 255, 255, 0.2) inset, 0 1px 2px
		rgba(0, 0, 0, 0.05);
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25)
}

.login-btn INPUT:hover {
	BACKGROUND-COLOR: #fa7716
}
.label-size{
	color:#FFF;
	font-size:12px;
}
.label-12size{
	color:#FFF;
	font-size:12px;
}
.body{
}
</style>
</head>
<body>
	<form action="" method="post">
		<div id="log_image">
			<div id="titel_text">
				<img id=titel_img src="${ctx}/resources/images/login_bg.gif">
			</div>
			<img id="log" src="${ctx}/resources/images/header.png"
				style="margin-left: 280px; margin-top: 50px;">
				<div id="text_box" style="margin-left: 320px; margin-top: 80px;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td style="font-weight: bold" height="30" colspan="2" align="left">
							<span class="label-12size">　登录名：</span>
							<input id="account" type="text" class="input01" />
							</td>
						</tr>
						<tr>
							<td style="font-weight: bold" height="6" colspan="2"></td>
						</tr>
						<tr>
							<td style="font-weight: bold" height="30" colspan="2" align="left">
							<span  class="label-12size">登录密码：</span>
							<input id="password" type="password" class="input01" />
							</td>
						</tr>
						<tr>
							<td style="font-weight: bold" height="6" colspan="2"></td>
						</tr>
						<tr>
							<td colspan="2">
							<input type="checkbox" id="rememberAccount" autocomplete="off" onclick="login.autoLoginFun(true)" name="rememberAccount" class=" x-form-checkbox x-form-field"/> 
							<label for="rememberAccount" class="label-size" onclick="login.autoLoginFun(true)" >记住账号</label>
							<input type="checkbox" id="rememberPassword" autocomplete="off" onclick="login.autoLoginFun(true)" name="rememberPassword" class=" x-form-checkbox x-form-field"/>
							<label for="rememberPassword" class="label-size" onclick="login.autoLoginFun(true)" >记住密码</label> 
							<input type="checkbox" id="autoLogin" autocomplete="off" onclick="login.autoLoginFun()" name="autoLogin" class=" x-form-checkbox x-form-field"/>
							<label for="autoLogin" class="label-size" onclick="login.autoLoginFun()">自动登录</label>
							</td>
							<tr>
								<tr>
									<td style="font-weight: bold" height="6" colspan="2"></td>
								</tr>
								<tr>
									<td style="text-align: center;">
										<DIV class="login-btn">
											<INPUT id="login-btn" value="登 录" type="button"
												onclick="login.loginFun()">
										</DIV>
									</td>
									<td>
										<DIV class="login-btn">
											<INPUT value="取 消" type="reset">
										</DIV>
									</td>
								</tr>
								<tr>
									<td style="font-weight: bold" height="12" colspan="2"></td>
								</tr>
								<tr>
									<td colspan="2" style="text-align: right;">
										<a href="javascript:login.resetPassword();" class="label-size">忘记密码？</a>
									</td>
								</tr>
					</table>
				</div>
		</div>
	</form>
</body>
</html>
