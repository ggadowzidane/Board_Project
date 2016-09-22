<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
	function fn_formSubmit(){
		var chk = document.form1.searchType;
		var flag = false;
		for(i=0; i<chk.length; i++){
			flag = chk[i].checked || flag;
				if (flag == true) break;
		}
		if(flag == false && document.form1.searchKeyword.value == "" )
			flag = true;
		if(flag == false){ 
			alert("검색 방법을 선택하세요!");
			return ; 
		}
		document.form1.submit();
	}
</script>
</head>
<body>
	<a href="board2Form">글쓰기</a>
	<table border="1" style="width:600px">
		<caption>게시판</caption>
		<colgroup>
			<col width='8%' />
			<col width='*%' />
			<col width='15%' />
			<col width='15%' />
			<col width='10%' />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th> 
				<th>제목</th>
				<th>등록자</th>
				<th>등록일</th>
				<th>조회수</th>
				<th>첨부</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="listview" items="${listview}" varStatus="status">		
				<c:url var="link" value="board2Read">
					<c:param name="brdNo" value="${listview.brdNo}" />
				</c:url>
				<!-- <c:out value="${pageVO.totRow-((pageVO.page-1)*pageVO.displayRowCount + status.index)}"/> --> <!-- 게시물 번호를 이쁘게 보이게 하려면 사용 --> 				 
				<tr>
					<td><c:out value="${listview.brdNo}"/></td>
					<td style="border :1px solid black; max-width:100px; overflow:hidden; white-space:nowrap; text-overflow:ellipsis;"><!-- 긴 글제목 자르기 -->
						<a href="${link}"><c:out value="${listview.brdTitle}"/></a>
					</td>
					<td><c:out value="${listview.brdWriter}" /></td>
					<td><c:out value="${listview.brdDate}" /></td>
					<td><c:out value="${listview.brdHit}" /></td>
					<td><c:out value="${listview.fileCnt}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>	
	<!-- action이 지정되지 않으므로 자기자신을 호출한다(board2List) -->
	<form id="form1" name="form1"  method="post">
		<jsp:include page="/WEB-INF/jsp/common/pagingforSubmit.jsp" />
		
		<div>
			<input type="checkbox" name="searchType" value="brdTitle" <c:if test="${fn:indexOf(searchVO.searchType, 'brdTitle')!=-1}">checked="checked"</c:if>/>
			<label class="chkselect" for="searchType1">제목</label>
			<input type="checkbox" name="searchType" value="brdMemo" <c:if test="${fn:indexOf(searchVO.searchType, 'brdMemo')!=-1}">checked="checked"</c:if>/>
			<label class="chkselect" for="searchType2">내용</label>
			<!-- 텍스트 필드에서 검색어를 치고 바로 Enter를 누르면 검색하게 만듬 -->
			<input type="text" name="searchKeyword" style="width:150px;" maxlength="50" value='<c:out value="${searchVO.searchKeyword}"/>' onkeydown="if(event.keyCode == 13) { fn_formSubmit();}">
			<input name="btn_search" value="검색" class="btn_sch" type="button" onclick="fn_formSubmit()" />
		</div>
	</form>
</body>
</html>