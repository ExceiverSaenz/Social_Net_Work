import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.io.*;

public class Saenz_Exceiver_Social_NetWork{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //This boolean is to c
        boolean exit = true;

        //This is the name of file to have users because we use a lot times
        String fileName = "users.txt";

        //This string array save all users from users.txt in array
        String[] user_list = getUsers("users.txt", numLines("users.txt"));




        while(true){
            System.out.println("Welcome to Blabber!\n\n'SignIn <username>'\n\n'CreateAccount <username>'\n\n 'Exit'\n\n");

            String user_input = input.nextLine();
            String[] us=user_input.split(" ");

            switch(us[0]){

                case "CreateAccount": if(us.length>2){System.out.println("Please provide one username");continue;}
                                      if(us.length!=2){System.out.println("Please provide one username");continue;}


                    int index =us[1].length();
                    int numLines = numLines(fileName);




                    if(isAlphanumeric(us[1],index-1) == true){
                        user_list = getUsers(fileName,numLines);

                        if(accountExists(us[1],user_list) == false){
                            createAccount(us[1]);
                            numLines = numLines(fileName);
                            user_list = getUsers(fileName,numLines);

                            for (int i=0;i<user_list.length-1;i++) {
                                System.out.println(user_list[i]);
                            }
                        }else if (accountExists(us[1],user_list) != false) {
                            System.out.println("Error: An account with username "+ us[1] +" already exists");
                        }

                    }else if(isAlphanumeric(us[1],index-1) != true){
                        System.out.println( "Error: Invalid username.");
                    }


                    break;

                case "SignIn": if(us.length>2){System.out.println("Please provide one username");continue;}
                    if(us.length<2){System.out.println("Please provide an username");continue;}


                    if(accountExists(us[1],user_list) == true){
                        boolean breakloop = true;
                        while(breakloop){
                            System.out.println("Select one option:\n\nFollowAccount <username>\n\nPostBlab\n\nViewTimeline\n\nViewTimelineReverse\n\nSignOut\n\n ");
                            String action = input.nextLine();
                            String[] action_arr = action.split(" ");

                            switch(action_arr[0]){
                                case "FollowAccount":
                                    if(action_arr.length>2){System.out.println("Please provide one username");continue;}
                                    if(action_arr.length<2){System.out.println("Please provide an username");continue;}

                                    followAccount(us[1],action_arr[1],user_list);

                                    break;

                                case "PostBlab":
                                    if(action_arr.length!=1){System.out.println("Only write PostBlab please");continue;}

                                    postBlab(us[1]);




                                    break;
                                case "ViewTimeline":
                                    if(action_arr.length!=1){System.out.println("Only write ViewTimeline please");continue;}
                                    if (numLines("microArray/"+us[1]+".txt")==0) {System.out.println("Sorry, you need follow other users to see comments");continue;}
                                    String[] blabs_user_time = getBlabs();//obtain blabs from blabs.txt file

                                    int n = 0;
                                    viewTimeline(us[1],blabs_user_time,n);//print all blabs if about followed users

                                    break;
                                case "ViewTimelineReverse":
                                    if(action_arr.length!=1){System.out.println("Only write ViewTimelineReverse please");continue;}
                                    if (numLines("microArray/"+us[1]+".txt")==0) {System.out.println("Sorry, you need follow other users to see comments");continue;}



                                    String[] blabs_user_time_reverse = getBlabs();//obtain blabs from blabs.txt file

                                    ViewTimelineReverse(us[1],blabs_user_time_reverse,blabs_user_time_reverse.length-1); //print all blabs if about followed users

                                    break;
                                case "SignOut": breakloop = false; break;

                            }
                        }

                    }else if (accountExists(us[1],user_list) != true) {
                        System.out.println("Error: user does not exist.");
                    }

                    break;

                case "Exit":System.exit(0);break;
            }
        }

    }






    public static void signedIn(String username,String[] users){
        //read users array and if username input is equals one user of array you login with this user
        for (int i=0;i<users.length;i++) {
            if(username.equals(users[i])){
                System.out.println("Welcome " + username);
            }else{System.out.println("Nonexistent User");};
        }
    }

    public static String[] getUsers(String filename,int size){
        String[] user_list = new String[size];
        try{
            //obtain users from users.txt file and save in array
            File file;
            if (filename.equals("users.txt")){
                file = new File(filename);
            }else {
                file = new File(filename);
            }

            Scanner reader = new Scanner(file);

            int i = 0;
            while(reader.hasNextLine()){
                user_list[i] = reader.nextLine();
                i++;
            }
        }catch(Exception e){
            e.getStackTrace();
        }
        return user_list;
    }
    public static int numLines(String fileName){

        int numLines = 0;
        try{
            //count numLines from file user specified
            File file = new File(fileName);
            Scanner reader = new Scanner(file);

            while(reader.hasNextLine()){
                if (fileName.equals("blabs.txt")) {
                    String lines = reader.nextLine();
                    numLines++;
                }else{

                    String lines = reader.next();
                    numLines++;
                }
            }
        }catch(Exception e){}

        return numLines;
    }
    public static boolean isAlphanumeric(String username, int index) {
        //return false boolean if username not is alphanimeric
        char result = username.charAt(index);
        if(!Character.isLetterOrDigit(result)){
            return false;
        }
        index--;
        if(index < 0) return true;
        return isAlphanumeric(username,index);



    }
    public static void createAccount(String username){
        try{

            File theDir = new File("microArray");
            if (!theDir.exists()){
                theDir.mkdirs();
            }


            //this create a file, and read file users, if user not exist you can save the user in users.txt, else no
            FileWriter file = new FileWriter("microArray/" + username + ".txt");
        }catch(Exception e){}
        try {
            FileWriter output = new FileWriter("users.txt",true);

            output.write(username+"\n");

            output.close();
        }

        catch (Exception e) {
            e.getStackTrace();
        }
        System.out.println("The account has been created");
    }
    public static boolean accountExists(String username,String[] users){
        //if account you write not exist return false, else return true
        for (int i=0;i<users.length;i++) {
            if(username.equalsIgnoreCase(users[i])){
                return true;
            }
        }
        return false;

    }

    public static void followAccount(String username, String usernameToFollow,String[] users){
        //if all conditions are fulfilled, then save username to follow in username file
        if (!accountExists(usernameToFollow,users)) {
            System.out.println("Error: At least one of the usernames is invalid or does not exist.");
        }else if (username.equalsIgnoreCase(usernameToFollow)) {
            System.out.println("Error: a user can’t follow themself.");
        }else if (checkFollowers(username,usernameToFollow) == true) {
            System.out.println("Error: user "+username+" already follows user "+usernameToFollow+".");
        }else {

            try {
                FileWriter output = new FileWriter("microArray/"+username + ".txt",true);

                output.write(usernameToFollow+"\n");

                output.close();
                System.out.println("Success!");
            }

            catch (Exception e) { e.getStackTrace(); System.out.println("An inconvenience happened");}
        }
    }
    public static boolean checkFollowers(String username,String usernameToFollow){
        String item = "";
        try{
            //read file and if archive username.txt has a username to follow return false if username not exist else return true
            File file = new File("microArray/" + username + ".txt");
            Scanner reader = new Scanner(file);

            while(reader.hasNextLine()){
                item += reader.next() + " ";
            }
        }catch(Exception e){}
        String[]user_list = item.split(" ");

        for (int i = 0;i<user_list.length ;i++ ) {
            if (user_list[i].equalsIgnoreCase(usernameToFollow)) { return true; }

        }return false;
    }

    public static void postBlab(String username){
        System.out.print("Hi, please write your comment in the next line\n>");

        Scanner input = new Scanner(System.in);
        String comment =input.nextLine();

        try{
            //create file blabs.txt if exist only post blab and save in file
            FileWriter output = new FileWriter("blabs.txt",true);

            output.write(username + "-" + comment + "\n");

            output.close();
            System.out.println("Comment post Success!");

        }catch (Exception e) {System.out.println(e);}

    }

    public static String[] getBlabs(){
        String fileName = "blabs.txt";
        String []blabs_list = new String[numLines(fileName)];
        //read file and save al blabs in string array
        try{
            File file  = new File(fileName);
            Scanner  reader = new Scanner(file);

            for (int i = 0 ;i<numLines(fileName) ;i++ ) {
                blabs_list[i] = reader.nextLine();

            }

        }catch(Exception e){}

        return blabs_list;



    }

    public static void viewTimeline(String username,String[]blabs,int idx){
        //obtain users from username file
        String[] users = getUsers("microArray/"+username+".txt",numLines("microArray/"+username+".txt"));
        //base case
        if(idx != blabs.length){
            //split username and comment
            String[] user = blabs[idx].split("-");

            for(int i = 0; i<users.length;i++){
                //if the user from users array is equal user array print the comment with idx
                if (users[i].equalsIgnoreCase(user[0])) {
                    System.out.println(blabs[idx]);
                }

            }
            //recursive call
            viewTimeline(username,blabs,idx+1);

        }

    }

    public static void ViewTimelineReverse(String username, String[] blabs, int idx){

        //obtain users from username file
        String[] users = getUsers("microArray/"+username+".txt",numLines("microArray/"+username+".txt"));
        int m = users.length-1;
        //base case
        if(idx != -1){
            //split username and comment
            String[] user = blabs[idx].split("-");
            int n = 0;

            for(int i = blabs.length-1; i>-1;i--){
                if (m==-1){ m=users.length-1;}
                //if the user from users array is equal user array print the comment with idx
                if (users[m].equalsIgnoreCase(user[0])) {
                    System.out.println(blabs[idx]);
                    m--;
                    break;
                }else {m--;}



            }
            //recursive call
            ViewTimelineReverse(username,blabs,idx-1);

        }
    }


}