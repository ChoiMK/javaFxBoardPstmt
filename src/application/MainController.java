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
	
	//데이터를 불러오기 위한 비즈니스 로직
	private BoardBiz biz = new BoardBizImpl();
	
	private MainController mainController = this;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setComboBox();
		setTableView();
		setTableData(null,null);
	}
	
	//테이블뷰 설정
	public void setTableView(){
		//테이블뷰에 추가할 테이블 컬럼
		TableColumn<BoardVO, String> tableCol_boardId = new TableColumn<>("글번호");
		TableColumn<BoardVO, String> tableCol_boardTitle = new TableColumn<>("글제목");
		TableColumn<BoardVO, String> tableCol_boardWriter = new TableColumn<>("작성자");
		TableColumn<BoardVO, String> tableCol_boardDate = new TableColumn<>("작성일자");
		
		//테이블컬럼과 데이터와 바인드 - 프로퍼티 밸류 팩토리를 통해 
		//BoardVO의 String값인 boardId를 이 컬럼과 매칭시켜 바인드한다.
		tableCol_boardId.setCellValueFactory(new PropertyValueFactory<BoardVO,String>("boardId"));
		tableCol_boardTitle.setCellValueFactory(new PropertyValueFactory<BoardVO,String>("boardTitle"));
		tableCol_boardWriter.setCellValueFactory(new PropertyValueFactory<BoardVO,String>("boardWriter"));
		tableCol_boardDate.setCellValueFactory(new PropertyValueFactory<BoardVO,String>("boardDate"));
		
		
		tableCol_boardId.setMinWidth(100);
		tableCol_boardTitle.setMinWidth(300);
		tableCol_boardWriter.setMinWidth(100);
		tableCol_boardDate.setMinWidth(100);
		
		//설정한 테이블 컴럼을 테이블뷰에 추가한다.
		tableView_boardList.getColumns().addAll(tableCol_boardId,tableCol_boardTitle,tableCol_boardWriter,tableCol_boardDate);
		
		//테이블뷰에 마우스 클릭 이벤트를 추가한다. - 더블클릭하면 게시판 상세내용 팝업이 뜨도록 할 예정
		tableView_boardList.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event){
				//이벤트가 마우스 주버튼 클릭이고, 마우스 클릭수가 2번이면 더블클릭이라고 판단
				if(event.isPrimaryButtonDown()&&event.getClickCount()==2){
					//테이블뷰에서 선택된 (더블클릭 한)아이템을 가져온다.
					BoardVO vo = (BoardVO)tableView_boardList.getSelectionModel().getSelectedItem();
					
					//팝업창을 불러오기 위한 새로운 스테이지(윈도우)
					Stage stage = new Stage();
				
					try {
						//Detail.fxml로부터 컴포넌트를 불러와서 Scene에 배치한다.
						FXMLLoader loader = new FXMLLoader(getClass().getResource("Detail.fxml"));
						FlowPane pane = (FlowPane)loader.load();
						
						//디테일 팝업창의 컨트롤러를 가져옴
						DetailController detailController = loader.getController();
						//디테일 컨트롤러에 게시판 글번호를 매개변수로 하여 게시판 상세정보를 셋팅
						detailController.setBoardData(vo.getBoardId());
						//디테일 팝업창에서 정보를 수정한 뒤 부모창에서도 바뀐 데이터가 갱신될 수 있도록
						//메인 컨트롤러를 알려줌
						detailController.setParentController(mainController);
						
						//fxml에서 불러온 패널을 씬에 설정함
						Scene scene = new Scene(pane,600,400);
						
						//스테이지에 씬을 붙임
						stage.setScene(scene);
						
						//스테이지를 보여줌
						stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				
				}
			}
		});
		
	}
	
	//콤보박스 설정 메소드
	public void setComboBox(){
		//콤보박스에 값을 "제목","작성자",로 추가
		comboBox_search.getItems().addAll("제목","작성자");
		//초기값은 "제목"이 되도록 설정
		comboBox_search.setValue("제목");
	}

	//테이블뷰에 데이터를 설정하는 메서드
	public void setTableData(String boardTitle, String boardWriter){
		//게시판 리스트를 불러옴
		List<BoardVO> list = biz.selectBoardList(boardTitle, boardWriter);
		//테이블뷰에 바인드 될 옵저버블리스트
		ObservableList<BoardVO> boardList;
		//불러온데이터를 FXCollectins를 통해 옵저버블리스트로 변환
		boardList = FXCollections.observableArrayList(list);
		//테이블뷰에 옵저버블 리스트를 바인드
		tableView_boardList.setItems(boardList);
	}
	
	@FXML
	public void onclickSearch(ActionEvent event){
		String boardTitle = null;
		String boardWriter = null;
		
		//콤보박스의 값이 "제목" 이면 검색조건값이 제목으로
		//"작성자" 이면 검색조건값이 작성자로 매개변수를 설정
		
		if(comboBox_search.getValue().toString().equals("제목")){
			boardTitle = textField_search.getText();
		}else if(comboBox_search.getValue().toString().equals("작성자")){
			boardWriter = textField_search.getText();
		}

		//테이블 데이터 설정 메서드에 두 매개변수를 보냄
		//하지만 매개변수 중 하나만 조건 값이 들어있음
		this.setTableData(boardTitle, boardWriter);
	}
	
	//삭제버튼클릭
	@FXML
	 public void onclickDelete(ActionEvent event){
		//테이블뷰에서 선택된 행의 게시판 정보를 가져옴
		BoardVO vo = (BoardVO)tableView_boardList.getSelectionModel().getSelectedItem();
		//db에서 해당 데이터를 삭제
		biz.deleteBoard(vo.getBoardId());
		//db에서 삭제후 게시판 리스트에서도 삭제
		tableView_boardList.getItems().remove(vo);
		
	}
	
	//게시판 내용을 새로 입력 클릭 이벤트
	@FXML
	public void clickInsert(ActionEvent event){
		//팝업창을 불러오기 위한 스테이지 생성
		Stage stage = new Stage();
		
		//Detail.fxml로부터 컴포넌트를 불러와서 Scene에 배치한다.
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
