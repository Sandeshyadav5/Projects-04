<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.PasswordCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
    <title>Add PASSWORD</title>
    <link rel="icon" type="image/png"
        href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
<form action="<%=ORSView.PASSWORD_CTL%>" method="POST">

<%@ include file="Header.jsp" %>

<jsp:useBean id="bean"
    class="in.co.rays.proj4.bean.PasswordBean"
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
				PASSWORD
			</h1>

    <h3><font color="green">
        <%=ServletUtility.getSuccessMessage(request)%>
    </font></h3>

    <h3><font color="red">
        <%=ServletUtility.getErrorMessage(request)%>
    </font></h3>

    <!-- Hidden Fields -->
    <input type="hidden" name="id"
        value="<%=DataUtility.getStringData(bean.getId())%>">

    <table>

        <!-- ResetCode -->
        <tr>
            <th align="left">ResetCode<span style="color:red">*</span></th>
            <td>
                <input type="text" name="code" placeholder="enter code"
             value="<%=DataUtility.getStringData(bean.getResetCode())%>">
            </td>
            <td>
                <font color="red">
                    <%=ServletUtility.getErrorMessage("code", request)%>
                </font>
            </td>
        </tr>

        <!-- Due Date -->
        <tr>
            <th align="left">User Name<span style="color:red">*</span></th>
            <td>
                <input type="text" name="username" placeholder="enter username"
                    value="<%=DataUtility.getStringData(bean.getUserName())%>">
            </td>
            <td>
                <font color="red">
                    <%=ServletUtility.getErrorMessage("username", request)%>
                </font>
            </td>
        </tr>
        <tr>
            <th align="left">Reset Token<span style="color:red">*</span></th>
            <td>
                <input type="text" name="token"  placeholder="enter token"
                    value="<%=DataUtility.getStringData(bean.getResetToken())%>">
            </td>
            <td>
                <font color="red">
                    <%=ServletUtility.getErrorMessage("token", request)%>
                </font>
            </td>
        </tr>

        <!-- Status -->
        <tr>
            <th align="left">Status<span style="color:red">*</span></th>
            <td>
                <input type="text" name="status" placeholder="enter status"
                    value="<%=DataUtility.getStringData(bean.getStatus())%>">
            </td>
            <td>
                <font color="red">
                    <%=ServletUtility.getErrorMessage("status", request)%>
                </font>
            </td>
        </tr>

        <tr><th></th><td></td></tr>

        <!-- Buttons -->
        <tr>
            <th></th>
            <td colspan="2">
            <% if (bean != null && bean.getId() > 0) { %>
                <input type="submit" name="operation"
                    value="<%=PasswordCtl.OP_UPDATE%>">
                <input type="submit" name="operation"
                    value="<%=PasswordCtl.OP_CANCEL%>">
            <% } else { %>
                <input type="submit" name="operation"
                    value="<%=PasswordCtl.OP_SAVE%>">
                <input type="submit" name="operation"
                    value="<%=PasswordCtl.OP_RESET%>">
            <% } %>
            </td>
        </tr>

    </table>

</div>
</form>
</body>
</html>