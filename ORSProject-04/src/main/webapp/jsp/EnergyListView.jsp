<%@page import="in.co.rays.proj4.controller.EnergyListCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.EnergyBean"%>

<%@page import="java.util.List"%>

<html>
<head>
<title>Energy List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png">

</head>

<body>

	<%@include file="Header.jsp"%>

	<div align="center">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.EnergyBean"
			scope="request"></jsp:useBean>

		<h1 align="center" style="margin-bottom: -15px; color: navy;">
			Energy List
		</h1>

		<div style="height: 15px; margin-bottom: 12px">

			<h3>
				<font color="red">
					<%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

			<h3>
				<font color="green">
					<%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>

		</div>

		<form action="<%=ORSView.ENERGY_LIST_CTL%>" method="POST">

			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);

			int index = ((pageNo - 1) * pageSize) + 1;

			Object nextObj = request.getAttribute("nextListSize");

			int nextPageSize = 0;

			if (nextObj != null) {
				nextPageSize = DataUtility.getInt(nextObj.toString());
			}

			List<EnergyBean> nameList = 
			(List<EnergyBean>) request.getAttribute("nameList");

			List<EnergyBean> list = 
			(List<EnergyBean>) ServletUtility.getList(request);

			if (list == null) {
				list = java.util.Collections.emptyList();
			}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>">

			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">

				<tr>

					<td align="center">

						<label>
							<b>Energy Code :</b>
						</label>

						<%=HTMLUtility.getList(
								"energyCode",
								String.valueOf(bean.getEnergyCode()),
								nameList)%>

						<input type="submit"
							name="operation"
							value="<%=EnergyListCtl.OP_SEARCH%>">

						<input type="submit"
							name="operation"
							value="<%=EnergyListCtl.OP_RESET%>">

					</td>

				</tr>

			</table>

			<br>

			<%
			if (list.size() != 0) {
			%>

			<table border="1" style="width: 100%; border: groove;">

				<tr style="background-color: #e1e6f1e3;">

					<th width="5%">
						<input type="checkbox" id="selectall">
					</th>

					<th width="5%">S.No</th>

					<th width="20%">Energy Code</th>

					<th width="25%">Device Name</th>

					<th width="15%">Units Consumed</th>

					<th width="15%">Status</th>

					<th width="10%">Edit</th>

				</tr>

				<%
				for (EnergyBean b : list) {
				%>

				<tr>

					<td align="center">

						<input type="checkbox"
							class="case"
							name="ids"
							value="<%=b.getId()%>">

					</td>

					<td align="center"><%=index++%></td>

					<td align="center"><%=b.getEnergyCode()%></td>

					<td align="center"><%=b.getDeviceName()%></td>

					<td align="center"><%=b.getUnitsConsumed()%></td>

					<td align="center"><%=b.getStatus()%></td>

					<td align="center">

						<a href="EnergyCtl?id=<%=b.getId()%>">
							Edit
						</a>

					</td>

				</tr>

				<%
				}
				%>

			</table>

			<br>

			<%
			}
			%>

			<table style="width: 100%">

				<tr>

					<td style="width: 25%">

						<input type="submit"
							name="operation"
							value="<%=EnergyListCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>>

					</td>

					<td align="center" style="width: 25%">

						<input type="submit"
							name="operation"
							value="<%=EnergyListCtl.OP_NEW%>">

					</td>

					<td align="center" style="width: 25%">

						<input type="submit"
							name="operation"
							value="<%=EnergyListCtl.OP_DELETE%>">

					</td>

					<td align="right" style="width: 25%">

						<input type="submit"
							name="operation"
							value="<%=EnergyListCtl.OP_NEXT%>"
							<%=(nextPageSize != 0) ? "" : "disabled"%>>

					</td>

				</tr>

			</table>

		</form>

	</div>

	<%@include file="Footer.jsp"%>

</body>
</html>