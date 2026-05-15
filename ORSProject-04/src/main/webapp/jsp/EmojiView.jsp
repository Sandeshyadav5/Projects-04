<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.EmojiCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Emoji</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<form action="<%=ORSView.EMOJI_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.EmojiBean"
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
				Emoji
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
					<th align="left">Reaction Code<span style="color: red">*</span></th>
					<td><input type="text" name="reactionCode"
						placeholder="Enter Reaction Code"
						value="<%=DataUtility.getStringData(bean.getReactionCode())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("reactionCode", request)%>
					</font></td>
				</tr>

				

				<tr>
					<th align="left">User Name<span style="color: red">*</span></th>
					<td><input type="text" name="userName" 
						placeholder="Enter User Name"
						value="<%=DataUtility.getStringData(bean.getUserName())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("userName", request)%>
					</font></td>
				</tr>
				<tr>
					<th align="left">Emoji Type<span style="color: red">*</span></th>
					<td><input type="text" name="emojiType"
						placeholder="Enter Emoji Type"
						value="<%=DataUtility.getStringData(bean.getEmojiType())%>">
					</td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("emojiType", request)%>
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
						value="<%=EmojiCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=EmojiCtl.OP_CANCEL%>"> <%
 } else {
 %> <input type="submit" name="operation"
						value="<%=EmojiCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=EmojiCtl.OP_RESET%>"> <%
 }
 %>
					</td>
				</tr>

			</table>
	</form></div>
<%@ include file="Footer.jsp"%>
</body>

</html>