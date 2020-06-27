<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
  <title>开心农场|管理系统预定</title>
  <meta name="Author" content="开心农场">
  <meta name="Keywords" content="开心农场管理系统">
  <meta name="Description" content="开心农场管理系统">
    <link rel="stylesheet" href="../../resources/home/css/index.css">
    <link rel="stylesheet" href="../../resources/home/css/order.css">
    <link rel="stylesheet" href="../../resources/home/css/jquery-ui.min.css">
</head>
<body>
<!--- 页头--->
<div id="c_header"></div>
<!----主体-->
<div id="section">
    <!--土地租借界面-->
    <div class="hotel_inf_w">

        <div class="hotel_roominfobox">
            <a href="#"><img src="${roomType.photo }" alt=""/></a>
            <div class="info">
            <h2>${roomType.name }</h2>
            
            </div>
            <ul class="hotel_detail">
            <li><span>预定数:</span>${roomType.bookNum }</li>
            <li><span>价格:</span>${roomType.price }</li>
            <li><span>土地面积:</span>${roomType.bedNum }</li>
            <li><span>可租土地数:</span>${roomType.liveNum }人</li>
            <li><span>其他:</span>${roomType.remark }</li>
            </ul>
        </div>
        <div class="jump">
         
            <a href="../index">更多土地</a>
        </div>
    </div>
    <!--预定信息-->
    <div class="book_info">
        <form id="order_info">
            <ul>
                <input type="hidden" name="rid" value=""/>
                <li>
                    <h3>租借信息</h3>

                    <div class="info_group">
                        <label>租借时间</label><input type="text" name="arriveDate" id="arriveDate" class="datepicker"/>
						<label>预计退租时间</label><input type="text" name="leaveDate" id="leaveDate" class="datepicker"/>
                    </div>
                    
                    <div class="info_group">
                        <label>租借总计</label><span class="total">￥${roomType.price }</span>
                        <input type="hidden" value="0"/>
                    </div>
                </li>
                <li>
                    <h3>租借信息</h3>

                    <div class="info_group">
                        <label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</label><input type="text" name="name" id="name" value="${account.name}"/><span class="msg"></span>
                    </div>
                    <div class="info_group">
                        <label>电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话</label><input type="text" maxlength="11" name="mobile" id="mobile" value="${account.mobile}"/><span class="msg"></span>
                    </div>
					<div class="info_group">
                        <label>身份证号</label><input type="text" name="idCard" id="idCard" value="${account.idCard}"/><span class="msg"></span>
                    </div>
                    <div class="info_group">
                        <label for="massage">留&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;言</label>
                         <textarea id="remark" name="remark" style="width:200px;"></textarea>
                    </div>

                </li>
                <li>
                    <div id="btn_booking">确认预定</div>

                </li>
            </ul>
        </form>
    </div>

    <div class="malog">
        <div class="message">
            <p class="msgs"></p>
            <p>您可以在 <a href="index#order">我的订单</a>查看详情</p>
            <p>系统会在<span class="num">5</span>秒后跳转回 <a href="../index">菜单列表</a></p>
        </div>
    </div>

</div>
<!----页底--->
<div id="c_footer"></div>
<script src="../../resources/home/js/jquery-1.11.3.js"></script>
<script src="../../resources/home/js/jquery-ui.min.js"></script>
</body>
<script>
  $(function() {
    $(".datepicker").datepicker({"dateFormat":"yy-mm-dd"});
    $("#btn_booking").click(function(){
    	var arriveDate = $("#arriveDate").val();
    	var leaveDate = $("#leaveDate").val();
    	if(arriveDate == '' || leaveDate == ''){
    		alert('请选择时间!');
    		return;
    	}
    	var name = $("#name").val();
    	if(name == ''){
    		$("#name").next("span.msg").text('请填写租借人!');
    		return;
    	}
    	$("#name").next("span.msg").text('');
    	var mobile = $("#mobile").val();
    	if(mobile == ''){
    		$("#mobile").next("span.msg").text('请填写手机号!');
    		return;
    	}
    	$("#mobile").next("span.msg").text('');
    	var idCard = $("#idCard").val();
    	if(idCard == ''){
    		$("#idCard").next("span.msg").text('请填写身份证号!');
    		return;
    	}
    	$("#idCard").next("span.msg").text('');
    	var remark = $("#remark").val();
    	$.ajax({
    		url:'book_order',
    		type:'post',
    		dataType:'json',
    		data:{roomTypeId:'${roomType.id }',name:name,mobile:mobile,idCard:idCard,remark:remark,arriveDate:arriveDate,leaveDate:leaveDate},
    		success:function(data){
    			if(data.type == 'success'){
    				$(".malog").show();
    				setTimeout(function(){
    					window.location.href = 'index';
    				},1000)
    			}else{
    				alert(data.msg);
    			}
    		}
    	});
    })
  });
  </script>
</html>