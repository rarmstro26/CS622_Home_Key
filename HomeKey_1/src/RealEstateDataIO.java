import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class RealEstateDataIO {

    public static void saveRealEstateData(String filePath, List<RealEstateData> data) {
        /**
         * Preconditions: filePath is a valid file path.
         * Postconditions: Prints success message if data successfully saved, otherwise prints error message if
         * an exception occurs.
         */
        try (ObjectOutputStream outfile = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outfile.writeObject(data);
            System.out.println("\nReal estate data saved successfully.");
        } catch (IOException ex) {
            System.out.println("Error saving real estate data: " + ex.getMessage());
        }
    }

    public static List<RealEstateData> loadRealEstateData(String filePath) {
        /**
         * Preconditions: filePath is a valid file path.
         * Postconditions:
         *  If the real estate data is successfully loaded, the method prints "Real estate data loaded successfully." followed by a new line character.
         *  If the specified file does not exist, the method prints "File not found: " followed by the `filePath` value. In this case, an empty list is created and saved to the file.
         *  If an error occurs while reading the real estate data, the method prints "Error reading real estate data: " followed by the error message.
         *  If an error occurs while loading the real estate data, the method prints "Error loading real estate data: " followed by the error message.
         *  If no errors occur and the real estate data is successfully loaded, the method returns a non-null List<RealEstateData> object.
         *  If any error occurs during the loading process, the method returns null.
         */

        try (ObjectInputStream infile = new ObjectInputStream(new FileInputStream(filePath))) {
            List<RealEstateData> data = (List<RealEstateData>) infile.readObject();
            System.out.println("Real estate data loaded successfully.\n");
            return data;
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + filePath);
            // If file doesn't already exist - create an empty list and save to file
            List<RealEstateData> emptyData = new ArrayList<>();
            saveRealEstateData(filePath, emptyData);
        } catch (IOException ex) {
            System.out.println("Error reading real estate data: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Error loading real estate data: " + ex.getMessage());
        }
        return null;
    }
}

