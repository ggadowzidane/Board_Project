package com.board.test2;

import com.board.test2.BoardGroupVO;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.test.common.TreeVO;

@Service
public class BoardGroupService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<TreeVO> selectBoardGroupList(){
		return sqlSession.selectList("selectBoardGroupList");
	}
	
	public BoardGroupVO selectBoardGroupOne(String param){
        return sqlSession.selectOne("selectBoardGroupOne", param);
	}
	
	public void insertBoard(BoardGroupVO param){
	    if ("".equals(param.getBgParent())) param.setBgParent(null);
	        
	    if (param.getBgNo()==null || "".equals(param.getBgNo()))
	         sqlSession.insert("insertBoardGroup", param);
	    else sqlSession.insert("updateBoardGroup", param);
    } 
	
	 public BoardGroupVO selectBoardGroupOne4Used(String param) {
		 return sqlSession.selectOne("selectBoardGroupOne4Used", param);
	 }
	 
	 public void deleteBoardGroup(String param) {
	        sqlSession.delete("deleteBoardGroup", param);
	 }
}
