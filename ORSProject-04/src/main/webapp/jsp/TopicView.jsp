<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.TopicCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add TOPIC</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<form action="<%=ORSView.TOPIC_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.TopicBean"
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
				TOPIC
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
					<th align="left">Topic Code<span style="color: red">*</span></th>
					<td><input type="text" name="topicCode"
						placeholder="enter topic code"
						value="<%=DataUtility.getStringData(bean.getTopicCode())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("topicCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Topic Name<span style="color: red">*</span></th>
					<td><input type="text" name="topicName"
						placeholder="enter topic name"
						value="<%=DataUtility.getStringData(bean.getTopicName())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("topicName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Partitions<span style="color: red">*</th>
					<td><input type="text" name="partitions"
						placeholder="enter partitions"
						value="<%=DataUtility.getStringData(bean.getPartitions())%>">
					</td>
				</tr>

				<tr>
					<th align="left">Status<span style="color: red">*</th>
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
						%> <input type="submit"
						name="operation" value="<%=TopicCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=TopicCtl.OP_CANCEL%>">
						<%
						} else {
						%> <input type="submit" name="operation"
						value="<%=TopicCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=TopicCtl.OP_RESET%>"> <%
 }
 %>
					</td>
				</tr>

			</table>

		</div>
	</form>
</body>
</html>