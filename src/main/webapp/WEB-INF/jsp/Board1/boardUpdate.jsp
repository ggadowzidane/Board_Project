<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>board1</title>
</head>
<body>
	<form name="form1" action="board1UpdateSave">
		<table border="1" style="width:600px">
			<caption>게시판</caption>
			<colgroup>
				<col width='15%' />
				<col width='*%' />
			</colgroup>
			<tbody>
				<tr>
					<td>작성자</td> 
					<td><input type="text" name="brdWriter" size="20" maxlength="20" value="<c:out value="${boardInfo.brdWriter}"/>"></td> 
				</tr>
				<tr>
					<td>제목</td> 
					<td><input type="text" name="brdTitle" size="70" maxlength="250" value="<c:out value="${boardInfo.brdTitle}"/>"></td> 
				</tr>
				<tr>
					<td>내용</td> 
					<td><textarea name="brdMemo" rows="5" cols="60"><c:out value="${boardInfo.brdMemo}"/></textarea></td> 
				</tr>
			</tbody>
		</table>    
		<input type="hidden" name="brdno" value="<c:out value="${boardInfo.brdNo}"/>"> 
		<a href="#" onclick="form1.submit()">저장</a>
	</form>	
</body>
</html>
