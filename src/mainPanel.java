import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;

/**
 * Created by jack on 2016/12/18.
 */
public class mainPanel extends JPanel implements MouseMotionListener {
    ImageIcon m_imgIconBack = new ImageIcon(getClass().getResource("/img/nongsuo.png"));
    JPanel m_panel;
    JLabel label1 = null;
    boolean m_bRunning = false;
    JLabel m_backLabel;
    Thread m_t;
    boolean s = false;
    int m_curStrokeIndex;
    ImageIcon m_imgIconZhuanlun = new ImageIcon(getClass().getResource("/img/zhuanlun.png"));
    double m_curRotating;
    double m_speed = 0.2;
    ImageIcon m_imgIconXiaFamen1 = new ImageIcon(getClass().getResource("/img/xiafamen.png"));
    ImageIcon m_imgIconXiaFamen2 = new ImageIcon(getClass().getResource("/img/xiafamen2.png"));
    ImageIcon m_imgIconYouFamen1 = new ImageIcon(getClass().getResource("/img/youfamen.png"));
    ImageIcon m_imgIconYouFamen2 = new ImageIcon(getClass().getResource("/img/youfamen2.png"));
    ImageIcon m_imgIconZuoFamen1 = new ImageIcon(getClass().getResource("/img/zuofamen.png"));
    ImageIcon m_imgIconZuoFamen2 = new ImageIcon(getClass().getResource("/img/zuofamen2.png"));

    public mainPanel() {
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        label1 = new JLabel();
        label1.setText("sss");
        label1.setBounds(20, 663, 200, 20);
        this.add(label1);
        this.addMouseMotionListener(this);
        AddBackgroundLabelAndBtn();

    }

    public void paint(Graphics g) {
        super.paint(g);
        m_panel = this;
        //DrawBackground(g);
        DrawIndicator(g);
        DrawDashedLind(g);
        DrawZhuanlunImg(g);
        DrawFamenImg(g);
    }

    private void DrawBackground(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        Image img1 = m_imgIconBack.getImage();
        g2.drawImage(img1, 0, 0, 1502, 663, m_imgIconBack.getImageObserver());
        g2.dispose();
    }

    private void AddBackgroundLabelAndBtn() {
        m_backLabel = new JLabel();
        m_backLabel.setBounds(0, 0, 1502, 653);
        m_backLabel.setIcon(m_imgIconBack);
        this.add(m_backLabel);
        m_backLabel.setLayout(null);
        JButton btn = new JButton("开始");
        btn.setBounds(35, 620, 80, 25);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (m_bRunning == false) {
                    m_t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (true) {
                                    m_curStrokeIndex = --m_curStrokeIndex < 0 ? 12 : m_curStrokeIndex;
                                    m_curRotating -= m_speed;
                                    m_backLabel.repaint(205, 529, 42, 42);
                                    m_backLabel.repaint(851, 581, 42, 42);
                                    Rectangle[] rects = DashAniUtil.GetUpdateRectangles();
                                    for(int t = 0; t < rects.length; t++){
                                        m_backLabel.repaint(rects[t]);
                                    }
                                    Thread.sleep(50);
                                }
                            } catch (InterruptedException e1) {
                                //e1.printStackTrace();
                            }
                        }
                    });
                    m_t.start();
                } else {
                    m_t.interrupt();
                }
                m_bRunning = !m_bRunning;
                btn.setText(m_bRunning == true ? "停止" : "开始");
                m_backLabel.repaint(47, 540, 51, 51);
                DrawFamenImg(m_panel.getGraphics());
                //以下为阀门
                m_panel.repaint( 302, 544, 35, 30);
                m_panel.repaint( 784, 596, 35, 30);
                m_panel.repaint( 1217, 520, 35, 30);
                m_panel.repaint( 1202, 474, 35, 30);
                m_panel.repaint( 480, 211, 35, 30);
                m_panel.repaint( 480, 163, 35, 30);
                m_panel.repaint( 1201, 71, 35, 30);

                m_panel.repaint( 402, 493, 30, 35);
                m_panel.repaint( 711, 490, 30, 35);
                m_panel.repaint(284, 174, 30, 35);

                m_panel.repaint(531, 46, 30, 35);
                m_panel.repaint( 840, 52, 30, 35);

            }
        });
        m_backLabel.add(btn);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        label1.setText(e.getX() + "," + e.getY());
        m_panel.repaint(20, 663, 200, 20);
    }

    private void DrawIndicator(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(m_bRunning == false ? Color.RED : Color.GREEN);
        g2.fillOval(47, 540, 51, 51);
        g2.dispose();
    }

    private void DrawDashedLind(Graphics g) {
        Point[] points = new Point[]{new Point(347, 544), new Point(347, 506), new Point(218, 506), new Point(218, 208), new Point(440, 208), new Point(440, 160), new Point(516, 160),
                new Point(455, 208), new Point(514, 208),
                new Point(281, 161), new Point(281, 202),
                new Point(281, 221), new Point(281, 302), new Point(336, 302),
                new Point(451, 278), new Point(514, 278),
                new Point(573, 375), new Point(573, 447), new Point(424, 447),
                new Point(564, 60), new Point(724, 60), new Point(724, 225),
                new Point(615, 158), new Point(710, 158),
                new Point(723, 158), new Point(800, 158), new Point(800, 205),
                new Point(761, 280), new Point(822, 280),
                new Point(881, 375), new Point(881, 446), new Point(734, 446),
                new Point(870, 62), new Point(1064, 62),
                new Point(978, 78), new Point(978, 322),
                new Point(1167, 67), new Point(1270, 67),
                new Point(1176, 452), new Point(1176, 536), new Point(1255, 536),
                new Point(1220, 402), new Point(1220, 345), new Point(1152, 345)};
        int[] arr = new int[]{7,2, 2, 3, 2, 3, 3, 2, 3, 2, 3, 2, 2, 2, 3, 3};
        DashAniUtil.DrawDashLineWithPoints(g, m_curStrokeIndex, points, arr);
    }

    public void DrawZhuanlunImg(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        Image img3 = m_imgIconZhuanlun.getImage();
        g2.translate(226, 550);
        g2.rotate(m_curRotating);
        g2.drawImage(img3, -21, -21, 42, 42, m_imgIconZhuanlun.getImageObserver());
        g2.dispose();

        Graphics2D g3 = (Graphics2D) g.create();
        g3.translate(872, 602);
        g3.rotate(m_curRotating);
        g3.drawImage(img3, -21, -21, 42, 42, m_imgIconZhuanlun.getImageObserver());
        g3.dispose();
    }
    public void DrawFamenImg(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        Image img1 = m_bRunning == true ? m_imgIconXiaFamen1.getImage() : m_imgIconXiaFamen2.getImage();
        ImageObserver imgObs1 = m_bRunning == true ? m_imgIconXiaFamen1.getImageObserver() : m_imgIconXiaFamen2.getImageObserver();
        g2.drawImage(img1, 302, 544, 35, 30, imgObs1);
        g2.drawImage(img1, 784, 596, 35, 30, imgObs1);
        g2.drawImage(img1, 1217, 520, 35, 30, imgObs1);
        g2.drawImage(img1, 1202, 474, 35, 30, imgObs1);
        g2.drawImage(img1, 480, 211, 35, 30, imgObs1);
        g2.drawImage(img1, 480, 163, 35, 30, imgObs1);
        g2.drawImage(img1, 1201, 71, 35, 30, imgObs1);

        Image img2 = m_bRunning == true ? m_imgIconYouFamen1.getImage() : m_imgIconYouFamen2.getImage();
        ImageObserver imgObs2 = m_bRunning == true ? m_imgIconYouFamen1.getImageObserver() : m_imgIconYouFamen2.getImageObserver();
        g2.drawImage(img2, 402, 493, 30, 35, imgObs2);
        g2.drawImage(img2, 711, 490, 30, 35, imgObs2);
        g2.drawImage(img2, 284, 174, 30, 35, imgObs2);


        Image img3 = m_bRunning == true ? m_imgIconZuoFamen1.getImage() : m_imgIconZuoFamen2.getImage();
        ImageObserver imgObs3 = m_bRunning == true ? m_imgIconZuoFamen1.getImageObserver() : m_imgIconZuoFamen2.getImageObserver();
        g2.drawImage(img3, 531, 46, 30, 35, imgObs3);
        g2.drawImage(img3, 840, 52, 30, 35, imgObs3);


        g2.dispose();
    }
}
