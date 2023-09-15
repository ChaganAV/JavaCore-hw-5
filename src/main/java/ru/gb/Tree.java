package ru.gb;

import java.io.File;

public class Tree {
    public static void main(String[] args) {
        printTree(new File("."),"",true);
    }

    /**
     * Печать директорий и файлов в виде дерева
     * @param file файл
     * @param indent символ перехода
     * @param isLast признак последнего элемента
     */
    public static void printTree(File file, String indent, boolean isLast){
        System.out.print(indent);
        if(isLast){
            System.out.print("└-");
            indent += " ";
        }else{
            System.out.print("├-");
            indent += "│ ";
        }
        System.out.println(file.getName());

        File[] files = file.listFiles();
        if(files == null)
            return;

        int subDirTotal = 0;
        int filesTotal = 0;
        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory())
                subDirTotal++;
            else{
                filesTotal++;
            }
        }

        int subDirCounter = 0;
        int filesCounter = 0;
        File[] subFiles = null;
        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()){
                subDirCounter++;
                filesTotal = 0;
                filesCounter = 0;
                printTree(files[i],indent,subDirCounter == subDirTotal);
            }else{
                filesCounter++;
                printTree(files[i], indent, filesCounter == filesTotal);
            }

        }
    }

}
