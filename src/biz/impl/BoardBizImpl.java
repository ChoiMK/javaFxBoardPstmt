package biz.impl;

import java.util.List;

import dao.BoardDao;
import dao.impl.BoardDaoImpl;
import vo.BoardVO;
import biz.BoardBiz;

/**
 * @author �ֹα�
 * @version 1.0
 * 
 * <p>
 * ���ϸ� : BoardBizImpl.java <br/>
 * ���� : �Խ��� Biz ����Ŭ���� <br/>
 * 
 * �����̷�<br/>
 * --------------------------------------------<br/>
 * ��������     |������|��������<br/>
 * --------------------------------------------<br/>
 * 2017.01.24 �ֹα� ���ʻ���<br/>
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
