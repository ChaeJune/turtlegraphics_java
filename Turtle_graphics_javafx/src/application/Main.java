package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class Main extends Application {
	int index=0;
	double x = 0;	
	double a = 400;
	int []Push=new int[50];
	int []Pop=new int[50];
	int []Count=new int[50];
	int cnt=0;
	double pi = Math.PI;
	double curx = a / 2, cury = a / 2;
	AnchorPane root = new AnchorPane();
	Canvas canvas = new Canvas(a, a);
	Button button1 = new Button();//Turn
	Button button2 = new Button();//Go
	Button button3 = new Button();//Delete
	Button button4 = new Button();//Do
	Button button5 = new Button();//Repeat
	Button button6 = new Button();//End
	Button button7 = new Button();//Clear
	Button button8 = new Button();//Move
	Button button9 = new Button();//Import
	Button button10 = new Button();//Export
	TextField gt=new TextField("10");
	TextField tt=new TextField("90");
	TextField rt=new TextField("2");
	Image img = new Image("file:화살표.jpg");
	Image img1 = new Image("file:turtle.png");
	ImageView iv = new ImageView();
	ImageView Turtle = new ImageView();
	ListView list = new ListView();
	 public ObservableList data = FXCollections.observableArrayList();
	GraphicsContext gc = canvas.getGraphicsContext2D();
	@Override
	public void start(Stage stage) {
		button1.setText("Turn");
		button1.setOnAction((ActionEvent event) -> {
			double k=Double.parseDouble(tt.getText());
			data.add("Turn:"+k);
			list.setItems(FXCollections.observableArrayList(data));
	           Re(k);
	        });
		button2.setText("Go");
		button2.setOnAction((ActionEvent event) -> {
			double k=Double.parseDouble(gt.getText());
			data.add("Go:"+k);
			list.setItems(FXCollections.observableArrayList(data));  
		//	Go(k);
	           
	        });
		button3.setText("Delete");
		button3.setOnAction((ActionEvent event) -> {
	          Del();
	        });
		
		button4.setText("Do");
		button4.setOnAction((ActionEvent event) -> {
	          Do(stage);
	        });
		button5.setText("Repeat");
		button5.setOnAction((ActionEvent event) -> {
	          Repeat();
	        });
		button6.setText("End");
		button6.setOnAction((ActionEvent event) -> {
	          End();
	        });
		button7.setText("Clear");
		button7.setOnAction((ActionEvent event) -> {
	          Clear();
	        });
		initUI(stage);
	}

	
	private void initUI(Stage stage) {

		button9.setText("Load");
		button9.setOnAction((ActionEvent event) -> {
			
      //  FileChooser fc= new FileChooser();
   	    //fc.getExtensionFilters().add(new ExtensionFilter("List Files","*.txt"));
	    //File selectedFile = fc.showOpenDialog(stage);
	        
			try {
				 File f = new File("C:/Temp/list.txt");
		FileReader fr = new FileReader(f);
		int readCharNo;
		char[]cbuf =new char[20000];
		while( (readCharNo = fr.read(cbuf) )!=-1)
		{
			String d = new String(cbuf,0,readCharNo);
			System.out.println(d);
			StringTokenizer tk = new StringTokenizer(d,"\r\n");
			int temp=Integer.parseInt(tk.nextToken());
			for(int i=0;i<temp;i++)
			{
				data.add(tk.nextToken());
			}
		}
	
		fr.close();
				 //	data=(ObservableList)ois.readObject();
				list.setItems(FXCollections.observableArrayList(data));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		});	
		
		button10.setText("Save");
		
		button10.setOnAction((ActionEvent event) -> {
			/*
	      FileChooser fc= new FileChooser();
	         fc.getExtensionFilters().add(new ExtensionFilter("List Files","*.txt"));
	         File selectedFile = fc.showSaveDialog(stage);
	         */
			try {
				File file = new File("C:/Temp/list.txt");
				file.delete();
				FileWriter fw=new FileWriter(file,true);				
				fw.write(String.valueOf(data.size())+"\r\n");
				  for(int i=0;i<data.size();i++){
					  fw.write(data.get(i).toString()+"\r\n");
				  }
				  fw.flush();
				  fw.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		});	
		
		
		

		drawLines(gc);
		gc.setStroke(Color.RED.brighter());
		gc.setLineWidth(5);
		drawEdge();
//		doDrawing(gc);
		AnchorPane.setTopAnchor(button1,a+20.0);
		AnchorPane.setLeftAnchor(button1,0.0);
		button1.setPrefWidth(100.0);
		
		AnchorPane.setTopAnchor(button2,a+70.0);
		AnchorPane.setLeftAnchor(button2,0.0);
		button2.setPrefWidth(100.0);
		
		AnchorPane.setTopAnchor(button3,a+220.0);
		AnchorPane.setLeftAnchor(button3,0.0);
		button3.setPrefWidth(100.0);
		
		
		AnchorPane.setTopAnchor(button4,a+170.0);
		AnchorPane.setLeftAnchor(button4,0.0);
		button4.setPrefWidth(100.0);
		
		AnchorPane.setTopAnchor(button5,a+120.0);
		AnchorPane.setLeftAnchor(button5,0.0);
		button5.setPrefWidth(100.0);
		
		AnchorPane.setTopAnchor(button6,a+120.0);
		AnchorPane.setLeftAnchor(button6,200.0);
		button6.setPrefWidth(100.0);
		
		AnchorPane.setTopAnchor(button7,a+220.0);
		AnchorPane.setLeftAnchor(button7,200.0);
		button7.setPrefWidth(100.0);
		
		AnchorPane.setTopAnchor(button9,a+270.0);
		AnchorPane.setLeftAnchor(button9,0.0);
		button9.setPrefWidth(100.0);
		
		AnchorPane.setTopAnchor(button10,a+270.0);
		AnchorPane.setLeftAnchor(button10,200.0);
		button10.setPrefWidth(100.0);
		
		AnchorPane.setTopAnchor(gt,a+70.0);
		AnchorPane.setLeftAnchor(gt,100.0);
		gt.setAlignment(Pos.CENTER);	
		
		AnchorPane.setTopAnchor(tt,a+20.0);
		AnchorPane.setLeftAnchor(tt,100.0);
		tt.setAlignment(Pos.CENTER);	
		
		
		AnchorPane.setTopAnchor(rt,a+120.0);
		AnchorPane.setLeftAnchor(rt,100.0);
		rt.setAlignment(Pos.CENTER);	
		rt.setPrefWidth(100.0);
		
		AnchorPane.setTopAnchor(list,50.0);
		AnchorPane.setRightAnchor(list,50.0);
		
		iv.setImage(img);
		iv.setFitWidth(100.0);
		iv.setFitHeight(100.0);
		AnchorPane.setBottomAnchor(iv,100.0);
		AnchorPane.setRightAnchor(iv,100.0);
		
		Turtle.setImage(img1);
		Turtle.setFitWidth(50.0);
		Turtle.setFitHeight(50.0);
		AnchorPane.setTopAnchor(Turtle,cury-Turtle.getFitHeight()/2);
		AnchorPane.setLeftAnchor(Turtle,curx-Turtle.getFitWidth()/2);
		
		list.setPrefHeight(a+10);
		root.getChildren().addAll(canvas,Turtle,iv,button9,button10,button7,button6,button5,button4,button3,button2,button1,rt,gt,tt,list);

		Scene scene = new Scene(root, 2*a, 2*a, Color.WHITESMOKE);

		stage.setTitle("Lines");
		stage.setScene(scene);
		stage.show();
	}
	

	private void drawEdge() {
		// TODO Auto-generated method stub
		gc.beginPath();
		gc.moveTo(0, 0);
		gc.lineTo(a, 0);
		gc.lineTo(a, a);
		gc.lineTo(0, a);
		gc.lineTo(0, 0);
		gc.stroke();
	}



	private void drawLines(GraphicsContext gc) {
		gc.beginPath();
		gc.moveTo(a / 2, 0);
		gc.lineTo(a / 2, a);
		gc.moveTo(0, a / 2);
		gc.lineTo(a, a / 2);
		gc.stroke();
	}

	private void dosetting(GraphicsContext gc) {
		gc.setStroke(Color.RED.brighter());
		gc.setLineWidth(5);
	}

private void Clear() {
	
		gc.clearRect(0, 0, a, a);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		drawLines(gc);
		dosetting(gc);
		drawEdge();
		gc.moveTo(a/2, a/2);
		curx=(double)a/2;
		cury=(double)a/2;
		x=0.0;
		AnchorPane.setTopAnchor(Turtle,cury-Turtle.getFitHeight()/2);
		AnchorPane.setLeftAnchor(Turtle,curx-Turtle.getFitWidth()/2);
		Turtle.setRotate(x);
		iv.setRotate(x);
	}
private void Del(){
	try{data.remove(list.getSelectionModel().getSelectedIndex());}
	catch(Exception e){};
	
	list.setItems(FXCollections.observableArrayList(data));
	
}

private void Do(Stage stage){
	cnt=0;
	Arrays.fill(Push, -1);
	Arrays.fill(Pop, -1);
// System.out.println("Do 호출됨");
	//System.out.println(data.size());
	
	Stack<Integer> s= new Stack();
		for(int i=0;i<data.size();i++)
		{
			//System.out.println(data.get(i).toString());
			StringTokenizer st = new StringTokenizer(data.get(i).toString(),":");
			String temp=st.nextToken();
		//	System.out.println(temp);
			if(temp.equals("Repeat")){
				//System.out.println("Repeat if문 진입함");
				Push[cnt]=i;
				Count[cnt]=Integer.parseInt(st.nextToken());
				//System.out.println("cnt :"+cnt);
				//System.out.println("Push[cnt]:"+i);
				//System.out.println("Count[cnt]:"+Count[cnt]);
				s.push(i);
				cnt++;
			}
			else{
				
		//	System.out.println(temp+"는 if문 실행 안'됬'다ㅠㅠ");
			}
			if(temp.equals("}")){
			//	System.out.println("if문 안의 }");
			int k=0;
				while(Push[k]!=Integer.parseInt(s.peek().toString()))
			{
				k++;
			}
				
				Pop[k]=i;
		//		System.out.println("Pop[k]:"+Pop[k]);
				s.pop();
				cnt++;
			}
		}
		if(!s.empty()){
			Popup pop = new Popup();
			Label l = new Label();
			l.setText("괄호닫아");
			l.setTextFill(Color.BLUE);
			l.setFont(Font.font("Verdana", FontWeight.BOLD, 100));
			pop.getContent().add(l);
			pop.show(stage);
			pop.setAutoHide(true);
		
			System.out.println("알림 : 괄호닫아");return;
		}
		for(int i=0;i<data.size();i++){
			i=Conduct(i);
		}
	
}

private int Conduct(int i){
	//System.out.println(i+"행 실행됨_PARAMETER:i");
	String temp=data.get(i).toString();
	//System.out.println(i);
	StringTokenizer st = new StringTokenizer(temp,":");
	String T=st.nextToken();
	//System.out.println("토큰 :"+T);
	switch(T)
	{
	case "Go": 
		//System.out.println("Go실행됨");
		Go(Double.parseDouble(st.nextToken()));
		AnchorPane.setTopAnchor(Turtle,cury-Turtle.getFitHeight()/2);
		AnchorPane.setLeftAnchor(Turtle,curx-Turtle.getFitWidth()/2);
		break;
	case "Turn":
	//	System.out.println("Turn실행됨");
		double t=Double.parseDouble(st.nextToken());
		Re(t);
		iv.setRotate(iv.getRotate()-t);
		Turtle.setRotate(Turtle.getRotate()-t);
		break;
	case "Repeat":
//	System.out.println("Repeat실행됨");
		int r=-1;//반복 횟수
//		int front=0;//시작 지점
		int end=0;//끝나는 지점
		for(int j=0;j<cnt;j++)
		{
	//		System.out.println("j : "+j);
			if(i==Push[j]){
		//		System.out.println("i와 push[j]같다 i:"+i);
				end=Pop[j];
				r=Count[j];
			}
		}
//		System.out.println("r : "+r);
		for(int c=0;c<r;c++){
			//System.out.println((c+1)+"회 실행됨");
		for(int p=i+1;p<end;p++){
		//System.out.println(p+"행 실행됨_PARAMETER:p");
			p=Conduct(p);
		}
		}
		return end;
	default: break;
	}
	return i;
}

	private void Go(double k) {
		gc.beginPath();
		gc.moveTo(curx, cury);
		gc.lineTo(curx + k * Math.cos(x), cury - k * Math.sin(x));
		curx = curx + k * Math.cos(x);
		cury = cury - k * Math.sin(x);
		gc.stroke();
	}

	private void Re(double k) {
		x = x +pi*k/180;
	}
 
	private void Repeat(){
		String temp=rt.getText();
	int count=Integer.parseInt(temp);
	data.add("Repeat:"+temp+":{");
	list.setItems(FXCollections.observableArrayList(data));
	}
	
	private void End(){
	data.add("}:");
	list.setItems(FXCollections.observableArrayList(data));
	}
	public static void main(String[] args) {
		launch(args);
	}
}

