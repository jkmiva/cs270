//Name: Huang Jiaming
//NSID: jih211
//StuID: 11207964


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.DefaultListModel;

/**
 *
 * @author jiajia
 */
public class Controler {

    public static final String FILE_NAME = "School.txt";
    DefaultListModel<Person> listModel;
    Lock lock = new ReentrantLock();

    public Controler(DefaultListModel<Person> newlistModel) {
        listModel = newlistModel;
    }

    void create(String firstName, String midName, String lastName, String phoneHome1, String phoneHome2,
        String phoneWork1, String phoneWork2, String address, String email,
        String yearRegister, String degreeProgram, String yearInProgram, String gpa, String totalCredits,
        String supervisor, String title, String area, String scholarship, String degreeType) {
        lock.lock();
        try {
            String[] name = {firstName, midName, lastName};
            String[] phoneHome = {phoneHome1, phoneHome2};
            String[] phoneWork = {phoneWork1, phoneWork2};

            if (yearRegister.equals("")) {
                Person newPerson = new Person(name, phoneHome, phoneWork, address, email);
                listModel.addElement(newPerson);
            }
            if (!yearRegister.equals("") && supervisor.equals("")) {
                UngraStudent newUngra = new UngraStudent(yearRegister, gpa, totalCredits, degreeProgram, yearInProgram, name, phoneHome, phoneWork, address, email);
                listModel.addElement(newUngra);
            }
            if (!supervisor.equals("")) {
                Thesis newThesis = new Thesis(title, area);
                GraStudent newGra = new GraStudent(supervisor, newThesis, scholarship, degreeType,
                    yearRegister, gpa, totalCredits, degreeProgram, yearInProgram, name, phoneHome, phoneWork, address, email);
                listModel.addElement(newGra);
            }
        } finally {
            lock.unlock();
        }
    }

    void update(String firstName, String midName, String lastName, String phoneHome1, String phoneHome2,
        String phoneWork1, String phoneWork2, String address, String email,
        String yearRegister, String degreeProgram, String yearInProgram, String gpa, String totalCredits,
        String supervisor, String title, String area, String scholarship, String degreeType, int index) {
        lock.lock();

        Person temp = listModel.get(index);
        String[] name = {firstName, midName, lastName};
        String[] phoneHome = {phoneHome1, phoneHome2};
        String[] phoneWork = {phoneWork1, phoneWork2};
        Thesis newThesis = new Thesis(title, area);

        if (temp instanceof GraStudent) {
            temp.setName(name);
            temp.setPhoneNumHome(phoneHome);
            temp.setPhoneNumWork(phoneWork);
            temp.setAddress(address);
            temp.setEmail(email);
            ((GraStudent) temp).setYearRegister(yearRegister);
            ((GraStudent) temp).setDegreeProgram(degreeProgram);
            ((GraStudent) temp).setYearInProgram(yearInProgram);
            ((GraStudent) temp).setGpa(gpa);
            ((GraStudent) temp).setTotalCredits(totalCredits);
            ((GraStudent) temp).setThesisSupervisor(supervisor);
            ((GraStudent) temp).setThesis(newThesis);
            ((GraStudent) temp).setScholarship(scholarship);
            ((GraStudent) temp).setDegreeType(degreeType);
            lock.unlock();
            return;
        }
        if (temp instanceof UngraStudent) {
            temp.setName(name);
            temp.setPhoneNumHome(phoneHome);
            temp.setPhoneNumWork(phoneWork);
            temp.setAddress(address);
            temp.setEmail(email);
            ((UngraStudent) temp).setYearRegister(yearRegister);
            ((UngraStudent) temp).setDegreeProgram(degreeProgram);
            ((UngraStudent) temp).setYearInProgram(yearInProgram);
            ((UngraStudent) temp).setGpa(gpa);
            ((UngraStudent) temp).setTotalCredits(totalCredits);
            lock.unlock();
            return;
        }
        temp.setName(name);
        temp.setPhoneNumHome(phoneHome);
        temp.setPhoneNumWork(phoneWork);
        temp.setAddress(address);
        temp.setEmail(email);

        lock.unlock();

    }

    void delete(int index) {
        lock.lock();
        try {
            listModel.removeElementAt(index);
        } finally {
            lock.unlock();
        }
    }

    void save() throws IOException {
        lock.lock();
        try {
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
                os.writeObject(listModel);
            }
        }
        finally {
            lock.unlock();
        }
    }

    void load() throws IOException, ClassNotFoundException {
        lock.lock();
        try {
            DefaultListModel<Person> inObject;
            try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                inObject = (DefaultListModel<Person>) is.readObject();
            }
            while (!inObject.isEmpty()) {
                listModel.addElement(inObject.remove(0));
            }
        }
        finally {
            lock.unlock();
        }    
    }

}
