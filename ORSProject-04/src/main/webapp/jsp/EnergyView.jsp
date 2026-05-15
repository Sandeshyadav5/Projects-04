<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.EnergyCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Energy</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<form action="<%=ORSView.ENERGY_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.EnergyBean"
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
				Energy
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
					<th align="left">Energy Code<span style="color: red">*</span></th>
					<td><input type="text" name="energyCode"
						placeholder="Enter Energy Code"
						value="<%=DataUtility.getStringData(bean.getEnergyCode())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("energyCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Device Name<span style="color: red">*</span></th>
					<td><input type="text" name="deviceName"
						placeholder="Enter Device Name"
						value="<%=DataUtility.getStringData(bean.getDeviceName())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("deviceName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Units Consumed<span style="color: red">*</span></th>
					<td><input type="text" name="unitsConsumed" 
						placeholder="Enter Units Consumed"
						value="<%=DataUtility.getStringData(bean.getUnitsConsumed())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("unitsConsumed", request)%>
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
						value="<%=EnergyCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=EnergyCtl.OP_CANCEL%>"> <%
                } else {
                %> <input type="submit" name="operation"
						value="<%=EnergyCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=EnergyCtl.OP_RESET%>"> <%
 }
 %>
					</td>
				</tr>

			</table>
	</form></div>
<%@ include file="Footer.jsp"%>
</body>

</html>