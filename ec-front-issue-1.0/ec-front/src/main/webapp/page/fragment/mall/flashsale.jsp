<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
	限时模块使用注意事项:
	1. 在bind='limit'的元素须要加上current和end参数
	2. current为服务器滴当前时间 类型为Long型
	3. end为商品的结束时间 类型为Long型
	4. 未完的工作如： 某商品时间到期后限时模块需重新加载
--%>

    <c:import url="/fragment/501"/>
