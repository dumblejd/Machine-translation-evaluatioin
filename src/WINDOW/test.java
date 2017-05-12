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
	    JTabbedPane tabPane=new JTabbedPane();//ѡ�����
	    Container con=new Container();//����1
	    Container con1=new Container();//����2
	    JLabel label1=new JLabel("\u9009\u62E9\u4EBA\u5DE5\u7FFB\u8BD1\u4E0E\u673A\u5668\u7FFB\u8BD1\u76EE\u5F55");
	    JLabel label2=new JLabel("\u9009\u62E9\u7FFB\u8BD1\u8BC4\u4EF7\u6587\u4EF6");
	    JTextField text1=new JTextField();
	    JTextField text2=new JTextField();
	    JButton button1=new JButton("...");
	    JButton button2=new JButton("...");
	    JFileChooser jfc=new JFileChooser();//�ļ�ѡ����
		 jfc.setCurrentDirectory(new File("d:\\"));//�ļ�ѡ�����ĳ�ʼĿ¼��Ϊd��
	        //����������ȡ����Ļ�ĸ߶ȺͿ��
	        double lx=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	        double ly=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	        frame.setLocation(new Point((int)(lx/2)-150,(int)(ly/2)-150));//�趨���ڳ���λ��
	        frame.setSize(976,482);//�趨���ڴ�С
	        frame.setContentPane(tabPane);//���ò���
	       //�����趨��ǩ�ȵĳ���λ�ú͸߿�
	        label1.setBounds(10,10,198,20);
	        label2.setBounds(10,46,100,20);
	        text1.setBounds(222,10,487,20);
	        text2.setBounds(222,46,487,20);
	        button1.setBounds(844,10,50,20);
	        button2.setBounds(844,46,50,20);
	        button1.addActionListener((ActionListener) this);//����¼�����
	        button2.addActionListener((ActionListener) this);//����¼�����
	        con.add(label1);
	        con.add(label2);
	        con.add(text1);
	        con.add(text2);
	        con.add(button1);
	        con.add(button2);
	        con.add(jfc);
	        tabPane.add("Ŀ¼/�ļ�ѡ��",con);//��Ӳ���1
	        tabPane.add("��������",con1);//��Ӳ���2
	        frame.setVisible(true);//���ڿɼ�
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ʹ�ܹرմ��ڣ���������
	        
	        JPanel panel_1 = new JPanel();
	    	JTextArea textArea = new JTextArea();
			textArea.setText("123\r\n1\r\n24\r\n234\r\n23\r\n5\r\n2345\r\n34\r\n534\r\n534\r\n5\r\n345\r\n3");
			textArea.setLineWrap(true);//�����Զ����й���
			textArea.setWrapStyleWord(true);//������в����ֹ���
			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setBounds(3, 164, 967, -109);
			scrollPane.setPreferredSize(new Dimension(300, 200));
			
			panel_1.add(scrollPane);
	}
}
