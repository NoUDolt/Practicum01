import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductWriter {
    public static void main(String[] args) {

        //defines the array used to store each record
        ArrayList<String> product = new ArrayList<>();
        //helps with getting inputted data from user
        Scanner input = new Scanner(System.in);

        //puts the created file in the directory this program is in
        File workingDirectory = new File(System.getProperty("user.dir"));
        //creates the output file with all of our data
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\ProductTestData.txt");

        //variable definitions
        boolean done = false;

        String record = "";
        String id = "";
        String product_name = "";
        String product_description = "";
        double product_cost = 0;

        //a while loop that collects the user's inputs and puts it in a record, loops until user wants to stop
        do {
            id = SafeInput.getNonZeroLenString(input, "Enter the product ID [6 digits]: ");
            product_name = SafeInput.getNonZeroLenString(input, "Enter the product name: ");
            product_description = SafeInput.getNonZeroLenString(input, "Enter a short description for the product: ");
            product_cost = SafeInput.getRangedDouble(input, "Enter the cost of the product: ", 0, 9999);

            record = id + ", " + product_name + ", " + product_description + ", " + product_cost;
            product.add(record);

            done = SafeInput.getYNConfirm(input, "Would you like to continue? (Y/N): ");

        }while(done);

        //a for loop that outputs each created record into the output file
        for( String p : product)
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

            for(String rec : product)
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
