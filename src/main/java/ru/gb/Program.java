package ru.gb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public class Program {
    private static final Random random = new Random();
    private static final int FILES_AMOUNT = 6;
    private static final int CHAR_RANDOM_L = 65;
    private static final int CHAR_RANDOM_H = 90;

    public static void main(String[] args) throws IOException{
        File testDir = new File("test");
        if(!testDir.exists())
            testDir.mkdirs();
        File testFile0 = new File("./test","file_0.txt");
        if(!testFile0.exists()) {
            String[] fileNames = new String[FILES_AMOUNT];
            for (int i = 0; i < fileNames.length; i++) {
                fileNames[i] = testDir+"/file_" + i + ".txt";
            }
            for (String file : fileNames) {
                writeFilesContent(file, random.nextInt(10, 100));
            }
        }
        backup(testDir.getName(),"backup");
    }

    /**
     * создает строку символов рандомно
     * @param amount размер строки
     * @return строка символов
     */
    public static String generateSymbols (int amount){
        StringBuilder sequenceSimbols = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            char simb = (char)(CHAR_RANDOM_L+random.nextInt(CHAR_RANDOM_H-CHAR_RANDOM_L));
            sequenceSimbols.append(simb);
        }
        return sequenceSimbols.toString();
    }

    /**
     * Записывает строку символов в файл
     * @param filename файл для записи
     * @param length размер строки символов
     * @throws IOException исключение
     */
    private static void writeFilesContent(String filename, int length) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);
        fileWriter.write(generateSymbols(length));
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Резервное сохранение файлов
     * @param dirNameFrom каталог откуда сохраняем
     * @param dirNameTo каталог куда сохраним
     */
    private static void backup(String dirNameFrom, String dirNameTo) throws IOException {
        File dirFrom = new File(dirNameFrom);
        if(!dirFrom.exists()){
            System.out.println("Нет такого каталога");
            return;
        }

        File dirTo = new File(dirNameTo);
        if(!dirTo.exists()){
            dirTo.mkdir();
        }
        File[] files = dirFrom.listFiles();
        for (File file: files) {
            if(file.isDirectory()){
                backup(file.getName(),dirTo.getName());
            }else{
                String fileNewName = createNewName(file.getName());
                Files.copy(Path.of(file.getCanonicalPath()),Path.of(dirNameTo,fileNewName));
            }
        }
    }

    /**
     * Преобразование имени файла с учетом текущей даты и времени создания
     * @param name имя файла
     * @return новой имя файла
     */
    private static String createNewName(String name){
        // 2023-09-15T09:32:14.619880500
        // 2023-09-15T09-32-14
        LocalDateTime localDateTime = LocalDateTime.now();
        String filename = "("+localDateTime.toString()
                .substring(1,19)
                .replace(":","-") + ")"+name;
        return filename;
    }
}
// javadoc -d docs -sourcepath ./src -cp ./target -subpackages ru.gb