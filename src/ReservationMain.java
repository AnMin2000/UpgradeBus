

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.text.*;
import java.time.LocalDate;
import java.util.Calendar;

// 표 테이블에 들어갈 표 정보 클래스
class Ticket {
    ArrayList<String> starttime = new ArrayList<String>();
    ArrayList<String> company = new ArrayList<String>();
    ArrayList<String> class_ = new ArrayList<String>();
    ArrayList<Integer> seats = new ArrayList<Integer>();
    ArrayList<Integer> price = new ArrayList<Integer>();

    public void insertTicket(String starttime) {
        this.starttime.add(starttime);
    }
}

// '출발 날짜' 콤보 박스에 삽입할 날짜 배열 클래스 DuringDateTest
class DuringDateTest {
    String[] date = new String[100];   // 날짜를 담을 배열(사이즈를 100으로 설정)
    int length;                        // 배열에 저장된 날짜의 개수를 담을 변수

    public DuringDateTest() {
        String startDt = "20230801";  // 시작 날짜
        int endDt = 20230831;        // 끝 날짜

        int startYear = Integer.parseInt(startDt.substring(0,4));   // 시작 날짜에서 연도만 잘라서 저장
        int startMonth= Integer.parseInt(startDt.substring(4,6));   // 시작 날짜에서 월만 잘라서 저장
        int startDate = Integer.parseInt(startDt.substring(6,8));   // 시작 날짜에서 일만 잘라서 저장

        Calendar cal = Calendar.getInstance();    // Calendar 객체 생성

        // Calendar의 Month, Date는 0부터 시작하므로 -1 해준다.
        // Calendar의 기본 날짜를 startDt로 세팅해준다.
        cal.set(startYear, startMonth - 1, startDate - 1);

        int count = 0;    // 날짜를 배열에 저장할 때마다 증가할 count 변수
        int i = 0;
        while(true) {
            this.date[i] = getDateByString(cal.getTime());   // 시작 날짜부터 시작하여 날짜를 배열에 저장

            // Calendar의 날짜를 하루씩 증가한다.
            cal.add(Calendar.DATE, 1); // one day increment
            i += 1;
            count += 1;   // count + 1

            // 현재 날짜가 종료일자보다 크면 종료
            if(getDateByInteger(cal.getTime()) > endDt) break;
        }

        this.length = count;   // 최종 count 값을 length에 저장
        }

    private static String getDateByLocalDate(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        return String.format("%04d%02d%02d", year, month, day);
    }
    public static int getDateByInteger(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return Integer.parseInt(sdf.format(date));
    }

    public static String getDateByString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}

// 뒤로가기 버튼
class ReservationBack extends JPanel {
    ReservationMain frame;

    public ReservationBack(ReservationMain frame, String id) {
        Color mycor=new Color(189,215,238);
        setBackground(mycor);
        this.frame = frame;

        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));


        // 뒤로가기 버튼 이미지
        ImageIcon backIcon = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/back.png");
        Image backImg = backIcon.getImage();
        Image backUpdate = backImg.getScaledInstance(100,50, Image.SCALE_SMOOTH);
        ImageIcon backUpdateIcon = new ImageIcon(backUpdate);

        ImageIcon backIcon2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/back2.png");
        Image backImg2 = backIcon2.getImage();
        Image backUpdate2 = backImg2.getScaledInstance(100,50, Image.SCALE_SMOOTH);
        ImageIcon backUpdateIcon2 = new ImageIcon(backUpdate2);

        JButton back = new JButton(backUpdateIcon);
        back.setPreferredSize(new Dimension(100,50));
        back.setRolloverIcon(backUpdateIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        back.setBorderPainted(false); // 버튼 테두리 설정해제
        back.setFocusPainted(false);
        back.setContentAreaFilled(false);
        back.setOpaque(false);
        add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new MainUi(id);
                frame.dispose();
            }
        });
    }
}

// 예약하기 화면의 위쪽 진회색 패널
class ReservationNorth extends JPanel {
    ReservationMain frame;

    public ReservationNorth(ReservationMain frame, String id) {
        Color mycor=new Color(189,215,238);
        setBackground(mycor);
        this.frame = frame;

        setLayout(new BorderLayout());
        add(new Title(), BorderLayout.WEST);
        add(new ReservationBack(this.frame, id), BorderLayout.EAST);
    }
}

// 표 테이블의 가운데 부분
class TicketOneWay extends JPanel implements MouseListener {
    JTable table;
    DefaultTableModel model;
    JScrollPane scroll;
    private String start = "시작";  // 시작 터미널
    private String end = "도착";    // 도착 터미널
    private String date = "날짜";   // 출발 날짜
    private String[] arr = null;   // 선택한 행의 데이터를 담을 배열
    int len;

    // 표 테이블 생성
    public TicketOneWay() {
        String[] title = {"출발시간","회사","등급","요금"}; // 컬럼 네임 설정
        String[][] row = new String[0][4];                    // 표들
        model = new DefaultTableModel(row,title);	// 열 이름 추가, 행은 0개 지정

        table = new JTable(model);   // 표 테이블 생성
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 하나의 행만 선택 가능

        scroll = new JScrollPane(table);  // 스크롤 팬 추가
        scroll.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//너무 붙어있어서 가장자리 띄움(padding)
        add("North", scroll);

        setVisible(true);
    }

    // 표 테이블에 데이터 추가
    public void showTicket(String start, String end, String date) {

        String[][] groupedData = new String[10][4];

        int index = 0; // Index to keep track of the current position in the groupedData array

        for (int i = 0; i < 10; i++) {
            // 출발시간 회사 등급 요금
            String[] data = {"09:00", "우진관광", "일반", "8,000원",
                    "10:00", "영호관광", "일반", "8,000원",
                    "11:00", "금왕관광", "우등", "9,000원",
                    "12:00", "우진관광", "일반", "8,000원",
                    "13:00", "우진관광", "우등", "9,000원",
                    "14:00", "금왕관광", "우등", "9,000원",
                    "15:00", "금왕관광", "일반", "8,000원",
                    "16:00", "영호관광", "우등", "9,000원",
                    "17:00", "금왕관광", "일반", "8,000원",
                    "18:00", "우진관광", "일반", "8,000원"};

            // Extract the data for the current hour (i.e., i * 4)
            String[] hourData = new String[4];
            System.arraycopy(data, i * 4, hourData, 0, 4);

            // Update the fare to 9000원 if it exceeds the limit
            int fareIndex = 3; // Index of the fare within the data array
            int fare = Integer.parseInt(hourData[fareIndex].replaceAll("[^0-9]", ""));
            if (fare > 9000) {
                hourData[fareIndex] = "9000원";
            }

            // Add the hourData to the groupedData array
            groupedData[index] = hourData;
            index++;

            // Insert the row to the JTable
            ((DefaultTableModel) table.getModel()).insertRow(i, hourData);
        }


        // 선택한 표에 대한 정보 가져오기
        table.addMouseListener(this);

        setVisible(true);
    }

    // 테이블에서 행 선택 시 이벤트 처리
    @Override
    public void mouseClicked(MouseEvent e) {
        String[] info = new String[4];     // 추출한 정보를 담을 배열
        int row = table.getSelectedRow();  // 테이블에서 선택한 행 인덱스 가져오기

        // 선택한 행에 대한 전체 데이터 추출
        for (int i = 0; i < table.getColumnCount(); i++) {
            info[i] = (String) table.getValueAt(row, i);
        }

        // 인스턴스 변수에 정보 전달
        this.arr = info;
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    // 출발 터미널 반환
    public String getStart() {
        return this.start;
    }

    // 도착 터미널 반환
    public String getEnd() {
        return this.end;
    }

    // 출발 날짜 반환
    public String getDate() {
        return this.date;
    }

    // 선택한 행 정보 반환
    public String[] getArr() {
        return this.arr;
    }

    // 선택한 행 정보 리셋
    public void setArr() {
        this.arr = null;
    }
}

// 예약하기 화면의 가운데 패널
class ReservationCenter extends JPanel {
    ReservationMain frame;
    static JComboBox<String> start = new JComboBox<String>();   // 출발 터미널 콤보박스
    static JComboBox<String> end = new JComboBox<String>();     // 도착 터미널 콤보박스
    static JComboBox<String> date = new JComboBox<String>();    // 출발 날짜 콤보박스
    //DB_connect DB = new DB_connect(); ********************************************************************
    public ReservationCenter(ReservationMain frame, String id) throws SQLException, ClassNotFoundException {
        setLayout(null);
        Color bgmycor=new Color(166,222,249);
        setBackground(bgmycor);
        Color mycor=new Color(189,215,238);

        //setBackground(mycor);
        // '예매하기' 글자
        JLabel title = new JLabel("예매하기");
        title.setBounds(60, 3, 150, 100);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        add(title);

        // 3개의 콤보박스를 감싸는 각각의 네모 박스 생성
        JPanel square = new JPanel();
        square.setBackground(mycor);
        square.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 16));
        square.setBounds(60,130,380,60);
        add(square);

        JPanel nextSquare1 = new JPanel();
        nextSquare1.setBackground(mycor);
        nextSquare1.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 16));
        nextSquare1.setBounds(60,330,380,60);
        add(nextSquare1);

        JPanel nextSquare2 = new JPanel();
        nextSquare2.setBackground(mycor);
        nextSquare2.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 16));
        nextSquare2.setBounds(60,530,380,60);
        add(nextSquare2);

        // 화살표 이미지 삽입
        ImageIcon profile = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/arrow.png");
        Image img = profile.getImage();
        Image updateImg = img.getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon updateIcon = new ImageIcon(updateImg);

        JLabel image = new JLabel(updateIcon);
        image.setBounds(220,235,50,50);
        image.setHorizontalAlignment(JLabel.CENTER);
        add(image);

        // '출발 터미널' 글자
        JLabel startTerminal = new JLabel("출발 터미널");
        startTerminal.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        square.add(startTerminal);

        // '도착 터미널' 글자
        JLabel endTerminal = new JLabel("도착 터미널");
        endTerminal.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        nextSquare1.add(endTerminal);

        // '출발 날짜' 글자
        JLabel startDate = new JLabel("출발 날짜");
        startDate.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        nextSquare2.add(startDate);

        String[] start_terminal = new String[100];
        String end_terminal[] = new String[100];

        //start_terminal=DB.start(); ********************************************************************
        ResultSet rs = new DB().print("distinct startRegion","timetable","Null","Null","Null","Null");
        int index = 0;
        while (rs.next() && index < 100) {
            start_terminal[index] = rs.getString(1);
            index++;
        }

        // '출발 터미널' 콤보박스에 정류장 리스트 삽입
        start.setPreferredSize(new Dimension(220,30));
        //start_terminal = DB.start().toArray(new String[0]);
        for (int i=0; i<start_terminal.length; i++){
            start.addItem(start_terminal[i]);
        }
        square.add(start);

        // '도착 터미널' 콤보박스에 정류장 리스트 삽입
        end.setPreferredSize(new Dimension(220,30));
        //end_terminal = DB.end(); ********************************************************************
        rs = new DB().print("distinct endRegion","timetable","Null","Null","Null","Null");
        index = 0;
        while (rs.next() && index < 100) {
            end_terminal[index] = rs.getString(1);
            index++;
        }
        for (int i=0; i<end_terminal.length; i++){
            end.addItem(end_terminal[i]);
        }
        nextSquare1.add(end);

        // '출발 날짜' 콤보박스에 삽입할 날짜 정보 객체 생성
        DuringDateTest ddt = new DuringDateTest();

        // '출발 날짜' 콤보박스 사이즈 설정
        this.date.setPreferredSize(new Dimension(230,30));

        // '출발 날짜' 콤보박스에 ddt 객체에서 생성했던 날짜들 삽입
        for (int i = 1; i <= ddt.length; i++) {
            this.date.addItem(ddt.date[i]);
        }
        nextSquare2.add(this.date);

        // 표 테이블
        JPanel ticketTable = new JPanel();
        ticketTable.setBounds(500,100,450,370);

        // 표 구현
        TicketOneWay tow = new TicketOneWay();
        ticketTable.add(tow);
        add(ticketTable);


        // 조회 버튼 이미지
        ImageIcon checkupIcon = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/checkup.png");
        Image checkupImg = checkupIcon.getImage();
        Image checkupUpdate = checkupImg.getScaledInstance(100,50, Image.SCALE_SMOOTH);
        ImageIcon checkupUpdateIcon = new ImageIcon(checkupUpdate);

        ImageIcon checkupIcon2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/checkup2.png");
        Image checkupImg2 = checkupIcon2.getImage();
        Image checkupUpdate2 = checkupImg2.getScaledInstance(100,50, Image.SCALE_SMOOTH);
        ImageIcon checkupUpdateIcon2 = new ImageIcon(checkupUpdate2);

        JButton checkup = new JButton(checkupUpdateIcon);
        checkup.setBounds(510,540,100,50);
        checkup.setRolloverIcon(checkupUpdateIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        checkup.setBorderPainted(false); // 버튼 테두리 설정해제
        checkup.setFocusPainted(false);
        checkup.setContentAreaFilled(false);
        checkup.setOpaque(false);
        add(checkup);


        // 좌석선택 버튼 이미지
        ImageIcon seatSelectIcon = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/seatSelect.png");
        Image seatSelectImg = seatSelectIcon.getImage();
        Image seatSelectUpdate = seatSelectImg.getScaledInstance(100,50, Image.SCALE_SMOOTH);
        ImageIcon seatSelectUpdateIcon = new ImageIcon(seatSelectUpdate);

        ImageIcon seatSelectIcon2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/seatSelect2.png");
        Image seatSelectImg2 = seatSelectIcon2.getImage();
        Image seatSelectUpdate2 = seatSelectImg2.getScaledInstance(100,50, Image.SCALE_SMOOTH);
        ImageIcon seatSelectUpdateIcon2 = new ImageIcon(seatSelectUpdate2);

        JButton seatSelect = new JButton(seatSelectUpdateIcon);
        seatSelect.setBounds(810,540,100,50);
        seatSelect.setRolloverIcon(seatSelectUpdateIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        seatSelect.setBorderPainted(false); // 버튼 테두리 설정해제
        seatSelect.setFocusPainted(false);
        seatSelect.setContentAreaFilled(false);
        seatSelect.setOpaque(false);
        add(seatSelect);

        // 조회 버튼 클릭시
        checkup.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tow.setArr();
                String st = start.getSelectedItem().toString();
                String ed = end.getSelectedItem().toString();
                String dt = date.getSelectedItem().toString();


                // DB로 시작 터미널, 도착 터미널 정보 보내기


                    tow.showTicket(st, ed, dt);


            }
        });

        // 좌석선택 버튼 클릭시
        seatSelect.addMouseListener(new MouseAdapter() {
            String st = null;
            String ed = null;
            String dt = null;

            public void mouseClicked(MouseEvent e) {
                this.st = tow.getStart();
                this.ed = tow.getEnd();
                this.dt = tow.getDate();

                    // 출발 터미널, 도착 터미널, 출발 날짜 등의 정보를 좌석 선택 페이지로 넘겨줌
                    new SeatsSelect(frame, id, st, ed, dt); //**********************************************************
                    frame.setVisible(false);

            }
        });
        ImageIcon background = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/test.jpg");
        JLabel image2 = new JLabel(background);
        image2.setBounds(-1800,-200,4500,1200);
        add(image2);
        setVisible(true);
    }
}

// '예약하기' 화면의 메인 부분
public class ReservationMain extends JFrame {
    public ReservationMain(String id) throws SQLException, ClassNotFoundException {
        setTitle("예약");
        setSize(1000,800);
        setResizable(false);
        setLocationRelativeTo(null);  // 프레임을 화면 정중앙에 뜨도록 설정
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        Container mainContainer = getContentPane();
        mainContainer.setLayout(new BorderLayout());

        mainContainer.add(new ReservationNorth(this, id), BorderLayout.NORTH);
        mainContainer.add(new ReservationCenter(this, id), BorderLayout.CENTER);

        setVisible(true);
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new ReservationMain("null");
    }

}
