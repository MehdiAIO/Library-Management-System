package storage;

import exceptions.InvalidUserException;
import models.Member;

import java.io.*;
import java.util.ArrayList;

public class MembersManagement {
    public static String path = "data/members.ser";
    public static void saveMembers(ArrayList<Member> members){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))){
            out.writeObject(members);
            System.out.println("the members have been serialized");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Member> loadMembers(){
        ArrayList<Member> members = new ArrayList<>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))){
            members = (ArrayList<Member>) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return members;
    }

    public static void addMember(Member member) throws InvalidUserException {
        ArrayList<Member> members = loadMembers();
        for(Member m : members){
           if(m.getId() == member.getId()){
               throw new InvalidUserException("this user already exists");
           }
        }
        members.add(member);
        saveMembers(members);
    }

    public static void updateMember(Member updatedMember) {
        ArrayList<Member> members = loadMembers();
        boolean found = false;

        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getId() == updatedMember.getId()) {
                members.set(i, updatedMember);
                found = true;
                break;
            }
        }

        if (!found) {
            members.add(updatedMember);
        }

        saveMembers(members);
    }
}
