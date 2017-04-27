<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>  
<body>
<h2>Hello World!!!</h2>
</body>
<script type="text/javascript">
$(function(){
$.ajax({
		url:createUrl("index/getUser"),
		success:function(user){
			alert(user);
		}
	})
})
</script>
</html>