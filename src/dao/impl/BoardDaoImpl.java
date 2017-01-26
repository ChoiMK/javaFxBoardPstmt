package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.BoardVO;
import dao.BoardDao;

/**
 * @author 최민규
 * @version 1.0
 * 
 * <p>
 * 파일명 : BoardDaoImpl.java <br/>
 * 설명 : 게시판 DAO 구현클래스 <br/>
 * 
 * 수정이력<br/>
 * --------------------------------------------<br/>
 * 수정일자     |수정인|수정내용<br/>
 * --------------------------------------------<br/>
 * 2017.01.24 최민규 최초생성<br/>
 * --------------------------------------------<br/>
 * </p>
 */

public class BoardDaoImpl implements BoardDao {
		
		// 싱글톤 패턴을 사용하기 위한 자신 클래스의 인스턴스
		private static BoardDaoImpl boardDaoImpl = new BoardDaoImpl();

		// 접속할 DB의 종류와 주소가 필요
		private static final String DB_URL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";

		// DB접속 ID
		private static final String DB_USER = "pc09";
		
		// DB접속 패스워드
		private static final String DB_PASSWORD = "java";
		
		private Connection conn = null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;
		
		
		
		private BoardDaoImpl() {
			//new로 새로운 인스턴스를 생성하지 못하도록 막는 생성자
			//접속할 DB의 드라이버를 로딩한다
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		public static BoardDaoImpl getInstance(){
			return boardDaoImpl;
		}
		
		
		//Connection 하는 부분을 메서드로 만들어 중복 코딩을 하지 말도록 하자.
		public void connect(){
			try {
				conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		//커넥션을 끊는 부분도 따로 메서드로 만들어 관리
		public void disconnect(){
			try {
				// ResultSet 닫기
				if(rs!=null){
					rs.close();
				}
				// statement 닫기
				if (pstmt != null) {
					pstmt.close();
				}
				// Connection을 닫는다
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	@Override
	public List<BoardVO> selectBoardList(String boardTitle, String boardWriter) {
		List<BoardVO> resultList = new ArrayList<BoardVO>();
		
		this.connect();
		
		String query=" SELECT ";
		query+= " BOARD_ID, BOARD_TITLE, BOARD_WRITER, BOARD_DATE, BOARD_CONTENT ";
		query+= " FROM FX_BOARD ";
		query+= " WHERE 1=1 ";
		
		if(boardTitle!=null &&!boardTitle.equals("")){
			query += " AND BOARD_TITLE LIKE '%'||?||'%' ";
		}
		
		if(boardWriter!=null &&!boardWriter.equals("")){
			query += " AND BOARD_WRITER = ? ";
		}
		
		try {
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			if(boardTitle!=null &&!boardTitle.equals("")){
				pstmt.setString(index, boardTitle);
				index++;
			}
			if(boardWriter!=null &&!boardWriter.equals("")){
				pstmt.setString(index, boardWriter);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				BoardVO vo = new BoardVO();
				
				String rsBoardId = rs.getString("BOARD_ID");
				String rsBoardTitle = rs.getString("BOARD_TITLE");
				String rsBoardWriter = rs.getString("BOARD_WRITER");
				String rsBoardDate = rs.getString("BOARD_DATE");
				String rsBoardContent = rs.getString("BOARD_CONTENT");
				
				vo.setBoardId(rsBoardId);
				vo.setBoardTitle(rsBoardTitle);
				vo.setBoardWriter(rsBoardWriter);
				vo.setBoardDate(rsBoardDate);
				vo.setBoardContent(rsBoardContent);
				
				resultList.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		
		return resultList;
	}

	@Override
	public BoardVO selectBoard(String boardId) {
		
		BoardVO vo = null;
		this.connect();
		String query = " SELECT BOARD_ID, BOARD_TITLE, BOARD_WRITER, BOARD_DATE, BOARD_CONTENT ";
			   query +=" FROM FX_BOARD WHERE BOARD_ID=? ";
				
				try {
					pstmt = conn.prepareStatement(query);
					
					pstmt.setString(1, boardId);
					
					rs = pstmt.executeQuery();
					
					if(rs.next()){
						vo = new BoardVO();
						
						String rsBoardId = rs.getString("BOARD_ID");
						String rsBoardTitle = rs.getString("BOARD_TITLE");
						String rsBoardWriter = rs.getString("BOARD_WRITER");
						String rsBoardDate = rs.getString("BOARD_DATE");
						String rsBoardContent = rs.getString("BOARD_CONTENT");
						
						 vo.setBoardId(rsBoardId);
				         vo.setBoardTitle(rsBoardTitle);
				         vo.setBoardWriter(rsBoardWriter);
				         vo.setBoardDate(rsBoardDate);
				         vo.setBoardContent(rsBoardContent);
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally{
					this.disconnect();
				}
				
				return vo;
		
	}
		
		

	@Override
	public void insertBoard(BoardVO vo) {
		this.connect();
		String query = " INSERT INTO FX_BOARD ";
	      query += " (BOARD_ID, BOARD_TITLE, BOARD_WRITER, BOARD_DATE, BOARD_CONTENT) ";
	      query += " VALUES ";
	      query += " (SQ_FX_BOARD.NEXTVAL,?,?,SYSDATE,?) ";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, vo.getBoardTitle());
			pstmt.setString(2, vo.getBoardWriter());
			pstmt.setString(3, vo.getBoardContent());
			
			pstmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		
	}

	@Override
	public void updateBoard(BoardVO vo) {
		this.connect();
		
		String query = "UPDATE FX_BOARD SET ";
			query += " BOARD_TITLE=?, ";
			query += " BOARD_WRITER=?, ";
			query += " BOARD_DATE=SYSDATE, ";
			query += " BOARD_CONTENT=? ";
			query += " WHERE BOARD_ID=? ";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, vo.getBoardTitle());
			pstmt.setString(2, vo.getBoardWriter());
			pstmt.setString(3, vo.getBoardContent());
			pstmt.setString(4, vo.getBoardId());
			
			
			pstmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
	}

	@Override
	public void deleteBoard(String boardId) {
		this.connect();
		
		String query = "DELETE FROM FX_BOARD WHERE BOARD_ID=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, boardId);
			
			pstmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
	}

}
