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
	
	@RequestMapping(value = "/board2List")
   	public String boardList(SearchVO searchVO,ModelMap modelMap) throws Exception {
		
		searchVO.pageCalculate( board2Service.selectBoardCount(searchVO) );
    	List<?> listview   = board2Service.selectBoardList(searchVO);
    	
    	modelMap.addAttribute("listview", listview);
    	modelMap.addAttribute("searchVO", searchVO);
        return "Board2/BoardList";
    }
	/*
	 * 글쓰기와 글수정은 기본적인 FORM이 같으므로 두가지를 따로 하지 않고 하나로 사용한다.
	 * */
	@RequestMapping(value = "/board2Form")
	public String boardForm(HttpServletRequest request,ModelMap modelMap) throws Exception {
		
		String brdNo = request.getParameter("brdNo");
		if(brdNo !=null){
			BoardVO boardInfo = board2Service.selectBoardOne(brdNo);
			List<?> fileList =board2Service.selectBoardFileList(brdNo);
			
			modelMap.addAttribute("boardInfo",boardInfo);
			modelMap.addAttribute("fileList",fileList);
		}
		
        return "Board2/BoardForm";
	}
	
	@RequestMapping(value = "/board2Save", method = RequestMethod.POST)
	public String boardSave(HttpServletRequest request,@ModelAttribute BoardVO boardInfo) throws Exception {
		String[] fileNo = request.getParameterValues("fileNo");
		FileUtil file = new FileUtil();
		List<FileVO> fileList = file.saveAllFiles(boardInfo.getUploadfile());
		board2Service.insertBoard(boardInfo,fileList,fileNo);
	    return "redirect:/board2List";
	}
	
	@RequestMapping(value = "/board2Read")
	public String boardSave(HttpServletRequest request, ModelMap modelMap) throws Exception {
	    
		String brdNo = request.getParameter("brdNo");
		
		board2Service.updateBoard2Read(brdNo);
		BoardVO boardInfo = board2Service.selectBoardOne(brdNo);
		List<?> fielList  = board2Service.selectBoardFileList(brdNo);
		List<?> replyList = board2Service.selectBoardReplyList(brdNo);
		
		modelMap.addAttribute("boardInfo",boardInfo);
		modelMap.addAttribute("fileList", fielList);
		modelMap.addAttribute("replyList",replyList);
		
	    return "Board2/BoardRead";
	}
	
	@RequestMapping(value ="/boardReplySave" , method = RequestMethod.POST)
	public String boardReplySave(HttpServletRequest request,BoardReplyVO boardReplyInfo) throws Exception{
		board2Service.insertBoardReply(boardReplyInfo);
		return "redirect:/board2Read?brdNo="+boardReplyInfo.getBrdNo();
	}
	
	@RequestMapping(value="/boardReplyDelete")
	public String boardReplyDelete(HttpServletRequest request,BoardReplyVO boardReplyInfo) throws Exception{
		board2Service.deleteBoardReply(boardReplyInfo.getReNo());
		return "redirect:/board2Read?brdNo="+boardReplyInfo.getBrdNo();
	}
	
	
	
	@RequestMapping(value = "/board2Delete")
   	public String boardDelete(HttpServletRequest request) throws Exception {
    	
    	String brdNo = request.getParameter("brdNo");
    	
    	board2Service.deleteBoardOne(brdNo);
        
        return "redirect:/board2List";
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
