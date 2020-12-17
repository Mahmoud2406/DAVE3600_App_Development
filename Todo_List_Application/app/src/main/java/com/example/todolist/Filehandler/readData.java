package com.example.todolist.Filehandler;

import com.example.todolist.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class readData {


    public static void readData(File getFileDir, List<Object> list) {
        File file = new File(getFileDir, "saved");
        if (!file.exists()) {
            return;
        }
        try {
            FileInputStream filStream = new FileInputStream(file);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(filStream));
            String line = bReader.readLine();
            while (line != null) {
                list.add(line);
                line = bReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
