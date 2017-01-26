package application;

import java.net.URL;
import java.util.ResourceBundle;

import vo.BoardVO;
import biz.BoardBiz;
import biz.impl.BoardBizImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DetailController implements Initializable {

	@FXML private Label label_boardId;
	@FXML private TextField textField_boardTitle;
	@FXML private Label label_boardWriter;
	@FXML private Label label_boardDate;
	@FXML private TextArea textArea_boardContent;
	
	private BoardBiz biz = new BoardBizImpl();
	private MainController parentController;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	//저장버튼 클릭 시 이벤트
	@FXML
	public void onclickSave(ActionEvent event){
		//게시판 정보를 담을 vo객체
		BoardVO vo = new BoardVO();
		
		//글번호
		vo.setBoardId(label_boardId.getText());
		//제목
		vo.setBoardTitle(textField_boardTitle.getText());
		//작성자
		vo.setBoardWriter(label_boardWriter.getText());
		//글내용
		vo.setBoardContent(textArea_boardContent.getText());
		
		if(label_boardId.getText().equals("")){
			//작성한 글을 DB에 저장
			biz.insertBoard(vo);
		}else{
			biz.updateBoard(vo);
		}
			
	
		//부모창의 게시판에 리스트를 갱신한다.
		parentController.onclickSearch(null);
		
		//자기자신의 창을 닫는다.
		Stage stage = (Stage)label_boardId.getScene().getWindow();
		stage.close();
	}
	
	public void setParentController(MainController parentController){
		this.parentController = parentController;
	}
	
	
	public void setBoardData(String boardId){
		BoardVO vo = biz.selectBoard(boardId);
		label_boardId.setText(vo.getBoardId());
		textField_boardTitle.setText(vo.getBoardTitle());
		label_boardWriter.setText(vo.getBoardWriter());
		label_boardDate.setText(vo.getBoardDate());
		textArea_boardContent.setText(vo.getBoardContent());
		
	}
	
	@FXML
	public void onclickDelete(ActionEvent event){
		String boardId = label_boardId.getText();
		
		if(!boardId.equals("")){
			biz.deleteBoard(boardId);
		}
		
		parentController.onclickSearch(null);
		
		//자기자신의 창을 닫는다.
		Stage stage = (Stage)label_boardId.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void onclickCannel(ActionEvent event){
		//자기자신의 창을 닫는다.
		Stage stage = (Stage)label_boardId.getScene().getWindow();
		stage.close();
	}
	
	
}
