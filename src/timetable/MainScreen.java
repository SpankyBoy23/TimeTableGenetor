package timetable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.ButtonModel;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;


public final class MainScreen extends javax.swing.JFrame {

   DepartmentNames DN = new DepartmentNames();

    public MainScreen() {
        initComponents();
      
        SQLManager sQLManager = new SQLManager();
        CloseAllPanels();
        ShowRooms();
        ShowTeachersRecords();
        ShowStudent();
        ShowDepartments();
        ShowSubject();
        SetTeacherList();
     // Subject2Field.setEditable(false);
        Subject2Field.setEnabled(Subject2CheckBox.isSelected());
        SubjectCode2_Field.setEnabled(Subject2CheckBox.isSelected());
        Subject3Field.setEnabled(Subject3CheckBox.isSelected());
        SubjectCode3_Field.setEnabled(Subject3CheckBox.isSelected());
        Subject4Field.setEnabled(Subject4CheckBox.isSelected());
        SubjectCode4_Field.setEnabled(Subject4CheckBox.isSelected());
        Subject5Field.setEnabled(Subject5CheckBox.isSelected());
        SubjectCode5_Field.setEnabled(Subject5CheckBox.isSelected());
       // jScrollPane5.add(DepartmentsList3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE);
        Dashboard.setVisible(true);
         //DepartmentList.removeAll();
         DepartmentsList4.addItem("All");
        
         for(String dp : DN.departmentsCodesArr){
           //  DepartmentList.addItem(dp);
          //   DepartmentsList2.addItem(dp);
             DepartmentsList3.addItem(dp);
             DepartmentsList4.addItem(dp);
         }
        // ChangeList();
        
    }

  
   void AddRooms(){
        int floors = 0,roomCount;
        String BuildingId;
     
        SQLManager sqlManager = new SQLManager();
        
        BuildingId = BuildingIDField.getText();
        floors = Integer.parseInt(FloorsField.getText());
        roomCount = Integer.parseInt(RoomCountField.getText());

        for(int i = 1;i <= floors;i++){
            for(int j = 1;j <= roomCount;j++){
               // System.out.println(id,BuildingId+":"+i+"0"+j);
               // String data[] = {DepartmentsList2.getSelectedItem().toString(),BuildingId+":"+i+"0"+j,RoomCapcityField.getText()};
                sqlManager.RoomsRegistory(DepartmentsList2.getSelectedItem().toString(), BuildingId+":"+i+"0"+j, RoomCapcityField.getText());
               // tableModel.addRow(data);
               // id++;
            }
        }
        ShowRooms();
      //  System.out.println("Floors: "+floors);
        
    }
   void ShowRooms(){
        SQLManager sqlManager = new SQLManager();
        ResultSet rs=sqlManager.Rooms();
        DefaultTableModel buildingTable = (DefaultTableModel)BuildingTable.getModel();
        
         try{
        while(rs.next()){
        
        String []data={rs.getString("ID"),rs.getString("Department"),rs.getString("Building ID"),rs.getString("Capacity")};
        buildingTable.addRow(data);
        
        }
        }catch(SQLException e){
        System.out.println(e);
        }
    }
   public void CloseAllPanels(){
        Dashboard.setVisible(false);
        TeachersPanel.setVisible(false);
        CoursesPanel.setVisible(false);
        Rooms.setVisible(false);
        Students.setVisible(false);
        Calender.setVisible(false);
        AddTeachers.setVisible(false);
        TimeTable.setVisible(false);
        AddStudent.setVisible(false);
        CreateSubject.setVisible(false);
        CreateDepartment.setVisible(false);
        AllocationPanel.setVisible(false);
       // DepartmentList.setVisible(false);
   }
   
   ////Department
   void ShowDepartments(){
        SQLManager sql = new SQLManager();
        ResultSet rs=sql.GetDepartments();

        //System.out.println("Working");
       // Department_SubjectBox.
        Department_SubjectBox.removeAllItems();
        DepartmentListCourse.removeAllItems();
        AllocationDepartmentBox.removeAllItems();

        try{
            while(rs.next()){
        
                String data=rs.getString("Name");
                Department_SubjectBox.addItem(data);
                DepartmentListCourse.addItem(data);
                AllocationDepartmentBox.addItem(data);
        
        }
        }catch(SQLException e){
             System.out.println(e);
        } 
   }
   void ShowSubject(){
        SQLManager sql = new SQLManager();
        System.out.println(AllocationDepartmentBox.getSelectedItem());
        ResultSet rs2 = sql.GetDepartmentBName((String) AllocationDepartmentBox.getSelectedItem());
        ResultSet rs;
      
        //System.out.println("Working");
       // Department_SubjectBox.
        AllocationSubjectBox.removeAllItems();
        CoursesSubjectBox.removeAllItems();
      //  AllocationDepartmentBox.removeAllItems();

        try{
            rs2.next();
             rs = sql.GetSubjectByName(Integer.parseInt(rs2.getString("Dep_ID")));
            while(rs.next()){
                
                String data=rs.getString("Name");
                AllocationSubjectBox.addItem(data);
                CoursesSubjectBox.addItem(data);
               // AllocationDepartmentBox.addItem(data);
        
        }
        }catch(SQLException e){
             System.out.println(e);
        } 
   }

    /////////////Allocation
   void AddAllocation(){
    
        SQLManager sqlManager = new SQLManager();
        
       sqlManager.AddAllocation(AllocationTeacherBox.getSelectedItem().toString(), AllocationDepartmentBox.getSelectedItem().toString(), AllocationSubjectBox.getSelectedItem().toString(), SecA.isSelected(), SecB.isSelected(), SecC.isSelected(), SecD.isSelected());
    }
   
   /// ////////Teachesrs Work
   boolean TeacherExpression(){
          
          return Pattern.matches("[A-Za-z\\s]{3,50}",T_NameField.getText())&&Pattern.matches("[0-9]{13,13}",T_CNICField.getText()) && Pattern.matches("[0-9]{11,11}", T_PhoneField.getText())&& Pattern.matches("\\w+@[a-zA-Z]+?\\.[a-zA-Z]{2,3}", T_PhoneField.getText());
      }
   void AddTeachers(){
    
        SQLManager sqlManager = new SQLManager();
        String dOB = ""+Day.getSelectedItem()+"-"+Month.getSelectedItem()+"-"+Years.getSelectedItem();
        String gender = Gender.getSelection().getActionCommand();
        //boolean gender = ("Male".equals(genderVar)) ? true : false;
        if(TeacherExpression()){
           sqlManager.TeacherRegistory(T_NameField.getText(),Long.parseLong(T_CNICField.getText()),Long.parseLong(T_PhoneField.getText()), T_EmailField.getText(), T_AddressFeild.getText(),dOB, gender);
           ShowTeachersRecords();
           SetTeacherList();
        }else{
            JOptionPane.showMessageDialog(null,"Some Or More Information Not Vaild. \n Please Try Again.","Invaild Info" , 0);
        }
       // JOptionPane.showMessageDialog(rootPane,Gender.getSelection().getActionCommand());
        //System.out.println(T_NameField.getText()+":"+Integer.parseInt(T_CNICField.getText())+ Integer.parseInt(T_PhoneField.getText())+ T_EmailField.getText()+ T_AddressFeild.getText()+dOB+ gender+ TF_SubjectList.getSelectedItem().toString() );
        //System.out.println(T_Course1.getSelectedItem().toString()+ T_Course2.getSelectedItem().toString()+ T_Course3.getSelectedItem().toString()+ T_Course4.getSelectedItem().toString());
       
        
    }
   void ShowTeachersRecords(){
           SQLManager sqlManager = new SQLManager();
           ResultSet rs=sqlManager.Teachers();
           DefaultTableModel teachersTable = (DefaultTableModel)TeachersTable.getModel();
        teachersTable.setNumRows(0);
         try{
        while(rs.next()){
        
        String []data={rs.getString("ID"),rs.getString("Name"),rs.getString("Course"),rs.getString("Email"),rs.getString("Gender"),rs.getString("Phone No"),rs.getString("CNIC"),rs.getString("DateOfBrith")};
        teachersTable.addRow(data);
        
        }
        }catch(SQLException e){
        System.out.println(e);
        }
    }
   void SetTeacherList(){
           SQLManager sqlManager = new SQLManager();
           ResultSet rs=sqlManager.Teachers();
           
             try{
                 AllocationTeacherBox.removeAllItems();
        while(rs.next()){
        
            String data = rs.getString("Name");
            AllocationTeacherBox.addItem(data);
        
        }
        }catch(SQLException e){
           System.out.println(e);
        }
    }
   void DeleteTeachersRecord(){
           DefaultTableModel teachersTable = (DefaultTableModel)TeachersTable.getModel();
        SQLManager sqlManager = new SQLManager();

        int row = TeachersTable.getSelectedRow();
        
        sqlManager.deleteTeacher(Integer.parseInt(TeachersTable.getValueAt(row,0).toString()));
        //teachersTable.setNumRows(0);
        ShowTeachersRecords();
        SetTeacherList();
       
    }
   void SeacrhTeacher(int ID){
        SQLManager sqlManager = new SQLManager();
        //ResultSet rs=sqlManager.SeacrhTeachers(ID);
         try {
           ResultSet rs= sqlManager.SeacrhSQLTeachers(ID);
           rs.next();
           T_NameField.setText(rs.getString("Name"));
           T_CNICField.setText(rs.getString("CNIC"));
           T_PhoneField.setText(rs.getString("Phone No"));
           T_EmailField.setText(rs.getString("Email"));
           T_AddressFeild.setText(rs.getString("Address"));
           T_Male.setSelected(true);

       } catch (SQLException ex) {
           System.out.println("Error"+ ex);
       }
    }
   private void UpdateTeacher() {                                           
         DefaultTableModel teachersTable = (DefaultTableModel)TeachersTable.getModel();
        SQLManager sqlManager = new SQLManager();
        
        if(TeacherExpression()){
         String dOB = ""+Day.getSelectedItem()+"-"+Month.getSelectedItem()+"-"+Years.getSelectedItem();
        String gender = Gender.getSelection().getActionCommand();
        //boolean gender = ("Male".equals(genderVar)) ? true : false;
        long cnic = Long.parseLong(T_CNICField.getText());
        long phone = Long.parseLong(T_PhoneField.getText());
       // int row = TeachersTable.getSelectedRow();
        sqlManager.UpdateTeacher(T_NameField.getText(),cnic, phone, T_EmailField.getText(), T_AddressFeild.getText(),dOB, gender,Integer.parseInt(SearchField.getText()));
        
        SetTeacherList();
        ShowTeachersRecords();
        UpdateRoomIDField.setText("");
        UpdateRoomCountField.setText("");
        }else{
            JOptionPane.showMessageDialog(null,"Some Or More Information Not Vaild. \n Please Try Again.","Invaild Info" , 0);
        }
    }   

    ///////////// Students Work
   void ShowStudent(){
         DefaultTableModel studentTable = (DefaultTableModel)StudentTable.getModel();
           SQLManager sqlManager = new SQLManager();
           ResultSet rs=sqlManager.ShowStudent();
           
        studentTable.setNumRows(0);
         try{
        while(rs.next()){
        
        String []data={rs.getString("ID"),rs.getString("Dep Code")+"-"+String.valueOf(rs.getString("Year")).substring(2)+rs.getString("Season")+"-"+String.format("%03d",Integer.parseInt(rs.getString("ID"))),rs.getString("Section")};
        studentTable.addRow(data);
        
        }
        }catch(SQLException e){
        System.out.println(e);
        }
         
         
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jButton1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel20 = new javax.swing.JLabel();
        Gender = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        DashboardBtn = new javax.swing.JButton();
        TeachersBtn = new javax.swing.JButton();
        CoursesBtn = new javax.swing.JButton();
        RoomsBtn = new javax.swing.JButton();
        StudentsBtn = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        TimeTableBtn = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        AllocationPanel = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        AllocationTeacherBox = new javax.swing.JComboBox<>();
        jLabel55 = new javax.swing.JLabel();
        AllocationDepartmentBox = new javax.swing.JComboBox<>();
        AllocationSubjectBox = new javax.swing.JComboBox<>();
        jLabel56 = new javax.swing.JLabel();
        SecA = new javax.swing.JCheckBox();
        SecC = new javax.swing.JCheckBox();
        SecB = new javax.swing.JCheckBox();
        SecD = new javax.swing.JCheckBox();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        CreateSubject = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        Department_SubjectBox = new javax.swing.JComboBox<>();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel32 = new javax.swing.JLabel();
        Subject1Field = new javax.swing.JTextField();
        Subject2CheckBox = new javax.swing.JCheckBox();
        Subject2Field = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        Subject3Field = new javax.swing.JTextField();
        Subject3CheckBox = new javax.swing.JCheckBox();
        jLabel35 = new javax.swing.JLabel();
        Subject4Field = new javax.swing.JTextField();
        Subject4CheckBox = new javax.swing.JCheckBox();
        Subject5CheckBox = new javax.swing.JCheckBox();
        Subject5Field = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JSeparator();
        SubjectCode2_Field = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        SubjectCode3_Field = new javax.swing.JTextField();
        SubjectCode4_Field = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        SubjectCode5_Field = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        SubjectCode1_Field = new javax.swing.JTextField();
        CreateDepartment = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        DepartmentName_Field = new javax.swing.JTextField();
        CancelDepartmentPanelBtn = new javax.swing.JButton();
        AddDepartmentBtn = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel22 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        DepartmentCode_Field = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        AddStudent = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        DepartmentsList3 = new javax.swing.JComboBox<>();
        YearsList = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        SeasonsList = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        StudentCount_AddListing = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        SectionLimit_AdListing = new javax.swing.JTextField();
        AddStudentListBtn = new javax.swing.JButton();
        Calender = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel38 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jLabel41 = new javax.swing.JLabel();
        jCheckBox5 = new javax.swing.JCheckBox();
        jLabel42 = new javax.swing.JLabel();
        jCheckBox6 = new javax.swing.JCheckBox();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jCheckBox7 = new javax.swing.JCheckBox();
        jButton16 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel51 = new javax.swing.JLabel();
        TimeLength = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        TimingTable = new javax.swing.JTable();
        TimeTable = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        Rooms = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        RoomIDLabel = new javax.swing.JLabel();
        BuildingIDField = new javax.swing.JTextField();
        FloorsField = new javax.swing.JTextField();
        RoomIDLabel1 = new javax.swing.JLabel();
        RoomIDLabel2 = new javax.swing.JLabel();
        RoomCountField = new javax.swing.JTextField();
        DepartmentsList2 = new javax.swing.JComboBox<>();
        RoomIDLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        BuildingTable = new javax.swing.JTable();
        DeleteRoomBtn = new javax.swing.JButton();
        UpdateRoomIDField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        RoomIDLabel4 = new javax.swing.JLabel();
        RoomCapcityField = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        UpdateRoomCountField = new javax.swing.JTextField();
        UpdateRoom = new javax.swing.JButton();
        Students = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        DepartmentsList4 = new javax.swing.JComboBox<>();
        jSeparator5 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        StudentTable = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        AddTeachers = new javax.swing.JPanel();
        Label = new javax.swing.JLabel();
        NameLabel = new javax.swing.JLabel();
        T_NameField = new javax.swing.JTextField();
        CNICLabel = new javax.swing.JLabel();
        T_CNICField = new javax.swing.JTextField();
        PhoneLabel1 = new javax.swing.JLabel();
        Day = new javax.swing.JComboBox<>();
        Month = new javax.swing.JComboBox<>();
        Years = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        T_Male = new javax.swing.JRadioButton();
        T_Female = new javax.swing.JRadioButton();
        T_PhoneField = new javax.swing.JTextField();
        CNICLabel1 = new javax.swing.JLabel();
        CNICLabel2 = new javax.swing.JLabel();
        T_EmailField = new javax.swing.JTextField();
        T_AddressFeild = new javax.swing.JTextField();
        CNICLabel3 = new javax.swing.JLabel();
        UpdateTeachers = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        T_Submit = new javax.swing.JButton();
        jLabel50 = new javax.swing.JLabel();
        SearchField = new javax.swing.JTextField();
        SearchBtn = new javax.swing.JButton();
        Dashboard = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        TeachersPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TeachersTable = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        DeleteTeacherBtn = new javax.swing.JButton();
        CoursesPanel = new javax.swing.JPanel();
        DepartmentListCourse = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton9 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        CoursesSubjectBox = new javax.swing.JComboBox<>();
        jButton18 = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JSeparator();
        jButton17 = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        AllocationBtn = new javax.swing.JButton();

        jButton1.setText("jButton1");

        jScrollPane4.setViewportView(jTextPane1);

        jLabel20.setText("jLabel20");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1080, 680));
        setResizable(false);

        jPanel4.setBackground(new java.awt.Color(238, 238, 238));
        jPanel4.setPreferredSize(new java.awt.Dimension(850, 550));

        DashboardBtn.setFont(new java.awt.Font("Yu Gothic", 0, 14)); // NOI18N
        DashboardBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Dashboard32x32.png"))); // NOI18N
        DashboardBtn.setText("Dashboard");
        DashboardBtn.setPreferredSize(new java.awt.Dimension(137, 57));
        DashboardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DashboardBtnActionPerformed(evt);
            }
        });

        TeachersBtn.setFont(new java.awt.Font("Yu Gothic", 0, 14)); // NOI18N
        TeachersBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/teacher32x32.png"))); // NOI18N
        TeachersBtn.setText("Teachers");
        TeachersBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TeachersBtnActionPerformed(evt);
            }
        });

        CoursesBtn.setFont(new java.awt.Font("Yu Gothic", 0, 14)); // NOI18N
        CoursesBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Course32x32.png"))); // NOI18N
        CoursesBtn.setText("Courses");
        CoursesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CoursesBtnActionPerformed(evt);
            }
        });

        RoomsBtn.setFont(new java.awt.Font("Yu Gothic", 0, 14)); // NOI18N
        RoomsBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Room32x32.png"))); // NOI18N
        RoomsBtn.setText("Rooms");
        RoomsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RoomsBtnActionPerformed(evt);
            }
        });

        StudentsBtn.setFont(new java.awt.Font("Yu Gothic", 0, 14)); // NOI18N
        StudentsBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/student32x32.png"))); // NOI18N
        StudentsBtn.setText("Students");
        StudentsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentsBtnActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Yu Gothic", 0, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Tear-off32x32.png"))); // NOI18N
        jButton6.setText("Calender");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        TimeTableBtn.setFont(new java.awt.Font("Yu Gothic", 0, 14)); // NOI18N
        TimeTableBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/calendar32x32.png"))); // NOI18N
        TimeTableBtn.setText("Time Table");
        TimeTableBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimeTableBtnActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Yu Gothic", 0, 14)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Quit32x32.png"))); // NOI18N
        jButton10.setText("Quit");

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));

        jLabel18.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel18.setText("Course Allocation:");

        jLabel54.setText("Teacher Names:");

        AllocationTeacherBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel55.setText("Department");

        AllocationDepartmentBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        AllocationDepartmentBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AllocationDepartmentBoxActionPerformed(evt);
            }
        });

        AllocationSubjectBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel56.setText("Select Subject:");

        SecA.setText("Section A");

        SecC.setText("Section C");

        SecB.setText("Section B");
        SecB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SecBActionPerformed(evt);
            }
        });

        SecD.setText("Section D");

        jSeparator13.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane7.setViewportView(jTextArea2);

        jLabel57.setText("Note (Optional):");

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel58.setText("Select Sections:");

        jButton4.setFont(new java.awt.Font("Yu Gothic", 0, 24)); // NOI18N
        jButton4.setText("Submit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel59.setText("Teachers:");

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel60.setText("Department:");

        javax.swing.GroupLayout AllocationPanelLayout = new javax.swing.GroupLayout(AllocationPanel);
        AllocationPanel.setLayout(AllocationPanelLayout);
        AllocationPanelLayout.setHorizontalGroup(
            AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AllocationPanelLayout.createSequentialGroup()
                .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AllocationPanelLayout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AllocationPanelLayout.createSequentialGroup()
                        .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AllocationPanelLayout.createSequentialGroup()
                                .addGap(133, 133, 133)
                                .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(AllocationPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(AllocationPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel54)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(AllocationTeacherBox, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(AllocationPanelLayout.createSequentialGroup()
                                .addGap(112, 112, 112)
                                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AllocationPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel58)
                                    .addComponent(jLabel60)))
                            .addGroup(AllocationPanelLayout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SecA, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(SecB, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(57, 57, 57)
                                .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SecD)
                                    .addComponent(SecC)))
                            .addGroup(AllocationPanelLayout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(AllocationPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(AllocationSubjectBox, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(AllocationPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(AllocationDepartmentBox, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(0, 241, Short.MAX_VALUE))
            .addGroup(AllocationPanelLayout.createSequentialGroup()
                .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AllocationPanelLayout.createSequentialGroup()
                        .addGap(353, 353, 353)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AllocationPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AllocationPanelLayout.setVerticalGroup(
            AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AllocationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AllocationPanelLayout.createSequentialGroup()
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AllocationTeacherBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(65, 65, 65)
                        .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AllocationPanelLayout.createSequentialGroup()
                            .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(AllocationDepartmentBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(AllocationSubjectBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(SecA)
                                .addComponent(SecC))
                            .addGap(18, 18, 18)
                            .addGroup(AllocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(SecB)
                                .addComponent(SecD))
                            .addGap(37, 37, 37))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AllocationPanelLayout.createSequentialGroup()
                            .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18))))
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel30.setText("Create Subject");

        jLabel31.setFont(new java.awt.Font("Yu Gothic Light", 1, 11)); // NOI18N
        jLabel31.setText("Department");

        Department_SubjectBox.setFont(new java.awt.Font("Yu Gothic", 0, 11)); // NOI18N

        jLabel32.setFont(new java.awt.Font("Yu Gothic Light", 1, 11)); // NOI18N
        jLabel32.setText("Subject # 1");

        Subject1Field.setFont(new java.awt.Font("Yu Gothic", 0, 11)); // NOI18N

        Subject2CheckBox.setFont(new java.awt.Font("Yu Gothic Light", 1, 11)); // NOI18N
        Subject2CheckBox.setText("Unlock");
        Subject2CheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Subject2CheckBoxStateChanged(evt);
            }
        });
        Subject2CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Subject2CheckBoxActionPerformed(evt);
            }
        });

        Subject2Field.setFont(new java.awt.Font("Yu Gothic", 0, 11)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Yu Gothic Light", 1, 11)); // NOI18N
        jLabel33.setText("Subject # 2");

        jLabel34.setFont(new java.awt.Font("Yu Gothic Light", 1, 11)); // NOI18N
        jLabel34.setText("Subject # 3");

        Subject3Field.setFont(new java.awt.Font("Yu Gothic", 0, 11)); // NOI18N

        Subject3CheckBox.setFont(new java.awt.Font("Yu Gothic Light", 1, 11)); // NOI18N
        Subject3CheckBox.setText("Unlock");
        Subject3CheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Subject3CheckBoxStateChanged(evt);
            }
        });
        Subject3CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Subject3CheckBoxActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Yu Gothic Light", 1, 11)); // NOI18N
        jLabel35.setText("Subject # 4");

        Subject4Field.setFont(new java.awt.Font("Yu Gothic", 0, 11)); // NOI18N

        Subject4CheckBox.setFont(new java.awt.Font("Yu Gothic Light", 1, 11)); // NOI18N
        Subject4CheckBox.setText("Unlock");
        Subject4CheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Subject4CheckBoxStateChanged(evt);
            }
        });
        Subject4CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Subject4CheckBoxActionPerformed(evt);
            }
        });

        Subject5CheckBox.setFont(new java.awt.Font("Yu Gothic Light", 1, 11)); // NOI18N
        Subject5CheckBox.setText("Unlock");

        Subject5Field.setFont(new java.awt.Font("Yu Gothic", 0, 11)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Yu Gothic Light", 1, 11)); // NOI18N
        jLabel36.setText("Subject # 5");

        jButton15.setFont(new java.awt.Font("Yu Gothic Light", 0, 11)); // NOI18N
        jButton15.setForeground(new java.awt.Color(102, 102, 102));
        jButton15.setText("Cancel");

        jButton19.setFont(new java.awt.Font("Yu Gothic Light", 0, 11)); // NOI18N
        jButton19.setForeground(new java.awt.Color(102, 102, 102));
        jButton19.setText("Submit");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        SubjectCode2_Field.setFont(new java.awt.Font("Yu Gothic", 0, 11)); // NOI18N

        jLabel45.setFont(new java.awt.Font("Yu Gothic Light", 1, 11)); // NOI18N
        jLabel45.setText("Subject Code");

        jLabel46.setFont(new java.awt.Font("Yu Gothic Light", 1, 11)); // NOI18N
        jLabel46.setText("Subject Code");

        jLabel47.setFont(new java.awt.Font("Yu Gothic Light", 1, 11)); // NOI18N
        jLabel47.setText("Subject Code");

        SubjectCode3_Field.setFont(new java.awt.Font("Yu Gothic", 0, 11)); // NOI18N

        SubjectCode4_Field.setFont(new java.awt.Font("Yu Gothic", 0, 11)); // NOI18N

        jLabel52.setFont(new java.awt.Font("Yu Gothic Light", 1, 11)); // NOI18N
        jLabel52.setText("Subject Code");

        SubjectCode5_Field.setFont(new java.awt.Font("Yu Gothic", 0, 11)); // NOI18N

        jLabel53.setFont(new java.awt.Font("Yu Gothic Light", 1, 11)); // NOI18N
        jLabel53.setText("Subject Code");

        SubjectCode1_Field.setFont(new java.awt.Font("Yu Gothic", 0, 11)); // NOI18N

        javax.swing.GroupLayout CreateSubjectLayout = new javax.swing.GroupLayout(CreateSubject);
        CreateSubject.setLayout(CreateSubjectLayout);
        CreateSubjectLayout.setHorizontalGroup(
            CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CreateSubjectLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CreateSubjectLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(CreateSubjectLayout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Department_SubjectBox, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator11)
                    .addComponent(jSeparator10)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CreateSubjectLayout.createSequentialGroup()
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85))
                    .addGroup(CreateSubjectLayout.createSequentialGroup()
                        .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CreateSubjectLayout.createSequentialGroup()
                                .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(CreateSubjectLayout.createSequentialGroup()
                                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(Subject4Field, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(CreateSubjectLayout.createSequentialGroup()
                                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(Subject5Field, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(CreateSubjectLayout.createSequentialGroup()
                                        .addComponent(jLabel52)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(SubjectCode5_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CreateSubjectLayout.createSequentialGroup()
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(33, 33, 33)
                                    .addComponent(Subject1Field, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel53)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(SubjectCode1_Field, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CreateSubjectLayout.createSequentialGroup()
                                    .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(CreateSubjectLayout.createSequentialGroup()
                                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(33, 33, 33)
                                            .addComponent(Subject3Field, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(CreateSubjectLayout.createSequentialGroup()
                                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(33, 33, 33)
                                            .addComponent(Subject2Field, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel45)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(SubjectCode4_Field, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                                        .addComponent(SubjectCode3_Field)
                                        .addComponent(SubjectCode2_Field)))))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Subject2CheckBox)
                            .addComponent(Subject3CheckBox)
                            .addComponent(Subject4CheckBox)
                            .addComponent(Subject5CheckBox))))
                .addGap(300, 300, 300))
        );
        CreateSubjectLayout.setVerticalGroup(
            CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CreateSubjectLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Department_SubjectBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SubjectCode1_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel53))
                    .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel32)
                        .addComponent(Subject1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CreateSubjectLayout.createSequentialGroup()
                        .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(Subject2Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SubjectCode2_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel45))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(Subject3Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46)
                            .addComponent(SubjectCode3_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Subject3CheckBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(Subject4Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47)
                            .addComponent(SubjectCode4_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Subject4CheckBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(Subject5Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52)
                            .addComponent(SubjectCode5_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Subject5CheckBox)))
                    .addComponent(Subject2CheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(CreateSubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75))
        );

        jLabel1.setFont(new java.awt.Font("Yu Gothic", 0, 36)); // NOI18N
        jLabel1.setText("Department");

        jLabel6.setFont(new java.awt.Font("Yu Gothic Light", 0, 14)); // NOI18N
        jLabel6.setText("Department Name:");

        CancelDepartmentPanelBtn.setFont(new java.awt.Font("Yu Gothic", 0, 14)); // NOI18N
        CancelDepartmentPanelBtn.setText("Cancel");
        CancelDepartmentPanelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelDepartmentPanelBtnActionPerformed(evt);
            }
        });

        AddDepartmentBtn.setFont(new java.awt.Font("Yu Gothic", 0, 14)); // NOI18N
        AddDepartmentBtn.setText("Done");
        AddDepartmentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddDepartmentBtnActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane5.setViewportView(jTextArea1);

        jLabel22.setFont(new java.awt.Font("Yu Gothic Light", 0, 14)); // NOI18N
        jLabel22.setText("Comment(Option):");

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        DepartmentCode_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DepartmentCode_FieldActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Yu Gothic Light", 0, 14)); // NOI18N
        jLabel23.setText("Department Code:");

        javax.swing.GroupLayout CreateDepartmentLayout = new javax.swing.GroupLayout(CreateDepartment);
        CreateDepartment.setLayout(CreateDepartmentLayout);
        CreateDepartmentLayout.setHorizontalGroup(
            CreateDepartmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CreateDepartmentLayout.createSequentialGroup()
                .addGroup(CreateDepartmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CreateDepartmentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CreateDepartmentLayout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addGroup(CreateDepartmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(CreateDepartmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5)
                            .addComponent(DepartmentName_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DepartmentCode_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(CreateDepartmentLayout.createSequentialGroup()
                        .addGap(257, 257, 257)
                        .addComponent(CancelDepartmentPanelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(AddDepartmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(231, Short.MAX_VALUE))
        );
        CreateDepartmentLayout.setVerticalGroup(
            CreateDepartmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CreateDepartmentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CreateDepartmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(CreateDepartmentLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(CreateDepartmentLayout.createSequentialGroup()
                        .addComponent(DepartmentName_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(DepartmentCode_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(CreateDepartmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CancelDepartmentPanelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddDepartmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 102, 102));
        jLabel25.setText("New Student Listing");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Department:");

        YearsList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023" }));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Year:");

        SeasonsList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Spring", "Fall" }));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Season:");

        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Semster");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("No Of Student:");

        StudentCount_AddListing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentCount_AddListingActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Section Limit:");

        SectionLimit_AdListing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SectionLimit_AdListingActionPerformed(evt);
            }
        });

        AddStudentListBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        AddStudentListBtn.setText("Add Student");
        AddStudentListBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddStudentListBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddStudentLayout = new javax.swing.GroupLayout(AddStudent);
        AddStudent.setLayout(AddStudentLayout);
        AddStudentLayout.setHorizontalGroup(
            AddStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddStudentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AddStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(AddStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(AddStudentLayout.createSequentialGroup()
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(DepartmentsList3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(64, 64, 64)
                            .addComponent(jLabel14)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(StudentCount_AddListing, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, AddStudentLayout.createSequentialGroup()
                            .addGroup(AddStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(AddStudentListBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(AddStudentLayout.createSequentialGroup()
                                    .addGroup(AddStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(AddStudentLayout.createSequentialGroup()
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(YearsList, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(AddStudentLayout.createSequentialGroup()
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(SeasonsList, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(AddStudentLayout.createSequentialGroup()
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(64, 64, 64)
                                    .addComponent(jLabel16)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SectionLimit_AdListing, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(313, Short.MAX_VALUE))
        );
        AddStudentLayout.setVerticalGroup(
            AddStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddStudentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(AddStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DepartmentsList3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StudentCount_AddListing, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SectionLimit_AdListing, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddStudentLayout.createSequentialGroup()
                        .addGroup(AddStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(YearsList, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(AddStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SeasonsList, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(AddStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addComponent(AddStudentListBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );

        jLabel37.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 51, 102));
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/calendar_plus_40px.png"))); // NOI18N
        jLabel37.setText("  Calender");

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Monday");

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Tuesday");

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Wenesday");

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Thursday");

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Friday");

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Saturday");

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("Sunday");

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Add32x32.png"))); // NOI18N
        jButton16.setText("Submit");

        jLabel51.setText("Timings:");

        TimeLength.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00:30 Hours", "00:45 Hours", "01:00 Hours", "01:15 Hours", "01:30 Hours", "01:45 Hours", "02:00 Hours", "02:15 Hours", "02:30 Hours", "02:45 Hours", "03:00 Hours" }));
        TimeLength.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimeLengthActionPerformed(evt);
            }
        });

        TimingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"09:00 To 10:30"},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Time"
            }
        ));
        jScrollPane6.setViewportView(TimingTable);

        javax.swing.GroupLayout CalenderLayout = new javax.swing.GroupLayout(Calender);
        Calender.setLayout(CalenderLayout);
        CalenderLayout.setHorizontalGroup(
            CalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CalenderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(CalenderLayout.createSequentialGroup()
                .addGroup(CalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CalenderLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(CalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CalenderLayout.createSequentialGroup()
                                .addGap(152, 152, 152)
                                .addGroup(CalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(CalenderLayout.createSequentialGroup()
                                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TimeLength, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(CalenderLayout.createSequentialGroup()
                                .addGap(166, 166, 166)
                                .addGroup(CalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel38)
                                    .addGroup(CalenderLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jCheckBox1)))
                                .addGap(18, 18, 18)
                                .addGroup(CalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel39)
                                    .addGroup(CalenderLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jCheckBox2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(CalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel40)
                                    .addGroup(CalenderLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jCheckBox3)))
                                .addGap(15, 15, 15)
                                .addGroup(CalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel41)
                                    .addGroup(CalenderLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jCheckBox4)))
                                .addGap(18, 18, 18)
                                .addGroup(CalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel42)
                                    .addGroup(CalenderLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jCheckBox5)))
                                .addGap(18, 18, 18)
                                .addGroup(CalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel43)
                                    .addGroup(CalenderLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jCheckBox6)))
                                .addGap(18, 18, 18)
                                .addGroup(CalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel44)
                                    .addGroup(CalenderLayout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addComponent(jCheckBox7))))))
                    .addGroup(CalenderLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(203, Short.MAX_VALUE))
        );
        CalenderLayout.setVerticalGroup(
            CalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CalenderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CalenderLayout.createSequentialGroup()
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(CalenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CalenderLayout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox1))
                            .addGroup(CalenderLayout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox2))
                            .addGroup(CalenderLayout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox3))
                            .addGroup(CalenderLayout.createSequentialGroup()
                                .addComponent(jLabel41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox4))
                            .addGroup(CalenderLayout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox5))
                            .addGroup(CalenderLayout.createSequentialGroup()
                                .addComponent(jLabel43)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox6))
                            .addGroup(CalenderLayout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox7)))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(TimeLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jLabel7.setText("TimeTable");

        javax.swing.GroupLayout TimeTableLayout = new javax.swing.GroupLayout(TimeTable);
        TimeTable.setLayout(TimeTableLayout);
        TimeTableLayout.setHorizontalGroup(
            TimeTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TimeTableLayout.createSequentialGroup()
                .addGap(214, 214, 214)
                .addComponent(jLabel7)
                .addContainerGap(675, Short.MAX_VALUE))
        );
        TimeTableLayout.setVerticalGroup(
            TimeTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TimeTableLayout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jLabel7)
                .addContainerGap(379, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Room32x32.png"))); // NOI18N
        jLabel8.setText("Rooms");

        RoomIDLabel.setText("Building ID");

        BuildingIDField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        FloorsField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        RoomIDLabel1.setText("Floors");

        RoomIDLabel2.setText("Room Conut");

        RoomCountField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        RoomIDLabel3.setText("Department");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Add32x32.png"))); // NOI18N
        jButton3.setText("Add Building");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        BuildingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Department Name", "Room Names", "Capcity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        BuildingTable.setColumnSelectionAllowed(true);
        BuildingTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BuildingTableMouseClicked(evt);
            }
        });
        BuildingTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BuildingTableKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(BuildingTable);
        BuildingTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (BuildingTable.getColumnModel().getColumnCount() > 0) {
            BuildingTable.getColumnModel().getColumn(0).setMinWidth(20);
            BuildingTable.getColumnModel().getColumn(0).setPreferredWidth(30);
            BuildingTable.getColumnModel().getColumn(0).setMaxWidth(40);
            BuildingTable.getColumnModel().getColumn(3).setMinWidth(50);
            BuildingTable.getColumnModel().getColumn(3).setPreferredWidth(60);
            BuildingTable.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        DeleteRoomBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Delete32x32.png"))); // NOI18N
        DeleteRoomBtn.setText("Delete Room");
        DeleteRoomBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteRoomBtnActionPerformed(evt);
            }
        });

        jLabel17.setText("Building ID:");

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        RoomIDLabel4.setText("Room Capcity");

        RoomCapcityField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jLabel49.setText("Room Capcity");

        UpdateRoom.setText("Update");
        UpdateRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateRoomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout RoomsLayout = new javax.swing.GroupLayout(Rooms);
        Rooms.setLayout(RoomsLayout);
        RoomsLayout.setHorizontalGroup(
            RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RoomsLayout.createSequentialGroup()
                .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RoomsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(RoomsLayout.createSequentialGroup()
                                .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(RoomIDLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(RoomIDLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(RoomIDLabel3)
                                    .addComponent(RoomIDLabel2))
                                .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(RoomsLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(BuildingIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(FloorsField, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(RoomCountField, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(RoomCapcityField, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(26, 26, 26))
                                    .addGroup(RoomsLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(DepartmentsList2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                            .addGroup(RoomsLayout.createSequentialGroup()
                                .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(RoomIDLabel)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(RoomsLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton3)
                                            .addGroup(RoomsLayout.createSequentialGroup()
                                                .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel49)
                                                    .addComponent(jLabel17))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(UpdateRoomIDField, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                                                    .addComponent(UpdateRoomCountField))))))
                                .addGap(0, 256, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RoomsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RoomsLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(UpdateRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                        .addComponent(DeleteRoomBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        RoomsLayout.setVerticalGroup(
            RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RoomsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RoomIDLabel)
                    .addComponent(BuildingIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RoomIDLabel1)
                    .addComponent(FloorsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RoomIDLabel2)
                    .addComponent(RoomCountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RoomIDLabel4)
                    .addComponent(RoomCapcityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RoomIDLabel3)
                    .addComponent(DepartmentsList2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addGap(15, 15, 15)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UpdateRoomIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UpdateRoomCountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(RoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(UpdateRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DeleteRoomBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(57, 57, 57))
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RoomsLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/student32x32.png"))); // NOI18N
        jLabel9.setText("Students Management Panel");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Summer", "Fall" }));
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 51, 102));
        jLabel15.setText("Student Listing");

        jLabel26.setText("Department");

        jLabel27.setText("Year");

        jLabel28.setText("Season");

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        StudentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "CSC-21F-084", null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "ID", "Section", "Department"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(StudentTable);
        if (StudentTable.getColumnModel().getColumnCount() > 0) {
            StudentTable.getColumnModel().getColumn(0).setResizable(false);
            StudentTable.getColumnModel().getColumn(0).setPreferredWidth(10);
            StudentTable.getColumnModel().getColumn(1).setResizable(false);
            StudentTable.getColumnModel().getColumn(1).setPreferredWidth(200);
            StudentTable.getColumnModel().getColumn(2).setPreferredWidth(100);
            StudentTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        }

        jButton5.setText("Add New Listing ");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout StudentsLayout = new javax.swing.GroupLayout(Students);
        Students.setLayout(StudentsLayout);
        StudentsLayout.setHorizontalGroup(
            StudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StudentsLayout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 580, Short.MAX_VALUE))
            .addGroup(StudentsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(StudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(StudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(DepartmentsList4, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.Alignment.LEADING, 0, 102, Short.MAX_VALUE))
                    .addComponent(jLabel27)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        StudentsLayout.setVerticalGroup(
            StudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StudentsLayout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(StudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(StudentsLayout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(DepartmentsList4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118))
                    .addGroup(StudentsLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(StudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(StudentsLayout.createSequentialGroup()
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
                        .addContainerGap())))
        );

        AddTeachers.setBackground(new java.awt.Color(204, 204, 204));
        AddTeachers.setMaximumSize(new java.awt.Dimension(618, 407));

        Label.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        Label.setForeground(new java.awt.Color(0, 102, 153));
        Label.setText("New Employee");

        NameLabel.setText("Full Name:");

        CNICLabel.setText("CINC:");

        PhoneLabel1.setText("Date Of Brith:");

        Day.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        Month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month", "Jan.", "Feb.", "Mar.", "Apr.", "May.", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec." }));

        Years.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Years", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022" }));

        jLabel19.setText("Gender");

        Gender.add(T_Male);
        T_Male.setText("Male");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, T_Male, org.jdesktop.beansbinding.ELProperty.create("${actionCommand}"), T_Male, org.jdesktop.beansbinding.BeanProperty.create("actionCommand"));
        bindingGroup.addBinding(binding);

        T_Male.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T_MaleActionPerformed(evt);
            }
        });

        Gender.add(T_Female);
        T_Female.setText("Female");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, T_Female, org.jdesktop.beansbinding.ELProperty.create("${actionCommand}"), T_Female, org.jdesktop.beansbinding.BeanProperty.create("actionCommand"));
        bindingGroup.addBinding(binding);

        T_Female.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T_FemaleActionPerformed(evt);
            }
        });

        CNICLabel1.setText("Phone No:");

        CNICLabel2.setText("E-mail:");

        CNICLabel3.setText("Address:");

        UpdateTeachers.setText("Update");
        UpdateTeachers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateTeachersActionPerformed(evt);
            }
        });

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel24.setText("Insert Info");

        T_Submit.setText("Submit");
        T_Submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T_SubmitActionPerformed(evt);
            }
        });

        jLabel50.setText("Search a Teacher:");

        SearchBtn.setText("Search");
        SearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddTeachersLayout = new javax.swing.GroupLayout(AddTeachers);
        AddTeachers.setLayout(AddTeachersLayout);
        AddTeachersLayout.setHorizontalGroup(
            AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddTeachersLayout.createSequentialGroup()
                .addGroup(AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddTeachersLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddTeachersLayout.createSequentialGroup()
                                .addGroup(AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(NameLabel)
                                    .addComponent(CNICLabel)
                                    .addComponent(CNICLabel1)
                                    .addComponent(CNICLabel2)
                                    .addComponent(CNICLabel3))
                                .addGap(27, 27, 27)
                                .addGroup(AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(T_NameField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(T_CNICField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(T_PhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(T_EmailField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(T_AddressFeild, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(AddTeachersLayout.createSequentialGroup()
                                .addComponent(PhoneLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Day, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Years, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddTeachersLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddTeachersLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(T_Submit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20)
                        .addComponent(UpdateTeachers, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddTeachersLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(T_Male)
                        .addGap(18, 18, 18)
                        .addComponent(T_Female))
                    .addGroup(AddTeachersLayout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SearchBtn)))
                .addGap(0, 347, Short.MAX_VALUE))
            .addGroup(AddTeachersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Label, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
        );
        AddTeachersLayout.setVerticalGroup(
            AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddTeachersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Label, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddTeachersLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(T_NameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NameLabel))
                        .addGap(14, 14, 14)
                        .addGroup(AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(T_CNICField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CNICLabel))
                        .addGap(14, 14, 14)
                        .addGroup(AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(T_PhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CNICLabel1))
                        .addGap(14, 14, 14)
                        .addGroup(AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(T_EmailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CNICLabel2))
                        .addGap(14, 14, 14)
                        .addGroup(AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(T_AddressFeild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CNICLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PhoneLabel1)
                            .addComponent(Day, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Years, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(T_Submit, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UpdateTeachers, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(141, Short.MAX_VALUE))
                    .addGroup(AddTeachersLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(T_Male)
                            .addComponent(T_Female))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(AddTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SearchBtn))
                        .addGap(147, 147, 147))))
            .addGroup(AddTeachersLayout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jSeparator4))
        );

        Dashboard.setBackground(new java.awt.Color(102, 102, 102));
        Dashboard.setPreferredSize(new java.awt.Dimension(660, 420));

        jButton7.setBackground(new java.awt.Color(204, 204, 204));
        jButton7.setFont(new java.awt.Font("Yu Gothic", 2, 18)); // NOI18N
        jButton7.setForeground(new java.awt.Color(51, 51, 51));
        jButton7.setText("Teachers : 41");
        jButton7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(204, 204, 204));
        jButton11.setFont(new java.awt.Font("Yu Gothic", 2, 18)); // NOI18N
        jButton11.setForeground(new java.awt.Color(51, 51, 51));
        jButton11.setText("Students : 40001");
        jButton11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(204, 204, 204));
        jButton12.setFont(new java.awt.Font("Yu Gothic", 2, 18)); // NOI18N
        jButton12.setForeground(new java.awt.Color(51, 51, 51));
        jButton12.setText("Classes : 5");
        jButton12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(204, 204, 204));
        jButton13.setFont(new java.awt.Font("Yu Gothic", 2, 18)); // NOI18N
        jButton13.setForeground(new java.awt.Color(51, 51, 51));
        jButton13.setText("Timings");
        jButton13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jButton14.setBackground(new java.awt.Color(204, 204, 204));
        jButton14.setFont(new java.awt.Font("Yu Gothic", 2, 18)); // NOI18N
        jButton14.setForeground(new java.awt.Color(51, 51, 51));
        jButton14.setText("Subjects : 52");
        jButton14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Yu Gothic Medium", 3, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Dashboard32x32.png"))); // NOI18N
        jLabel3.setText("Dashboard");
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));

        javax.swing.GroupLayout DashboardLayout = new javax.swing.GroupLayout(Dashboard);
        Dashboard.setLayout(DashboardLayout);
        DashboardLayout.setHorizontalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
            .addGroup(DashboardLayout.createSequentialGroup()
                .addGroup(DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DashboardLayout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(127, 127, 127)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DashboardLayout.createSequentialGroup()
                        .addGap(302, 302, 302)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(216, Short.MAX_VALUE))
        );
        DashboardLayout.setVerticalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(113, Short.MAX_VALUE))
        );

        TeachersPanel.setBackground(new java.awt.Color(204, 204, 204));
        TeachersPanel.setPreferredSize(new java.awt.Dimension(666, 400));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Teachers");

        TeachersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Subjects", "E-mail", "Gender", "Phone", "CNIC", "DOB"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TeachersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TeachersTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TeachersTable);
        if (TeachersTable.getColumnModel().getColumnCount() > 0) {
            TeachersTable.getColumnModel().getColumn(0).setMinWidth(20);
            TeachersTable.getColumnModel().getColumn(0).setPreferredWidth(25);
            TeachersTable.getColumnModel().getColumn(0).setMaxWidth(35);
            TeachersTable.getColumnModel().getColumn(3).setPreferredWidth(100);
            TeachersTable.getColumnModel().getColumn(4).setMinWidth(60);
            TeachersTable.getColumnModel().getColumn(4).setPreferredWidth(70);
            TeachersTable.getColumnModel().getColumn(4).setMaxWidth(120);
            TeachersTable.getColumnModel().getColumn(5).setPreferredWidth(60);
            TeachersTable.getColumnModel().getColumn(6).setPreferredWidth(80);
            TeachersTable.getColumnModel().getColumn(7).setPreferredWidth(60);
        }

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Add32x32.png"))); // NOI18N
        jButton2.setText("Add Teacher");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        DeleteTeacherBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        DeleteTeacherBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Delete32x32.png"))); // NOI18N
        DeleteTeacherBtn.setText("Delete");
        DeleteTeacherBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteTeacherBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TeachersPanelLayout = new javax.swing.GroupLayout(TeachersPanel);
        TeachersPanel.setLayout(TeachersPanelLayout);
        TeachersPanelLayout.setHorizontalGroup(
            TeachersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TeachersPanelLayout.createSequentialGroup()
                .addGap(228, 228, 228)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(542, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TeachersPanelLayout.createSequentialGroup()
                .addGroup(TeachersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 956, Short.MAX_VALUE)
                    .addGroup(TeachersPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(DeleteTeacherBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        TeachersPanelLayout.setVerticalGroup(
            TeachersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TeachersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(TeachersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(DeleteTeacherBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        CoursesPanel.setBackground(new java.awt.Color(204, 204, 204));
        CoursesPanel.setPreferredSize(new java.awt.Dimension(686, 400));

        DepartmentListCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DepartmentListCourseActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Courses");

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel5.setText("Department");

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Course80x80.png"))); // NOI18N

        jButton9.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Add32x32.png"))); // NOI18N
        jButton9.setText("New Department");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel21.setText("Subjects:");

        CoursesSubjectBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CoursesSubjectBoxActionPerformed(evt);
            }
        });

        jButton18.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Add32x32.png"))); // NOI18N
        jButton18.setText("New Department");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton17.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Dashboard32x32.png"))); // NOI18N
        jButton17.setText("Dashboard");

        javax.swing.GroupLayout CoursesPanelLayout = new javax.swing.GroupLayout(CoursesPanel);
        CoursesPanel.setLayout(CoursesPanelLayout);
        CoursesPanelLayout.setHorizontalGroup(
            CoursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CoursesPanelLayout.createSequentialGroup()
                .addGroup(CoursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CoursesPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CoursesPanelLayout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addGroup(CoursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CoursesPanelLayout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(DepartmentListCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75)
                                .addComponent(jButton9))
                            .addGroup(CoursesPanelLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(56, 56, 56)
                                .addComponent(CoursesSubjectBox, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74)
                                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(CoursesPanelLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(CoursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(CoursesPanelLayout.createSequentialGroup()
                        .addGap(303, 303, 303)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CoursesPanelLayout.setVerticalGroup(
            CoursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CoursesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CoursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addGroup(CoursesPanelLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addGroup(CoursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DepartmentListCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(CoursesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CoursesSubjectBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jButton17)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(CoursesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(TeachersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(AddTeachers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Students, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Rooms, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Calender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(TimeTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(AddStudent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(CreateDepartment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(CreateSubject, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(AllocationPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(CoursesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addGap(0, 11, Short.MAX_VALUE)
                    .addComponent(TeachersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Dashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(AddTeachers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Students, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Rooms, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Calender, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(TimeTable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addGap(0, 10, Short.MAX_VALUE)
                    .addComponent(AddStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addGap(0, 9, Short.MAX_VALUE)
                    .addComponent(CreateDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addGap(0, 12, Short.MAX_VALUE)
                    .addComponent(CreateSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(AllocationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Logo.png"))); // NOI18N

        AllocationBtn.setFont(new java.awt.Font("Yu Gothic", 0, 14)); // NOI18N
        AllocationBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timetable/Icons/Room32x32.png"))); // NOI18N
        AllocationBtn.setText("Allocation");
        AllocationBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AllocationBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TimeTableBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(StudentsBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(AllocationBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DashboardBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TeachersBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CoursesBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RoomsBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addComponent(jLabel48)
                .addGap(0, 230, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 48, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(DashboardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(TeachersBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CoursesBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(RoomsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(AllocationBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(StudentsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(TimeTableBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1084, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DashboardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DashboardBtnActionPerformed
          CloseAllPanels();
        Dashboard.setVisible(true);
        
    }//GEN-LAST:event_DashboardBtnActionPerformed

    private void TeachersBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TeachersBtnActionPerformed
        CloseAllPanels();
        TeachersPanel.setVisible(true);
    }//GEN-LAST:event_TeachersBtnActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void CoursesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CoursesBtnActionPerformed
        CloseAllPanels();
        CoursesPanel.setVisible(true);
    }//GEN-LAST:event_CoursesBtnActionPerformed

    private void DepartmentListCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DepartmentListCourseActionPerformed
       ShowSubject();
    }//GEN-LAST:event_DepartmentListCourseActionPerformed

    private void StudentsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentsBtnActionPerformed
        CloseAllPanels();
        Students.setVisible(true);
    }//GEN-LAST:event_StudentsBtnActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        CloseAllPanels();
        Calender.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void RoomsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RoomsBtnActionPerformed
        CloseAllPanels();
        Rooms.setVisible(true);
    }//GEN-LAST:event_RoomsBtnActionPerformed

    private void TimeTableBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimeTableBtnActionPerformed
        CloseAllPanels();
        TimeTable.setVisible(true);
    }//GEN-LAST:event_TimeTableBtnActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        AddRooms();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void DeleteRoomBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteRoomBtnActionPerformed
        DefaultTableModel buildingTable = (DefaultTableModel)BuildingTable.getModel();
        SQLManager sqlManager = new SQLManager();

        int row =BuildingTable.getSelectedRow();
        
        sqlManager.deleteRoom(Integer.parseInt(BuildingTable.getValueAt(row,0).toString()));
        buildingTable.setNumRows(0);
        ShowRooms();
        UpdateRoomIDField.setText("");
        UpdateRoomCountField.setText("");
        
      //  DefaultTableModel tableModel = (DefaultTableModel)BuildingTable.getModel();
        //tableModel.removeRow(Integer.parseInt(UpdateRoomIDField.getText())-1);
    }//GEN-LAST:event_DeleteRoomBtnActionPerformed

    private void BuildingTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuildingTableKeyPressed
       
    }//GEN-LAST:event_BuildingTableKeyPressed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CloseAllPanels();
        AddTeachers.setVisible(true);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void T_MaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_MaleActionPerformed
       // T_Female.setSelected(false);
      //  T_Male.setSelected(true);
    }//GEN-LAST:event_T_MaleActionPerformed

    private void T_FemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_FemaleActionPerformed
     //    T_Female.setSelected(true);
     //   T_Male.setSelected(false);
    }//GEN-LAST:event_T_FemaleActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void SectionLimit_AdListingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SectionLimit_AdListingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SectionLimit_AdListingActionPerformed

    private void StudentCount_AddListingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentCount_AddListingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StudentCount_AddListingActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        CloseAllPanels();
        AddStudent.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void AddStudentListBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddStudentListBtnActionPerformed
           StudentListing st = new StudentListing();
         st.CreateStudentListing(DepartmentsList3.getSelectedItem().toString(), SeasonsList.getSelectedItem().toString(),Integer.parseInt(YearsList.getSelectedItem().toString()), Integer.parseInt(StudentCount_AddListing.getText()),Integer.parseInt(SectionLimit_AdListing.getText()));
          ShowStudent();
           CloseAllPanels();
           Students.setVisible(true);
    }//GEN-LAST:event_AddStudentListBtnActionPerformed

    private void BuildingTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BuildingTableMouseClicked
       
        DefaultTableModel buildingTable = (DefaultTableModel)BuildingTable.getModel();
        SQLManager sqlManager = new SQLManager();

        int row =BuildingTable.getSelectedRow();
  
       try {
           ResultSet rs= sqlManager.SelectRoom(Integer.parseInt(BuildingTable.getValueAt(row,0).toString()));
           rs.next();
           UpdateRoomIDField.setText(rs.getString("Building ID"));
           UpdateRoomCountField.setText(rs.getString("Capacity"));
       } catch (Exception ex) {
           System.out.println("Error"+ ex);
       }
    }//GEN-LAST:event_BuildingTableMouseClicked

    private void UpdateRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateRoomActionPerformed
        DefaultTableModel buildingTable = (DefaultTableModel)BuildingTable.getModel();
        SQLManager sqlManager = new SQLManager();
        
        int row =BuildingTable.getSelectedRow();
        sqlManager.UpdateRoom(Integer.parseInt(BuildingTable.getValueAt(row,0).toString()),Integer.parseInt(UpdateRoomCountField.getText()), UpdateRoomIDField.getText());
        
        buildingTable.setNumRows(0);
        ShowRooms();
        UpdateRoomIDField.setText("");
        UpdateRoomCountField.setText("");
    }//GEN-LAST:event_UpdateRoomActionPerformed

    private void UpdateTeachersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateTeachersActionPerformed
        UpdateTeacher();
    }//GEN-LAST:event_UpdateTeachersActionPerformed

    private void T_SubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_SubmitActionPerformed
        AddTeachers();
    }//GEN-LAST:event_T_SubmitActionPerformed

    private void TeachersTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TeachersTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TeachersTableMouseClicked

    private void DeleteTeacherBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteTeacherBtnActionPerformed
        DeleteTeachersRecord();
    }//GEN-LAST:event_DeleteTeacherBtnActionPerformed

    private void SearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBtnActionPerformed
       SeacrhTeacher(Integer.parseInt(SearchField.getText()));
    }//GEN-LAST:event_SearchBtnActionPerformed

    private void TimeLengthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimeLengthActionPerformed
        int a = 480 /  toMins(TimeLength.getSelectedItem().toString());
        int start = toMins("09:00");
        for(int i = 0;i < a;i++){
            start += toMins(TimeLength.getSelectedItem().toString());
            //String[] = {fromMinutesToHHmm(start)}
          //  TimingTable.add();
        }
    }//GEN-LAST:event_TimeLengthActionPerformed

    private void CoursesSubjectBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CoursesSubjectBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CoursesSubjectBoxActionPerformed

    private void DepartmentCode_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DepartmentCode_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DepartmentCode_FieldActionPerformed

    private void Subject4CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Subject4CheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Subject4CheckBoxActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        CloseAllPanels();
        CreateDepartment.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void CancelDepartmentPanelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelDepartmentPanelBtnActionPerformed
        CloseAllPanels();
        CoursesPanel.setVisible(true);
        
    }//GEN-LAST:event_CancelDepartmentPanelBtnActionPerformed

    private void AddDepartmentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddDepartmentBtnActionPerformed
        SQLManager sql = new SQLManager();
        sql.AddDepartment(DepartmentName_Field.getText(), DepartmentCode_Field.getText());
        ShowDepartments();
    }//GEN-LAST:event_AddDepartmentBtnActionPerformed

    private void Subject2CheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Subject2CheckBoxStateChanged
    
    }//GEN-LAST:event_Subject2CheckBoxStateChanged

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        CloseAllPanels();
        CreateSubject.setVisible(true);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void Subject3CheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Subject3CheckBoxStateChanged

         Subject3Field.setEnabled(Subject3CheckBox.isSelected());
         SubjectCode3_Field.setEnabled(Subject3CheckBox.isSelected());
      
    }//GEN-LAST:event_Subject3CheckBoxStateChanged

    private void Subject3CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Subject3CheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Subject3CheckBoxActionPerformed

    private void Subject4CheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Subject4CheckBoxStateChanged
   
            Subject4Field.setEnabled(Subject4CheckBox.isSelected());
            SubjectCode4_Field.setEnabled(Subject4CheckBox.isSelected());
       
    }//GEN-LAST:event_Subject4CheckBoxStateChanged

    private void Subject2CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Subject2CheckBoxActionPerformed
            System.out.println(Subject2CheckBox.isSelected());
   
            Subject2Field.setEnabled(Subject2CheckBox.isSelected());
            SubjectCode2_Field.setEnabled(Subject2CheckBox.isSelected());
     
    }//GEN-LAST:event_Subject2CheckBoxActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        SQLManager sql = new SQLManager();
        ResultSet rs = sql.GetDepartmentBName(Department_SubjectBox.getSelectedItem().toString());
        try {
            rs.next();
            String ID  =  rs.getString("Dep_ID");
            sql.AddSubject(Integer.parseInt(ID),Subject1Field.getText(), SubjectCode1_Field.getText());
            if(Subject2CheckBox.isSelected())
              sql.AddSubject(Integer.parseInt(ID),Subject2Field.getText(), SubjectCode2_Field.getText());  
            
            if(Subject3CheckBox.isSelected())
              sql.AddSubject(Integer.parseInt(ID),Subject3Field.getText(), SubjectCode3_Field.getText());  
            
            if(Subject4CheckBox.isSelected())
              sql.AddSubject(Integer.parseInt(ID),Subject4Field.getText(), SubjectCode4_Field.getText()); 
            
            if(Subject5CheckBox.isSelected())
              sql.AddSubject(Integer.parseInt(ID),Subject5Field.getText(), SubjectCode5_Field.getText()); 
            
       } catch (SQLException ex) {
           Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
       }
       
    }//GEN-LAST:event_jButton19ActionPerformed

    private void SecBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SecBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SecBActionPerformed

    private void AllocationBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AllocationBtnActionPerformed
        CloseAllPanels();
        AllocationPanel.setVisible(true);
    }//GEN-LAST:event_AllocationBtnActionPerformed

    private void AllocationDepartmentBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AllocationDepartmentBoxActionPerformed
        ShowSubject();
    }//GEN-LAST:event_AllocationDepartmentBoxActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        AddAllocation();
    }//GEN-LAST:event_jButton4ActionPerformed

    public String fromMinutesToHHmm(int minutes) {
    long hours = TimeUnit.MINUTES.toHours(Long.valueOf(minutes));
    long remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
    return String.format("%02d:%02d", hours, remainMinutes);
}
    private static int toMins(String s) {
    String[] hourMin = s.split(":");
    int hour = Integer.parseInt(hourMin[0]);
    int mins = Integer.parseInt(hourMin[1]);
    int hoursInMins = hour * 60;
    return hoursInMins + mins;
}

    public static void main(String args[]) {
        
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreen().setVisible(true);
            }
        });
    }

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddDepartmentBtn;
    private javax.swing.JPanel AddStudent;
    private javax.swing.JButton AddStudentListBtn;
    private javax.swing.JPanel AddTeachers;
    private javax.swing.JButton AllocationBtn;
    private javax.swing.JComboBox<String> AllocationDepartmentBox;
    private javax.swing.JPanel AllocationPanel;
    private javax.swing.JComboBox<String> AllocationSubjectBox;
    private javax.swing.JComboBox<String> AllocationTeacherBox;
    private javax.swing.JTextField BuildingIDField;
    private javax.swing.JTable BuildingTable;
    private javax.swing.JLabel CNICLabel;
    private javax.swing.JLabel CNICLabel1;
    private javax.swing.JLabel CNICLabel2;
    private javax.swing.JLabel CNICLabel3;
    private javax.swing.JPanel Calender;
    private javax.swing.JButton CancelDepartmentPanelBtn;
    private javax.swing.JButton CoursesBtn;
    private javax.swing.JPanel CoursesPanel;
    private javax.swing.JComboBox<String> CoursesSubjectBox;
    private javax.swing.JPanel CreateDepartment;
    private javax.swing.JPanel CreateSubject;
    private javax.swing.JPanel Dashboard;
    private javax.swing.JButton DashboardBtn;
    private javax.swing.JComboBox<String> Day;
    private javax.swing.JButton DeleteRoomBtn;
    private javax.swing.JButton DeleteTeacherBtn;
    private javax.swing.JTextField DepartmentCode_Field;
    private javax.swing.JComboBox<String> DepartmentListCourse;
    private javax.swing.JTextField DepartmentName_Field;
    private javax.swing.JComboBox<String> Department_SubjectBox;
    private javax.swing.JComboBox<String> DepartmentsList2;
    private javax.swing.JComboBox<String> DepartmentsList3;
    private javax.swing.JComboBox<String> DepartmentsList4;
    private javax.swing.JTextField FloorsField;
    private javax.swing.ButtonGroup Gender;
    private javax.swing.JLabel Label;
    private javax.swing.JComboBox<String> Month;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JLabel PhoneLabel1;
    private javax.swing.JTextField RoomCapcityField;
    private javax.swing.JTextField RoomCountField;
    private javax.swing.JLabel RoomIDLabel;
    private javax.swing.JLabel RoomIDLabel1;
    private javax.swing.JLabel RoomIDLabel2;
    private javax.swing.JLabel RoomIDLabel3;
    private javax.swing.JLabel RoomIDLabel4;
    private javax.swing.JPanel Rooms;
    private javax.swing.JButton RoomsBtn;
    private javax.swing.JButton SearchBtn;
    private javax.swing.JTextField SearchField;
    private javax.swing.JComboBox<String> SeasonsList;
    private javax.swing.JCheckBox SecA;
    private javax.swing.JCheckBox SecB;
    private javax.swing.JCheckBox SecC;
    private javax.swing.JCheckBox SecD;
    private javax.swing.JTextField SectionLimit_AdListing;
    private javax.swing.JTextField StudentCount_AddListing;
    private javax.swing.JTable StudentTable;
    private javax.swing.JPanel Students;
    private javax.swing.JButton StudentsBtn;
    private javax.swing.JTextField Subject1Field;
    private javax.swing.JCheckBox Subject2CheckBox;
    private javax.swing.JTextField Subject2Field;
    private javax.swing.JCheckBox Subject3CheckBox;
    private javax.swing.JTextField Subject3Field;
    private javax.swing.JCheckBox Subject4CheckBox;
    private javax.swing.JTextField Subject4Field;
    private javax.swing.JCheckBox Subject5CheckBox;
    private javax.swing.JTextField Subject5Field;
    private javax.swing.JTextField SubjectCode1_Field;
    private javax.swing.JTextField SubjectCode2_Field;
    private javax.swing.JTextField SubjectCode3_Field;
    private javax.swing.JTextField SubjectCode4_Field;
    private javax.swing.JTextField SubjectCode5_Field;
    private javax.swing.JTextField T_AddressFeild;
    private javax.swing.JTextField T_CNICField;
    private javax.swing.JTextField T_EmailField;
    private javax.swing.JRadioButton T_Female;
    private javax.swing.JRadioButton T_Male;
    private javax.swing.JTextField T_NameField;
    private javax.swing.JTextField T_PhoneField;
    private javax.swing.JButton T_Submit;
    private javax.swing.JButton TeachersBtn;
    private javax.swing.JPanel TeachersPanel;
    private javax.swing.JTable TeachersTable;
    private javax.swing.JComboBox<String> TimeLength;
    private javax.swing.JPanel TimeTable;
    private javax.swing.JButton TimeTableBtn;
    private javax.swing.JTable TimingTable;
    private javax.swing.JButton UpdateRoom;
    private javax.swing.JTextField UpdateRoomCountField;
    private javax.swing.JTextField UpdateRoomIDField;
    private javax.swing.JButton UpdateTeachers;
    private javax.swing.JComboBox<String> Years;
    private javax.swing.JComboBox<String> YearsList;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextPane jTextPane1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
