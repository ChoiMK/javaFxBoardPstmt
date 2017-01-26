package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import biz.BoardBiz;
import biz.impl.BoardBizImpl;
import vo.BoardVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MainController implements Initializable {

	
	@FXML private TableView  tableView_boardList;
	@FXML private ComboBox   comboBox_search;
	@FXML private TextField  textField_search;
	
	//�����͸� �ҷ����� ���� ����Ͻ� ����
	private BoardBiz biz = new BoardBizImpl();
	
	private MainController mainController = this;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setComboBox();
		setTableView();
		setTableData(null,null);
	}
	
	//���̺�� ����
	public void setTableView(){
		//���̺�信 �߰��� ���̺� �÷�
		TableColumn<BoardVO, String> tableCol_boardId = new TableColumn<>("�۹�ȣ");
		TableColumn<BoardVO, String> tableCol_boardTitle = new TableColumn<>("������");
		TableColumn<BoardVO, String> tableCol_boardWriter = new TableColumn<>("�ۼ���");
		TableColumn<BoardVO, String> tableCol_boardDate = new TableColumn<>("�ۼ�����");
		
		//���̺��÷��� �����Ϳ� ���ε� - ������Ƽ ��� ���丮�� ���� 
		//BoardVO�� String���� boardId�� �� �÷��� ��Ī���� ���ε��Ѵ�.
		tableCol_boardId.setCellValueFactory(new PropertyValueFactory<BoardVO,String>("boardId"));
		tableCol_boardTitle.setCellValueFactory(new PropertyValueFactory<BoardVO,String>("boardTitle"));
		tableCol_boardWriter.setCellValueFactory(new PropertyValueFactory<BoardVO,String>("boardWriter"));
		tableCol_boardDate.setCellValueFactory(new PropertyValueFactory<BoardVO,String>("boardDate"));
		
		
		tableCol_boardId.setMinWidth(100);
		tableCol_boardTitle.setMinWidth(300);
		tableCol_boardWriter.setMinWidth(100);
		tableCol_boardDate.setMinWidth(100);
		
		//������ ���̺� �ķ��� ���̺�信 �߰��Ѵ�.
		tableView_boardList.getColumns().addAll(tableCol_boardId,tableCol_boardTitle,tableCol_boardWriter,tableCol_boardDate);
		
		//���̺�信 ���콺 Ŭ�� �̺�Ʈ�� �߰��Ѵ�. - ����Ŭ���ϸ� �Խ��� �󼼳��� �˾��� �ߵ��� �� ����
		tableView_boardList.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event){
				//�̺�Ʈ�� ���콺 �ֹ�ư Ŭ���̰�, ���콺 Ŭ������ 2���̸� ����Ŭ���̶�� �Ǵ�
				if(event.isPrimaryButtonDown()&&event.getClickCount()==2){
					//���̺�信�� ���õ� (����Ŭ�� ��)�������� �����´�.
					BoardVO vo = (BoardVO)tableView_boardList.getSelectionModel().getSelectedItem();
					
					//�˾�â�� �ҷ����� ���� ���ο� ��������(������)
					Stage stage = new Stage();
				
					try {
						//Detail.fxml�κ��� ������Ʈ�� �ҷ��ͼ� Scene�� ��ġ�Ѵ�.
						FXMLLoader loader = new FXMLLoader(getClass().getResource("Detail.fxml"));
						FlowPane pane = (FlowPane)loader.load();
						
						//������ �˾�â�� ��Ʈ�ѷ��� ������
						DetailController detailController = loader.getController();
						//������ ��Ʈ�ѷ��� �Խ��� �۹�ȣ�� �Ű������� �Ͽ� �Խ��� �������� ����
						detailController.setBoardData(vo.getBoardId());
						//������ �˾�â���� ������ ������ �� �θ�â������ �ٲ� �����Ͱ� ���ŵ� �� �ֵ���
						//���� ��Ʈ�ѷ��� �˷���
						detailController.setParentController(mainController);
						
						//fxml���� �ҷ��� �г��� ���� ������
						Scene scene = new Scene(pane,600,400);
						
						//���������� ���� ����
						stage.setScene(scene);
						
						//���������� ������
						stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				
				}
			}
		});
		
	}
	
	//�޺��ڽ� ���� �޼ҵ�
	public void setComboBox(){
		//�޺��ڽ��� ���� "����","�ۼ���",�� �߰�
		comboBox_search.getItems().addAll("����","�ۼ���");
		//�ʱⰪ�� "����"�� �ǵ��� ����
		comboBox_search.setValue("����");
	}

	//���̺�信 �����͸� �����ϴ� �޼���
	public void setTableData(String boardTitle, String boardWriter){
		//�Խ��� ����Ʈ�� �ҷ���
		List<BoardVO> list = biz.selectBoardList(boardTitle, boardWriter);
		//���̺�信 ���ε� �� ����������Ʈ
		ObservableList<BoardVO> boardList;
		//�ҷ��µ����͸� FXCollectins�� ���� ����������Ʈ�� ��ȯ
		boardList = FXCollections.observableArrayList(list);
		//���̺�信 �������� ����Ʈ�� ���ε�
		tableView_boardList.setItems(boardList);
	}
	
	@FXML
	public void onclickSearch(ActionEvent event){
		String boardTitle = null;
		String boardWriter = null;
		
		//�޺��ڽ��� ���� "����" �̸� �˻����ǰ��� ��������
		//"�ۼ���" �̸� �˻����ǰ��� �ۼ��ڷ� �Ű������� ����
		
		if(comboBox_search.getValue().toString().equals("����")){
			boardTitle = textField_search.getText();
		}else if(comboBox_search.getValue().toString().equals("�ۼ���")){
			boardWriter = textField_search.getText();
		}

		//���̺� ������ ���� �޼��忡 �� �Ű������� ����
		//������ �Ű����� �� �ϳ��� ���� ���� �������
		this.setTableData(boardTitle, boardWriter);
	}
	
	//������ưŬ��
	@FXML
	 public void onclickDelete(ActionEvent event){
		//���̺�信�� ���õ� ���� �Խ��� ������ ������
		BoardVO vo = (BoardVO)tableView_boardList.getSelectionModel().getSelectedItem();
		//db���� �ش� �����͸� ����
		biz.deleteBoard(vo.getBoardId());
		//db���� ������ �Խ��� ����Ʈ������ ����
		tableView_boardList.getItems().remove(vo);
		
	}
	
	//�Խ��� ������ ���� �Է� Ŭ�� �̺�Ʈ
	@FXML
	public void clickInsert(ActionEvent event){
		//�˾�â�� �ҷ����� ���� �������� ����
		Stage stage = new Stage();
		
		//Detail.fxml�κ��� ������Ʈ�� �ҷ��ͼ� Scene�� ��ġ�Ѵ�.
		FlowPane pane = null;
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Detail.fxml"));
			pane = (FlowPane)loader.load();
			
			DetailController detailController=(DetailController)loader.getController();
			detailController.setParentController(this);
			
			Scene scene = new Scene(pane, 600, 400);
			stage.setScene(scene);
			
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
				
	}
	



}
