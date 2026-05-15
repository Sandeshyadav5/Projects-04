<%@page import="in.co.rays.proj4.controller.BatchListCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.Collections"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.BatchBean"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Batch List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>

<body>
	<%@include file="Header.jsp"%>

	<div align="center">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.BatchBean"
			scope="request"></jsp:useBean>

		<h1 align="center" style="margin-bottom: -15; color: navy;">Batch
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.BATCH_LIST_CTL%>" method="POST">

			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;

			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<BatchBean> nameList = (List<BatchBean>) request.getAttribute("nameList");
			List<BatchBean> list = (List<BatchBean>) ServletUtility.getList(request);
			Iterator<BatchBean> it = list.iterator();

			if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Batch Code : </b></label> <%=HTMLUtility.getList("batchCode", String.valueOf(bean.getBatchCode()), nameList)%> <input type="submit"
						name="operation" value="<%=BatchListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation" value="<%=BatchListCtl.OP_RESET%>">
					</td>
				</tr>
			</table>

			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="20%">Batch Code</th>
					<th width="25%">Batch Name</th>
					<th width="10%">Total Records</th>
					<th width="10%">Status</th>
					<th width="10%">Edit</th>
				</tr>

				<%
				while (it.hasNext()) {
					bean = it.next();
				%>

				<tr>
					<td align="center"><input type="checkbox" class="case"
						name="ids" value="<%=bean.getId()%>"></td>

					<td align="center"><%=index++%></td>
					<td align="center"><%=bean.getBatchCode()%></td>
					<td align="center"><%=bean.getBatchName()%></td>
					<td align="center"><%=bean.getTotalRecords()%></td>
					<td align="center"><%=bean.getStatus()%></td>

					<td align="center"><a href="BatchCtl?id=<%=bean.getId()%>">Edit</a>
					</td>
				</tr>

				<%
				}
				%>
			</table>

			<table style="width: 100%">
				<tr>
					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=BatchListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=BatchListCtl.OP_NEW%>"></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=BatchListCtl.OP_DELETE%>"></td>

					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=BatchListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
			}
			%>

		</form>
	</div>
</body>
</html>