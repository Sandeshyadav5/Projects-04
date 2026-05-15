<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.CommandCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Command</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<form action="<%=ORSView.COMMAND_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.CommandBean"
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
				Command
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
					<th align="left">Command Test<span style="color: red">*</span></th>
					<td><input type="text" name="commandTest"
						placeholder="Enter Command Test"
						value="<%=DataUtility.getStringData(bean.getCommandTest())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("commandTest", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Device Target<span style="color: red">*</span></th>
					<td><input type="text" name="deviceTarget"
						placeholder="Enter Device Target"
						value="<%=DataUtility.getStringData(bean.getDeviceTarget())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("deviceTarget", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Execution Time<span style="color: red">*</span></th>
					<td><input type="text" name="executionTime" id="udatee"
						placeholder="Enter Execution Time"
						value="<%=DataUtility.getStringData(bean.getExecutionTime())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("executionTime", request)%>
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
						value="<%=CommandCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=CommandCtl.OP_CANCEL%>"> <%
 } else {
 %> <input type="submit" name="operation"
						value="<%=CommandCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=CommandCtl.OP_RESET%>"> <%
 }
 %>
					</td>
				</tr>

			</table>
	</form></div>
<%@ include file="Footer.jsp"%>
</body>

</html>