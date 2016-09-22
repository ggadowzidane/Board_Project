package com.board.test2;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.test.common.FileVO;
import com.board.test.common.SearchVO;


@Service
public class Board2Service {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<?> selectBoardList(SearchVO param) throws Exception{
		return sqlSession.selectList("selectBoard2List",param);
	}
	public List<?> selectBoardFileList(String param) throws Exception{
		return sqlSession.selectList("selectBoardFileList",param);
	}
	public List<?> selectBoardReplyList(String param) throws Exception{
		return sqlSession.selectList("selectBoardReplyList",param);
	}
	
	public Integer selectBoardCount(SearchVO param) throws Exception {
		return sqlSession.selectOne("selectBoard2Count",param);
    }
	
	public BoardVO selectBoardOne(String param) throws Exception {
		return sqlSession.selectOne("selectBoard2One", param);
    }
	public void insertBoard(BoardVO param,List<FileVO> fileList,String[] fileNo) throws Exception {
		if(param.getBrdNo() == null || param.getBrdNo().equals(""))
			sqlSession.insert("insertBoard2",param);
		else	
			sqlSession.update("updateBoard2",param);
		
		if(fileNo != null){
			HashMap p = new HashMap();
			p.put("fileNo", fileNo);
			sqlSession.insert("deleteBoard2File",p);
		}
		
		//글 저장할때 파일도 저장
		for(FileVO file : fileList){
			file.setParentPK(param.getBrdNo());
			sqlSession.insert("insertBoard2File",file);
		}
	
        //sqlSession.insert("insertBoard2", param);
	}
	/*
	public void updateBoard(BoardVO param) throws Exception {
		sqlSession.insert("updateBoard2", param);
    }
    */
	
	public void updateBoard2Read(String param) throws Exception {
	    sqlSession.update("updateBoard2Read", param);
	}
	
	public void insertBoardReply(BoardReplyVO param) throws Exception{
		System.out.println(param.getReNo());
		if(param.getReNo() == null || "".equals(param.getReNo())){
			sqlSession.insert("insertBoardReply",param);
		}else{
			System.out.println("여기가 되야지");
			sqlSession.insert("updateBoardReply",param);
		}
	}
	public void deleteBoardOne(String param) throws Exception {
		sqlSession.delete("deleteBoard2One", param);
    }
	public void deleteBoardReply(String param) throws Exception{
		sqlSession.delete("deleteBoardReply",param);
	}
}
