package cn.bonjour;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DrawImage
 */
@WebServlet("/DrawImage")
public class DrawImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 120;
	private static final int HEIGHT = 30;
	private static Properties prop = null;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 在内存中创建一张图片（120*30，RGB格式）
		BufferedImage checkImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		// 取出图片的画笔
		Graphics g = checkImg.createGraphics();
		// 设置缓存图片背景色
		this.setBackground(g);
		// 绘制图片边框
		this.drawBorder(g);
		// 添加干扰线
		this.drawRandomLines(g);
		// 生成随机码
		String randomCode = genRandomCode(g, request).toString();
		//保存随机码到session
		request.getSession().setAttribute("randomCode", randomCode);
		// 设置响应头，告知浏览器以图片形式打开
		response.setHeader("Content-Type", "image/jpeg");
//		response.setContentType("image/jpeg");
		// 设置响应头控制浏览器不要缓存
		response.setDateHeader("expries", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		// 将图片写给浏览器
		ImageIO.write(checkImg, "jpg", response.getOutputStream());
	}

	/**
	 * 设置缓存图片的背景色
	 * 
	 * @param g
	 */
	private void setBackground(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}

	private void drawBorder(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, WIDTH, HEIGHT);
	}

	private void drawRandomLines(Graphics g) {
		int randNum = 0;
		for (int i = 0; i < 5; i++) {
			randNum = new Random().nextInt(5);
			// 随机改变画笔颜色
			switch (randNum) {
			case 0:
				g.setColor(Color.RED);
				break;
			case 1:
				g.setColor(Color.YELLOW);
				break;
			case 2:
				g.setColor(Color.GREEN);
				break;
			case 3:
				g.setColor(Color.GRAY);
				break;
			case 4:
				g.setColor(Color.CYAN);
				break;
			}
			int x1 = new Random().nextInt(WIDTH);
			int y1 = new Random().nextInt(HEIGHT);
			int x2 = new Random().nextInt(WIDTH);
			int y2 = new Random().nextInt(HEIGHT);
			g.drawLine(x1, y1, x2, y2);
		}
	}
	/**
	 * 随机生成验证码（根据传递的标识从对应的库中提取）
	 * @param g
	 */
	public StringBuffer genRandomCode(Graphics g,HttpServletRequest request) {
		StringBuffer res = new StringBuffer();
		// 选择类型
		int type = new Random().nextInt(4)+1;
		String path = request.getSession().getServletContext().getRealPath("");
		switch (type) {
		case 1:
			//letter
			path = path + "/WEB-INF/randomCodeLib/letter.properties";
			break;
		case 2:
			// digital
			path = path + "/WEB-INF/randomCodeLib/digital.properties";
			break;
		case 3:
			// combination
			path = path + "/WEB-INF/randomCodeLib/combination.properties";
			break;
		case 4:
			//character
			path = path + "/WEB-INF/randomCodeLib/character.properties";
			break;
		}
		System.out.println(path);
		try {
			prop.load(new FileInputStream(new File(path)));
		} catch (IOException e) {
			// ===============================logger==============
			System.out.println("选择库出错");
		}
		int libSize = prop.size();
		int randCode = 0;
		// 每次4-8个随机码
		int randCodeNum = new Random().nextInt(2)+4;
		for(int i=0; i<randCodeNum; i++) {
			// 对应库中的随机码字符
			randCode = new Random().nextInt(libSize)+1;
			res.append(prop.getProperty(randCode+""));
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font("华文楷体", Font.PLAIN, 20));
		//((Graphics2D)g).rotate(10);
		g.drawString(res.toString(), 15, 15);
		System.out.println(res.length());
		return res;
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		prop = new Properties();
	}

}
