package com.kingdee.bern;

import sun.lwawt.macosx.LWCToolkit;

import java.awt.*;

import javax.swing.*;


@SuppressWarnings("serial")
public class eightPlanets extends JPanel implements Runnable {
	private Image ep[], sky;
	private planet p[];
	private planet pEarth;
	private int period[] = {1,3,4,8,49,122,349,386};
	private int a = 40, b = 30;
	private double speed = 0.001;
	public eightPlanets() {
		setBackground(Color.BLACK);
		ep = new Image[10];
		p = new planet[9];
		sky = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/Images/sky.jpg"));
		for(int i=0; i<ep.length; i++) {		//��ʼ��ͼƬ0~9(������������~̫��)
			ep[i] = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/Images/"+(9-i)+".jpg"));
		}
		for(int i=0; i<p.length-1; i++) {		//��ʼ������(������~ˮ��)��ת�켣
			
			p[i] = new planet(20+a*i, 20+b*i+(620-b*(i)*2-25)/2, (920-a*(i)*2)/2, (620-b*(i)*2)/2, speed*period[i]);
			Thread m = new Thread(p[i]);
			m.start();
//			System.out.println(i+1+":"+(20+a*i)+","+(20+b*i+(620-b*(i)*2-25)/2)+","+((920-a*(i)*2)/2)+","+((620-b*(i)*2)/2));
		}

/*************text***************/
//		pEarth = new planet(220,317,260,160);
//		Thread a = new Thread(pEarth);
//		a.start();
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(sky, 0, 0, 1000, 700, this);		//������
		g.drawImage(ep[9], 400, 300, 60, 50, this);		//��̫��
		g.setColor(Color.CYAN);
		for(int i=0; i<8; i++) {		//�����
			g.drawOval(30+a*i, 20+b*i, 920-a*(i)*2, 620-b*(i)*2);
//			System.out.println(i+1+":"+(30+a*i)+","+(20+b*i)+","+(920-a*(i)*2)+","+(620-b*(i)*2));
		}
		for(int i=0; i<p.length-1; i++) {		//���˴�����
			g.drawImage(ep[i+1], p[i].getX(), p[i].getY(), 30, 30, this);
		}
//		g.drawImage(ep[6], pEarth.getX(), pEarth.getY(), 30, 30, this);
	}
	
	public static void main(String[] args) {
		JFrame jf = new JFrame();
		eightPlanets p = new eightPlanets();
		jf.setVisible(true);
		jf.setBounds(200, 20, 1000, 700);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(p);
		Thread threadJp = new Thread(p);
		threadJp.start();
	}
	@Override
	public void run() {
		while(true) {
			repaint();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}//run

}

class planet implements Runnable{
	private int x, y, rx, ry; 
	private int a;	//���� 
	private int b;	//���� 
	private double speed;
	private double degree;
	
	planet(int x, int y, int a, int b,double speed) {
		this.x = x;
		this.y = y;
		rx = x + a;
		ry = y;
		this.a = a;
		this.b = b;
		this.speed = speed;
		degree = Math.PI;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void run() {
		while(true) {
			if(degree > 2*Math.PI)
				degree = 0;
			x = (int)(a*Math.cos(degree))+rx;
			y = (int)(b*Math.sin(degree))+ry;
			degree = degree + speed;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}//run
}
