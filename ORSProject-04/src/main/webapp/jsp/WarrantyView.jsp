<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.WarrantyCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
    <title>Add WARRANTY</title>
    <link rel="icon" type="image/png"
        href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
<form action="<%=ORSView.WARRANTY_CTL%>" method="POST">

<%@ include file="Header.jsp" %>

<jsp:useBean id="bean"
    class="in.co.rays.proj4.bean.WarrantyBean"
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
				WARRANTY
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
            <th align="left">Product Name<span style="color:red">*</span></th>
            <td>
                <input type="text" name="productName" placeholder="enter productName"
             value="<%=DataUtility.getStringData(bean.getProductName())%>">
            </td>
            <td>
                <font color="red">
                    <%=ServletUtility.getErrorMessage("productName", request)%>
                </font>
            </td>
        </tr>

        <!-- Due Date -->
        <tr>
            <th align="left">Start Date<span style="color:red">*</span></th>
            <td>
                <input type="text" name="Start"  id="udatee" placeholder="enter Start Date"
                    value="<%=DataUtility.getStringData(bean.getStartDate())%>">
            </td>
            <td>
                <font color="red">
                    <%=ServletUtility.getErrorMessage("Start", request)%>
                </font>
            </td>
        </tr>
        <tr>
            <th align="left">End Date<span style="color:red">*</span></th>
            <td>
                <input type="text" name="End"  id="udatee" placeholder="enter End date" 
                    value="<%=DataUtility.getStringData(bean.getEndDate())%>">
            </td>
            <td>
                <font color="red">
                    <%=ServletUtility.getErrorMessage("End", request)%>
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
                    value="<%=WarrantyCtl.OP_UPDATE%>">
                <input type="submit" name="operation"
                    value="<%=WarrantyCtl.OP_CANCEL%>">
            <% } else { %>
                <input type="submit" name="operation"
                    value="<%=WarrantyCtl.OP_SAVE%>">
                <input type="submit" name="operation"
                    value="<%=WarrantyCtl.OP_RESET%>">
            <% } %>
            </td>
        </tr>

    </table>

</div>
</form>
</body>
</html>