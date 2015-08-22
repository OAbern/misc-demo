package com.kingdee.bern.fastjson.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.kingdee.mobile.credit.entity.Course;
import com.kingdee.mobile.credit.entity.Student;
import com.kingdee.mobile.credit.entity.Teacher;

public class GeneratorData {
	public static void main(String[] args) {
		String[] cName = {"生物","化学","物理","数学","语文"};
		String[] cDes = { "生物科学","有机化学","电路","微积分", "论语"};
		String[] stuName = {"john","bern","sola","angle","Dw"};
		
		List<Course> courses = new ArrayList<Course>();
		List<Student> students = new ArrayList<Student>();
		for(int i=0; i<5; i++) {
			Student student = new Student(i, stuName[i], 12, null);
			students.add(student);
			Course course = new Course(i, cName[i], cDes[i]);
			courses.add(course);
		}
		
		Teacher teacher = new Teacher(1, "Jack", 30, "北京");
		teacher.setCourses(courses);
		teacher.setStudents(students);
		
		try {
			File file = new File(PathUtils.RES_PATH+"teacher.serli");
			if(!file.exists()) {
				file.createNewFile();
			}
		
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
			outputStream.writeObject(teacher);
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
		
		
	}
}
