import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE;

// use the necessary imports

public class PersonReader {
    public static void main(String[] args) {

        //Opens a window that lets the user select a file
        System.out.println("Please use the menu to select a desired file containing a list of people: \n");
        JFileChooser chooser = new JFileChooser();
        File selectedFile;

        // defines variables
        String rec = "";
        String id = "";
        String first_name = "";
        String last_name = "";
        String title = "";
        String yearOfBirth = "";


        //block of code reads the file user selected
        try {
            // uses a fixed known path:
            //  Path file = Paths.get("c:\\My Documents\\data.txt");

            // use the toolkit to get the current working directory of the IDE
            // Not sure if the toolkit is thread safe...
            File workingDirectory = new File(System.getProperty("user.dir"));

            // Typically, we want the user to pick the file so we use a file chooser
            // kind of ugly code to make the chooser work with NIO.
            // Because the chooser is part of Swing it should be thread safe.
            chooser.setCurrentDirectory(workingDirectory);
            // Using the chooser adds some complexity to the code.
            // we have to code the complete program within the conditional return of
            // the filechooser because the user can close it without picking a file

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                // Typical java pattern of inherited classes
                // we wrap a BufferedWriter around a lower level BufferedOutputStream
                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                System.out.println("ID#      Firstname    Lastname    Title     YOB");
                System.out.println("==================================================");

                // Finally we can read the file LOL!
                int line = 0;
                while (reader.ready()) {
                    rec = reader.readLine();
                    line++;
                    // echo to screen
                    String[] fields = rec.split(",");
                    if (fields.length >= 5) {
                        id = fields[0].trim();
                        first_name = fields[1].trim();
                        last_name = fields[2].trim();
                        title = fields[3].trim();
                        yearOfBirth = fields[4].trim();

                        // Format the output line
                        String formattedRec = String.format("%-8s %-12s %-12s %-8s %-4s", id, first_name, last_name, title, yearOfBirth);
                        System.out.println(formattedRec);
                    }
                }
                reader.close(); // must close the file to seal it and flush buffer
                System.out.println("\n\nData file read!");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!!!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
