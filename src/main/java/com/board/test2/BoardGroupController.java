package com.board.test2;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.board.test.common.TreeMaker;
import com.board.test.common.TreeVO;

@Controller
public class BoardGroupController {
	
	@Autowired
	private BoardGroupService boardGroupService;
	
	@RequestMapping(value="/boardGroupList")
	public String boardList(ModelMap modelMap){
		List<TreeVO> list = boardGroupService.selectBoardGroupList();
		
		String tree = new TreeMaker().makeTreeByHierachy(list);
		
		modelMap.addAttribute("treeStr",tree);
		
		return "Board2/BoardGroupList";
	}
	
	@RequestMapping(value="/boardGroupRead")
	public void boardRead(HttpServletRequest request,HttpServletResponse response){
		
		String bgNo = request.getParameter("bgNo");
		
		BoardGroupVO bgInfo = boardGroupService.selectBoardGroupOne(bgNo);
		
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");
		
		try{
			response.getWriter().print(mapper.writeValueAsString(bgInfo));
		} catch (Exception e){
			System.out.println("게시판 그룹에 문제가 생김");
		}
	}
	
	 @RequestMapping(value = "/boardGroupSave")
     public void boardSave(HttpServletResponse response, BoardGroupVO bgInfo){
      
		 boardGroupService.insertBoard(bgInfo);
      
		 ObjectMapper mapper = new ObjectMapper();
		 response.setContentType("application/json;charset=UTF-8");
		  
		 try {
		      response.getWriter().print( mapper.writeValueAsString(bgInfo));
		 } catch (Exception ex) {
		      System.out.println("오류: 게시판 그룹에 문제가 발생했습니다.");
		 }
	 }
}
