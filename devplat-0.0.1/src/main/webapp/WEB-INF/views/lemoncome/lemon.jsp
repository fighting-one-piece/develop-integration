<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<body>
<h5 align="center">&nbsp;</h5>
<div align="left" style="left: 40%;position: absolute;font-size: large;">
	<p><a href="<%=basePath %>/checknameid">1、姓名-身份证校验</a></p>
	<p><a href="<%=basePath %>/nameIdCardAccountVerify">2、姓名-身份证-银行卡校验</a></p>
	<p><a href="<%=basePath %>/accountVerifyM">2、M姓名-身份证-银行卡校验</a></p>
	<p><a href="<%=basePath %>/namephonection">3、姓名-身份证-手机号校验</a></p>
	<p><a href="<%=basePath %>/nameidphonebankcat">4、姓名-身份证-手机号-银行卡校验</a></p>
	<p><a href="<%=basePath %>/nameIDPhoto3Dcat">5、姓名-身份证-照片三维校验</a></p>
	<p><a href="<%=basePath %>/nameidcat">6、姓名-银行卡校验</a></p>
	<p><a href="<%=basePath %>/education">7、学历查询</a></p>
	<p><a href="<%=basePath %>/idphoto">8、身份证照片查询</a></p>
	<p><a href="<%=basePath %>/lemonphone">9、手机号在网时长查询</a></p>
	<p><a href="<%=basePath %>/lemonphonetime">10、手机号当前状态查</a></p>
	<p><a href="<%=basePath %>/lemonphonelines">11、手机号绑定银行卡出入账查询</a></p>
	<p><a href="<%=basePath %>/lemonphoneinfo">12、手机号绑定银行卡还款情况查询</a></p>
	<p><a href="<%=basePath %>/lemonphoneactive">13、手机号绑定银行卡账动信息查询</a></p>
	<p><a href="<%=basePath %>/phoneBankCardinfo">14、手机号绑定银行卡信息查询</a></p>
	<p><a href="<%=basePath %>/NameQueryinfo">15、手机号所属运营商查询</a></p>
	<p><a href="<%=basePath %>/verify">17、逾期短信信息查询</a></p>
	<%-- <p><a href="<%=basePath %>">18、银行卡消费信息查询</a></p> --%>
	<p><a href="<%=basePath %>/Fraudulentcat">19、欺诈案件信息查询 </a></p>
	<p><a href="<%=basePath %>/suspiciouscat">20、可疑人员查询 </a></p>
	<p><a href="<%=basePath %>/Blacklistcat">21、柠檬黑名单</a></p>
	<p><a href="<%=basePath %>/dataApply">22、申请数据查询</a></p>
	<!-- 开  -->
	<p><a href="<%=basePath %>/baiduQuery">23、百度金融消费评价查询</a></p>
	<p><a href="<%=basePath %>/addressProve">24、 地址验证</a></p>
	<p><a href="<%=basePath %>/phoneTagQuery">25、 手机号标签查询</a></p>
	<p><a href="<%=basePath %>/CourtEnforce">26、 法院被执行人记录</a></p>
	<p><a href="<%=basePath %>/P2POverdue">27、 银行、P2P逾期记录</a></p>
	<p><a href="<%=basePath %>/repeatedlyInquireA">28、多次申请记录查询 A</a></p>
	<p><a href="<%=basePath %>/repeatedlyInquire">29、多次申请记录查询 B</a></p>
	<p><a href="<%=basePath %>/lemonGamblingDrug">30、赌博吸毒名单</a></p>
	<p><a href="<%=basePath %>/internetNegative">31、网络公开黑名单</a></p>
	<!-- 关  -->
	<p><a href="<%=basePath %>/blacklist">32、合作机构共享黑名单</a></p>	
	<p><a href="<%=basePath %>/overdue">33、网络公开逾期信息</a></p>	
	<p><a href="<%=basePath %>/plat">34、多次申请记录查询C</a></p>	
	<p><a href="<%=basePath %>/blacklistLoanPlatform">35、数信网黑名单</a></p>
</div>
</body>
</html>
