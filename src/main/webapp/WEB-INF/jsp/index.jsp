<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="common.jsp" %>
<%@ page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<body>
<div>
    <a href="/"target="leftFrame"> 首页</a>

    <sec:authorize access="hasRole('ROLE_USEROP')">
        <a href="/authority/getAllUsers"target="leftFrame"> 管理用户</a>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_ROLEOP')">
        <a href="/role/getAllRoles"target="leftFrame"> 管理角色</a>
    </sec:authorize>

    <a href="/index" target="leftFrame"> 考勤记录</a>


    <a href="/logout"target="leftFrame"> 注销</a>

    </ul>
</div>
<div>
    <form id="query" action="/selectRecordByIdAndDate" method="post" class="form-horizontal" style="text-align: center;">

        <%--<security:authentication property="principal.username"></security:authentication>--%>

        <sec:authorize access="hasRole('ROLE_ROLEOP')">
        工号:<input type="text" name="staffId" value="${staffId}">
        </sec:authorize>

        年份：<select name='year'>
        <% Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR)-3;
            for(int i=1; i<=4; i++){
                String str = String.valueOf(year);
                if(str.equals(request.getParameter("year"))){
        %>
        <option selected="selected"><%=str%></option>
        <%} else{%>
        <option><%=str%></option>
        <%}  year = year + 1;
        }%>
        </select>

        月份：<select name= "month">
        <option >1</option>
        <option >2</option>
        <option >3</option>
        <option >4</option>
        <option >5</option>
        <option >6</option>
        <option >7</option>
        <option >8</option>
        <option >9</option>
        <option >10</option>
        <option >11</option>
        <option >12</option>
    </select>

        <input type="hidden" name="now" id="pageno">
        <input type="submit" value="查询">
    </form>
</div>
<div style="text-align: center;">
    <table>
        <c:set value="${selectedStaff}" var="c" scope="request"/>
            <tr>
                <td>工号:</td>
                <td>${c.username}　　</td>
                <td></td>
                <td>姓名:</td>
                <td>${c.name}　　</td>
                <td></td>
                <td>部门:</td>
                <td>${c.department}</td>
            </tr>
    </table>
</div>
<div class="table-responsive">
    <table class="table">
        <thead class="Table cell">
        <td>日期</td>
        <td>员工号</td>
        <td>上班时间</td>
        <td>下班时间</td>
        </thead>
        <tbody>
        <c:forEach var="record" items="${record}">
            <tr class="success">
                <td>${record.riqi}</td>
                <td>${record.staffId}</td>
                <td>${record.sbtime}</td>
                <td>${record.xbtime}</td>
            </tr>
        </c:forEach>>
        </tbody>
    </table>
</div>
<div>
    <sec:authorize access="hasRole('ROLE_EXPORTOP')">
        <a href="/export"><button type="button" class="btn btn-primary">导出</button></a>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_IMPORTOP')">
        <form class="form-horizontal" id="form_table" action="/import" enctype="multipart/form-data" method="post">
            <br/>
            <br/>
            <script>
                function Import(){
                    alert("导入成功");
                }
            </script>
            <button type="submit" class="btn btn-primary"onclick="Import()">导入</button>
            <input class="form-input" type="file" name="filename"></input>
        </form>
    </sec:authorize>
</div>
</body>
</html>
