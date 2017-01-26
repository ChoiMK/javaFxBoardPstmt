package biz.impl;

import java.util.List;

import dao.BoardDao;
import dao.impl.BoardDaoImpl;
import vo.BoardVO;
import biz.BoardBiz;

/**
 * @author 최민규
 * @version 1.0
 * 
 * <p>
 * 파일명 : BoardBizImpl.java <br/>
 * 설명 : 게시판 Biz 구현클래스 <br/>
 * 
 * 수정이력<br/>
 * --------------------------------------------<br/>
 * 수정일자     |수정인|수정내용<br/>
 * --------------------------------------------<br/>
 * 2017.01.24 최민규 최초생성<br/>
 * --------------------------------------------<br/>
 * </p>
 */

public class BoardBizImpl implements BoardBiz{
	
	private BoardDao dao = BoardDaoImpl.getInstance();
	
	
	@Override
	public List<BoardVO> selectBoardList(String boardTitle, String boardWriter) {
		return dao.selectBoardList(boardTitle, boardWriter);
	}

	@Override
	public BoardVO selectBoard(String boardId) {
		
		return dao.selectBoard(boardId);
	}

	@Override
	public void insertBoard(BoardVO vo) {
		dao.insertBoard(vo);
		
	}

	@Override
	public void updateBoard(BoardVO vo) {
		dao.updateBoard(vo);
	}

	@Override
	public void deleteBoard(String boardId) {
		dao.deleteBoard(boardId);
	}
	
}
