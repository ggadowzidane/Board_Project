<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Board2</title>
<script>
	var updateReNo = updateReMemo = null;
	function fn_formSubmit(){
		var form1 = document.form1;
		
		if (form1.reWriter.value=="") {
			alert("작성자를 입력해주세요.");
			form1.reWriter.focus();
			return;
		}
		if (form1.reMemo.value=="") {
			alert("글 내용을 입력해주세요.");
			form1.reMemo.focus();
			return;
		}
		form1.submit();	
	}
	
	function fn_replyDelete(reNo){
		if(!confirm("삭제하시겠습니까?"))
			return ;
		
		var form = document.form2;
		form.action="boardReplyDelete";
		form.reNo.value=reNo;
		form.submit();
	}
	
	function fn_replyUpdate(reNo){
		var form  = document.form2;
		var reply = document.getElementById("reply"+reNo);
		var replyDiv = document.getElementById("replyDiv");
		replyDiv.style.display="";
		if(updateReNo){
			document.body.appendChild(replyDiv);
			var oldReno = document.getElementById("reply"+updateReNo);
			oldReno.innerText = updateReMemo;
		}
		
		form.reNo.value=reNo;
		form.reMemo.value=reply.innerText;
		reply.innerText="";
		reply.appendChild(replyDiv);
		updateReNo = reno;
		updateReMemo = form.reMemo.value;
		form.reMemo.focus();
	}
	
	function fn_replyUpdateSave(){
		var form =document.form2;
		if(form.reMemo.value==""){
			alert("글 내용을 입력하세요");
			form.reMemo.focus();
			return;
		}
		
		form.action="boardReplySave";
		form.submit();
	}
	
	function fn_replyUpdateCancel(){
		var form =document.form2;
		var replDiv = document.getElementById("replyDiv");
		document.body.appendChild(replyDiv);
		replyDiv.style.display="none";
		
		var oldReno = document.getElementById("reply"+updateReNo);
		oldReno.innerText = updateReMemo;
		updateReNo = updateReMemo = null;
	}

</script>
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
					<td><c:out value="${boardInfo.brdmemo}" escapeXml="false"/></td> 
				</tr>
                <tr>
                    <td>첨부</td>
						<td>
							<c:forEach var="fileList" items="${fileList}" varStatus="status">
								<input type="checkbox" name="fileNo" value="<c:out value="${fileList.fileNo }"/>" />
								<a href="fileDownload?fileName=<c:out value="${fileList.fileName}"/>&downName=<c:out value="${fileList.realName}"/>"><c:out value="${fileList.fileName }" /> </a> <c:out value="${fileList.size2String() }" /> <br />
							</c:forEach>
							<!-- <input type="file" name="uploadfile" multiple="" />  -->
						</td>
                </tr>
            </tbody>
        </table>    
        <a href="#" onclick="history.back(-1)">돌아가기</a>
        <a href="board2Delete?brdNo=<c:out value="${boardInfo.brdNo}"/>">삭제</a>
        <a href="board2Form?brdNo=<c:out value="${boardInfo.brdNo}"/>">수정</a>
        
        <p> &nbsp; </p>
        
        <div style="border: 1px sloid; width:600px; padding:5px">
        	<form name="form1" action="boardReplySave" method="post">
        		<input type="hidden" name="brdNo" value="<c:out value="${boardInfo.brdNo}"/>"> 
	          작성자: <input type="text" name="reWriter" size="20" maxlength="20"> <br/>
		        <textarea name="reMemo" rows="3" cols="60" maxlength="500" placeholder="댓글을 달아주세요."></textarea>
		        <a href="#" onclick="fn_formSubmit()">저장</a>
        	</form>
        </div>
        
        <c:forEach var="replyList" items="${replyList}" varStatus="status">
		    <div style="border: 1px solid gray; width: 600px; padding: 5px; margin-top: 5px;">    
		        <c:out value="${replyList.reWriter}"/> <c:out value="${replyList.reDate}"/>
		        <a href="#" onclick="fn_replyDelete('<c:out value="${replyList.reNo}"/>')">삭제</a>
		        <a href="#" onclick="fn_replyUpdate('<c:out value="${replyList.reNo}"/>')">수정</a>
		        <br/>
		        <div id="reply<c:out value="${replyList.reNo}"/>"><c:out value="${replyList.reMemo}"/></div>
		    </div>
		</c:forEach>
		
		<div id="replyDiv" style="width: 99%; display:none">
			<form name="form2" action="boardReplySave" method="post">
				<input type="hidden" name="brdNo" value="<c:out value="${boardInfo.brdNo}"/>"> 
				<input type="hidden" name="reNo"> 
				<textarea name="reMemo" rows="3" cols="60" maxlength="500"></textarea>
				<a href="#" onclick="fn_replyUpdateSave()">저장</a>
				<a href="#" onclick="fn_replyUpdateCancel()">취소</a>
			</form>
		</div>
</body>
</html>