import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AppPanel extends JPanel implements ActionListener {

    private int WIDTH = 1000,
            HEIGTH = 700;

    private int DELAY = 25;

    private boolean goDraw = false;
    private Timer timer;

    private BufferedImage img;
    private Graphics2D g2;
    private Image background;


    private JButton repaintBtn, clearBtn;
    private JTextField nFld, kFld, x0Fld, y0Fld;
    private JLabel lbl;

    private double x, y,
            x0, y0,
            fi, // початкова фаза
            err = 1, // похибка
            h = 0.1, // крок руху по колу

            n = 10, // кількість полукругів
            k = 5,  // відстань між кругами
            startX = 350, // початкова точка X
            startY = 350; // початкова точка Y

    public AppPanel(){
        setPreferredSize(new Dimension(WIDTH, HEIGTH));
        setLayout(null);
        initVisualComponent();
        background = Toolkit.getDefaultToolkit().createImage("background.jpg");
        x = 0;
        y = 0;
        fi = 0;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void initVisualComponent(){
        img = new BufferedImage(700, 700, BufferedImage.TYPE_INT_ARGB);

        g2 = img.createGraphics();
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(5));

        lbl = new JLabel("Кількість кругів:");
        lbl.setLocation(750, 75);
        lbl.setSize(125, 25);
        lbl.setVerticalAlignment(JLabel.CENTER);
        add(lbl);

        lbl = new JLabel("Відстань між кругами:");
        lbl.setLocation(750, 125);
        lbl.setSize(125, 25);
        lbl.setVerticalAlignment(JLabel.CENTER);
        add(lbl);

        lbl = new JLabel("Початкова Х:");
        lbl.setLocation(750, 175);
        lbl.setSize(125, 25);
        lbl.setVerticalAlignment(JLabel.CENTER);
        add(lbl);

        lbl = new JLabel("Початкова Y:");
        lbl.setLocation(750, 225);
        lbl.setSize(125, 25);
        lbl.setVerticalAlignment(JLabel.CENTER);
        add(lbl);

        nFld = new JTextField();
        nFld.setLocation(750, 100);
        nFld.setText("5");
        nFld.setSize(125, 25);
        add(nFld);

        kFld = new JTextField();
        kFld.setText("5");
        kFld.setLocation(750, 150);
        kFld.setSize(125, 25);
        add(kFld);

        x0Fld = new JTextField();
        x0Fld.setText("350");
        x0Fld.setLocation(750, 200);
        x0Fld.setSize(125, 25);
        add(x0Fld);

        y0Fld = new JTextField();
        y0Fld.setText("350");
        y0Fld.setLocation(750, 250);
        y0Fld.setSize(125, 25);
        add(y0Fld);

        repaintBtn = new JButton("Намалювати");
        repaintBtn.setLocation(750, 300);
        repaintBtn.setSize(125, 25);
        repaintBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    n = Integer.parseInt(nFld.getText()) * 2;
                    k = Integer.parseInt(kFld.getText());
                    startX = Integer.parseInt(x0Fld.getText());
                    startY = Integer.parseInt(y0Fld.getText());

                }catch (Exception exe){
                    n = 5;
                    k = 5;
                    startX = 350;
                    startY = 350;
                }
                fi = 0;
                x0 = 0;
                y0 = 0;
                x = 0;
                y = 0;
                goDraw = true;
            }
        });
        add(repaintBtn);

        clearBtn = new JButton("Очистити");
        clearBtn.setLocation(750, 350);
        clearBtn.setSize(125, 25);
        clearBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                img = new BufferedImage(700, 700, BufferedImage.TYPE_INT_ARGB);
                g2 = img.createGraphics();
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(5));
                goDraw = false;
                repaint();
            }
        });
        add(clearBtn);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g2.drawLine((int)(x0 + startX), (int)(y0 + startY),
                (int)(x + startX), (int)(y + startY));
        x0 = x;
        y0 = y;
        g.drawImage(img, 0, 0, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if((fi - n * Math.PI) < err && goDraw){
            fi += h;
            x = fi * Math.cos(fi) * k;
            y = fi * Math.sin(fi) * k;
            repaint();
        }else{
            fi = 0;
            x = 0;
            y = 0;
            x0 = 0;
            y0 = 0;
            startX = 350;
            startY = 350;
            goDraw = false;
        }
    }
}
