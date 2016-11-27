//Name: Huang Jiaming
//NSID: jih211
//StuID: 11207964


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school;

import java.io.Serializable;

/**
 *
 * @author jiajia
 */
public class GraStudent extends UngraStudent{
    private String thesisSupervisor;
    private Thesis thesis;
    private String scholarship;
    private String degreeType;

    public GraStudent(String thesisSupervisor, Thesis thesis, String scholarship, String degreeType, 
                      String yearRegister, String gpa, String totalCredits, String degreeProgram, String yearInProgram, 
                      String[] name, String[] phoneNumHome, String[] phoneNumWork, String address, String email) {
        super(yearRegister, gpa, totalCredits, degreeProgram, yearInProgram, name, phoneNumHome, phoneNumWork, address, email);
        this.thesisSupervisor = thesisSupervisor;
        this.thesis = thesis;
        this.scholarship = scholarship;
        this.degreeType = degreeType;
    }

    @Override
    public String toString(){
        String[] name = this.getName();
        if (name[1].equals("")){
            return name[0]+" "+name[2]+"---[ Graduates ]";
        }
        return name[0]+" "+name[1]+" "+name[2]+"---[ Graduates ]";
    }
    
    public String getThesisSupervisor() {
        return thesisSupervisor;
    }

    public Thesis getThesis() {
        return thesis;
    }

    public String getScholarship() {
        return scholarship;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setThesisSupervisor(String thesisSupervisor) {
        this.thesisSupervisor = thesisSupervisor;
    }

    public void setThesis(Thesis thesis) {
        this.thesis = thesis;
    }

    public void setScholarship(String scholarship) {
        this.scholarship = scholarship;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    
       
}

class Thesis implements Serializable{
    private String title;
    private String area;

    public Thesis(String title, String area) {
        this.title = title;
        this.area = area;
    }

    public String getTitle() {
        return title;
    }

    public String getArea() {
        return area;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArea(String area) {
        this.area = area;
    }
    
}
