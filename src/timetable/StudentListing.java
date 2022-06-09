package timetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentListing {
      List<String[]> list=new ArrayList<String[]>();
    public void CreateStudentListing(String depCode,String season,int year,int count,int sectionLimit){
      
        
        String seasonCode;
        String section = "A";
        
        SQLManager sqlManager = new SQLManager();
        seasonCode = ("Fall".equals(season)) ? "F" : "S";
        for(int i = 1;i <= count;i++){
            if(i < sectionLimit)
                section = "A";
            else if(i > sectionLimit && i < sectionLimit*2)
                section = "B";
            else if(i > sectionLimit*2 && i < sectionLimit*3)
                section = "C";
             else if(i > sectionLimit*3)
                section = "D";
            
            sqlManager.StudentListing(depCode, year, seasonCode,Integer.parseInt(String.format("%03d", i)), section);
           // String.valueOf(year).substring(2)+seasonCode,String.format("%03d", i),depCode,section
        //    String[] StudentInfo = {"",depCode+"-"+String.valueOf(year).substring(2)+seasonCode+"-"+String.format("%03d", i),"",section};
          //  list.add(StudentInfo);
             
        }
       // for(String[] ls:list){
        //    for(String l:ls)
       //      System.out.println(l);
       // }
      
        
    }
}
