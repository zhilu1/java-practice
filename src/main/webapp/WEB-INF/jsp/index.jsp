<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="common.jsp" %>
<%@ page import="java.util.Calendar"%>
<%--<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<body>
<%--顶部--%>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">考勤管理系统 名字既长且空</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse ">
            <ul class="nav navbar-nav">
                <li><a href="/"> 首页 </a></li>
                <sec:authorize access="hasRole('ROLE_USEROP')">
                    <li> <!-- 用户权限为ROLE_ADMIN 显示 -->
                        <a href="/authority/getAllUsers"> 管理用户</a>
                    </li>
                </sec:authorize>

                <sec:authorize access="hasRole('ROLE_ROLEOP')">
                    <li>
                        <a href="/role/getAllRoles"> 管理角色</a>
                    </li>
                </sec:authorize>

                <sec:authorize access="hasRole('ROLE_CALENDARVW')">
                    <li>
                        <a href="/calendar/manage"> 工作时间历</a>
                    </li>
                </sec:authorize>

                <sec:authorize access="isAuthenticated()">
                    <li>
                        <a href="/index"> 考勤记录</a>
                    </li>
                </sec:authorize>

                <sec:authorize access="isAuthenticated()">
                    <li>
                        <a href="/logout"> 注销</a>
                    </li>
                </sec:authorize>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<%--选择年月--%>
<sec:authorize access="hasRole('ROLE_SELECTOP')">
    <div>
        <form id="query" action="/selectRecordByIdAndDate" method="post" class="form-horizontal" style="text-align: center;">

            工号:<input type="text" name="staffId" value="${staffId}">

            年份：<select name='year' id="year" onchange="seleday()">
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

            月份：<select name= "month" id="month" onchange="seleday()">
            <% int month = 1;
                for(int i=1; i<=12; i++){
                    String str = String.valueOf(month);
                    if(str.equals(request.getParameter("month"))){
            %>
            <option selected="selected"><%=str%></option>
            <%} else{%>
            <option><%=str%></option>
            <%}  month = month + 1;
            }%>
        </select>
            <input type="hidden" name="now" id="pageno1">
            是否查询该月所有员工的考勤记录<input type="checkbox" name="check" value="true" >
            <input type="submit" value="查询">
        </form>
    </div>
</sec:authorize>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel">修改状态</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="staffId" class="control-label">工号:</label>
                        <input type="text" class="form-control" id="staffId">
                    </div>
                    <div class="form-group">
                        <label for="riqi" class="control-label">日期:</label>
                        <input type="text" class="form-control" id="riqi">
                    </div>
                    <div class="form-group">
                        <label for="statu" class="control-label">状态</label>
                        <%--<textarea class="form-control" id="statu"></textarea>--%>
                        <select name="statu" class="form-control" id="statu"  >
                            <option>正常</option>
                            <option>旷工半天</option>
                            <option>旷工</option>
                            <option>请假</option>
                            <option>出差</option>
                            <option>加班</option>
                            <option>迟到</option>
                            <option>早退</option>
                            <option>迟到早退</option>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="changeStatu()">修改</button>
            </div>
        </div>
    </div>
</div>

<sec:authorize access="!hasRole('ROLE_SELECTOP')">
    <div>
        <form id="query1" action="/selectRecordByDate" method="post" class="form-horizontal" style="text-align: center;">

            年份：<select name='year' id="year1">
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

            月份：<select name= "month" id="month1">
            <% int month = 1;
                for(int i=1; i<=12; i++){
                    String str = String.valueOf(month);
                    if(str.equals(request.getParameter("month"))){
            %>
            <option selected="selected"><%=str%></option>
            <%} else{%>
            <option><%=str%></option>
            <%}  month = month + 1;
            }%>
        </select>

            <input type="hidden" name="now" id="pageno">
            <input type="submit" value="查询">
        </form>
    </div>
</sec:authorize>

<c:forEach var="staff1" items="${staff}">
    <div style="text-align: center;">
        <table>
            <c:set value="${staff1}" var="c" scope="request"/>
            <tr>
                <td>工号:</td>
                <td>${c.username}　　</td>
                <td></td>
                <td>姓名:</td>
                <td>${c.name}　　</td>
                <td></td>
                <td>部门:</td>
                <td>${c.department}　　</td>
            </tr>
        </table>
    </div>
    <div class="table-responsive">
        <table class="table">
            <thead class="Table cell">
            <tr>
                <td>日期</td>
                <c:forEach var="record" items="${record}">
                    <c:if test="${staff1.username==record.staffId}">
                        <td><fmt:formatDate value="${record.riqi}" pattern="dd"/></td>
                    </c:if>
                </c:forEach>>
            </tr>
            </thead>
            <tbody>
            <tr class="success">
                <td>上班时间</td>
                <c:forEach var="record" items="${record}">
                    <c:if test="${staff1.username==record.staffId}">
                        <td>${record.sbtime}</td>
                    </c:if>
                </c:forEach>
            </tr>
            <tr class="success">
                <td>下班时间</td>
                <c:forEach var="record" items="${record}">
                    <c:if test="${staff1.username==record.staffId}">
                        <td>${record.xbtime}</td>
                    </c:if>
                </c:forEach>
            </tr>
            <tr class="success">
                <td>状态</td>
                <c:forEach var="record" items="${record}">
                    <c:if test="${staff1.username==record.staffId}">
                        <td>${record.statu}</td>
                    </c:if>
                </c:forEach>
            </tr>
            <sec:authorize access="hasRole('ROLE_ROLEOP')">
                <tr class="success">
                    <c:forEach var="monthStatus" items="${monthStatus}">
                        <c:if test="${staff1.username==monthStatus.staffId}">
                            <c:choose>
                                <c:when test="${monthStatus.statu == 1}">
                                    <td>已确认</td>
                                </c:when>
                                <c:otherwise>
                                    <td>编辑</td>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <c:forEach var="record" items="${record}">
                            <c:if test="${staff1.username==record.staffId&&staff1.username==monthStatus.staffId}">
                                <c:choose>
                                    <c:when test="${monthStatus.statu == 1}">
                                        <td></td>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${record.statu == null}">
                                                <td></td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>
                                                    <button type="button" class="btn btn-primary" data-toggle="modal"
                                                            data-target="#exampleModal"
                                                            data-whatever="${record.staffId}"
                                                            data-whatever1="${record.riqi}">编辑
                                                    </button>
                                                </td>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </tr>
            </sec:authorize>
            </tbody>
        </table>
    </div>
    <div>
        <sec:authorize access="!hasRole('ROLE_ROLEOP')">
            <c:set value="${monthStatu}" var="ms" scope="request"/>
            <c:choose>
                <c:when test="${ms.statu == 0}">
                    <button type="button" class="btn btn-primary" onclick="changeMonthStatu()">请确认</button>
                </c:when>
                <c:when test="${ms.statu == null}">
                    <button type="button" class="btn btn-primary">暂无记录</button>
                </c:when>
                <c:otherwise>
                    <button type="button" class="btn btn-primary">已确认</button>
                </c:otherwise>
            </c:choose>
        </sec:authorize>
    </div>
</c:forEach>



<%--导入导出--%>
<div>
    <sec:authorize access="hasRole('ROLE_EXPORTOP')">
        <a id="exExpo" href="/export?year=(Calendar.getInstance().get(Calendar.YEAR)-3)&month=1"><button type="button" class="btn btn-primary">导出该月记录</button></a>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_IMPORTOP')">
        <form class="form-horizontal" id="form_table" action="/import" enctype="multipart/form-data" method="post">
            <br/>
            <br/>
            <button type="submit" class="btn btn-primary">导入</button>
            <input class="form-input" type="file" name="filename"></input>
            <%--<div class="alert alert-danger alert-dismissible" style="display: none" role="alert" id="errorMsg">--%>
                <%--<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>--%>

            <%--</div>--%>
        </form>
    </sec:authorize>
</div>

<%--获取年月传入export--%>
<script>
        let year1 = document.getElementById("year");
        let index = year1.selectedIndex;
        let year = year1.options[index].value;
        let month1 = document.getElementById("month");
        let index1 = month1.selectedIndex;
        let month = month1.options[index1].value;
        document.getElementById('exExpo').href="/export?year=" + year +'&month='+month;
</script>
<script>
    function seleday() {
        let year1 = document.getElementById("year");
        let index = year1.selectedIndex;
        let year = year1.options[index].value;
        let month1 = document.getElementById("month");
        let index1 = month1.selectedIndex;
        let month = month1.options[index1].value;
        document.getElementById('exExpo').href="/export?year=" + year +'&month='+month;
    }
</script>

</body>
</html>