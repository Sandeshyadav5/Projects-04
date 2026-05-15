<%@page import="in.co.rays.proj4.controller.JobListCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.JobBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Job List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

<script>
	function selectAll(source) {
		let checkboxes = document.getElementsByName('ids');
		for (let i = 0; i < checkboxes.length; i++) {
			checkboxes[i].checked = source.checked;
		}
	}
</script>

</head>

<body>

	<%@include file="Header.jsp"%>

	<div align="center">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.JobBean"
			scope="request"></jsp:useBean>

		<h1 style="color: navy;">Job List</h1>

		<h3>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
		</h3>
		<h3>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</h3>

		<form action="<%=ORSView.JOB_LIST_CTL%>" method="POST">

			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;

			int nextPageSize = 0;
			if (request.getAttribute("nextListSize") != null) {
				nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
			}

			List<JobBean> nameList = (List<JobBean>) request.getAttribute("nameList");
			List<JobBean> list = (List<JobBean>) ServletUtility.getList(request);

			Iterator<JobBean> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- 🔍 Search Panel -->
			<table width="100%">
				<tr>
					<td align="center"><label><b>Job Code : </b></label> <%=HTMLUtility.getList("jobCode", String.valueOf(bean.getJobCode()), nameList)%> &nbsp;&nbsp; <input
						type="submit" name="operation" value="<%=JobListCtl.OP_SEARCH%>">

						<input type="submit" name="operation"
						value="<%=JobListCtl.OP_RESET%>"></td>
				</tr>
			</table>

			<br>

			<%
			if (list != null && list.size() != 0) {
			%>

			<!-- 📋 Data Table -->
			<table border="1" width="100%" style="border-collapse: collapse;">
				<tr style="background-color: #e1e6f1e3;">
					<th><input type="checkbox" onclick="selectAll(this)"></th>
					<th>S.No</th>
					<th>Job Code</th>
					<th>Job Name</th>
					<th>Cron Expression</th>
					<th>Status</th>
					<th>Edit</th>
				</tr>

				<%
				while (it.hasNext()) {
					bean = it.next();
				%>

				<tr>
					<td align="center"><input type="checkbox" name="ids"
						value="<%=bean.getId()%>"></td>

					<td align="center"><%=index++%></td>

					<td align="center"><%=bean.getJobCode()%></td>
					<td align="center"><%=bean.getJobName()%></td>
					<td align="center"><%=bean.getCronExpression()%></td>
					<td align="center"><%=bean.getStatus()%></td>

					<td align="center"><a
						href="<%=ORSView.JOB_CTL%>?id=<%=bean.getId()%>">Edit</a></td>
				</tr>

				<%
				}
				%>

			</table>

			<br>

			<!-- 🔄 Pagination + Buttons -->
			<table width="100%">
				<tr>

					<td><input type="submit" name="operation"
						value="<%=JobListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=JobListCtl.OP_NEW%>"></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=JobListCtl.OP_DELETE%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=JobListCtl.OP_NEXT%>"
						<%=nextPageSize != 0 ? "" : "disabled"%>></td>

				</tr>
			</table>

			<%
			} else {
			%>

			<!-- ❌ No Record -->
			<table>
				<tr>
					<td align="center"><input type="submit" name="operation"
						value="<%=JobListCtl.OP_BACK%>"></td>
				</tr>
			</table>

			<%
			}
			%>

		</form>
	</div>

</body>
</html>