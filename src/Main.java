import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress saveFirst = new GameProgress(54,3,44,5.6);
        GameProgress saveSecond = new GameProgress(62,2,46,3.2);
        GameProgress saveThird = new GameProgress(50,1,49,1.0);

        saveGame("D:\\Games\\savegames\\Save1.dat", saveFirst);
        saveGame("D:\\Games\\savegames\\Save2.dat", saveSecond);
        saveGame("D:\\Games\\savegames\\Save3.dat", saveThird);

        zipSave("D:\\Games\\savegames\\savegames.zip",
                "D:\\Games\\savegames\\Save1.dat",
                "D:\\Games\\savegames\\Save2.dat",
                "D:\\Games\\savegames\\Save3.dat");

        delete("D:\\Games\\savegames");

    }

    public static void saveGame(String path, GameProgress save) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(save);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipSave(String path, String...files) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String file : files) {
                FileInputStream fis = new FileInputStream(file);
                ZipEntry entry = new ZipEntry(file.substring(19));
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void delete(String path) {
        File dir = new File(path);

        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (item.isFile()) {
                    if (item.getName().endsWith(".zip")) {
                        continue;
                    }
                    item.delete();
                }
            }
        }
    }
}