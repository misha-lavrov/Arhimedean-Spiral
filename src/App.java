import javax.swing.*;

public class App extends JFrame{

    public App(){
        setTitle("Спіраль Архімеда");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(new AppPanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}
