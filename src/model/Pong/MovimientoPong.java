package model.Pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MovimientoPong extends JPanel implements ActionListener, KeyListener  {
	
	Random rand = new Random();
	
	int ptos1=0, ptos2=0 ;
	int x1=7, x2=520, xB=270;
	int velocidadXB=rand.nextInt(6)-3;
	int y1=100, y2=100, yB=100;
	int velocidadY1=0, velocidadY2=0, velocidadYB=rand.nextInt(11)-5 ;
	
	Timer timer;
	
	public MovimientoPong() {
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(this);
		
		timer = new Timer(10, this);
//		timer.start();
		
	} 
	
	public void reiniciar() {
		ptos1=0;
		ptos2=0;
		x1=7;
		x2=520;
		xB=270;
		velocidadXB=rand.nextInt(6)-3;
		y1=100;
		y2=100;
		yB=100;
		velocidadY1=0;
		velocidadY2=0;
		velocidadYB=rand.nextInt(11)-5;
		
		timer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(x1, y1, 10, 70);
		
		g.setColor(Color.WHITE);
		g.fillRect(x2, y2, 10, 70);
		
		g.setColor(Color.WHITE);
		g.fillOval(xB, yB, 20, 20);
		
		g.setColor(Color.GRAY);
		g.setFont(new Font("Consolas", Font.BOLD, 50));
		g.drawString(String.valueOf(ptos1)+"-"+String.valueOf(ptos2), 230, 100);
		
		if (ptos1>=7) {
			timer.stop();
			g.setColor(Color.YELLOW);
			g.drawString("Gana jugador 1!", 60, 100);
		}
		if (ptos2>=7) {
			timer.stop();
			g.setColor(Color.YELLOW);
			g.drawString("Gana jugador 2!", 60, 100);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(velocidadXB==0) velocidadXB=rand.nextInt(11)-5;
		if(velocidadYB==0) velocidadYB=rand.nextInt(11)-5;
		
		xB+=velocidadXB;
		
		y1+=velocidadY1;
		y2+=velocidadY2;
		yB+=velocidadYB;
		
		Rectangle rect1 = new Rectangle(x1, y1, 10, 70);
		Rectangle rect2 = new Rectangle(x2, y2, 10, 70);
		Rectangle bola = new Rectangle(xB, yB, 15, 15);
		
		if(yB<0) velocidadYB*=-1;
		if(yB>312) velocidadYB*=-1;
		if(y1<0) y1=0;
		if(y1>270) y1=270;
		if(y2<0) y2=0;
		if(y2>270) y2=270;
		if(bola.intersects(rect1)) {
			velocidadXB*=-1 ;
			velocidadXB+=1 ;
		}
		if(bola.intersects(rect2)) {
			velocidadXB*=-1;
			velocidadXB-=1 ;
		}
		if(xB<0) {
			ptos2++;
			xB=270;
			yB=100;
			velocidadXB=5;
		}
		if (xB>x2) {
			ptos1++;
			xB=270;
			yB=100;
			velocidadXB=-5;
		}
		repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {	
		int tecla = e.getKeyCode();
		
        if (tecla == KeyEvent.VK_UP) velocidadY2 = -5;
        if (tecla == KeyEvent.VK_DOWN) velocidadY2 = 5;
        
        if (tecla == KeyEvent.VK_W) velocidadY1 = -5;
        if (tecla == KeyEvent.VK_S) velocidadY1 = 5; 
        if (tecla == KeyEvent.VK_ESCAPE) {
        	if (timer.isRunning()){
        		timer.stop(); 
        	} else timer.start() ;
        }
        if (tecla == KeyEvent.VK_R && !timer.isRunning()) reiniciar(); 
        if(!timer.isRunning()) if(tecla == KeyEvent.VK_UP || tecla == KeyEvent.VK_DOWN || tecla == KeyEvent.VK_W || tecla == KeyEvent.VK_S) timer.start();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int tecla = e.getKeyCode();
		
        if (tecla == KeyEvent.VK_UP) velocidadY2 = 0;
        if (tecla == KeyEvent.VK_DOWN) velocidadY2 = 0;
        
        if (tecla == KeyEvent.VK_W) velocidadY1 = 0;
        if (tecla == KeyEvent.VK_S) velocidadY1 = 0; 
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

}