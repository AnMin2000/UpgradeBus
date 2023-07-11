import javax.swing.*;
import java.awt.*;

public class tmp1 extends JFrame{
    private JPanel panel1;
    private JButton button1;

    public tmp1(){
        setSize(350,400);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon background = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/login_bus.png");
        Image img = background.getImage();
        Image updateImg = img.getScaledInstance(330,400,Image.SCALE_DEFAULT);
        ImageIcon updateIcon = new ImageIcon(updateImg);
        JLabel image2 = new JLabel(updateIcon);
        image2.setBounds(-7,-60,350,450);
        add(image2);
        add(panel1);
        setVisible(true);
    }
    public static void main(String[] args){
        new tmp1();
    }
}
