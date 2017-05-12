package WINDOW;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import BLEU.DataprepareforBLEU;
import BLEU.LoadTransandAss;
import Impovement.Improvement;
import KLDistance.KLDistance;
import KLDistance.ReadfromTheta;
import ReadfromPeople.Correlation;
import ReadfromPeople.LoadAssessment;
import ReadfromPeople.ScoresforPassage;
import topicmodel.LDA_FilterStopWord;
import topicmodel.LDA_PrepareLDADataTxt;
import topicmodel.LDA_Use;

import java.awt.Color;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.Box;
import java.awt.Scrollbar;
import java.awt.TextArea;

import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class MTEapp implements ActionListener
{
   
	JFrame frame = new JFrame();
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	JPanel panel = new JPanel();
	JLabel lblNewLabel = new JLabel("\u673A\u5668\u8BC4\u5206\u6587\u4EF6");
	JLabel label = new JLabel("\u4EBA\u5DE5\u8BC4\u5206\u6570\u636E\u8BB0\u5F55\u6587\u4EF6");
	JLabel label_1 = new JLabel("\u673A\u5668\u7FFB\u8BD1\u4E0E\u4EBA\u5DE5\u7FFB\u76EE\u5F55");
	JTextField textField = new JTextField();
	JTextField textField_1 = new JTextField();
	JTextField textField_2 = new JTextField();
	JButton button_file = new JButton("\u00B7\u00B7\u00B7");
	JButton button_file_1 = new JButton("\u00B7\u00B7\u00B7");
	JButton button_file_2 = new JButton("\u00B7\u00B7\u00B7");
	JTextPane textPane = new JTextPane();
	JLabel lblLda = new JLabel("LDA\u6A21\u578B\u76EE\u5F55");
	JTextField textField_3 = new JTextField();
	JButton button_file_3 = new JButton("\u00B7\u00B7\u00B7");
	JLabel label_2 = new JLabel("\u7FFB\u8BD1\u6587\u4EF6\u59CB\u672B\u5E8F\u53F7");
	JTextField textField_5 = new JTextField();
	JPanel panel_1 = new JPanel();
	JTextPane txtpnCSystem = new JTextPane();
	JButton btnNewButton = new JButton("\u8BA1\u7B97\u8BC4\u5206\u5E76\u4FDD\u5B58");
	JButton button_4 = new JButton("\u8BFB\u53D6\u6587\u4EF6");
	JTextField textField_4 = new JTextField();
	JTextPane textPane_1 = new JTextPane();
	JPanel panel_2 = new JPanel();
	JTextPane txtpnLdatheta = new JTextPane();
	JButton button_5 = new JButton("\u6982\u7387\u5206\u5E03\u76F8\u4F3C\u5EA6\u8BA1\u7B97");
	JButton btnLda = new JButton("LDA\u6A21\u578B\u5E94\u7528");
	JTextField textField_6 = new JTextField();
	JTextPane textPane_3 = new JTextPane();
	JPanel panel_3 = new JPanel();
	JTextPane textPane_2 = new JTextPane();
	JButton button_7 = new JButton("求LDA分数与机器评测分数整合比例");
	JTextField textField_7 = new JTextField();
	JTextField txtX = new JTextField();
	JLabel lblbleu = new JLabel("*BLEU+");
	JTextField txtx = new JTextField();
	JLabel lblldanewEvaluation = new JLabel("*LDA=New Evaluation");
	JPanel panel_4 = new JPanel();
	JButton button_6 = new JButton("\u6539\u8FDB\u524D\u673A\u5668\u8BC4\u5206\u4E0E\u4EBA\u5DE5\u8BC4\u5206\u76F8\u4F3C\u5EA6");
	JButton button_8 = new JButton("\u6539\u8FDB\u540E\u8BC4\u5206\u4E0E\u4EBA\u5DE5\u8BC4\u5206\u76F8\u4F3C\u5EA6");
	JTextField textField_8 = new JTextField();
	JTextField textField_11 = new JTextField();
	JPanel panel_5 = new JPanel();
	JButton button_9 = new JButton("\u8BED\u6599\u5E93\u6587\u4EF6");
	JButton btnlda_1 = new JButton("\u8BAD\u7EC3LDA");
	JTextField textField_9 = new JTextField();
	JTextField textField_10 = new JTextField();
	JLabel lblNewLabel_1 = new JLabel("\u8BAD\u7EC3\u4F1A\u4F7F\u7528\u5F88\u957F\u65F6\u95F4\uFF01\uFF01\uFF01");
	JLabel lblg = new JLabel("\u8BF7\u81F3\u5C11\u4FDD\u8BC130G\u7A7A\u95F4\uFF01\uFF01\uFF01");
	JTextPane textPane_4 = new JTextPane();
	JButton button_file_4 = new JButton("\u00B7\u00B7\u00B7");
	 JTextField textField_12 = new JTextField();
	 JButton button = new JButton("\u00B7\u00B7\u00B7");
	 JLabel label_3 = new JLabel("\u8BBE\u7F6E\u4FDD\u5B58\u6587\u4EF6\u8DEF\u5F84");
	JButton button_1 = new JButton("\u00B7\u00B7\u00B7");
	JTextField textField_13=new JTextField();;
    JButton button_2 = new JButton("\u9884\u5904\u7406\u7FFB\u8BD1\u6587\u4EF6");
  JLabel label_5 = new JLabel("\u8BBE\u7F6E\u6EE4\u8BCD\u6587\u4EF6");
    JTextField textField_14 = new JTextField();
    JButton button_3 = new JButton("\u00B7\u00B7\u00B7");
    JFileChooser jfc=new JFileChooser();//文件选择器
	JTextPane textPane_5 = new JTextPane();
	
	//======================
   
	String translationdir=null;
	int []startendindex=new int[4];
	String humanassessmentfile_page1=null;
	String savehumanscore_page2=null;
	String bleufile=null;
	String ldamodeldir=null;
	String fbisfile=null;
	String filterfile=null;
    String preparedir=null;
    
    //=======================
    Correlation co=null;
    ScoresforPassage sfphuman=null;
    ScoresforPassage sfpimprovedevaluation=null;
    ScoresforPassage sfpmachineevaluation=null;
	LoadAssessment la=null;
	ScoresforPassage sp = null;
	LoadTransandAss a=null;
	DataprepareforBLEU da=null;
    LDA_FilterStopWord 	ldaf_filter=null;
    LDA_PrepareLDADataTxt ldaf_list=null;
    LDA_Use ldaf_use=null;
    KLDistance kld=null;
    ReadfromTheta r=null;
    Improvement ip=null;
    //===========================
	public MTEapp() {
		frame.setTitle("\u673A\u5668\u7FFB\u8BD1\u8BC4\u6D4B\u6539\u8FDB\u7CFB\u7EDF");
		frame.setBounds(100, 100, 1000, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tabbedPane.setBounds(0, 0, 982, 453);
		frame.getContentPane().add(tabbedPane);
		
		tabbedPane.addTab("文件目录选取", null, panel, null);
		panel.setLayout(null);
		
		lblNewLabel.setBounds(14, 147, 95, 30);
		panel.add(lblNewLabel);
		
		label.setBounds(14, 87, 162, 30);
		panel.add(label);
		
		label_1.setBounds(14, 30, 200, 30);
		panel.add(label_1);
		
		textField.setBounds(226, 30, 386, 30);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1.setColumns(10);
		textField_1.setBounds(226, 87, 386, 30);
		panel.add(textField_1);
		
		textField_2.setColumns(10);
		textField_2.setBounds(226, 147, 386, 30);
		panel.add(textField_2);
		
		button_file.addActionListener(this);
		button_file.setBounds(626, 30, 52, 30);
		panel.add(button_file);
		
		button_file_1.addActionListener(this);
		button_file_1.setBounds(626, 87, 52, 30);
		panel.add(button_file_1);
		
		button_file_2.addActionListener(this);
		button_file_2.setBounds(626, 147, 52, 30);
		panel.add(button_file_2);
		
		textPane.setBackground(Color.LIGHT_GRAY);
		textPane.setText("\u672C\u7A0B\u5E8F\u4F7F\u7528\u8FC7\u7A0B\r\n1.\u9009\u62E9\u6587\u4EF6\u76EE\u5F55\r\n2.\u5BF9\u4EBA\u5DE5\u8BC4\u5206\u6570\u636E\u8FDB\u884C\u8BA1\u7B97\uFF0C\u5F97\u5230\u5F52\u4E00\u5316\u7684\u6807\u51C6\u8BC4\u5206\r\n3.\u4F7F\u7528LDA\u4E3B\u9898\u6A21\u578B\u5BF9\u673A\u5668\u7FFB\u8BD1\u8FDB\u884C\u8BC4\u5206\r\n4.\u6574\u5408\u5DF2\u6709\u673A\u5668\u8BC4\u5206\u4E0ELDA\u8BC4\u5206\uFF0C\u4EA7\u751F\u6539\u8FDB\u540E\u7684\u8BC4\u5206\r\n5.\u4F7F\u7528\u6539\u8FDB\u540E\u7684\u8BC4\u5206\u4E0E\u4EBA\u5DE5\u8BC4\u5206\u8FDB\u884C\u76F8\u4F3C\u5EA6\u8BA1\u7B97\uFF0C\u5BF9\u6BD4\u672A\u6539\u8FDB\u8BC4\u5206\u7684\u76F8\u4F3C\u5EA6");
		textPane.setBounds(0, 303, 982, 121);
		panel.add(textPane);
		
		lblLda.setBounds(14, 202, 95, 30);
		panel.add(lblLda);
		
		textField_3.setColumns(10);
		textField_3.setBounds(226, 205, 386, 30);
		panel.add(textField_3);
		
		button_file_3.addActionListener(this);
		button_file_3.setBounds(626, 205, 52, 30);
		panel.add(button_file_3);
		
		label_2.setBounds(704, 30, 125, 30);
		panel.add(label_2);
		
		textField_5.setText("1,4,9,22");
		textField_5.setColumns(10);
		textField_5.setBounds(832, 30, 108, 30);
		panel.add(textField_5);
		
		tabbedPane.addTab("人工评分计算", null, panel_1, null);
		panel_1.setLayout(null);
		
		txtpnCSystem.setBounds(0, 235, 977, 186);
		txtpnCSystem.setBackground(Color.LIGHT_GRAY);
		txtpnCSystem.setForeground(Color.BLACK);
		txtpnCSystem.setText("E11,AFC20030203.0023,aford,E02,S1,3,3,\"{F}{A}\",\"20030519 10:19:04\"\r\nC1 - system ID\u2014\u2014\u8BD1\u6587\u76EE\u5F55\u7F16\u53F7\r\nC2 - doc ID\u2014\u2014\u8BD1\u6587\u6587\u6863\u7F16\u53F7\r\nC3 - judge\u2014\u2014\u7FFB\u8BD1\u4EBA\r\nC4 - gold standard ID\u2014\u2014\u8BC4\u5224\u7528\u7684\u6807\u51C6\u8BD1\u6587\u7F16\u53F7\r\nC5 - seg ID\u2014\u2014\u6BB5\u843D\u53F7\r\nC6 - fluency judgment\u2014\u2014\u6D41\u7545\u5EA6\r\nC7 - adequacy judgment\u2014\u2014\u51C6\u786E\u5EA6\r\nC8 - comment on fluency/adequacy\u2014\u2014\u8BC4\u5206\u4FEE\u6B63\r\nC9 - judgement timestamp\u2014\u2014\u8BC4\u5224\u65F6\u95F4");
		panel_1.add(txtpnCSystem);
		
		btnNewButton.setBounds(167, 0, 146, 27);
		btnNewButton.addActionListener(this);
		panel_1.add(btnNewButton);
		
		button_4.setBounds(0, 0, 146, 27);
		button_4.addActionListener(this);
		panel_1.add(button_4);
		textField_4.setBackground(Color.LIGHT_GRAY);
		textField_4.setText("\u8BF7\u5148\u8BFB\u53D6\uFF0C\u540E\u8BA1\u7B97");
		//frame.getContentPane().add(scrollPane);
		
		textField_4.setBounds(344, 1, 452, 27);
		panel_1.add(textField_4);
		textField_4.setColumns(10);
		textPane_1.setBackground(Color.LIGHT_GRAY);
		
		textPane_1.setBounds(0, 97, 977, 139);
		panel_1.add(textPane_1);
		textField_12.setColumns(10);
		textField_12.setBounds(344, 54, 386, 30);
		
		panel_1.add(textField_12);
		button.setBounds(744, 54, 52, 30);
		button.addActionListener(this);
		
		panel_1.add(button);
		label_3.setBounds(180, 60, 133, 18);
		
		panel_1.add(label_3);
		
		tabbedPane.addTab("LDA模型应用", null, panel_2, null);
		panel_2.setLayout(null);
		
		txtpnLdatheta.setText("LDA\u6A21\u578B\u5E94\u7528\u540E\u4EA7\u751Ftheta\u7ED3\u5C3E\u7684\u6587\u4EF6\uFF0C\u4E3A\u6BCF\u4E2A\u7CFB\u7EDF\u5BF9\u5E94\u6BCF\u4E2A\u4E3B\u9898\u7684\u6982\u7387\u5206\u5E03\r\n\u76F8\u4F3C\u5EA6\u8BA1\u7B97\uFF1A\u673A\u5668\u7FFB\u8BD1\u7684\u6982\u7387\u5206\u5E03\u4E0E\u6BCF\u4E2A\u4EBA\u5DE5\u7FFB\u8BD1\u7684\u6982\u7387\u5206\u5E03\u8FDB\u884C\u8BA1\u7B97\uFF0C\u53D6\u5747\u503C\r\n\u76F8\u4F3C\u5EA6\uFF1A\u5373\u5F52\u4E00\u5316\u534F\u65B9\u5DEE\uFF0C\u4F53\u73B0\u4E24\u4E2A\u6570\u636E\u4E4B\u95F4\u7684\u53D8\u5316\u8D8B\u52BF\u4E00\u81F4\u7684\u7A0B\u5EA6\r\n\u6B64\u8BA1\u7B97\u8BC4\u4F30\u4E86\u673A\u5668\u7FFB\u8BD1\u4E0E\u4EBA\u5DE5\u7FFB\u8BD1\u5728\u4E3B\u9898\u4E0A\u7684\u4E00\u81F4\u5EA6");
		txtpnLdatheta.setForeground(Color.BLACK);
		txtpnLdatheta.setBackground(Color.LIGHT_GRAY);
		txtpnLdatheta.setBounds(0, 326, 977, 95);
		panel_2.add(txtpnLdatheta);
		
		button_5.setBounds(15, 133, 167, 27);
		button_5.addActionListener(this);
		panel_2.add(button_5);
		
		btnLda.setBounds(15, 81, 146, 27);
		btnLda.addActionListener(this);
		panel_2.add(btnLda);
		textField_6.setBackground(Color.LIGHT_GRAY);
		
		textField_6.setColumns(10);
		textField_6.setBounds(346, 0, 453, 27);
		panel_2.add(textField_6);
		textPane_3.setBackground(Color.LIGHT_GRAY);
		
		textPane_3.setBounds(0, 173, 977, 153);
		panel_2.add(textPane_3);
		
		JLabel label_4 = new JLabel("\u8BBE\u7F6E\u4FDD\u5B58\u6587\u4EF6\u8DEF\u5F84");
		label_4.setBounds(175, 88, 133, 18);
		panel_2.add(label_4);
		
	
		textField_13.setColumns(10);
		textField_13.setBounds(346, 79, 386, 30);
		panel_2.add(textField_13);
		
		button_1.addActionListener(this);
		button_1.setBounds(747, 79, 52, 30);
		panel_2.add(button_1);
		button_2.setBounds(14, 41, 146, 27);
		button_2.addActionListener(this);
		panel_2.add(button_2);
		label_5.setBounds(174, 45, 133, 18);
		
		panel_2.add(label_5);
		textField_14.setColumns(10);
		textField_14.setBounds(346, 38, 386, 30);
		
		panel_2.add(textField_14);
		button_3.setBounds(746, 37, 52, 30);
		button_3.addActionListener(this);
		panel_2.add(button_3);
		
		panel_3.setLayout(null);
		tabbedPane.addTab("改进机器评测", null, panel_3, null);
		
		textPane_2.setText("\u5BFB\u627E\u5408\u9002\u7684\u6BD4\u4F8B\uFF0C\u4F7F\u5F97\u6539\u8FDB\u540E\u7684\u8BC4\u5206\u4E0E\u4EBA\u5DE5\u8BC4\u5206\u7684\u76F8\u4F3C\u5EA6\u6700\u9AD8\r\n\u6574\u5408\u5E76\u4FDD\u5B58");
		textPane_2.setForeground(Color.BLACK);
		textPane_2.setBackground(Color.LIGHT_GRAY);
		textPane_2.setBounds(0, 326, 977, 95);
		panel_3.add(textPane_2);
		
		button_7.setBounds(0, 0, 267, 27);
		button_7.addActionListener(this);
		panel_3.add(button_7);
		textField_7.setBackground(Color.LIGHT_GRAY);
		
		textField_7.setColumns(10);
		textField_7.setBounds(346, 0, 453, 27);
		panel_3.add(textField_7);
		
		txtX.setText("X");
		txtX.setBounds(298, 64, 50, 50);
		txtX.setHorizontalAlignment(SwingConstants.CENTER); 
		panel_3.add(txtX);
		txtX.setColumns(10);
		
		lblbleu.setBounds(362, 80, 59, 18);
		panel_3.add(lblbleu);
		
		txtx.setText("1-X");
		txtx.setHorizontalAlignment(SwingConstants.CENTER);
		txtx.setColumns(10);
		txtx.setBounds(422, 64, 50, 50);
		panel_3.add(txtx);
		
		lblldanewEvaluation.setBounds(486, 80, 174, 18);
		panel_3.add(lblldanewEvaluation);
		
		textPane_5.setBackground(Color.LIGHT_GRAY);
		textPane_5.setBounds(0, 173, 977, 163);
		panel_3.add(textPane_5);
		
		panel_4.setLayout(null);
		tabbedPane.addTab("改进效果", null, panel_4, null);
		
		button_6.setBounds(57, 26, 277, 27);
		button_6.addActionListener(this);
		panel_4.add(button_6);
		
		button_8.setBounds(57, 66, 277, 27);
		button_8.addActionListener(this);
		panel_4.add(button_8);
		
		textField_8.setColumns(10);
		textField_8.setBounds(348, 26, 453, 27);
		panel_4.add(textField_8);
		
		textField_11.setColumns(10);
		textField_11.setBounds(348, 67, 453, 27);
		panel_4.add(textField_11);
		
		panel_5.setLayout(null);
		tabbedPane.addTab("附加功能：LDA模型训练", null, panel_5, null);
		
		button_9.setBounds(60, 114, 277, 27);
		panel_5.add(button_9);
		
		btnlda_1.setBounds(60, 154, 277, 27);
		panel_5.add(btnlda_1);
		
		textField_9.setColumns(10);
		textField_9.setBounds(391, 114, 453, 27);
		panel_5.add(textField_9);
		
		textField_10.setText("\u8BF7\u5148\u586B\u5199\u4E3B\u9898\u6570\u91CF");
		textField_10.setColumns(10);
		textField_10.setBounds(391, 154, 149, 27);
		panel_5.add(textField_10);
		
		lblNewLabel_1.setBounds(60, 13, 205, 18);
		panel_5.add(lblNewLabel_1);
		
		lblg.setBounds(60, 44, 205, 18);
		panel_5.add(lblg);
		
		textPane_4.setText("\u8BED\u6599\u5E93\u6587\u4EF6\u8981\u6C42\r\n<corpus id=\"fbis\">\r\n<doc id=\"CPP20000205000050.txt\">\r\n<sentence sid=\"1\">\r\n<src> \u5E7F\u5DDE \u519B\u533A \u4EE5 \u8BD5\u70B9 \u5148\u884C \u5E26\u52A8 \u9762\u4E0A \u5C55\u5F00  </src>\r\n<trg> guangzhou military region is using test beds as the vanguard to lead its advance </trg>");
		textPane_4.setForeground(Color.BLACK);
		textPane_4.setBackground(Color.LIGHT_GRAY);
		textPane_4.setBounds(0, 293, 977, 128);
		panel_5.add(textPane_4);
		
		button_file_4.addActionListener(this);
		button_file_4.setBounds(858, 114, 52, 30);
		panel_5.add(button_file_4);
        
		jfc.setCurrentDirectory(new File("C:\\Users\\JinDi\\Desktop"));//文件选择器的初始目录定为d盘

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使能关闭窗口，结束程序
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{

		 if(e.getSource().equals(button_file))
		 {//判断触发方法的按钮是哪个
	            jfc.setFileSelectionMode(1);//设定只能选择到文件夹
	            int state=jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
	            if(state==1){
	                return;//撤销则返回
	            }
	            else{
	            	System.out.println(textField_5.getText());
	            	if(textField_5.getText().equals(""))
	            	{
	            		JOptionPane.showMessageDialog(null, "请先输入始末位置", "请先输入始末位置", JOptionPane.ERROR_MESSAGE);
	            		return;
	            	}
	            	else
	            	{
	                File f=jfc.getSelectedFile();//f为选择到的目录
	                translationdir=f.getAbsolutePath();
	                textField.setText(f.getAbsolutePath());
	                String temp=textField_5.getText();
	                String[] index=temp.split(",");
	                for(int i=0;i<index.length;i++)
	                {
	                	startendindex[i]=Integer.parseInt(index[i]);
	                }
	            	}
	            }
	        }
	        if(e.getSource().equals(button_file_1)){
	            jfc.setFileSelectionMode(0);//设定只能选择到文件
	            int state=jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
	            if(state==1){
	                return;//撤销则返回
	            }
	            else{
	                File f=jfc.getSelectedFile();//f为选择到的文件
	                humanassessmentfile_page1=f.getAbsolutePath();
	                System.out.println(humanassessmentfile_page1);
	                textField_1.setText(f.getAbsolutePath());
	            }
	        }
	        if(e.getSource().equals(button_file_2 )){
	            jfc.setFileSelectionMode(0);//设定只能选择到文件
	            int state=jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
	            if(state==1){
	                return;//撤销则返回
	            }
	            else{
	                File f=jfc.getSelectedFile();//f为选择到的文件
	                bleufile=f.getAbsolutePath();
	                textField_2.setText(f.getAbsolutePath());
	            }
	        }
	        if(e.getSource().equals(button_file_3  ))
			 {//判断触发方法的按钮是哪个
		            jfc.setFileSelectionMode(1);//设定只能选择到文件夹
		            int state=jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
		            if(state==1){
		                return;//撤销则返回
		            }
		            else{
		                File f=jfc.getSelectedFile();//f为选择到的目录
		                ldamodeldir=f.getAbsolutePath();
		                textField_3.setText(f.getAbsolutePath());
		            }
		        }
	        if(e.getSource().equals(button))
			 {//判断触发方法的按钮是哪个
		            jfc.setFileSelectionMode(1);//设定只能选择到文件夹
		            int state=jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
		            if(state==1){
		                return;//撤销则返回
		            }
		            else{
		                File f=jfc.getSelectedFile();//f为选择到的目录
		                savehumanscore_page2=f.getAbsolutePath();
		                textField_12.setText(f.getAbsolutePath());
		            }
		        }
	        if(e.getSource().equals(button_file_4   )){
	            jfc.setFileSelectionMode(0);//设定只能选择到文件
	            int state=jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
	            if(state==1){
	                return;//撤销则返回
	            }
	            else{
	                File f=jfc.getSelectedFile();//f为选择到的文件
	                fbisfile=f.getAbsolutePath();
	                textField_9.setText(f.getAbsolutePath());
	            }
	        }
	        if(e.getSource().equals(button_4))
	        {
	        	if(humanassessmentfile_page1==null)
	        	{
            		JOptionPane.showMessageDialog(null, "请先完成第一个页面:4个路径选取", "您未选取路径", JOptionPane.ERROR_MESSAGE);
            		return;
	        	}
	        	else
	        	{
	        		la=new LoadAssessment(humanassessmentfile_page1);
	        		la.Load();
	        		textField_4 .setText("读取完成");
	        		textPane_1.setText(textPane_1.getText()+ "读取文件路径："+humanassessmentfile_page1+"\r\n");
	            }
	        }
	        if(e.getSource().equals(btnNewButton))
	        {
	        	if(savehumanscore_page2==null)
	        	{
            		JOptionPane.showMessageDialog(null, "请选取文件保存目录", "您未选取路径", JOptionPane.ERROR_MESSAGE);
            		return;
	        	}
	        	else
	        	{
	        		 sp = new ScoresforPassage();
	        		sp.cal_Score(la.getAss_list(), la.getJudge_list());
	        		sp.sort();
	        		sp.saveinfile(savehumanscore_page2+"//human_evaluation.txt");
	        		textField_4 .setText("计算并保存完成");
	        		textPane_1.setText(textPane_1.getText()+"保存文件路径："+savehumanscore_page2+"\\human_evaluation.txt\r\n");
	            }
	        }
	        if(e.getSource().equals(button_1))
	        {
	        	//判断触发方法的按钮是哪个
	            jfc.setFileSelectionMode(1);//设定只能选择到文件夹
	            int state=jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
	            if(state==1){
	                return;//撤销则返回
	            }
	            else{
	                File f=jfc.getSelectedFile();//f为选择到的目录
                    preparedir=f.getAbsolutePath();
	                textField_13.setText(f.getAbsolutePath());
	            }
	        }
	        if(e.getSource().equals(button_3))
	        {
	        	//判断触发方法的按钮是哪个
	            jfc.setFileSelectionMode(0);//设定只能选择到文件
	            int state=jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
	            if(state==1){
	                return;//撤销则返回
	            }
	            else{
	                File f=jfc.getSelectedFile();
	                filterfile=f.getAbsolutePath();
	                textField_14.setText(f.getAbsolutePath());
	            }
	        }
	        if(e.getSource().equals(button_2))
	        {
	        	if(translationdir==null||preparedir==null||ldamodeldir==null)
	        	{
            		JOptionPane.showMessageDialog(null, "请选取LDA模型目录和文件保存目录以及翻译目录", "您未选取路径", JOptionPane.ERROR_MESSAGE);
            		return;
	        	}
	        	else
	        	{
	        		a=new LoadTransandAss();
	        		try {
						a.read(translationdir);
						da=new DataprepareforBLEU(startendindex[2],startendindex[3],startendindex[0],startendindex[1],a.getAllData());
						File a=new File(preparedir+"//MTcontent");
						a.mkdir();
						 a=new File(preparedir+"\\MT_nostop_withstem");
						a.mkdir();
						 a=new File(preparedir+"\\MT_nostop_withstem_list");
							a.mkdir();
							a=new File(preparedir+"\\MT_nostop_withstem_lda");
							a.mkdir();
						da.Createcontentfile(da.getTranslation(), preparedir+"//MTcontent");
						da.Createcontentfile(da.getAnswer(), preparedir+"//MTcontent");
						
						
						File file=new File(preparedir+"//MTcontent");
						File[] tempList = file.listFiles();
						for(int i=0;i<tempList.length;i++)
						{
							if (tempList[i].isDirectory()) {
								 ldaf_filter=new LDA_FilterStopWord();
								 ldaf_filter.Encoding="ASCII";
							    System.out.println("文件夹："+tempList[i]);
							    File file1=new File(preparedir+"\\MT_nostop_withstem\\"+tempList[i].getName());
							    file1.mkdir();
							    String t=preparedir+"\\MT_nostop_withstem\\"+tempList[i].getName();
								System.out.println(filterfile);
							    ldaf_filter.FilterStopWord(filterfile,tempList[i].getPath(),t,true);
							   }
						}
						
						
						 file=new File(preparedir+"\\MT_nostop_withstem");
						 tempList = file.listFiles();
						 LDA_PrepareLDADataTxt ldaf_list=new LDA_PrepareLDADataTxt();
						 
						for(int i=0;i<tempList.length;i++)
						{
							   if (tempList[i].isDirectory()) {
							    System.out.println("文件夹："+tempList[i]);
							    File file2=new File(preparedir+"\\MT_nostop_withstem_list\\"+tempList[i].getName());
							    file2.mkdir();
							    String t=preparedir+"\\MT_nostop_withstem\\"+tempList[i].getName();
							    System.out.println(t);
							    ldaf_list.prePareLDA(100,t , preparedir+"\\MT_nostop_withstem_list\\"+tempList[i].getName(),"passageoneline.nostop");
							   }
						}
						
						
						 file=new File(preparedir+"\\MT_nostop_withstem");
						 tempList = file.listFiles();
						
						for(int i=0;i<tempList.length;i++)
						{
							   if (tempList[i].isDirectory()) {
							    System.out.println("文件夹："+tempList[i]);
							    File file3=new File(preparedir+"\\MT_nostop_withstem_lda\\"+tempList[i].getName());
							    file3.mkdir();
							    String t=preparedir+"\\MT_nostop_withstem\\"+tempList[i].getName();
							    System.out.println(t);
								ldaf_list.prePareLDAByFilelists(100,t , preparedir+"\\MT_nostop_withstem_list\\"+tempList[i].getName()+"\\filelists.txt",preparedir+"\\MT_nostop_withstem_lda\\"+tempList[i].getName()+"\\lda_en_all_fortrain.dat");
								File file4=new File(ldamodeldir+"\\"+tempList[i].getName());
							    file4.mkdir();
								ldaf_list.prePareLDAByFilelists(100,t , preparedir+"\\MT_nostop_withstem_list\\"+tempList[i].getName()+"\\filelists.txt",ldamodeldir+"\\"+tempList[i].getName()+"\\lda_en_all_fortrain.dat");
							   }
						}
						textPane_3.setText(textPane_3.getText()+"系统预处理文件保存完成\r\n");
		        		textField_6.setText("文件预处理完成");


	        		} catch (IOException e1) {
						e1.printStackTrace();
					}
	            }
	        }
	        if(e.getSource().equals(btnLda))
	        {
	        	//判断触发方法的按钮是哪个
	        	File check =new File(preparedir+"\\MT_nostop_withstem_lda\\");
	            if(!check.exists())
	            {
            		JOptionPane.showMessageDialog(null, "请先预处理再使用", "您未选取路径", JOptionPane.ERROR_MESSAGE);
            		return;
	            }
	            if(ldamodeldir==null)
            	{
            		JOptionPane.showMessageDialog(null, "请先设置第一个页面：LDA模型目录", "您未选取路径", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
	            else{
	            	
	            	
	            	
		            	File file=new File(preparedir+"\\MT_nostop_withstem");
		        		File[] tempList = file.listFiles();
		        		ldaf_use=new LDA_Use();
	        			ldaf_use.setEncoding("ASCII");
		        		for(int i=0;i<tempList.length;i++)
		        		{
		        			
		        			   if (tempList[i].isDirectory()) {
		        			    System.out.println("文件夹："+tempList[i]);
		        				ldaf_use.inferTm(ldamodeldir, "model-final", "\\"+tempList[i].getName()+"\\lda_en_all_fortrain.dat");
		        			   }
		        		}
		        		textPane_3.setText(textPane_3.getText()+"LDA模型应用完成\r\n");
		        		textField_6.setText("LDA模型应用完成");
	            	}
	            
	        }
	        if(e.getSource().equals(button_5))
	        {
	        	String index="E"+String.valueOf(startendindex[3]);
	        	if(startendindex[3]<10)
	        	{
	        		index="E0"+String.valueOf(startendindex[3]);
	        	}
	        
	        	File f1=new File(ldamodeldir+"\\"+index+"\\lda_en_all_fortrain.dat");
	        	System.out.println(f1.getAbsolutePath());
	            if(!f1.exists())
	            {
            		JOptionPane.showMessageDialog(null, "请先应用LDA模型", "您未选取路径", JOptionPane.ERROR_MESSAGE);
            		return;
	            }
	            if(humanassessmentfile_page1==null)
	            {
	            	JOptionPane.showMessageDialog(null, "请先设置第一个页面：人工评分文件", "您未选取路径", JOptionPane.ERROR_MESSAGE);
            		return;
	            }
	            else{
	            	 kld=new KLDistance();
	        		 try {
	        			 	la=new LoadAssessment(humanassessmentfile_page1);
	        				la.Load();
	        				 sp = new ScoresforPassage();
	        				sp.cal_Score(la.getAss_list(), la.getJudge_list());  
	        				sp.sort();
						r=new ReadfromTheta(startendindex[2],startendindex[3],startendindex[0],startendindex[1],ldamodeldir);
		        		kld.conuntcorrelation(r, sp);//KLDcorrelation.txt            直接归一化协方差
		        		File q=new File(humanassessmentfile_page1);
		        		kld.saveKLD(q.getParentFile()+"\\LDA_correlation.txt");
		        		textPane_3.setText(textPane_3.getText()+"相似度文件LDA_correlation.txt保存完成\r\n");
		        		textField_6.setText("相似度文件LDA_correlation.txt保存完成");
	        		 } catch (IOException e1) {
						e1.printStackTrace();
					}
	            	}
	            
	        }
	        if(e.getSource().equals(button_7))
	        {
	        	File q=new File(humanassessmentfile_page1);
	        	q=new File(q.getParentFile()+"\\human_evaluation.txt");
	        	if(bleufile==null)
	        	{
	        		JOptionPane.showMessageDialog(null, "请先设置第一个页面：机器评测文件，并保存", "您未选取路径", JOptionPane.ERROR_MESSAGE);
            		return;
	        	}
	            if(!q.exists())
	            {
	            	JOptionPane.showMessageDialog(null, "请先完成第二个页面：人工评分计算，并保存", "您未选取路径", JOptionPane.ERROR_MESSAGE);
            		return;
	            }
	            q=new File(q.getParentFile()+"\\LDA_correlation.txt");
	            if(!q.exists())
	            {
	            	JOptionPane.showMessageDialog(null, "请先完成第三个页面：LDA模型应用后相似度计算", "您未选取路径", JOptionPane.ERROR_MESSAGE);
            		return;
	            }
	            else{
	            	
	            	 q=new File(humanassessmentfile_page1);
	            	 ip=new Improvement();
	            	 System.out.println(q.getParentFile());
	        		 ip.Load(bleufile, q.getParentFile()+"\\LDA_correlation.txt", q.getParentFile()+"\\human_evaluation.txt");
	        		 ip.findratio();  
	        		 double x=ip.getX();
	        		 txtX.setText(String.format("%.2f",x));
	        		 txtx.setText(String.format("%.2f",1-x));
	        		 ip.saveimproved(q.getParentFile().getAbsolutePath()+"\\Improved_Evaluation.txt");
	        		 textField_7.setText("改进比例计算完成,并保存为Improved_Evaluation.txt");
	        		 textPane_5.setText(textPane_5.getText()+"改进比例计算完成,并保存为Improved_Evaluation.txt"+"\r\n"+"相似度为："+String.valueOf(ip.getMax())+"\r\n");
	            }
	        }
		
	        if(e.getSource().equals(button_8))
	        {
	        	
	        	if(humanassessmentfile_page1==null)
	        {
	        		JOptionPane.showMessageDialog(null, "请先完成第一个页面:4个路径选取", "您未选取路径", JOptionPane.ERROR_MESSAGE);
            		return;
	        }
	        	else
	        	{
	        	File q=new File(humanassessmentfile_page1);
	        	q=new File(q.getParentFile()+"\\Improved_evaluation.txt");
	        	if(!q.exists())
	        	{
	        		JOptionPane.showMessageDialog(null, "请先第四个页面的：改进机器评测", "您未选取路径", JOptionPane.ERROR_MESSAGE);
            		return;
	        	}
	            else
	            {
	            	co=new Correlation(0);
	            	sfphuman=new ScoresforPassage();
	            	sfpimprovedevaluation=new ScoresforPassage();
	            	try {
						sfphuman.loadonefile(q.getParentFile()+"\\human_evaluation.txt");
		            	sfpimprovedevaluation.loadonefile(q.getParentFile().getAbsolutePath()+"\\Improved_evaluation.txt");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//	            	for(int i=0;i<sfpimprovedevaluation.getPassage_list().size();i++)
//	            	{
//	            	sfpimprovedevaluation.getPassage_list().get(i).print();
//	            	}
	            	co.cal_correlation(sfphuman.getPassage_list(), sfpimprovedevaluation.getPassage_list());
	            	textField_11.setText(String.valueOf(co.getCorrelation()));
	            }
	         }
	        }
	        if(e.getSource().equals(button_6))
	        {
	        	File q=null;
	        	q=new File(bleufile);
	        	if(!q.exists())
	        	{
	        		JOptionPane.showMessageDialog(null, "请先选择第一页：机器评分文件", "您未选取路径", JOptionPane.ERROR_MESSAGE);
            		return;
	        	}
	            else
	            {
	            	co=new Correlation(0);
	            	sfphuman=new ScoresforPassage();
	            	sfpmachineevaluation=new ScoresforPassage();
	            	try {
						sfphuman.loadonefile(q.getParentFile()+"\\human_evaluation.txt");
						sfpmachineevaluation.loadonefile(bleufile);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            	co.cal_correlation(sfphuman.getPassage_list(), sfpmachineevaluation.getPassage_list());
	            	textField_8.setText(String.valueOf(co.getCorrelation()));
	            }
	        }
	}
	public static void main(String[] args) {
		
		
        MTEapp aEapp=new MTEapp();
    }
}
