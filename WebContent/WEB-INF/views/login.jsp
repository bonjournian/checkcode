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
		var r = Math.random();
		//为了防止浏览器中缓存图片的影响，在url后面添加一个随机参数
		document.getElementById("codeImg").src = "${pageContext.request.contextPath }/DrawImage?"+r;
		document.getElementById("rand").innerHTML=r;
	}
	
	function showPwd() {
		document.getElementById("eye").width=10;
		document.getElementById("pwd").type = "text";
	}
	
	function hiddenPwd() {
		document.getElementById("eye").width=15;
		document.getElementById("pwd").type = "password";
	}
</script>
</head>

<body>
	<form action="${pageContext.request.contextPath}/CheckServlet"
		method="post">
		用户名：<input type="text" name="name"><br>
		密&nbsp;码：<input type="password" id="pwd" name="pwd"/>
		<img src="${pageContext.request.contextPath }/imgs/eye.png" onmousedown="showPwd()" onmouseup="hiddenPwd()" width="15" id="eye"><br/>
		 验证码：<input type="text" name="validateCode" /> 
		 <img alt="看不清，换一张" src="${pageContext.request.contextPath }/DrawImage" onclick="changeImg()" id="codeImg" width="80"/>
		 <a	href="javascript:void(changeImg())" ><font size="2" style="华文楷体">看不清，换一张</font></a> <br />
		 <input type="submit" value="提交"> 
	</form>
	<p id="rand"></p>
</body>
</html>
