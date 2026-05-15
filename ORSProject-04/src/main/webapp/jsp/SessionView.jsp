<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.SessionCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Session</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<form action="<%=ORSView.SESSION_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.SessionBean"
			scope="request" />

		<div align="center">

			<h1 style="color: navy">
				<%
				if (bean != null && bean.getId() > 0) {
				%>
				Update
				<%
				} else {
				%>
				Add
				<%
				}
				%>
				Session
			</h1>

			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>

			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<input type="hidden" name="id"
				value="<%=DataUtility.getStringData(bean.getId())%>">

			<table>

				<tr>
					<th align="left">Session Code<span style="color: red">*</span></th>
					<td><input type="text" name="sessionCode"
						placeholder="Enter session code"
						value="<%=DataUtility.getStringData(bean.getSessionCode())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("sessionCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">User Name<span style="color: red">*</span></th>
					<td><input type="text" name="userName"
						placeholder="Enter User name"
						value="<%=DataUtility.getStringData(bean.getUserName())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("userName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Login Time<span style="color: red">*</span></th>
					<td><input type="text" name="loginTime" id="udatee"
						placeholder="Enter Login Time"
						value="<%=DataUtility.getStringData(bean.getLoginTime())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("loginTime", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Status<span style="color: red">*</span></th>
					<td><input type="text" name="status"
						placeholder="Enter status"
						value="<%=DataUtility.getStringData(bean.getStatus())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("status", request)%>
					</font></td>
				</tr>

				<tr>
					<th></th>
					<td></td>
				</tr>

				<tr>
					<th></th>
					<td colspan="2">
						<%
						if (bean != null && bean.getId() > 0) {
						%> <input type="submit" name="operation"
						value="<%=SessionCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=SessionCtl.OP_CANCEL%>"> <%
 } else {
 %> <input type="submit" name="operation"
						value="<%=SessionCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=SessionCtl.OP_RESET%>"> <%
 }
 %>
					</td>
				</tr>

			</table>
		</div>
	</form>
</body>
</html><%@ page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

</body>
</html>