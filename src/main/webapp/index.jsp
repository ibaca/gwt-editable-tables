<!doctype html>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setDateHeader("Date", new Date().getTime());
    response.setDateHeader("Expires", new Date().getTime() - 86400000L); // one day old
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
%><%
    final ServletContext ctx = pageContext.getServletContext();
    final Set modules = ctx.getResourcePaths("/");
    for (Iterator resourceIterator = modules.iterator(); resourceIterator.hasNext(); ) {
        String path = (String) resourceIterator.next();
        if (path.length() <= 2) {
            resourceIterator.remove();
        } else if (!path.endsWith("/")) {
            resourceIterator.remove();
        } else if (ctx.getResource(path + path.substring(1, path.length() - 1) + ".nocache.js") == null) {
            resourceIterator.remove();
        }
    }
    final String moduleParam = request.getParameter("module");
    final String codesvr = request.getParameterMap().containsKey("gwt.codesvr") ?
            request.getParameter("gwt.codesvr") : "127.0.0.1:9997";
%>
<html>
<head>
    <title>GWT Module selector</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<% if (moduleParam != null && ctx.getResource("/" + moduleParam + "/" + moduleParam + ".nocache.js") != null) { %>
<script type="text/javascript" src="<%= moduleParam %>/<%= moduleParam %>.nocache.js"></script>
<% } else {
    out.println("<ul>");
    if (moduleParam != null) {
        out.println("<li style='color:red'>Module '" + moduleParam + "' not found.");
    }
    if (modules.isEmpty()) {
        out.println("<li style='color:red'>No modules found.");
    } else {
        for (Object item : modules) {
            String path = (String) item;
            String module = path.substring(1, path.length() - 1);
            out.print("<li>" + module);
            out.print(" <a href='?module=" + module + "&gwt.codesvr=" + codesvr + "'>Java Mode</a>");
            out.print(" <a href='?module=" + module + "'>Script mode</a>");
        }
    }
    out.println("</ul>");
}
%></body>
</html>
