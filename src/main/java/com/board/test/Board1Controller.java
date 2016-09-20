package com.board.test;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Board1Controller {
	
	@Autowired
	private Board1Service board1Service;
	
	/**
	 * @author HanDaeWoon
	 * 게시판의 모든 리스트를 보여준다*/
	@RequestMapping(value = "/board1List")
   	public String boardList(ModelMap modelMap) throws Exception {
    	List<?> listview   = board1Service.selectBoardList();

    	modelMap.addAttribute("listview", listview);
        return "Board1/BoardList";
    }
	
	//게시판 글 작성 FORM화면을 RETURN
	@RequestMapping(value = "/board1Form")
	public String boardForm() throws Exception {
	        return "Board1/BoardForm";
	}
	
	//게시판 글 작성후 저장
	//게시글 작성 jsp에서 input의 name을 @ModelAttribute사용하여 BoardVO객체의 멤버변수에 바로 바인딩해서 반환해준다
	//@ModelAttribute를 사용하지 않으면 바인딩 되지 않는다.
	@RequestMapping(value = "/board1Save")
	public String boardSave(@ModelAttribute BoardVO boardInfo) throws Exception {
	    System.out.println(boardInfo.toString());
		board1Service.insertBoard(boardInfo);
	        
	    return "redirect:/board1List";
	}
	
	//GET 방식으로 넘어온 게시글의 번호로 게시글을 읽기
	//Request객체를 이용해 게시글 번호를 받아서 보여준다
	@RequestMapping(value = "/board1Read")
	public String boardRead(HttpServletRequest request, ModelMap modelMap) throws Exception {
	        
	        String brdno = request.getParameter("brdNo");
	        
	        BoardVO boardInfo = board1Service.selectBoardOne(brdno);
	        
	        modelMap.addAttribute("boardInfo", boardInfo);
	        
	        return "Board1/BoardRead";
	}
	
	//GET 방식으로 글 수정페이지를 보여줌
	@RequestMapping(value = "/board1Update")
	public String boardUpdate(HttpServletRequest request, ModelMap modelMap) throws Exception {
	        
	        String brdno = request.getParameter("brdNo");
	        
	        BoardVO boardInfo = board1Service.selectBoardOne(brdno);
	        
	        modelMap.addAttribute("boardInfo", boardInfo);
	        
	        return "Board1/BoardUpdate";
	}
	
	//게시글 수정후에 저장
	@RequestMapping(value="/board1UpdateSave")
	public String board1UpdateSave(@ModelAttribute("boardInfo") BoardVO boardInfo) throws Exception {
	        
		board1Service.updateBoard(boardInfo);
	        
	        return "redirect:/Board1List";
	}
	
	//게시글 삭제
	//GET 방식으로 글의 번호를 받아와 게시글을 삭제
	@RequestMapping(value = "/board1Delete")
    public String boardDelete(HttpServletRequest request) throws Exception {
     
     String brdno = request.getParameter("brdNo");
     
     board1Service.deleteBoardOne(brdno);
     
     return "redirect:/Board1List";
	}
	
}
