/**
 * 地址联动js
 */

/*
 * 加载下一个地址栏
 */
var parentName = "";
function loadingAddress(obj) {
    //得到a标签的值
    var value = obj.innerHTML;
    //alert(value);
    //获得当前菜单的Div的Id值
    var divId = $(obj).parents(".ads").attr("id");
    //设置父节点名字
    parentName = value;
    showAddress(divId,value);
}
/*
 * 显示下一级菜单
 */
function showAddress(divId,name) {
    //截取divId最后一位并加一
    var id = parseInt(divId.substr(divId.length-1,divId.length)) + 1;
    /*
     *  先设置所有下级菜单的样式为隐藏，再清除ul标签里的li数据
     */
   	for(var i=id; i<=3; i++) {
   		$("#address"+i).hide();
   		$("#address"+i).find("ul").empty();
   	}
   	
    /*
     *  动态输出ul标签里的节点信息
     */
   	if (window.XMLHttpRequest) {
   		// code for IE7+, Firefox, Chrome, Opera, Safari
   		xhr = new XMLHttpRequest();
    }
   	else { 
   		// code for IE6, IE5
   		xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }
   	xhr.open("GET", "/AJAX_Demo/AddressServlet?name="+name, true);
   	xhr.setRequestHeader("Content-Type","text/html;charset=utf-8");
   	xhr.send();
   	var data;
   	xhr.onreadystatechange = function() {
   		if (xhr.readyState==4 && xhr.status==200) {
   			data = xhr.responseText;
//   			alert("data:"+data+".");
   			if(data == "") {
   				return;
   			}
   			var obj = null;
   			try {
   				obj = eval('('+data+')');
   			} catch(Exception) {
   				alert("json parse error!");
   				return;
   			}
   			for(var i=0; i<obj.length;i++) {
   		    	$("#address"+id).find("ul").append("<li><a href=\"javascript:void(0);\" onclick=\"loadingAddress(this)\">"+obj[i]+"</a></li>");
   			}
   		}
     }
   	
   	if(data == "") {
		alert("暂无对应地址详细信息");
		return;
	}
	//显示下一级的菜单栏
	$("#address"+id).show();
	
}