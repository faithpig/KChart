<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title>股票行情图对比</title>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <meta name="description" content="Animated Buttons with CSS3" />
        <meta name="keywords" content="css3, transitions, button, animation, hover, effect, icon, call-to-action, active" />
        <meta name="author" content="Codrops" />
        <link rel="stylesheet" type="text/css" href="css/demo.css" />
        <link rel="stylesheet" type="text/css" href="css/style6.css" />
        <link rel="stylesheet" href="css/tinybox.css" />
        <link type="text/css" rel="stylesheet" media="screen" href="css/input.css">
        <link type="text/css" rel="stylesheet" media="screen" href="css/subbutton.css">
		<script src="js/tinybox.js" type="text/javascript"></script>
        
<script type="text/javascript">
function subform(){
	var input1 = document.getElementById("input1").value;
	var input2 = document.getElementById("input2").value;
	var input3 = document.getElementById("input3").value;
	TINY.box.show({iframe:'<%=path%>/Home_welcome?inPic=${inPic}&method=ABC&aw.weight1='+input1+'&aw.weight2='+input2+'&aw.weight3='+input3,boxid:'frameless',width:486,height:750,fixed:false,maskopacity:40});
}
function good(){
	var suc = document.getElementById("goodspan");
	suc.innerHTML = "正在导入中....";
}
function subform(){
	var input1 = document.getElementById("input1").value;
	var input2 = document.getElementById("input2").value;
	var input3 = document.getElementById("input3").value;
	var weight1 = document.getElementById("input1");
	var weight2 = document.getElementById("input2");
	var weight3 = document.getElementById("input3");
	var trick = document.getElementById("trick");
	reg=/^\d+(\.\d+)?$/;
	if(reg.test(input1)&&reg.test(input2)&&reg.test(input3)&&(parseFloat(input1)+parseFloat(input2)+parseFloat(input3))!=0)
	{TINY.box.show({iframe:'<%=path%>/Home_welcome?inPic=${inPic}&method=ABC&aw.weight1='+input1+'&aw.weight2='+input2+'&aw.weight3='+input3,boxid:'frameless',width:486,height:800,fixed:false,maskopacity:40});}
	else{
		trick.innerHTML = "<label for='trick' style='color:#f00;'>请输入正确的权重值！</label>";
	}
}
</script>


    </head>

    <body style="height:531px;">
		<div id="floater" style="height:50%; margin:100px auto 40px auto;">
        
		<div class="container fix">
            <h1 class="threed">K线图对比系统 <span style="color:#f00;font-size:20px;" id="goodspan"><%if("true".equals(request.getAttribute("flag"))){%>导入成功！<%}else{ %>请导入样本<%}%></span><span>点击按钮使用相应功能</span></h1>
            <div class="content">
				<div class="button-wrapper">
                	<a href="<%=path%>/Home_welcome?inPic=${inPic}&method=init" class="a-btn" onclick="good()">
                        <span></span>
						<span>导入样本文件</span>
                        <span></span>
					</a>
					<a href="javascript:TINY.box.show({iframe:'<%=path%>/Home_welcome?inPic=${inPic}&method=A',boxid:'frameless',width:486,height:800,fixed:false,maskopacity:40})" class="a-btn">
                        <span></span>
						<span>算法A</span>
                        <span></span>
					</a>
					<a href="javascript:TINY.box.show({iframe:'<%=path%>/Home_welcome?inPic=${inPic}&method=B',boxid:'frameless',width:486,height:800,fixed:false,maskopacity:40})" class="a-btn">
                        <span></span>
						<span>算法B</span>		
                        <span></span>
					</a>
					<a href="javascript:TINY.box.show({iframe:'<%=path%>/Home_welcome?inPic=${inPic}&method=C',boxid:'frameless',width:486,height:800,fixed:false,maskopacity:40})" class="a-btn">
                        <span></span>
						<span>算法C</span>	
                        <span></span>
					</a>
				</div>
                
            </div>
        </div>

</div>
  <div align="center">   
  <form class="form" action="<%= path %>/Home_welcome" method="post" id="form2"> 
   <p>
   <label for=""></label> 
   </p>
    <p class="name"> 
        <input type="text" name="name" id="input1"/> 
        <label for="name">算法A权重</label> 
    </p> 
   
    <p class="email"> 
        <input type="text" name="email" id="input2" /> 
        <label for="email">算法B权重</label> 
    </p> 
   
    <p class="web"> 
        <input type="text" name="web" id="input3" /> 
        <label for="web">算法C权重</label> 
    </p>
    <p id="trick"> 
        
    </p>  
    <p> 
        <a href="javascript:subform()" class="button">加权最优解</a>
    </p>  
   </form>
  </div>
  ${win}
    </body>

</html>