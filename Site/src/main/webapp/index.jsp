<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <title>Site</title>
    <!-- 当Site模块启动时，会直接启动index.jsp页面，
    并通过该页面访问CakeServlet获得全部蛋糕数据并转发到/WEB-INF/views/index.jsp页面-->
    <meta http-equiv="refresh" content="0;url=<%=request.getContextPath()%>/cake/list.do">
  </head>
</html>
