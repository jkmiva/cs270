//Name: Huang Jiaming
//NSID: jih211
//StuID: 11207964


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school;

/**
 *
 * @author jiajia
 */
public class UngraStudent extends Person{
    
    private String yearRegister;
    private String gpa;
    private String totalCredits;
    private String degreeProgram;
    private String yearInProgram;


//    public UngraStudent(String[] name, String[] phoneNumHome, String[] phoneNumWork, String address, String email) {
//        super(name, phoneNumHome, phoneNumWork, address, email);
//        
//    }

    public UngraStudent(String yearRegister, String gpa, String totalCredits, String degreeProgram, String yearInProgram, String[] name, String[] phoneNumHome, String[] phoneNumWork, String address, String email) {
        super(name, phoneNumHome, phoneNumWork, address, email);
        this.yearRegister = yearRegister;
        this.gpa = gpa;
        this.totalCredits = totalCredits;
        this.degreeProgram = degreeProgram;
        this.yearInProgram = yearInProgram;
    }

    

    @Override
    public String toString(){
        String[] name = this.getName();
        if (name[1].equals("")){
            return name[0]+" "+name[2]+"---[ Undergraduates ]";
        }
        return name[0]+" "+name[1]+" "+name[2]+"---[ Undergraduates ]";
    }

    public String getYearRegister() {
        return yearRegister;
    }

    public String getGpa() {
        return gpa;
    }

    public String getTotalCredits() {
        return totalCredits;
    }

    public String getDegreeProgram() {
        return degreeProgram;
    }

    public String getYearInProgram() {
        return yearInProgram;
    }

    public void setYearRegister(String yearRegister) {
        this.yearRegister = yearRegister;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public void setTotalCredits(String totalCredits) {
        this.totalCredits = totalCredits;
    }

    public void setDegreeProgram(String degreeProgram) {
        this.degreeProgram = degreeProgram;
    }

    public void setYearInProgram(String yearInProgram) {
        this.yearInProgram = yearInProgram;
    }
    
    
}
