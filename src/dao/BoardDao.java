package dao;

import java.util.List;

import vo.BoardVO;


/**
    * @author �ֹα�
    * @version 1.0
    * 
    * <p>
    * ���ϸ� : BoardDao.java <br/>
    * ���� : �Խ��� DAO �������̽� <br/>
    * 
    * ���� �̷�<br/>
    * -------------------------------------------<br/>
    * ��������      |������|��������<br/>
    * -------------------------------------------<br/>
    * 2017.01.24  �ֹα� ���ʻ���<br/>
    * -------------------------------------------<br/>
    * </p>
    * 
    */
public interface BoardDao {
   
   /**
    * selectBoardList - �Խ��� ����Ʈ ��ȸ
    * @param String boardTitle
    * @param String boardWriter
    * @return java.util.List<BoardVO>
    */
   public List<BoardVO> selectBoardList(String boardTitle, String boardWriter);
   
   /**
    * selectBoard - �Խ��� �󼼱� ��ȸ
    * @param String boardId
    * @return BoardVO
    */
   public BoardVO selectBoard(String boardId);
   
   /**
    * insertBoard - �Խ��� �� �Է�
    * @param BoardVO vo
    * @return null
    */
   public void insertBoard(BoardVO vo);
   /**
    * updateBoard - �Խ��� �� ����
    * @param BoardVO vo
    * @return null
    */
   public void updateBoard(BoardVO vo);
   /**
    * deleteBoard - �Խ��� �� ����
    * @param String boardId
    * @return null
    */
   public void deleteBoard(String boardId);
   
}














