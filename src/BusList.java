import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.util.Vector;

class AddBus extends JFrame {
   // DB_connect DB = new DB_connect();

    public AddBus() {
        setTitle("추가");
        setLayout(null);
        setSize(600,400);
        setLocationRelativeTo(null);  // 프레임을 화면 정중앙에 뜨도록 설정
        setDefaultCloseOperation(0);
        Container c = getContentPane();

        JLabel ment = new JLabel("출발 터미널");
        ment.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        ment.setBounds(30,35,100,20);
        c.add(ment);

        JTextField start = new JTextField();
        start.setBounds(30,60,100,20);
        c.add(start);

        JLabel ment2 = new JLabel("도착 터미널");
        ment2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        ment2.setBounds(155,35,100,20);
        c.add(ment2);

        JTextField end = new JTextField();
        end.setBounds(155,60,100,20);
        c.add(end);

        JButton add = new JButton("추가");
        add.setBounds(100,180,50,10);
        c.add(add);


        JButton cancel = new JButton("취소");
        cancel.setBounds(160,180,50,10);
        c.add(cancel);
        cancel.addMouseListener(new MouseAdapter() {
            public void mouseCliked(MouseEvent e) {
                AddBus.this.dispose();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent w) {
                AddBus.this.dispose();
            }
        });
        setVisible(true);
    }
}

public class BusList extends JFrame {
    private static final long serialVersionUID = 1L;
    private Vector data = null;
    private Vector result = null;
    private Vector title = null;
    private JTable table = null;
    private DefaultTableModel model = null;
    private JButton btnAdd = null;
    private JButton btnDel = null;
    private JButton btnUpdate = null;
    private JButton btnClear = null;
    private JTextField tfNum = null;
    private JTextField tfName = null;
    private JTextField tfName2 = null;
    private JTextField stt = null;
    private JTextField cmp = null;
    private JTextField bcs = null;
    private JTextField bseat = null;
    private JLabel lblNum = null;
    private JLabel lblName = null;
    private JLabel lblName2 = null;
    private JLabel jstt = null;
    private JLabel jcmp = null;
    private JLabel jbcs = null;
    private JLabel jbseat = null;
    private String start = null;
    private String end = null;
    private String starttime = null;
    private String company = null;
    private String db_class = null;
    private String seats = null;



    public BusList(String user2) {
        super("운행정보 관리");
        this.setLocation(130,150);  // 프레임을 위치 설정

        this.setDefaultCloseOperation(0);

        this.data = new Vector();
        this.title = new Vector();
        this.title.add("배차 ID");
        this.title.add("버스 번호");
        this.title.add("출발 지역");
        this.title.add("도착 지역");
        this.title.add("출발 시간");
        this.title.add("도착 시간");

        this.model = new DefaultTableModel();

        this.model.setDataVector(result, this.title);
        this.table = new JTable(this.model);
        JScrollPane sp = new JScrollPane(this.table);
        this.table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index = BusList.this.table.getSelectedRow();
                Vector in = (Vector)BusList.this.data.get(index);
                start = (String)in.get(0);
                end = (String)in.get(1);
                starttime = (String)in.get(2);
                company = (String)in.get(3);
                db_class = (String)in.get(4);
                seats = (String)in.get(5);

                BusList.this.tfName.setText(start);
                BusList.this.tfName2.setText(end);
                BusList.this.stt.setText(starttime);
                BusList.this.cmp.setText(company);
                BusList.this.bcs.setText(db_class);
                BusList.this.bseat.setText(seats);
            }
        });
        JPanel panel = new JPanel();
        this.tfName = new JTextField(5);
        this.tfName2 = new JTextField(5);
        this.stt = new JTextField(5);
        this.cmp = new JTextField(5);
        this.bcs = new JTextField(10);
        this.bseat = new JTextField(10);


        this.lblName = new JLabel("배차 ID");
        this.lblName2 = new JLabel("버스 번호");
        this.jstt = new JLabel("출발  지역");
        this.jcmp = new JLabel("도착 지역");
        this.jbcs = new JLabel("출발 시간");
        this.jbseat = new JLabel("도착 시간");
        Vector result = BusList.this.selectAll();
        BusList.this.model.setDataVector(result, BusList.this.title);

        ImageIcon add1 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/add1.png");
        Image add11 = add1.getImage();
        Image add111 = add11.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon addIcon1 = new ImageIcon(add111);
        ImageIcon add2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/add2.png");
        Image add22 = add2.getImage();
        Image add222 = add22.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon addIcon2 = new ImageIcon(add222);
        this.btnAdd = new JButton(addIcon1);
        this.btnAdd.setBorderPainted(false); // 버튼 테두리 설정해제
        this.btnAdd.setRolloverIcon(addIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        this.btnAdd.setFocusPainted(false);
        this.btnAdd.setContentAreaFilled(false);
        this.btnAdd.setOpaque(false);
        //this.btnAdd = new JButton("추가");

        ImageIcon del1 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/del1.png");
        Image del11 = del1.getImage();
        Image del111 = del11.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon delIcon1 = new ImageIcon(del111);
        ImageIcon del2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/del2.png");
        Image del22 = del2.getImage();
        Image del222 = del22.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon delIcon2 = new ImageIcon(del222);
        this.btnDel = new JButton(delIcon1);
        this.btnDel.setBorderPainted(false); // 버튼 테두리 설정해제
        this.btnDel.setRolloverIcon(delIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        this.btnDel.setFocusPainted(false);
        this.btnDel.setContentAreaFilled(false);
        this.btnDel.setOpaque(false);
        //this.btnDel = new JButton("삭제");

        ImageIcon close1 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/close1.png");
        Image close11 = close1.getImage();
        Image close111 = close11.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon closeIcon1 = new ImageIcon(close111);
        ImageIcon close2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/close2.png");
        Image close22 = close2.getImage();
        Image close222 = close22.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon closeIcon2 = new ImageIcon(close222);
        this.btnUpdate = new JButton(closeIcon1);
        this.btnUpdate.setBorderPainted(false); // 버튼 테두리 설정해제
        this.btnUpdate.setRolloverIcon(closeIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        this.btnUpdate.setFocusPainted(false);
        this.btnUpdate.setContentAreaFilled(false);
        this.btnUpdate.setOpaque(false);

        this.btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String timeID = BusList.this.tfName.getText();
                String busNum = BusList.this.tfName2.getText();
                String startRe = BusList.this.stt.getText();
                String endRe = BusList.this.cmp.getText();
                String startT = BusList.this.bcs.getText();
                String endT = BusList.this.bseat.getText();

                try {
                    String[] PrArr = new String[]{timeID, busNum, startRe, endRe, startT, endT};
                    new DB().insert("timetable", 6, PrArr);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                Vector result = BusList.this.selectAll();
                BusList.this.model.setDataVector(result, BusList.this.title);


            }
        });
        this.btnDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String timeID = BusList.this.tfName.getText();
                String busNum = BusList.this.tfName2.getText();
                String startRe = BusList.this.stt.getText();
                String endRe = BusList.this.cmp.getText();
                String startT = BusList.this.bcs.getText();
                String endT = BusList.this.bseat.getText();

                try {
                    new DB().timeDelete(timeID, busNum, startRe, endRe, startT, endT);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                Vector result = BusList.this.selectAll();
                BusList.this.model.setDataVector(result, BusList.this.title);
            }
        });
        this.btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BusList.this.dispose();
            }
        });
        panel.add(this.lblName);
        panel.add(this.tfName);
        panel.add(this.lblName2);
        panel.add(this.tfName2);
        panel.add(this.jstt);
        panel.add(this.stt);
        panel.add(this.jcmp);
        panel.add(this.cmp);
        panel.add(this.jbcs);
        panel.add(this.bcs);
        panel.add(this.jbseat);
        panel.add(this.bseat);
        panel.add(this.btnAdd);
        panel.add(this.btnDel);
        panel.add(this.btnUpdate);
        Container c = this.getContentPane();
        c.add(new JLabel("운행정보 리스트", 0), "North");
        c.add(sp, "Center");
        c.add(panel, "South");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent w) {
                try {
                    BusList.this.setVisible(false);
                    BusList.this.dispose();
                } catch (Exception var3) {
                }

            }
        });
        this.setSize(1300, 600);
        this.setVisible(true);
    }
    public static void main(String[] args){
        new BusList("name");
    }
    private Vector selectAll() {
        this.data.clear();

        try {
            ResultSet rs = new DB().print("*","timetable","Null","Null","Null","Null");

            while(rs.next()) {
                Vector in = new Vector();
                String start = rs.getString(1);
                String end = rs.getString(2);
                String starttime = rs.getString(3);
                String company = rs.getString(4);
                String db_class = rs.getString(5);
                String seats = rs.getString(6);

                in.add(start);
                in.add(end);
                in.add(starttime);
                in.add(company);
                in.add(db_class);
                in.add(seats);

                this.data.add(in);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }
        return this.data;

    }

}
