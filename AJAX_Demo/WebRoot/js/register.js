/**
 * 注册页面
 */
$(document).ready( function() {
	$("#submit").click( function() {
		var name = $("input").eq(0).val();
		var sex = $("input").eq(1).val();
		var age = $("input").eq(2).val();
		var address = $("input").eq(3).val();
		alert(age);
		
		var jsonText = "{\"name\":\""+name+"\",\"sex\":\""+sex+"\",\"age\":\""+age+"\",\"address\":\""+address+"\"}";
		alert(jsonText);
		
		var xhr = new XMLHttpRequest();
		xhr.open("Post", "/AJAX_Demo/JsonServlet", true);
		xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xhr.send("json="+jsonText);
	});

});