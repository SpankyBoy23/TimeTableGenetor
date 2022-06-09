package timetable;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class SQLManager {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    
 SQLManager(){
try{
     Class.forName("com.mysql.cj.jdbc.Driver");
     con=DriverManager.getConnection("jdbc:mysql://localhost:3306/timetabledatabase", "root","");
     st=con.createStatement();
     
}catch(ClassNotFoundException | SQLException e){
   System.out.println(e);
} 
 }
 
 
 public void RoomsRegistory(String department, String buildingID, String capcity){
 
     String sql=  "INSERT INTO `rooms manager`(`Department`, `Building ID`, `Capacity`) VALUES ('"+department+"','"+buildingID+"','"+capcity+"')";
             //"insert into rooms Mana (user,pass, email)values('"+department+"','"+buildingID+"','"+capcity+"')";
     try{
     st.executeUpdate(sql);
     }catch(SQLException e){
     System.out.println(e);
     }
             
 }
 public ResultSet Rooms(){

        try {
            String sql="SELECT * FROM `rooms manager`";
            rs=st.executeQuery(sql);
        } catch (SQLException ex) {
           // Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return rs;
}
 public ResultSet SelectRoom(int Id){
    
        try {
            String sql= "SELECT `ID`, `Capacity`, `Building ID` FROM `rooms manager` WHERE `ID` = "+Id+"";
            rs=st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    
      return rs;
}
 public void deleteRoom(int StudentID){
    
        try {
            String sql="delete from `rooms manager` where ID='"+StudentID+"'";
            //st.executeQuery(sql);
            st.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null, "Data is Sucessfully Removed "+StudentID, "Alert",1);
        } catch (SQLException ex) {
            System.out.println("Error While Deleting room "+ ex);
        }
}
 public void UpdateRoom(int ID,int capacity,String roomId){

        try {
            String sql= "UPDATE `rooms manager` SET `Capacity` ="+capacity+",`Building ID` =  '"+roomId+"' WHERE `ID` = "+ID+"";
            
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
        }

}

 
// Tecahers Part here:
public void TeacherRegistory(String name, long cnic, long phoneNo,String email,String Address,String dOB,String Gender,String Course,String Subject1,String Subject2,String Subject3,String Subject4){
 
     String sql=  "INSERT INTO `teacher manager`(`Name`, `CNIC`, `Phone No`, `Email`, `Address`, `DateOfBrith`, `Gender`, `Course`, `Subject1`, `Subject2`, `Subject3`, `Subject4`) VALUES ('"+name+"',"+cnic+","+phoneNo+",'"+email+"','"+Address+"','"+dOB+"','"+Gender+"','"+Course+"','"+Subject1+"','"+Subject2+"','"+Subject3+"','"+Subject4+"')";
             //"insert into rooms Mana (user,pass, email)values('"+department+"','"+buildingID+"','"+capcity+"')";
     try{
     st.executeUpdate(sql);
     }catch(SQLException e){
     System.out.println(e);
     }
             
 } 
public ResultSet Teachers(){

        try {
            String sql="SELECT `ID`,`Name`, `CNIC`, `Phone No`, `Email`, `DateOfBrith`, `Gender`, `Course` FROM `teacher manager`";
            rs=st.executeQuery(sql);
        } catch (SQLException ex) {
           // Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return rs;
}
public ResultSet SeacrhSQLTeachers(int ID){

        try {
            String sql="SELECT * FROM `teacher manager` WHERE `ID` = "+ID+"";
            rs=st.executeQuery(sql);
        } catch (SQLException ex) {
           // Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return rs;
}
public void deleteTeacher(int TeacherID){
    
        try {
            String sql="delete from `teacher manager` where ID='"+TeacherID+"'";
            //st.executeQuery(sql);
            st.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null, "Data is Sucessfully Removed "+StudentID, "Alert",1);
        } catch (SQLException ex) {
            System.out.println("Error While Deleting room "+ ex);
        }
}
public void UpdateTeacher(String name, long cnic, long phoneNo,String email,String Address,String dOB,String Gender,String Course,String Subject1,String Subject2,String Subject3,String Subject4,int ID){

        try {
            String sql= "UPDATE `rooms manager` SET `Name`='"+name+"',`CNIC`= "+cnic+",`Phone No`= "+phoneNo+",`Email`='"+email+"',`Address`='"+Address+"',`DateOfBrith`='"+dOB+"',`Gender`='"+Gender+"',`Course`='"+Course+"',`Subject1`='"+Subject1+"',`Subject2`='"+Subject2+"',`Subject3`='"+Subject3+"',`Subject4`='"+Subject4+"'  WHERE `ID` = "+ID+")";
            
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
        }

} 
public ResultSet matchLoginDetails(String user, String pass){
 
   String sql="select * from user where user='"+user+"' and pass='"+pass+"'";
   try{
   rs=st.executeQuery(sql);
   }catch(Exception e){
   System.out.println(e);
   }
   return rs;
 }
 
 
public void StudentListing(String depCode,int year,String season,int ID,String section){
 
     String sql=  "INSERT INTO `student manager`(`Dep Code`, `Year`, `Season`, `ID`, `Section`) VALUES ('"+depCode+"',"+year+",'"+season+"',"+ID+",'"+section+"')";
             //"insert into rooms Mana (user,pass, email)values('"+department+"','"+buildingID+"','"+capcity+"')";
     try{
     st.executeUpdate(sql);
     }catch(SQLException e){
     System.out.println(e);
     }
             
 } 
public ResultSet ShowStudent(){

        try {
            String sql="SELECT * FROM `student manager`";
            rs=st.executeQuery(sql);
        } catch (SQLException ex) {
           // Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return rs;
}


 public ResultSet DisplayUsers(){
 
   String sql="select * from user";
   try{
   rs=st.executeQuery(sql);
   }catch(Exception e){
   System.out.println(e);
   }
   return rs;
    
     
 }
public ResultSet searchUser(String search){
 
   String sql="select * from user where user='"+search+"' or pass='"+search+"'";
   try{
   rs=st.executeQuery(sql);
   }catch(SQLException e){
   System.out.println(e);
   }
   return rs;  
 }
 

public ResultSet displayStudentsData(){

        try {
            String sql="select * from student";
            rs=st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
       return rs;
    

}


public void removeRecordByID(int ID){

        try {
            String sql="delete from student where StudentID='"+ID+"'";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Record successfully Removed"+ ID,"Alert",1);
        } catch (SQLException ex) {
            Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
     
     
}


public ResultSet getRecordByID(int ID){

        try {
            String sql="select * from student where StudentID='"+ID+"'";
            rs=st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    
    
}


public void updateStudent(String studentname,String studentdepartment,String studentemail,int ID){

        try {
            String sql="update student set StudentName='"+studentname+"',StudentDepartment='"+studentdepartment+"',StudentEmail='"+studentemail+"' where StudentID='"+ID+"'";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Record is updated" +ID,"alert",1);
        } catch (SQLException ex) {
            Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    

}


    public ResultSet getStudentInformation(){
      
        try {
            String sql="select * from student";
            rs=st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
                return rs;
    }
    
    

    public void RemoveStudentByID(int ID){
    
        try {
            String sql="delete from student where StudentID='"+ID+"'";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Record Removed ID :"+ID,"alert",1);
        } catch (SQLException ex) {
            Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public ResultSet getInfoByID(int ID){
    
        String sql="select * from student where StudentID='"+ID+"'";
        try {
            rs=st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
        
    }
    
    
    public void UpdateStudentByID(String name,String department, String email, int ID){
     
        try {
            String sql="update student set StudentName='"+name+"',StudentDepartment='"+department+"',StudentEmail='"+email+"' where StudentID='"+ID+"'";
            st.executeUpdate(sql);
           
        } catch (SQLException ex) {
            Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    

}