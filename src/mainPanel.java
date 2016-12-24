import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.awt.image.ImageObserver;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    JLabel m_tempLabel1;
    JLabel m_tempLabel2;
    double m_temp1;
    double m_temp2;
    double m_yewei1;
    double m_yewei2;
    double m_tempZheng1;
    double m_tempZheng2;
    double m_tempWater1Top;
    ExecutorService m_cachedThreadPool;

    public mainPanel() {
        m_cachedThreadPool = Executors.newCachedThreadPool();
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        label1 = new JLabel();
        label1.setText("sss");
        label1.setBounds(20, 663, 200, 20);
        this.add(label1);
        this.addMouseMotionListener(this);
        AddBackgroundLabelAndBtn();

        m_tempLabel1 = new JLabel("SB个号");
        m_tempLabel1.setFont(new Font("宋体", Font.PLAIN, 18));
        m_tempLabel1.setBounds(1427, 133, 100, 20);
        m_backLabel.add(m_tempLabel1);
        m_tempLabel2 = new JLabel("SB个号");
        m_tempLabel2.setFont(new Font("宋体", Font.PLAIN, 18));
        m_tempLabel2.setBounds(1427, 166, 100, 20);
        m_backLabel.add(m_tempLabel2);
        AddSliders();
    }

    public void paint(Graphics g) {
        super.paint(g);
        m_panel = this;
        //DrawBackground(g);
        DrawIndicator(g);
        DrawDashedLind(g);
        DrawZhuanlunImg(g);
        DrawZhenqi1(g);
        DrawZhenqi2(g);
        DrawFamenImg(g);
        DrawTempStaticLabel();
        DrawWaters(g);DrawWater1Top(g);
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

                m_panel.repaint( 1205, 371, 30, 35);
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
                new Point(1220, 402), new Point(1220, 345), new Point(1152, 345),
                new Point(339,400), new Point(258, 400)
        };
        int[] arr = new int[]{7,2, 2, 3, 2, 3, 3, 2, 3, 2, 3, 2, 2, 2, 3, 3, 2};
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
        g2.drawImage(img2, 1205, 371, 30, 35, imgObs2);


        Image img3 = m_bRunning == true ? m_imgIconZuoFamen1.getImage() : m_imgIconZuoFamen2.getImage();
        ImageObserver imgObs3 = m_bRunning == true ? m_imgIconZuoFamen1.getImageObserver() : m_imgIconZuoFamen2.getImageObserver();
        g2.drawImage(img3, 531, 46, 30, 35, imgObs3);
        g2.drawImage(img3, 840, 52, 30, 35, imgObs3);


        g2.dispose();
    }

    private void DrawTempStaticLabel(){
        JLabel label1 = new JLabel();
        label1.setText("温度1");
        label1.setBounds(1277, 338, 100, 20);
        m_backLabel.add(label1);
        JLabel label2 = new JLabel();
        label2.setText("液位1");
        label2.setBounds(1277, 383, 100, 20);
        m_backLabel.add(label2);
        JLabel label3 = new JLabel();
        label3.setText("温度2");
        label3.setBounds(1277, 428, 100, 20);
        m_backLabel.add(label3);
        JLabel label4 = new JLabel();
        label4.setText("液位2");
        label4.setBounds(1277, 473, 100, 20);
        m_backLabel.add(label4);
        JLabel label5 = new JLabel();
        label5.setText("蒸1温度");
        label5.setBounds(1277, 518, 100, 20);
        m_backLabel.add(label5);
        JLabel label6 = new JLabel();
        label6.setText("蒸2温度");
        label6.setBounds(1277, 563, 100, 20);
        m_backLabel.add(label6);
        JLabel label7 = new JLabel();
        label7.setText("水1上温度");
        label7.setBounds(1277, 608, 100, 20);
        m_backLabel.add(label7);
    }

    private void AddSliders(){
        JSlider slider = new JSlider(0, 100);
        slider.setMajorTickSpacing(2);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(false);
        slider.setBackground(Color.WHITE);
        slider.setBounds(1328, 333, 150, 40);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider j = (JSlider) (e.getSource());
                m_temp1 = j.getValue();
                m_cachedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        m_tempLabel1.setText(m_temp1 + "℃");
                        if (m_panel != null) {
                            m_backLabel.repaint(519, 126, 90, 245);
                            m_backLabel.repaint(556,375, 7, 72);
                            m_backLabel.repaint(424,440, 132, 7);
                            //m_backLabel.repaint(VelocimeterUtil.GetUpdateArea());
                        }
                    }
                });
            }
        });
        slider.setValue(40);
        m_backLabel.add(slider);


        JSlider sliderYewei1 = new JSlider(0, 187);
        sliderYewei1.setMajorTickSpacing(2);
        sliderYewei1.setMinorTickSpacing(1);
        sliderYewei1.setPaintTicks(true);
        sliderYewei1.setPaintLabels(false);
        sliderYewei1.setBackground(Color.WHITE);
        sliderYewei1.setBounds(1328, 378, 150, 40);
        sliderYewei1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider j = (JSlider) (e.getSource());
                m_yewei1 = j.getValue();
                m_cachedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (m_panel != null) {
                            m_backLabel.repaint(519, 126, 90, 245);
                            //m_backLabel.repaint(VelocimeterUtil.GetUpdateArea());
                        }
                    }
                });
            }
        });
        sliderYewei1.setValue(2);
        m_backLabel.add(sliderYewei1);

        JSlider slider2 = new JSlider(0, 100);
        slider2.setMajorTickSpacing(2);
        slider2.setMinorTickSpacing(1);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(false);
        slider2.setBackground(Color.WHITE);
        slider2.setBounds(1328, 423, 150, 40);
        slider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider j = (JSlider) (e.getSource());
                m_temp2 = j.getValue();
                m_cachedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        m_tempLabel2.setText(m_temp2 + "℃");
                        if (m_panel != null) {
                            m_backLabel.repaint(829, 126, 90, 245);
                            m_backLabel.repaint(873,375, 7, 72);
                            m_backLabel.repaint(734, 440, 61, 7);
                            //m_backLabel.repaint(VelocimeterUtil.GetUpdateArea());
                        }
                    }
                });
            }
        });
        slider2.setValue(70);
        m_backLabel.add(slider2);

        JSlider sliderYewei2 = new JSlider(0, 187);
        sliderYewei2.setMajorTickSpacing(2);
        sliderYewei2.setMinorTickSpacing(2);
        sliderYewei2.setPaintTicks(true);
        sliderYewei2.setPaintLabels(false);
        sliderYewei2.setBackground(Color.WHITE);
        sliderYewei2.setBounds(1328, 468, 150, 40);
        sliderYewei2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider j = (JSlider) (e.getSource());
                m_yewei2 = j.getValue();
                m_cachedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (m_panel != null) {
                            m_backLabel.repaint(829, 126, 90, 245);
                            //m_backLabel.repaint(VelocimeterUtil.GetUpdateArea());
                        }
                    }
                });
            }
        });
        sliderYewei2.setValue(2);
        m_backLabel.add(sliderYewei2);

        JSlider sliderZhengqi1 = new JSlider(0, 100);
        sliderZhengqi1.setMajorTickSpacing(2);
        sliderZhengqi1.setMinorTickSpacing(1);
        sliderZhengqi1.setPaintTicks(true);
        sliderZhengqi1.setPaintLabels(false);
        sliderZhengqi1.setBackground(Color.WHITE);
        sliderZhengqi1.setBounds(1328, 513, 150, 40);
        sliderZhengqi1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider j = (JSlider) (e.getSource());
                m_tempZheng1 = j.getValue();
                m_cachedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        //m_tempLabel1.setText(m_temp1 + "℃");
                        if (m_panel != null) {
                            m_backLabel.repaint(287, 159, 20, 150);
                            m_backLabel.repaint(288,292, 45, 10);
                            //m_backLabel.repaint(424,440, 132, 7);
                            //m_backLabel.repaint(VelocimeterUtil.GetUpdateArea());
                        }
                    }
                });
            }
        });
        sliderZhengqi1.setValue(40);
        m_backLabel.add(sliderZhengqi1);

        JSlider sliderZhengqi2 = new JSlider(0, 100);
        sliderZhengqi2.setMajorTickSpacing(2);
        sliderZhengqi2.setMinorTickSpacing(1);
        sliderZhengqi2.setPaintTicks(true);
        sliderZhengqi2.setPaintLabels(false);
        sliderZhengqi2.setBackground(Color.WHITE);
        sliderZhengqi2.setBounds(1328, 558, 150, 40);
        sliderZhengqi2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider j = (JSlider) (e.getSource());
                m_tempZheng2 = j.getValue();
                m_cachedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        //m_tempLabel1.setText(m_tempZheng2 + "℃");
                        if (m_panel != null) {
                            m_backLabel.repaint(258, 388, 81, 10);
                            //m_backLabel.repaint(424,440, 132, 7);
                            //m_backLabel.repaint(VelocimeterUtil.GetUpdateArea());
                        }
                    }
                });
            }
        });
        sliderZhengqi2.setValue(40);
        m_backLabel.add(sliderZhengqi2);

        JSlider sliderWater1Top = new JSlider(0, 100);
        sliderWater1Top.setMajorTickSpacing(2);
        sliderWater1Top.setMinorTickSpacing(1);
        sliderWater1Top.setPaintTicks(true);
        sliderWater1Top.setPaintLabels(false);
        sliderWater1Top.setBackground(Color.WHITE);
        sliderWater1Top.setBounds(1328, 603, 150, 40);
        sliderWater1Top.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider j = (JSlider) (e.getSource());
                m_tempWater1Top = j.getValue();
                m_cachedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        //m_tempLabel1.setText(m_tempWater1Top + "℃");
                        if (m_panel != null) {
                            m_backLabel.repaint(562, 71, 10, 24);
                            m_backLabel.repaint(566,67, 51, 41);
                            m_backLabel.repaint(713,71, 10, 154);
                            //m_backLabel.repaint(VelocimeterUtil.GetUpdateArea());
                        }
                    }
                });
            }
        });
        sliderWater1Top.setValue(40);
        m_backLabel.add(sliderWater1Top);
    }

    private void DrawTempValueLabel(){
        JLabel label1 = new JLabel();
        label1.setText("温度1");
        label1.setBounds(1277, 338, 100, 20);
        m_backLabel.add(label1);
        JLabel label2 = new JLabel();
        label2.setText("液位1");
        label2.setBounds(1277, 388, 100, 20);
        m_backLabel.add(label2);
    }

    private void DrawWaters(Graphics g){
        double x = m_yewei1;
        Graphics2D g2 = (Graphics2D) g.create();
        Color color1 = ColorUtil.GetColor(m_temp1);
        g2.setColor(color1);
        GeneralPath path1 = new GeneralPath();
        path1.moveTo(608,332);
        path1.lineTo(564, 373);
        path1.lineTo(522, 331);
        path1.lineTo(522, 331 - m_yewei1);
        path1.curveTo(533, 331 - m_yewei1 - 10, 553, 331 - m_yewei1 + 10, 565, 331 - m_yewei1);
        path1.curveTo(576, 331 - m_yewei1 - 10, 587, 331 - m_yewei1 + 10, 608, 331 - m_yewei1);
        path1.closePath();
        g2.fill(path1);
        g2.dispose();
        Graphics2D g21=(Graphics2D)g.create();
        GeneralPath path=new GeneralPath();
        path.moveTo(565,375);
        path.lineTo(565,440);
        path.lineTo(424,440);
        BasicStroke bs=new BasicStroke(7);
        g21.setStroke(bs);
        g21.setColor(color1);
        g21.draw(path);
        g21.dispose();
        
        Graphics2D g22 = (Graphics2D) g.create();
        Color color2 = ColorUtil.GetColor(m_temp2);
        g22.setColor(color2);
        GeneralPath path2 = new GeneralPath();
        path2.moveTo(916,332);
        path2.lineTo(873, 374);
        path2.lineTo(828, 331);
        path2.lineTo(828, 331 - m_yewei2);
        path2.curveTo(837, 331 - m_yewei2 - 10, 852, 331 - m_yewei2 + 10, 868, 331 - m_yewei2);
        path2.curveTo(878, 331 - m_yewei2 - 10, 894, 331 - m_yewei2 + 10, 916, 331 - m_yewei2);
        path2.closePath();
        g22.fill(path2);
        g22.dispose();
        Graphics2D g23=(Graphics2D)g.create();
        GeneralPath path3=new GeneralPath();
        path3.moveTo(873,375);
        path3.lineTo(873, 440);
        path3.lineTo(734, 440);
        BasicStroke bs3=new BasicStroke(7);
        g23.setStroke(bs3);
        g23.setColor(color2);
        g23.draw(path3);
        g23.dispose();
    }

    private void DrawZhenqi1(Graphics g){
        Graphics2D g23=(Graphics2D)g.create();
        GeneralPath path3=new GeneralPath();
        path3.moveTo(291, 159);
        path3.lineTo(291, 295);
        path3.lineTo(336, 295);
        BasicStroke bs3=new BasicStroke(6);
        g23.setStroke(bs3);
        g23.setColor(ColorUtil.GetColor(m_tempZheng1));
        g23.draw(path3);
        g23.dispose();
    }

    private void DrawWater1Top(Graphics g){
        Graphics2D g23=(Graphics2D)g.create();
        GeneralPath path3=new GeneralPath();
        path3.moveTo(566, 95);
        path3.lineTo(566, 71);
        path3.lineTo(717, 71);
        path3.lineTo(717, 225);
        BasicStroke bs3=new BasicStroke(6);
        g23.setStroke(bs3);
        g23.setColor(ColorUtil.GetColor(m_tempWater1Top));
        g23.draw(path3);
        g23.dispose();
    }

    private void DrawZhenqi2(Graphics g){
        Graphics2D g23=(Graphics2D)g.create();
        GeneralPath path3=new GeneralPath();
        path3.moveTo(339,392);
        path3.lineTo(258, 392);
        BasicStroke bs3=new BasicStroke(6);
        g23.setStroke(bs3);
        g23.setColor(ColorUtil.GetColor(m_tempZheng2));
        g23.draw(path3);
        g23.dispose();
    }

}
