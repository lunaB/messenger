package client;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Client extends JFrame{
	
	/*
	 * ����â
	 * �α��� â
	 * ����â
	 * ü�� â
	 * Ŀ���� ���� ü�ø�带 ���� �ϰų� �÷��̾�� �̴ϰ����� �ֵ��� �����ȹ
	 */
	
	JButton small = new JButton("_");
	JButton close = new JButton("X");
	
	CardLayout cardLayout = new CardLayout();
	Container container = getContentPane();
	JPanel top_con = new JPanel();
	JPanel card_con = new JPanel();
	
	ClientBack clientBack = new ClientBack();
	
	//�⺻������ 360 560
	
	int dsizeW=350;
	int dsizeH=550;
	
	Color def_color[] = {
		new Color(234,197,184),
		new Color(213,139,113),
		new Color(104,92,78),
		new Color(46,68,66),
		new Color(130,178,178)
	};
	
	public Client(){
		clientBack.setClient(this);
		container.setLayout(new CardLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		container.setLayout(null);
		
		top_con.setLayout(null);
		top_con.setLocation(0,0);
		top_con.setSize(dsizeW,40);
		top_con.setBackground(def_color[3]);
		
		small.setSize(38, 38);
		small.setLocation(dsizeW-80,0);
		small.addActionListener(e->{
			
		});
		close.setSize(38, 38);
		close.setLocation(dsizeW-40,0);
		close.addActionListener(e->{
			
		});
		
		top_con.add(small);
		top_con.add(close);
		//card_con.setLayout();
		
		card_con.setLocation(0, 20);
		card_con.setSize(dsizeW,dsizeH-40);
		
		card_con.add("select",new Select(this));
		card_con.add("join", new Join(this));
		card_con.add("mainPanel", new MainPanel(this));//������ �ǾƷ�������..
		
		container.add(top_con);
		container.add(card_con);
		
		//pack();
		setUndecorated(true);//�׵θ� ����
		setResizable(false);
		setVisible(true);
	}
	
	public void changeCard(String str){
		cardLayout.show(container, str);
	}
}
