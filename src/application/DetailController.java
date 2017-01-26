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
	
	//�����ư Ŭ�� �� �̺�Ʈ
	@FXML
	public void onclickSave(ActionEvent event){
		//�Խ��� ������ ���� vo��ü
		BoardVO vo = new BoardVO();
		
		//�۹�ȣ
		vo.setBoardId(label_boardId.getText());
		//����
		vo.setBoardTitle(textField_boardTitle.getText());
		//�ۼ���
		vo.setBoardWriter(label_boardWriter.getText());
		//�۳���
		vo.setBoardContent(textArea_boardContent.getText());
		
		if(label_boardId.getText().equals("")){
			//�ۼ��� ���� DB�� ����
			biz.insertBoard(vo);
		}else{
			biz.updateBoard(vo);
		}
			
	
		//�θ�â�� �Խ��ǿ� ����Ʈ�� �����Ѵ�.
		parentController.onclickSearch(null);
		
		//�ڱ��ڽ��� â�� �ݴ´�.
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
		
		//�ڱ��ڽ��� â�� �ݴ´�.
		Stage stage = (Stage)label_boardId.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void onclickCannel(ActionEvent event){
		//�ڱ��ڽ��� â�� �ݴ´�.
		Stage stage = (Stage)label_boardId.getScene().getWindow();
		stage.close();
	}
	
	
}
