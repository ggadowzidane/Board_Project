package com.board.test2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.board.test.common.FileUtil;
import com.board.test.common.FileVO;
import com.board.test.common.SearchVO;

@Controller
public class Board2Controller {

	@Autowired
	private Board2Service board2Service;
	@Autowired
	private BoardGroupService boardGroupService;
	
	@RequestMapping(value = "/board2List")
   	public String boardList(SearchVO searchVO,ModelMap modelMap) throws Exception {
		
		if(searchVO.getBgNo() == null){
			searchVO.setBgNo("2");
		}
		BoardGroupVO bgInfo = boardGroupService.selectBoardGroupOne4Used(searchVO.getBgNo());
		if (bgInfo == null) {
            return "Board2/BoardGroupFail";
        }
		searchVO.pageCalculate( board2Service.selectBoardCount(searchVO) );
    	List<?> listview   = board2Service.selectBoardList(searchVO);
    	
    	modelMap.addAttribute("listview", listview);
    	modelMap.addAttribute("searchVO", searchVO);
    	modelMap.addAttribute("bgInfo", bgInfo);
    	
        return "Board2/BoardList";
    }
	/*
	 * 글쓰기와 글수정은 기본적인 FORM이 같으므로 두가지를 따로 하지 않고 하나로 사용한다.
	 * */
	@RequestMapping(value = "/board2Form")
	public String boardForm(HttpServletRequest request,ModelMap modelMap) throws Exception {
		
		String brdNo = request.getParameter("brdNo");
		String bgNo = request.getParameter("bgNo");
		if(brdNo !=null){
			BoardVO boardInfo = board2Service.selectBoardOne(brdNo);
			List<?> fileList =board2Service.selectBoardFileList(brdNo);
			
			modelMap.addAttribute("boardInfo",boardInfo);
			modelMap.addAttribute("fileList",fileList);
			bgNo = boardInfo.getBgNo();
		}
		BoardGroupVO bgInfo = boardGroupService.selectBoardGroupOne4Used(bgNo);
		if (bgInfo == null) {
            return "Board2/BoardGroupFail";
        }
		modelMap.addAttribute("bgNo",bgNo);
		modelMap.addAttribute("bgInfo", bgInfo);
		
        return "Board2/BoardForm";
	}
	
	@RequestMapping(value = "/board2Save", method = RequestMethod.POST)
	public String boardSave(HttpServletRequest request,@ModelAttribute BoardVO boardInfo) throws Exception {
		String[] fileNo = request.getParameterValues("fileNo");
		FileUtil file = new FileUtil();
		List<FileVO> fileList = file.saveAllFiles(boardInfo.getUploadfile());
		board2Service.insertBoard(boardInfo,fileList,fileNo);
		System.out.println(boardInfo.getBgNo());
	    return "redirect:/board2List?bgNo="+boardInfo.getBgNo();
	}
	
	@RequestMapping(value = "/board2Read")
	public String boardSave(HttpServletRequest request, ModelMap modelMap) throws Exception {
	    
		String brdNo = request.getParameter("brdNo");
		
		board2Service.updateBoard2Read(brdNo);
		BoardVO boardInfo = board2Service.selectBoardOne(brdNo);
		List<?> fielList  = board2Service.selectBoardFileList(brdNo);
		List<?> replyList = board2Service.selectBoardReplyList(brdNo);
		
		BoardGroupVO bgInfo = boardGroupService.selectBoardGroupOne4Used(boardInfo.getBgNo());
		if (bgInfo == null) {
            return "Board2/BoardGroupFail";
        }
		
		modelMap.addAttribute("boardInfo",boardInfo);
		modelMap.addAttribute("fileList", fielList);
		modelMap.addAttribute("replyList",replyList);
		modelMap.addAttribute("bgInfo", bgInfo);
		
	    return "Board2/BoardRead";
	}
	
	@RequestMapping(value ="/boardReplySave" , method = RequestMethod.POST)
	public String boardReplySave(HttpServletRequest request,BoardReplyVO boardReplyInfo) throws Exception{
		board2Service.insertBoardReply(boardReplyInfo);
		return "redirect:/board2Read?brdNo="+boardReplyInfo.getBrdNo();
	}
	
	@RequestMapping(value="/boardReplySaveAjax")
	public void boardReplySasveAjax(HttpServletResponse response, BoardReplyVO boardReplyInfo) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");
		
		board2Service.insertBoardReply(boardReplyInfo);
		try{
			response.getWriter().print(mapper.writeValueAsString(boardReplyInfo.getReNo()));
		} catch (Exception e){
			System.out.println("오류로 인해 댓글 저장에 문제가 발생하였습니다");
		}
	}
	
	@RequestMapping(value="/boardReplySaveAjaxReply")
	public String boardReplySaveAjaxReply(BoardReplyVO boardReplyInfo, ModelMap modelMap) throws Exception{
		
		board2Service.insertBoardReply(boardReplyInfo);
		
		modelMap.addAttribute("replyInfo",boardReplyInfo);
		return "Board2/BoardReadAjaxReply";
	}
	
	
	@RequestMapping(value = "/board2Delete")
   	public String boardDelete(HttpServletRequest request) throws Exception {
    	
    	String brdNo = request.getParameter("brdNo");
    	String bgNo = request.getParameter("bgNo");
    	
    	board2Service.deleteBoardOne(brdNo);
        
        return "redirect:/board2List?bgNo="+bgNo;
    }  
	
	@RequestMapping(value="/boardReplyDelete")
	public String boardReplyDelete(HttpServletRequest request,BoardReplyVO boardReplyInfo) throws Exception{
		  if (!board2Service.deleteBoardReply(boardReplyInfo.getReNo()) ) {
	            return "Board2/BoardFaiure";
	        }

	        return "redirect:/board2Read?brdNo=" + boardReplyInfo.getBrdNo();
	}
	
	@RequestMapping(value="/boardReplyDeleteAjax")
	public void boardReplyDeleteAjax(HttpServletResponse response , BoardReplyVO boardReplyInfo) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");
		
		try{
			if(!board2Service.deleteBoardReply(boardReplyInfo.getReNo())){
				response.getWriter().print(mapper.writeValueAsString("Fail"));
			}else{
				response.getWriter().print(mapper.writeValueAsString("OK"));
			}
		} catch(Exception e){
			System.out.println("오류:댓글 삭제에 문제가 발생하였습니다");
		}
	}
	
	@RequestMapping(value="/fileDownload")
	public void boardFileDownload(HttpServletRequest request,HttpServletResponse response){
	 	String path = "F:\\FileUpload\\"; 
        
        String filename = request.getParameter("fileName");
        String downname = request.getParameter("downName");
        String realPath = "";
        
        if (filename == null || "".equals(filename)) {
            filename = downname;
        }
        
        try {
            filename = URLEncoder.encode(filename, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            System.out.println("UnsupportedEncodingException");
        }
        
        realPath = path + downname.substring(0,4) + "\\" + downname;
        File file1 = new File(realPath);
        if (!file1.exists()) {
        	System.out.println("asdasd");
            return ;
        }
        
        // 파일명 지정
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        try {
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(realPath);

            int ncount = 0;
            byte[] bytes = new byte[512];

            while ((ncount = fis.read(bytes)) != -1 ) {
                os.write(bytes, 0, ncount);
            }
            fis.close();
            os.close();
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException");
        } catch (IOException ex) {
            System.out.println("IOException");
        }
	}
	
}
