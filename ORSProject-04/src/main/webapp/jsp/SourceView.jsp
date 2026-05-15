<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.SourceCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Source</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<form action="<%=ORSView.SOURCE_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.SourceBean"
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
				Source
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
					<th align="left">Source Code<span style="color: red">*</span></th>
					<td><input type="text" name="sourceCode"
						placeholder="Enter source code"
						value="<%=DataUtility.getStringData(bean.getSourceCode())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("sourceCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Source Name<span style="color: red">*</span></th>
					<td><input type="text" name="sourceName"
						placeholder="Enter source name"
						value="<%=DataUtility.getStringData(bean.getSourceName())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("sourceName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Connection Type<span style="color: red">*</span></th>
					<td><input type="text" name="connectionType"
						placeholder="Enter Connection Type"
						value="<%=DataUtility.getStringData(bean.getConnectionType())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("connectionType", request)%>
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
						value="<%=SourceCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=SourceCtl.OP_CANCEL%>"> <%
 } else {
 %> <input type="submit" name="operation" value="<%=SourceCtl.OP_SAVE%>">
						<input type="submit" name="operation"
						value="<%=SourceCtl.OP_RESET%>"> <%
 }
 %>
					</td>
				</tr>

			</table>
		</div>
	</form>
</body>
</html><%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

</body>
</html>