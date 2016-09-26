<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Board2</title>
<script src="<c:url value="/js/jquery-1.11.2.min.js" />"></script>
<script>
	var updateReNo = updateReMemo = null;

	function fn_formSubmit(){
		if( $.trim($("#reWriter1").val()) == ""){
			alert("작성자를 입력해주세요.");
			$("#reWriter1").focus();
			return;
		}
		if( $.trim($("#reMemo1").val()) ==""){
			alert("내용을 입력해주세요.");
			$("#reMemo1").focus();
			return;
		}
		$.ajax({
			url:"boardReplySaveAjax",
			type:"post",
			data:{"brdNo":$("#brdNo1").val(),"reWriter":$("#reWriter1").val(),"reMemo":$("#reMemo1").val()},
			success:function(result){
				if(result!=""){
					var div = $("<div>");
					div.attr("id","replyItem"+result);
					div.css({border:"1px solid gray",width:"600px","padding": "5px", "margin-top": "5px", "margin-left": "0px", display:"inline-block"});
					div.text($("#reWriter1").val()+"방금");
					div.appendTo($("#replyList"));
					
					$("<a>",{
						text:"삭제",
						href:"#",
						click:function(){fn_replyDelete(result)}
					}).appendTo(div);
					$("<a>").attr("href","#").text("수정").click(function(){fn_replyUpdate(result)}).appendTo(div);
					$("<a>").attr("href","#").text("댓글"). click(function(){fn_replyReply(result)}).appendTo(div);
					$("<div>").attr("id","reply"+result).html($("#reMemo1").val()).appendTo(div);
					$("#reWriter1").val("");
					$("#reMemo1").val("");
					alert("저장되었습니다");
				} else{
					alert("오류가 발생하여 저장 실패하였습니다.");
				}
			}
		});
	}
	function fn_replyDelete(reNo){
		if(!confirm("삭제하시겠습니까?"))
			return ;
		$.ajax({
			url:"boardReplyDeleteAjax",
			type:"post",
			data:{"brdNo":$("#brdNo1").val() , "reNo":reNo},
			success:function(result){
				if(result=="OK"){
					$("#replyItem"+reNo).remove();
					alert("삭제되었습니다");
				}else{
					alert("댓글이 있어서 삭제할 수 없습니다");
				}
			}
		});
	}
	function fn_replyUpdate(reNo){
		hideDiv("replyDialog");
		
		$("#replyDiv").show();
		if(updateReNo){
			$("#replyDiv").appendTo(document.body);
			$("#reply"+updateReNo).text(updateReMemo);
		}
		$("#reNo2").val(reNo);
		$("#reMemo2").val($("#reply"+reNo).text());
		$("#reply"+reNo).text("");
		$("#replyDiv").appendTo($("#reply"+reNo));
		updateReNo = reNo;
		updateReMemo = $("#reMemo2").val();
		$("#reMemo2").focus();
	}
	function fn_replyUpdateSave(){
		if($("#reMemo2").val() == ""){
			alert("글 내용을 입력하세요");
			$("reMemo2").focus();
			return ;
		}
		$("#form2").attr("action","boardReplySave");
		$("#form2").submit();
	}
	function fn_replyUpdateCancel(){
		hideDiv("#replyDiv");
		
		$("#reply"+updateReNo).text(updateReMemo);
		updateReNo = updateReMemo = null;
	}
	function hideDiv(id){
		$(id).hide();
		$(id).appendTo(document.body);
	}
	function fn_replyReply(reno){
		$("#replyDialog").show();
		
		if (updateReNo) {
			fn_replyUpdateCancel();
		} 
		
		$("#reParent3").val(reno);
		$("#reMemo3").val("");
		$("#replyDialog").appendTo($($("#reply"+reno)));
		$("#reWriter3").focus();
	}
	function fn_replyReplyCancel(){
	    hideDiv("replyDialog");
	} 
	function fn_replyReplySave(){
		if( $.trim( $("#reWriter3").val() ) == "" ){
			alert("작성자를 입력해주세요");
			$("#reWriter3").focus();
			return ;
		}
		if( $.trim($("#reMemo3").val()) == "" ){
			alert("내용을 입력해주세요 ");
			$("#reMemo3").focus();
			return ;
		}
		var formData = $("#form3").serialize();
		$.ajax({
			url:"boardReplySaveAjaxReply",
			type:"post",
			data:formData,
			success:function(result){
				if(result !== ""){
					var parent =$("#reParent3").val();
					$("#replyItem"+parent).after(result);
					$("#replyDialog").hide();
					alert("저장되었습니다");
				} else{
					alert("서버에 오류가 있어서 저장되지 않았습니다.");
				}
			}
		});
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
					<td><c:out value="${boardInfo.brdMemo}" escapeXml="false"/></td> 
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
        	<form id="form1" name="form1" action="boardReplySave" method="post">
        		<input type="hidden" id="brdNo1" name="brdNo" value="<c:out value="${boardInfo.brdNo}"/>">
        		<input type="hidden" id="reNo1" name="reNo">
	          작성자: <input type="text" id="reWriter1" name="reWriter" size="20" maxlength="20"> <br/>
		        <textarea id="reMemo1" name="reMemo" rows="3" cols="60" maxlength="500" placeholder="댓글을 달아주세요."></textarea>
		        <a href="#" onclick="fn_formSubmit()">저장</a>
        	</form>
        </div>
        
        <div id="replyList">
	        <c:forEach var="replyList" items="${replyList}" varStatus="status">
			    <div id="replyItem<c:out value="${replyList.reNo}"/>" 
			         style="border: 1px solid gray; width: 600px; padding: 5px; margin-top:5px; 
			         margin-left: <c:out value="${20*replyList.reDepth}"/>px; display: inline-block">       
			        <c:out value="${replyList.reWriter}"/> <c:out value="${replyList.reDate}"/>
			        <a href="#" onclick="fn_replyDelete('<c:out value="${replyList.reNo}"/>')">삭제</a>
			        <a href="#" onclick="fn_replyUpdate('<c:out value="${replyList.reNo}"/>')">수정</a>
			        <a href="#" onclick="fn_replyReply('<c:out value="${replyList.reNo}"/>')">댓글</a>
			        <br/>
			        <div id="reply<c:out value="${replyList.reNo}"/>"><c:out value="${replyList.reMemo}"/></div>
			    </div>
			</c:forEach>
		</div>
		
		<div id="replyDiv" style="width: 99%; display:none">
			<form id="form2" name="form2" action="boardReplySave" method="post">
				<input type="hidden" id="brdNo2" name="brdNo" value="<c:out value="${boardInfo.brdNo}"/>"> 
				<input type="hidden" id="reNo2" name="reNo"> 
				<textarea id="reMemo2" name="reMemo" rows="3" cols="60" maxlength="500"></textarea>
				<a href="#" onclick="fn_replyUpdateSave()">저장</a>
				<a href="#" onclick="fn_replyUpdateCancel()">취소</a>
			</form>
		</div>
		
		<div id="replyDialog" style="width:99%; display:none">
			<form id="form3" name="form3" action="boardReplySave" method="post">
				<input type="hidden" id="brdNo3" name="brdNo" value="<c:out value="${boardInfo.brdNo }"/> ">
				<input type="hidden" id="reNo3" name="reNo">
				<input type="hidden" id="reParent3" name="reParent">
				작성자 : <input type="text" id="reWriter3" name="reWriter" size="20" maxlength="20"> <br />
				<textarea id="reMemo3" name="reMemo" rows="3" cols="60" maxlength="500"></textarea>
				<a href="#" onclick="fn_replyReplySave()">저장</a>
				<a href="#" onclick="fn_replyReplyCancel()">취소</a>
			</form>
		</div>
		
</body>
</html>