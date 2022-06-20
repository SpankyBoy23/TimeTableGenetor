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
     System.out.println("Connection Successfull.");
     
}catch(ClassNotFoundException | SQLException e){
   System.out.println(e);
} 
 }
 
 public void AddDepartment(String Name,String Code){
     String sql=  "INSERT INTO `departments`(`Name`, `Code`) VALUES ('"+Name+"','"+Code+"')";
     
             //"insert into rooms Mana (user,pass, email)values('"+department+"','"+buildingID+"','"+capcity+"')";
     try{
     st.executeUpdate(sql);
     JOptionPane.showMessageDialog(null,"Department Added Successfully");
     }catch(SQLException e){
     System.out.println(e);
     }
 }
 public ResultSet GetDepartments(){
     
      try {
            String sql="SELECT * FROM `departments`";
            rs=st.executeQuery(sql);
        } catch (SQLException ex) {
           // Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
     return rs;
 }
 public ResultSet GetDepartmentBName(String Name){
     
      try {
            String sql="SELECT `Dep_ID` FROM `departments` WHERE `Name` = '"+Name+"'";
            rs=st.executeQuery(sql);
        } catch (SQLException ex) {
           // Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
     return rs;
 }
 public void AddSubject(int Id,String Name,String Code){
     String sql=  "INSERT INTO `subjects`(Dep_ID,`Name`, `Code`) VALUES ("+Id+",'"+Name+"','"+Code+"')";
     
             //"insert into rooms Mana (user,pass, email)values('"+department+"','"+buildingID+"','"+capcity+"')";
     try{
     st.executeUpdate(sql);
     JOptionPane.showMessageDialog(null,"Subject Added Successfully");
     }catch(SQLException e){
     System.out.println(e);
     }
 }
  
  
   public void AddAllocation(String Name,String Department,String Subject,boolean secA,boolean secB,boolean secC,boolean secD) {
       
       String checkSql = "SELECT * FROM `allocation manager`";
     
     String sql=  "INSERT INTO `allocation manager`(`T_Name`, `Department`,`Subject`, `SecA`, `SecB`, `SecC`, `SecD`) VALUES ('"+Name+"','"+Department+"','"+Subject+"',"+secA+","+secB+","+secC+","+secD+")";
     
             //"insert into rooms Mana (user,pass, email)values('"+department+"','"+buildingID+"','"+capcity+"')";
     try{
         rs = st.executeQuery(checkSql);
       while(rs.next()){
           
           System.out.println(rs.getString("T_Name"));
           if(Name.equals(rs.getString("T_Name"))){
               if(Department.equals(rs.getString("Department"))){
                   sql = "UPDATE `allocation manager` SET `Subject` ='"+Subject+"', `SecA`="+secA+", `SecB`="+secB+", `SecC`="+secC+", `SecD`="+secD+" WHERE `T_Name` = '"+Name+"' AND `Department` = '"+Department+"'";
                    if(Subject.equals(rs.getString("Subject"))){
                        sql = "UPDATE `allocation manager` SET  `SecA`="+secA+", `SecB`="+secB+", `SecC`="+secC+", `SecD`="+secD+" WHERE `T_Name` = '"+Name+"' AND `Department` = '"+Department+"' AND `Subject` ='"+Subject+"'";
                    }
               }
                  
               sql = "UPDATE `allocation manager` SET `Department`= '"+Department+"',`Subject` ='"+Subject+"', `SecA`="+secA+", `SecB`="+secB+", `SecC`="+secC+", `SecD`="+secD+" WHERE `T_Name` = '"+Name+"'";
               break;
           }
       }
         st.executeUpdate(sql);
        JOptionPane.showMessageDialog(null,"Allocated Successfully","Allocated" , 1);
     }catch(SQLException e){
         System.out.println(e);
     }
 }
  
  
 public ResultSet GetSubjectByName(int id){
     
      try {
            String sql="SELECT `Name` FROM `subjects` WHERE `Dep_ID` = "+id+"";
            rs=st.executeQuery(sql);
        } catch (SQLException ex) {
           // Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
     return rs;
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
public void TeacherRegistory(String name, long cnic, long phoneNo,String email,String Address,String dOB,String Gender){
 
     String sql=  "INSERT INTO `teacher manager`(`Name`, `CNIC`, `Phone No`, `Email`, `Address`, `DateOfBrith`, `Gender`) VALUES ('"+name+"',"+cnic+","+phoneNo+",'"+email+"','"+Address+"','"+dOB+"','"+Gender+"')";
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
public void UpdateTeacher(String name, long cnic, long phoneNo,String email,String Address,String dOB,String Gender,int ID){

        try {
            String sql= "UPDATE `rooms manager` SET `Name`='"+name+"',`CNIC`= "+cnic+",`Phone No`= "+phoneNo+",`Email`='"+email+"',`Address`='"+Address+"',`DateOfBrith`='"+dOB+"',`Gender`='"+Gender+"' WHERE `ID` = "+ID+")";
            
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SQLManager.class.getName()).log(Level.SEVERE, null, ex);
        }

} 
public ResultSet MatchLoginDetails(String user, String pass){
 
   String sql="SELECT `UserName`, `Password` FROM `loginmanager` WHERE `UserName` = '"+user+"' AND `Password` = '"+pass+"'";
   try{
   rs=st.executeQuery(sql);
   }catch(SQLException e){
   System.out.println("Login Error: "+ e);
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