package com.board.test2;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Board2Controller {

	@Autowired
	private Board2Service board2Service;
	
	@RequestMapping(value = "/board2List")
   	public String boardList(ModelMap modelMap) throws Exception {
    	List<?> listview   = board2Service.selectBoardList();

    	modelMap.addAttribute("listview", listview);
        return "Board2/BoardList";
    }
	/*
	 * 글쓰기와 글수정은 기본적인 FORM이 같으므로 두가지를 따로 하지 않고 하나로 사용한다.
	 * */
	@RequestMapping(value = "/board2Form", method = RequestMethod.POST )
	public String boardForm(HttpServletRequest request,ModelMap modelMap) throws Exception {
		
		String brdNo = request.getParameter("brdNo");
		if(brdNo !=null){
			BoardVO boardInfo = board2Service.selectBoardOne(brdNo);
			modelMap.addAttribute("boardInfo",boardInfo);
		}
		
        return "Board2/BoardForm";
	}
	
	@RequestMapping(value = "/board2Save")
	public String boardSave(@ModelAttribute BoardVO boardInfo) throws Exception {
	    
		if(boardInfo.getBrdNo() ==null)
			board2Service.insertBoard(boardInfo);
		else
			board2Service.updateBoard(boardInfo);
	        
	    return "redirect:/board2List";
	}
	
	@RequestMapping(value = "/board2Read")
	public String boardSave(HttpServletRequest request, ModelMap modelMap) throws Exception {
	    
		String brdNo = request.getParameter("brdNo");
		board2Service.updateBoard2Read(brdNo);
		BoardVO boardInfo = board2Service.selectBoardOne(brdNo);
		modelMap.addAttribute("boardInfo",boardInfo);
		
	    return "Board2/BoardRead";
	}
}
