<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.GenderCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Gender</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<form action="<%=ORSView.GENDER_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.GenderBean"
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
				Gender
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
					<th align="left">Gender Code<span style="color: red">*</span></th>
					<td><input type="text" name="genderCode"
						placeholder="Enter Gender Code"
						value="<%=DataUtility.getStringData(bean.getGenderCode())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("genderCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Gender Type<span style="color: red">*</span></th>
					<td><input type="text" name="genderType"
						placeholder="Enter Gender Type"
						value="<%=DataUtility.getStringData(bean.getGenderType())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("genderType", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Description<span style="color: red">*</span></th>
					<td><input type="text" name="description" 
						placeholder="Enter Description"
						value="<%=DataUtility.getStringData(bean.getDescription())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("description", request)%>
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
						value="<%=GenderCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=GenderCtl.OP_CANCEL%>"> <%
 } else {
 %> <input type="submit" name="operation"
						value="<%=GenderCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=GenderCtl.OP_RESET%>"> <%
 }
 %>
					</td>
				</tr>

			</table>
	</form></div>
<%@ include file="Footer.jsp"%>
</body>

</html>