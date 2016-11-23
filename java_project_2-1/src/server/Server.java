package server;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server extends JFrame {
	
	JTextArea log = new JTextArea();
	JScrollPane log_scroll = new JScrollPane(log);
	JLabel input_field_info = new JLabel("�ý��� �޼���");
	JTextField input_field = new JTextField();
	JButton run_button = new JButton("����");
	
	JPanel left_pan = new JPanel();
	JPanel right_pan = new JPanel();
	
	ServerBack serverBack = new ServerBack();
	Font default_font = new Font("���� ���", Font.PLAIN, 14);
	
	public Server(){
		setTitle("YC ���� ������");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,550);
		serverBack.setGUI(this);
		
		//log - �ڵ� ��ũ�� ���ϴ� �̺�Ʈ ������
		log_scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});
		log.setFont(default_font);
		log.setEditable(false);
		
		//input_field
		input_field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == '\n'){
					String msg = "System : "+input_field.getText();
					serverBack.send_message(msg);
					append_log(msg);
					input_field.setText("");
				}
			}
		});
		input_field_info.setSize(391,30);
		input_field_info.setLocation(0,0);
		input_field_info.setFont(default_font);
		input_field.setSize(391,30);
		input_field.setLocation(0,30);
		input_field.setFont(default_font);
		input_field.setEnabled(false); // ��Ȱ��ȭ
		
		//run_button
		run_button.setSize(391,35);
		run_button.setLocation(0,475);
		run_button.addActionListener(e->{
			run_button.setEnabled(false); //��ư�� ��Ȱ��ȭ
			input_field.setEnabled(true); //Ȱ��ȭ
			run_button.setText("running...");
			try {
				serverBack.start_back();
			} catch (Exception e1) {
				//start_back ���� exception �� ������ �Ͼ�°Ͱ���.
				e1.printStackTrace();
			}
		});
		
		//left
		left_pan.setLayout(new GridLayout(1,1));
		left_pan.add(log_scroll);
		
		//right
		right_pan.setLayout(null);
		right_pan.add(input_field_info);
		right_pan.add(input_field);
		right_pan.add(run_button);
		
		Container con = getContentPane();
		setLayout(new GridLayout(1, 2));
		con.setBackground(Color.DARK_GRAY);
		con.add(left_pan);
		con.add(right_pan);
		
		setVisible(true);
	}

	public void append_log(String msg){ //back ���� �ҷ��ͼ� ���
		log.append(msg+"\n");
	}
	
}
