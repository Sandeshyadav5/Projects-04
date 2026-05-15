<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.JobCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add JOB</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<form action="<%=ORSView.JOB_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.JobBean"
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
				JOB
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
					<th align="left">Job Code<span style="color: red">*</span></th>
					<td><input type="text" name="jobCode"
						value="<%=DataUtility.getStringData(bean.getJobCode())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("jobCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Job Name<span style="color: red">*</span></th>
					<td><input type="text" name="jobName"
						value="<%=DataUtility.getStringData(bean.getJobName())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("jobName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Cron Expression<span style="color: red">*</span></th>
					<td><input type="text" name="cronExpression"
						value="<%=DataUtility.getStringData(bean.getCronExpression())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("cronExpression", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Status<span style="color: red">*</span></th>
					<td><input type="text" name="status"
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
						%> <input type="submit"
						name="operation" value="<%=JobCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=JobCtl.OP_CANCEL%>">
						<%
						} else {
						%> <input type="submit" name="operation"
						value="<%=JobCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=JobCtl.OP_RESET%>"> <%
 }
 %>
					</td>
				</tr>

			</table>
		</div>
	</form>
</body>
</html>