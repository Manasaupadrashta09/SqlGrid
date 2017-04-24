<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import = "com.optum.cloudsdk.datagrid.sample.DistributedCache" %>
<body>

<% DistributedCache dc = new DistributedCache(); 
dc.saveData("cache1","Hello this is message1"); %>

</body>

