<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<!-- <link rel="stylesheet" href=<c:url value="/js/dynatree/ui.dynatree.css" /> id="skinSheet" /> -->
<link rel="stylesheet" href="js/dynatree/ui.dynatree.css" id="skinSheet"/>
<script src="js/jquery-1.11.2.min.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/dynatree/jquery.dynatree.js"></script>
<script>
	var selectedNode = null;
	$(function(){
		$("#tree").dynatree({
			children: <c:out value="${treeStr}" escapeXml="false"/>,
			onActivate: TreenodeActivate
		});
		fn_groupNew();
	});
	
	function TreenodeActivate(node) {
		selectedNode = node;
		
	    if (selectedNode==null || selectedNode.data.key==0) return;
	    $.ajax({
	    	url: "boardGroupRead", 
	    	cache: false,
	    	data: { bgNo : selectedNode.data.key }    	
	    }).done(receiveData);
	}
	function receiveData(data){
		$("#bgNo").val(data.bgno);
		$("#bgName").val(data.bgname);
	    $('input:radio[name="bgUsed"][value="' + data.bgUsed + '"]').prop('checked', true);
	    $('input:radio[name="bgReadOnly"][value="' + data.bgReadOnly + '"]').prop('checked', true);
	    $('input:radio[name="bgReply"][value="' + data.bgReply + '"]').prop('checked', true);
	}
	function fn_groupNew(){
	    $("#bgNo").val("");
	    $("#bgName").val("");
		$('input:radio[name="bgUsed"][value="Y"]').prop('checked', true);
		$('input:radio[name="bgReadOnly"][value="N"]').prop('checked', true);
		$('input:radio[name="bgReply"][value="Y"]').prop('checked', true);
	}
	

	function fn_groupSave(){
	    if($("#bgName").val() == ""){
	        alert("게시판 이름을 입력해주세요.");
	        return;
	    }
	    var bgno =null;
	    if (selectedNode!=null) bgno =selectedNode.data.key;

	    if (!confirm("저장하시겠습니까?")) return;

	    $.ajax({
	        url: "boardGroupSave",
	        cache: false,
	        type: "POST",
	        data: { bgNo:$("#bgNo").val(), bgName:$("#bgName").val(), bgParent: bgno,
	                bgUsed : $("input:radio[name=bgUsed]:checked").val(), 
	                bgReadOnly : $("input:radio[name=bgReadOnly]:checked").val(), 
	                bgReply : $("input:radio[name=bgReply]:checked").val()}        
	    }).done(receiveData4Save);
	} 
	
	function receiveData4Save(data){
	    if (selectedNode!==null && selectedNode.data.key===data.bgno) {
	        selectedNode.data.title=data.bgname;
	        selectedNode.render();
	    } else {
	        addNode(data.bgno, data.bgname);
	    }
	    
	    alert("저장되었습니다.");
	}
	

	function addNode(nodeNo, nodeTitle){
	    var node = $("#tree").dynatree("getActiveNode");
	    if (!node) node = $("#tree").dynatree("getRoot");
	    var childNode = node.addChild({key: nodeNo, title: nodeTitle});
	    node.expand() ;
	    node.data.isFolder=true;
	    node.tree.redraw();
	} 
</script>
<body>
	<div style="width:270px; height:400px; overflow:auto; display: inline-block;" >
    	<div id="tree">
			<ul id="treeData" style="display: none;">
				
			</ul>
		</div>
	</div>
	
	<div style="width: 500px; padding-left: 10px; display: inline-block;vertical-align:top">
		<div style="text-align: right;"><a onclick="fn_groupNew()" href="#">추가</a></div>
		<input name="bgNo" id="bgNo" type="hidden" value=""> 
		<table style="border: 1px solid; width: 100%; height: 160px">
			<colgroup>
				<col width="30%">
				<col width="70%">
			</colgroup>
 	 		<tbody>
			<tr>
	 			<th>그룹명</th>
	 			<td> 
	 				<input name="bgName" id="bgName" style="width: 300px;" type="text" maxlength="100" value="">
	 			</td>
 			</tr>
 			<tr>
				<th>사용여부</th>
				<td>
					<input name="bgUsed" id="bgUsedY" type="radio" checked="checked" value="Y"><label for="bgusedY">사용</label>
					<input name="bgUsed" id="bgUsedN" type="radio" value="N"><label for="bgusedN">사용중지</label>
				</td>
			</tr>
 			<tr>
	 			<th>등록가능</th>
	 			<td>
					<input name="bgReadOnly" id="bgReadOnlyN" type="radio" checked="checked" value="N"><label for="bgreadonlyN">사용</label>
					<input name="bgReadOnly" id="bgReadOnlyY" type="radio" value="Y"><label for="bgreadonlyY">사용안함</label>
				</td>
 			</tr>
 			<tr>
	 			<th>댓글</th>
	 			<td>
					<input name="bgReply" id="bgReplyY" type="radio" checked="checked" value="Y"><label for="bgreplyY">사용</label>
					<input name="bgReply" id="bgReplyN" type="radio" value="N"><label for="bgreplyN">사용안함</label>
				</td>
 			</tr>
		 </tbody>
		 </table>
		<div style="text-align: right;">
			<a onclick="fn_groupSave()" href="#">저장</a>
			<a onclick="fn_groupDelete()" href="#">삭제</a>
        </div>
	</div>
</body>
</html>