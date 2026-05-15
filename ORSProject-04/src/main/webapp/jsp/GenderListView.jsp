<%@page import="in.co.rays.proj4.controller.GenderListCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.Collections"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.GenderBean"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Gender List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>

<body>
	<%@include file="Header.jsp"%>

	<div align="center">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.GenderBean"
			scope="request"></jsp:useBean>

		<h1 align="center" style="margin-bottom: -15; color: navy;">Gender
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.GENDER_LIST_CTL%>" method="POST">

			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;

			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<GenderBean> nameList = (List<GenderBean>) request.getAttribute("nameList");
			List<GenderBean> list = (List<GenderBean>) ServletUtility.getList(request);
			Iterator<GenderBean> it = list.iterator();

			if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Gender Code : </b></label> <%=HTMLUtility.getList("genderCode", String.valueOf(bean.getGenderCode()), nameList)%> <input type="submit"
						name="operation" value="<%=GenderListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation" value="<%=GenderListCtl.OP_RESET%>">
					</td>
				</tr>
			</table>

			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="20%">Gender Code</th>
					<th width="25%">Gender Type</th>
					<th width="10%">Description</th>
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
					<td align="center"><%=bean.getGenderCode()%></td>
					<td align="center"><%=bean.getGenderType()%></td>
					<td align="center"><%=bean.getDescription()%></td>
					<td align="center"><%=bean.getStatus()%></td>

					<td align="center"><a href="GenderCtl?id=<%=bean.getId()%>">Edit</a>
					</td>
				</tr>

				<%
				}
				%>
			</table>

			<table style="width: 100%">
				<tr>
					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=GenderListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=GenderListCtl.OP_NEW%>"></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=GenderListCtl.OP_DELETE%>"></td>

					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=GenderListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
			}
			%>

		</form>
	</div>
	<%@ include file="Footer.jsp"%>
</body>
</html>