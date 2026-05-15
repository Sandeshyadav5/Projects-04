<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.BatchCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Batch</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<form action="<%=ORSView.BATCH_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.BatchBean"
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
				Batch
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
					<th align="left">Batch Code<span style="color: red">*</span></th>
					<td><input type="text" name="batchCode"
						placeholder="enter batch code"
						value="<%=DataUtility.getStringData(bean.getBatchCode())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("batchCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Batch Name<span style="color: red">*</span></th>
					<td><input type="text" name="batchName"
						placeholder="enter batch name"
						value="<%=DataUtility.getStringData(bean.getBatchName())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("batchName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Total Records<span style="color: red">*</span></th>
					<td><input type="text" name="totalRecords"
						placeholder="enter total records"
						value="<%=DataUtility.getStringData(bean.getTotalRecords())%>">
					</td>
				</tr>

				<tr>
					<th align="left">Status<span style="color: red">*</span></th>
					<td><input type="text" name="status"
						placeholder="enter status"
						value="<%=DataUtility.getStringData(bean.getStatus())%>">
					</td>
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
						value="<%=BatchCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=BatchCtl.OP_CANCEL%>"> <%
 } else {
 %> <input type="submit" name="operation" value="<%=BatchCtl.OP_SAVE%>">
						<input type="submit" name="operation"
						value="<%=BatchCtl.OP_RESET%>"> <%
 }
 %>
					</td>
				</tr>

			</table>
		</div>
	</form>
</body>
</html>