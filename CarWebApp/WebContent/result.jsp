<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Result</title>
</head>
<body>
	<CENTER>
		<H1>Your New Car:</H1>
		<%
			Automobile auto = (Automobile) session.getAttribute("auto");
			ArrayList<String> optionSetList = auto.getOptionSetNames();
			for (int i = 0; i < optionSetList.size(); i++) {
				String optionSetName = optionSetList.get(i);
				String optionName = request.getParameter(optionSetName);
				auto.setOptionChoice2(optionSetName, optionName);
			}
		%>
		<form method="post">
			<table border="1" cellpadding="5" width="600">
			
				<tr>
					<td>Model</td>
					<td>
						<%
							out.println(auto.getModelName());
						%>
					</td>
					<td> </td>
				</tr>
				
				<tr>
					<td>Base Price</td>
					<td> </td>
					
					<td>
						<%
							out.println(auto.getBaseprice());
						%>
					</td>
				</tr>
				<%
					for (int i = 0; i < optionSetList.size(); i++) {
				%>
				<tr>
					<td>
						<%
							String setName = optionSetList.get(i);
								out.println(setName);
						%>
					</td>
					<td>
						<%
							out.println(auto.getOptionChoiceName2(setName));
						%>
					</td>
					<td>
						<%
							out.println(auto.getOptionChoicePrice2(setName));
						%>
					</td>

				</tr>
				<%
					}
				%>

				<tr>
					<td><b>Total Cost</b></td>
					<td></td>
					<td><b>
						<%
							out.println(auto.getTotalPrice2());
						%>
					</b></td>
				</tr>

			</table>
		</form>
	</CENTER>

</body>
</html>