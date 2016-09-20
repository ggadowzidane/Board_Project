package com.board.test2;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.test2.BoardVO;

@Service
public class Board2Service {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<?> selectBoardList() throws Exception{
		return sqlSession.selectList("selectBoard1List");
	}
	
	public BoardVO selectBoardOne(String param) throws Exception {
		return sqlSession.selectOne("selectBoard2One", param);
    }
	
	public void insertBoard(BoardVO param) throws Exception {
        sqlSession.insert("insertBoard2", param);
	}
	
	public void updateBoard(BoardVO param) throws Exception {
		sqlSession.insert("updateBoard2", param);
    }
	
	public void updateBoard2Read(String param) throws Exception {
	    sqlSession.update("updateBoard2Read", param);
	}
}
