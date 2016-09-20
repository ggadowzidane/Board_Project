package com.board.test;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Board1Service {
		@Autowired
		private SqlSessionTemplate sqlSession;
		
		public List<?> selectBoardList() throws Exception{
			return sqlSession.selectList("selectBoard1List");
		}
		
		public void insertBoard(BoardVO param) throws Exception {
	        sqlSession.insert("insertBoard1", param);
		}
		
		public void updateBoard(BoardVO param) throws Exception {
			sqlSession.insert("updateBoard1", param);
	    }
	 
	    public BoardVO selectBoardOne(String param) throws Exception {
			return sqlSession.selectOne("selectBoard1One", param);
	    }
	    
	    public void deleteBoardOne(String param) throws Exception {
			sqlSession.delete("deleteBoard1One", param);
	    }
}
