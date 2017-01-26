package dao;

import java.util.List;

import vo.BoardVO;


/**
    * @author 최민규
    * @version 1.0
    * 
    * <p>
    * 파일명 : BoardDao.java <br/>
    * 설명 : 게시판 DAO 인터페이스 <br/>
    * 
    * 수정 이력<br/>
    * -------------------------------------------<br/>
    * 수정일자      |수정인|수정내용<br/>
    * -------------------------------------------<br/>
    * 2017.01.24  최민규 최초생성<br/>
    * -------------------------------------------<br/>
    * </p>
    * 
    */
public interface BoardDao {
   
   /**
    * selectBoardList - 게시판 리스트 조회
    * @param String boardTitle
    * @param String boardWriter
    * @return java.util.List<BoardVO>
    */
   public List<BoardVO> selectBoardList(String boardTitle, String boardWriter);
   
   /**
    * selectBoard - 게시판 상세글 조회
    * @param String boardId
    * @return BoardVO
    */
   public BoardVO selectBoard(String boardId);
   
   /**
    * insertBoard - 게시판 글 입력
    * @param BoardVO vo
    * @return null
    */
   public void insertBoard(BoardVO vo);
   /**
    * updateBoard - 게시판 글 수정
    * @param BoardVO vo
    * @return null
    */
   public void updateBoard(BoardVO vo);
   /**
    * deleteBoard - 게시판 글 삭제
    * @param String boardId
    * @return null
    */
   public void deleteBoard(String boardId);
   
}














