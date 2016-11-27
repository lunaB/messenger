package client;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Client extends JFrame{
	/*
	 * 메인화면 -> mainPanel
	 * 로그인 -> login
	 * 가입창 -> join
	 * 체팅 창 -> chat
	 * 커스텀 으로 체팅모드를 지원 하거나 플레이어간의 미니게임이 있도록 만들계획
	 */
	
	JLabel title = new JLabel(":: 대화로 ::");
	JButton small = new JButton();
	JButton close = new JButton();
	
	@Override //label change
	public void setTitle(String title) {
		this.title.setText(title);
	};
	
	private CardLayout cardLayout = new CardLayout();
	Container container;
	JPanel top_con = new JPanel();
	JPanel card_con = new JPanel();
	
	//card panel-----
	MainPanel mainPanel;
	Login login;
	Join join;
	//---------------
	
	//def_size 360 550
	int dsizeW=350;
	int dsizeH=550;
	int dcardH = 530;
	
	private Point mPoint;//pointer location tmp
	
	//adobe color CC
	Color def_color[] = {
		new Color(234,197,184),
		new Color(213,139,113),
		new Color(104,92,78),
		new Color(46,68,66),
		new Color(130,178,178)
	};
	
	Color titleBar = new Color(250, 245, 235);
	
	public Font def_font(int size){
		return new Font("맑은 고딕", 0, size);
	}
	
	public Client(){
		container = getContentPane();
		setSize(dsizeW, dsizeH); //debug
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {//1
                mPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {//3
                mPoint = null;
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {//2
            	//프레임 위치-(처음 누른위치-움직인 위치)
            	//프레임을 처음위치에서 움직인만큼 이동
            	if(mPoint != null){
            		setLocation(new Point(
            			getLocation().x - (mPoint.x-e.getX()),
            			getLocation().y - (mPoint.y-e.getY())
            		));
            	}
            }
		};
		
		top_con.setLayout(null);
		top_con.setLocation(0,0);
		top_con.setSize(dsizeW,35);
		top_con.setBackground(titleBar);
		
		top_con.addMouseListener(ma);//released pressed
		top_con.addMouseMotionListener(ma);//dragged
		
		title.setLocation(15, 6);
		title.setSize(100,18);
		title.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		
		small.setSize(38, 28);
		small.setLocation(dsizeW-80,4);
		small.setBackground(new Color(255, 140, 0));
		small.addActionListener(e->{
			setState(Frame.ICONIFIED); //최소화
		});
		
		close.setSize(38, 28);
		close.setLocation(dsizeW-40,4);
		close.setBackground(new Color(255, 45, 0));
		close.addActionListener(e->{
			int tmp = JOptionPane.showConfirmDialog(this,"정말종료하시겠습니까?","확인",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
			if(tmp == 0){
				System.exit(0);
			}
		});
		
		top_con.add(title);
		top_con.add(small);
		top_con.add(close);
		
		card_con.setLayout(cardLayout);
		card_con.setLocation(0, 20);
		card_con.setSize(dsizeW,dsizeH-20);
		
		mainPanel = new MainPanel(this); //start panel
		login = new Login(this);
		join = new Join(this);
		
		card_con.add("mainPanel", mainPanel); //start panel
		card_con.add("login",login);
		card_con.add("join", join);
		
		container.add(top_con);
		container.add(card_con);
		
		setUndecorated(true);//테두리 삭제
		setResizable(false);
		setVisible(true);
		changeCard("mainPanel");
	}
	
	public void changeCard(String str){
		cardLayout.show(card_con, str);
		container.repaint(); //debug.. 3hour
		if(str.equals("mainPanel")){
			setTitle(":: 대화로 ::");
		}else if(str.equals("login")){
			setTitle(":: 로그인 ::");
		}else if(str.equals("join")){
			setTitle(":: 가입 ::");
		}
	}
}
