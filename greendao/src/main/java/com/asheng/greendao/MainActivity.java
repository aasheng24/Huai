package com.asheng.greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.asheng.greendao.dao.Student;
import com.asheng.greendao.dao.StudentDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void testDao() {
        StudentDao studentDao = ((GreenDaoApplication)getApplication()).getDaoSession().getStudentDao();
        //增
        Student student = new Student();
        studentDao.insert(student);
        //删
        studentDao.delete(student);
        studentDao.deleteAll();
        //改
        studentDao.update(student);
        //查
        List<Student> students = studentDao.loadAll();
    }

}
