<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Configure</title>
</head>
<body>
	<CENTER>
		<H1>Configure Your Car:</H1>
		<form method="post" action="result.jsp">
			<%
				Automobile auto = (Automobile) session.getAttribute("auto");
				ArrayList<String> optionSetList = auto.getOptionSetNames();
			%>
			<table border="1" cellpadding="5" width="600">
				<tr>
					<td>Model</td>
					<td>
						<%
							out.println(auto.getModelName());
						%>
					</td>
				</tr>
				<%
					for (int i = 0; i < optionSetList.size(); i++) {
				%>
				<tr>
					<td>
						<%
							String optionSetName = optionSetList.get(i);
								out.println(optionSetName);
						%>
					</td>
					<td><select name="<%=optionSetName%>">
							<%
								ArrayList<String> optionList = auto.getOptionNames(auto.findAnOptionSetIndex(optionSetName));
									for (int j = 0; j < optionList.size(); j++) {
										String optname = optionList.get(j);
							%>
							<option value="<%=optname%>"><%=optname%></option>
							<%
								}
							%>
					</select>
				</tr>
				<%
					}
				%>
			</table>
			<td><input type="submit" value="Done"></td>

		</form>
	</CENTER>
</body>
</html>