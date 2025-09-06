package storage;

import models.Librarian;

import java.io.*;
import java.util.ArrayList;

public class LibrariansManagement {
    public static String path = "data/librarians.ser";
    public static void saveLibrarians(ArrayList<Librarian> librarians) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))){
            out.writeObject(librarians);
            System.out.println("the librarians have been serialized");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Librarian> loadLibrarians(){
        ArrayList<Librarian> loadedLibrarians = new ArrayList<>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))){
            loadedLibrarians = (ArrayList<Librarian>) in.readObject();
        }catch(IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return loadedLibrarians;
    }
}
