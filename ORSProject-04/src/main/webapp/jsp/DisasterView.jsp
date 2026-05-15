<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.DisasterCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Disaster</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<form action="<%=ORSView.DISASTER_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.DisasterBean"
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
				Disaster
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
					<th align="left">Alert Type<span style="color: red">*</span></th>
					<td><input type="text" name="alertType"
						placeholder="Enter Alert Type"
						value="<%=DataUtility.getStringData(bean.getAlertType())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("alertType", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Location<span style="color: red">*</span></th>
					<td><input type="text" name="location"
						placeholder="Enter Location"
						value="<%=DataUtility.getStringData(bean.getLocation())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("location", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Severty<span style="color: red">*</span></th>
					<td><input type="text" name="severty" 
						placeholder="Enter Severty"
						value="<%=DataUtility.getStringData(bean.getSeverty())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("severty", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">AlertTime<span style="color: red">*</span></th>
					<td><input type="text" name="alertTime" id="udatee"
						placeholder="Enter alertTime"
						value="<%=DataUtility.getStringData(bean.getAlertTime())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("alertTime", request)%>
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
						value="<%=DisasterCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=DisasterCtl.OP_CANCEL%>"> <%
 } else {
 %> <input type="submit" name="operation"
						value="<%=DisasterCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=DisasterCtl.OP_RESET%>"> <%
 }
 %>
					</td>
				</tr>

			</table>
	</form></div>
<%@ include file="Footer.jsp"%>
</body>

</html>