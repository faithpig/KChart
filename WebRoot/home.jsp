<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <title>图片对比</title>
  <!---CSS Files-->
  <link rel="stylesheet" href="css/master.css">
  <link rel="stylesheet" href="css/tables.css">
  <!---jQuery Files-->
  <script src="js/jquery-1.7.1.min.js"></script>
  <script src="js/jquery-ui-1.8.17.min.js"></script>
  <script src="js/styler.js"></script>
  <script src="js/jquery.tipTip.js"></script>
  <script src="js/colorpicker.js"></script>
  <script src="js/sticky.full.js"></script>
  <script src="js/global.js"></script>
  <script src="js/flot/jquery.flot.min.js"></script>
  <script src="js/flot/jquery.flot.resize.min.js"></script>
  <script src="js/jquery.dataTables.min.js"></script>
  <!--[if lt IE 9]>
  <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
  <![endif]-->
  <!--[if lte IE 8]>
  <script language="javascript" type="text/javascript" src="js/flot/excanvas.min.js"></script>
  <![endif]-->
 <%!String[] outlistP;
 	double[] ratelistP;
 %>
 <%
 	if(request.getAttribute("outPicListRank")!=null&&request.getAttribute("rateListRank")!=null){
 		outlistP = (String[])request.getAttribute("outPicListRank");
 		ratelistP =(double[])request.getAttribute("rateListRank");
 	}
 	else{
 		outlistP = new String[]{""};
 		ratelistP = new double[]{0};
 	}
  %>
<script type="text/javascript">
function nextPage(){
	var target = document.getElementById("outPicture");
	var targetRate = document.getElementById("TRate");
	var m = document.getElementById("getRank");
	var i = parseInt(m.innerHTML.toString())+1;
	if(i<0||i>=<%=outlistP.length%>){
		return;
	}
	var piclist = new Array();
	var ratelist = new Array();
<%for(int j=0;j<outlistP.length;j++){%>
	piclist.push('<%=outlistP[j]%>');
<%}%>
<%for(int k=0;k<ratelistP.length;k++){%>
	ratelist.push('<%=ratelistP[k]%>');
<%}%>
	target.innerHTML="<img src='"+piclist[i]+"' style='width:450px;height:280px;'/>";
	targetRate.innerHTML="<h4 style='color:#444'>排名:<span id='getRank'>"+i+"</span>　相似度："+ratelist[i]+"</h4>";
}

function frontPage(){
	var target = document.getElementById("outPicture");
	var targetRate = document.getElementById("TRate");
	var m = document.getElementById("getRank");
	var i = parseInt(m.innerHTML.toString())-1;
	if(i<0||i>=<%=outlistP.length%>){
		return;
	}
	var piclist = new Array();
	var ratelist = new Array();
<%for(int j=0;j<outlistP.length;j++){%>
	piclist.push('<%=outlistP[j]%>');
<%}%>
<%for(int k=0;k<ratelistP.length;k++){%>
	ratelist.push('<%=ratelistP[k]%>');
<%}%>
	target.innerHTML="<img src='"+piclist[i]+"' style='width:450px;height:280px;'/>";
	targetRate.innerHTML="<h4 style='color:#444'>排名:<span id='getRank'>"+i+"</span>　相似度："+ratelist[i]+"</h4>";
}

function goPage(){
	var target = document.getElementById("outPicture");
	var targetRate = document.getElementById("TRate");
	var m = document.getElementById("txt");
	var i = parseInt(m.value);
	if(isNaN(i)||i<0||i>=<%=outlistP.length%>){
		return;
	}
	var piclist = new Array();
	var ratelist = new Array();
<%for(int j=0;j<outlistP.length;j++){%>
	piclist.push('<%=outlistP[j]%>');
<%}%>
<%for(int k=0;k<ratelistP.length;k++){%>
	ratelist.push('<%=ratelistP[k]%>');
<%}%>
	target.innerHTML="<img src='"+piclist[i]+"' style='width:450px;height:280px;'/>";
	targetRate.innerHTML="<h4 style='color:#444'>排名:<span id='getRank'>"+i+"</span>　相似度："+ratelist[i]+"</h4>";
}
</script>

</head>

<body>
<div class="content container_12">

<div class="box grid_6" id="k-chart1">
<div class="box-head"><span class="box-icon-24 fugue-24 system-monitor"></span><h2>目标分析图</h2></div>
<div class="box-content">
  <img src="<%=path %>/${inPic}" style="width:450px;height:280px;"/>
</div>
</div>
<div class="box grid_6" id="k-chart2">
<div class="box-head"><span class="box-icon-24 fugue-24 system-monitor"></span><h2>样本分析图</h2></div>
<div class="box-content" id="outPicture">
<img src="<%=path %>/${outPic}" style="width:450px;height:280px;"/>
</div>
</div>
<div style="text-align:center;" id="TRate"><h4 style="color:#444">排名:<span id="getRank">0</span>　相似度：${rate}</h4></div>
<br/>
<button onclick="frontPage()">上一条</button>&nbsp;<button onclick="nextPage()">下一条</button>&nbsp;<input type="text" id="txt" style="width:30px;"/>&nbsp;<button onclick="goPage()">跳转</button>
&nbsp;跳转范围:0~<%=outlistP.length-1%>
</div>





<script type="text/javascript"> /* SCRIPTS */
  $(function () {
      var sin = [], cos = [];
      for (var i = 0; i < 14; i += 0.5) {
          sin.push([i, Math.sin(i)]);
          cos.push([i, Math.cos(i)]);
      }
      var plot = $.plot($("#flot-demo"),
             [ { data: sin, label: "Green", color: "#71a100"}, { data: cos, label: "Blue", color: "#308eef" } ], {
                 series: {
                     lines: { show: true },
                     points: { show: true }
                 },
                 grid: { hoverable: true },
                 yaxis: { min: -1.2, max: 1.2 }
               });
      var previousPoint = null;
      $("#flot-demo").bind("plothover", function (event, pos, item) {
          if ($("#enablePosition:checked").length > 0) {
              var str = "(" + pos.x.toFixed(2) + ", " + pos.y.toFixed(2) + ")";
              $("#hoverdata").text(str);
          }
      });
  });/* for the flot chart demo */

  $('#example').dataTable( {
      "bJQueryUI": true
  }); /* For the data tables */
</script>
</body>
</html>