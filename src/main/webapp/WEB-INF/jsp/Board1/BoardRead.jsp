<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Board1</title>
</head>
<body>
	 <table border="1" style="width:600px">
            <caption>게시판</caption>
            <colgroup>
                <col width='15%' />
                <col width='*%' />
            </colgroup>
            <tbody>
                <tr>
                    <td>작성자</td> 
                    <td><c:out value="${boardInfo.brdWriter}"/></td> 
                </tr>
                <tr>
                    <td>제목</td> 
                    <td><c:out value="${boardInfo.brdTitle}"/></td> 
                </tr>
                <tr>
                    <td>내용</td> 
                    <td><c:out value="${boardInfo.brdMemo}"/></td> 
                </tr>
            </tbody>
        </table>    
        <a href="#" onclick="history.back(-1)">돌아가기</a>
        <a href="board1Delete?brdno=<c:out value="${boardInfo.brdNo}"/>">삭제</a>
        <a href="board1Update?brdno=<c:out value="${boardInfo.brdNo}"/>">수정</a>
</body>
</html>