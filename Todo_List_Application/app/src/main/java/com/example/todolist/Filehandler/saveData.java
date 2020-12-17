package com.example.todolist.Filehandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class saveData {


    public static void saveData(File getFileDir, List<Object> list) {


        try {

            File file = new File(getFileDir, "saved");
            FileOutputStream out = new FileOutputStream(file);
            BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(out));
            for (int i = 0; i < list.size(); i++) {
                bWriter.write((String) list.get(i));
                bWriter.newLine();
            }
            bWriter.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
