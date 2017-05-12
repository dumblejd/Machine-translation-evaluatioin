package WINDOW;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JTextField;
import javax.swing.JMenuBar;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

public class test {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	    JFrame frame=new JFrame("Machine Translation Evaluation");
	    JTabbedPane tabPane=new JTabbedPane();//选项卡布局
	    Container con=new Container();//布局1
	    Container con1=new Container();//布局2
	    JLabel label1=new JLabel("\u9009\u62E9\u4EBA\u5DE5\u7FFB\u8BD1\u4E0E\u673A\u5668\u7FFB\u8BD1\u76EE\u5F55");
	    JLabel label2=new JLabel("\u9009\u62E9\u7FFB\u8BD1\u8BC4\u4EF7\u6587\u4EF6");
	    JTextField text1=new JTextField();
	    JTextField text2=new JTextField();
	    JButton button1=new JButton("...");
	    JButton button2=new JButton("...");
	    JFileChooser jfc=new JFileChooser();//文件选择器
		 jfc.setCurrentDirectory(new File("d:\\"));//文件选择器的初始目录定为d盘
	        //下面两行是取得屏幕的高度和宽度
	        double lx=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	        double ly=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	        frame.setLocation(new Point((int)(lx/2)-150,(int)(ly/2)-150));//设定窗口出现位置
	        frame.setSize(976,482);//设定窗口大小
	        frame.setContentPane(tabPane);//设置布局
	       //下面设定标签等的出现位置和高宽
	        label1.setBounds(10,10,198,20);
	        label2.setBounds(10,46,100,20);
	        text1.setBounds(222,10,487,20);
	        text2.setBounds(222,46,487,20);
	        button1.setBounds(844,10,50,20);
	        button2.setBounds(844,46,50,20);
	        button1.addActionListener((ActionListener) this);//添加事件处理
	        button2.addActionListener((ActionListener) this);//添加事件处理
	        con.add(label1);
	        con.add(label2);
	        con.add(text1);
	        con.add(text2);
	        con.add(button1);
	        con.add(button2);
	        con.add(jfc);
	        tabPane.add("目录/文件选择",con);//添加布局1
	        tabPane.add("暂无内容",con1);//添加布局2
	        frame.setVisible(true);//窗口可见
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使能关闭窗口，结束程序
	        
	        JPanel panel_1 = new JPanel();
	    	JTextArea textArea = new JTextArea();
			textArea.setText("123\r\n1\r\n24\r\n234\r\n23\r\n5\r\n2345\r\n34\r\n534\r\n534\r\n5\r\n345\r\n3");
			textArea.setLineWrap(true);//激活自动换行功能
			textArea.setWrapStyleWord(true);//激活断行不断字功能
			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setBounds(3, 164, 967, -109);
			scrollPane.setPreferredSize(new Dimension(300, 200));
			
			panel_1.add(scrollPane);
	}
}
