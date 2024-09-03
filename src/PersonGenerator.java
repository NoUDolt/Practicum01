import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

// use the necessary imports

public class PersonGenerator {
    public static void main(String[] args) {

        //defines the array used to store each record
        ArrayList<String> people = new ArrayList<>();
        //helps with getting inputted data from user
        Scanner input = new Scanner(System.in);

        //puts the created file in the directory this program is in
        File workingDirectory = new File(System.getProperty("user.dir"));
        //creates the output file with all of our data
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\PersonTestData.txt");

        //variable definitions
        boolean done = false;

        String record = "";
        String id = "";
        String first_name = "";
        String last_name = "";
        String title = "";
        int yearOfBirth = 0;

        //a while loop that collects the user's inputs and puts it in a record, loops until user wants to stop
        do {
            id = SafeInput.getNonZeroLenString(input, "Enter the ID [6 digits]: ");
            first_name = SafeInput.getNonZeroLenString(input, "Enter a first name: ");
            last_name = SafeInput.getNonZeroLenString(input, "Enter a last name: ");
            title = SafeInput.getNonZeroLenString(input, "Enter a title: ");
            yearOfBirth = SafeInput.getRangedInt(input, "Enter the year of birth: ", 1000, 9999);

            record = id + ", " + first_name + ", " + last_name + ", " + title + ", " + yearOfBirth;
            people.add(record);

            done = SafeInput.getYNConfirm(input, "Would you like to continue? (Y/N): ");

        }while(done);

        //a for loop that outputs each created record into the output file
        for( String p : people)
            System.out.println(p);

        try
        {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Finally can write the file LOL!

            for(String rec : people)
            {
                writer.write(rec, 0, rec.length());  // stupid syntax for write rec
                // 0 is where to start (1st char) the write
                // rec. length() is how many chars to write (all)
                writer.newLine();  // adds the new line

            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
        }

        //catches any errors
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}