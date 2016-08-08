<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<script type="text/javascript">
	//刷新验证码
	function changeImg() {
		//为了防止浏览器中缓存图片的影响，在url后面添加一个随机参数
		document.getElementById("validateCodeImg").src = "${pageContext.request.contextPath}/DrawImage?"
				+ Math.random();
	}
</script>
</head>

<body>
	<form action="${pageContext.request.contextPath}/CheckServlet"
		method="post">
		用户名：<input type="text" name="name"><br> 验证码：<input
			type="text" name="validateCode" /> <img alt="验证码看不清，换一张"
			src="${pageContext.request.contextPath}/DrawImage"
			id="validateCodeImg" onclick="changeImg()"> <a
			href="javascript:void(0)" onclick="changeImg()">看不清，换一张</a> <br /> <input
			type="submit" value="提交"> 
	</form>
</body>
</html>
